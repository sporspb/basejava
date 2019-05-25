package com.spor.webapp.storage;

import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.ContactType;
import com.spor.webapp.model.Link;
import com.spor.webapp.model.Resume;
import com.spor.webapp.sql.SqlHelper;

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
        return sqlHelper.executeQuery("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String typeString = rs.getString("type");
                        if (typeString != null) {
                            String value = rs.getString("value");
                            ContactType type = ContactType.valueOf(typeString);
                            r.setContacts(type, new Link(value));
                        }
                    }
                    while (rs.next());
                    return r;
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
            try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.execute();
            }
            insertContacts(r, conn);
            return null;
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
            return null;
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
    public List<Resume> getAllSorted() {
        LOG.info("GetAll");
        Map<String, Resume> resumeMap = new LinkedHashMap<>();
        sqlHelper.executeQuery("SELECT * FROM resume ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        String fullName = rs.getString("full_name").trim();
                        resumeMap.computeIfAbsent(uuid, s -> new Resume(uuid, fullName));
                    }
                    return null;
                });

        sqlHelper.executeQuery("SELECT resume_uuid, type, value FROM contact",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String resumeUuid = rs.getString("resume_uuid");
                        String type = rs.getString("type").trim();
                        String value = rs.getString("value").trim();
                        Resume resume = resumeMap.get(resumeUuid);
                        resume.setContacts(ContactType.valueOf(type), new Link(value));
                    }
                    return null;
                });
        return new ArrayList<>(resumeMap.values());
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

    private void checkNotExist(PreparedStatement ps, String uuid) throws SQLException {
        int rs = ps.executeUpdate();
        if (rs == 0) {
            throw new NotExistStorageException(uuid);
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
}
