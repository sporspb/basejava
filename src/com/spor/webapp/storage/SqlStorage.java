package com.spor.webapp.storage;

import com.spor.webapp.exception.NotExistStorageException;
import com.spor.webapp.exception.StorageException;
import com.spor.webapp.model.Resume;
import com.spor.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        return sqlHelper.executeQuery("SELECT * FROM resume r WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r.getUuid());
        sqlHelper.executeQuery("UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());
            checkNotExist(preparedStatement, r.getUuid());
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r.getUuid());
        sqlHelper.executeQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)", preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.executeQuery("DELETE FROM resume WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            checkNotExist(preparedStatement, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAll");
        List<Resume> list = new ArrayList<>();
        return sqlHelper.executeQuery("SELECT * FROM resume ORDER BY uuid, full_name", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public int size() {
        LOG.info("Get storage size");
        return sqlHelper.executeQuery("SELECT COUNT (*) FROM resume", preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
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
}
