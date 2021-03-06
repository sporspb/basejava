package com.spor.webapp.storage;

import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.*;
import com.spor.webapp.sql.SqlHelper;
import com.spor.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.sql.DriverManager.getConnection;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear storage");
        sqlHelper.executeQuery("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT uuid, full_name, c.type, c.value FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     WHERE r.uuid = ? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(resume, rs);
                } while (rs.next());
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT type, value FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            checkNotExist(ps, uuid);
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r.getUuid());
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                checkNotExist(ps, r.getUuid());
            }
            deleteFromTable(conn, r.getUuid(), "DELETE FROM contact WHERE resume_uuid = ?");
            deleteFromTable(conn, r.getUuid(), "DELETE FROM section WHERE resume_uuid = ?");
            insertContacts(r, conn);
            insertSection(r, conn);
            return null;
        });
    }

    @Override
    public int size() {
        LOG.info("Get storage size");
        return sqlHelper.executeQuery("SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("Count error");
            }
            return rs.getInt(1);
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r.getUuid());
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, conn);
            insertSection(r, conn);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAll");
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString(1).trim();
                    String full_name = rs.getString(2);
                    resumeMap.computeIfAbsent(uuid, k -> new Resume(k, full_name));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid, type, value FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid").trim();
                    Resume resume = resumeMap.get(uuid);
                    addContact(resume, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT resume_uuid, type, value FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid").trim();
                    Resume resume = resumeMap.get(uuid);
                    addSection(resume, rs);
                }
            }
            return new ArrayList<>(resumeMap.values());
        });
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                AbstractSection section = e.getValue();
                ps.setString(3, JsonParser.write(section, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
            resume.setSections(sectionType, JsonParser.read(value, AbstractSection.class));
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, Link> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue().getName());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.setContacts(ContactType.valueOf(rs.getString("type")), new Link(value));
        }
    }

    private void deleteFromTable(Connection conn, String uuid, String query) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
        }
    }

    private void checkNotExist(PreparedStatement ps, String uuid) throws SQLException {
        int rs = ps.executeUpdate();
        if (rs == 0) {
            throw new NotExistStorageException(uuid);
        }
    }
}
