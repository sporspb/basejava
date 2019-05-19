package com.spor.webapp.sql;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeQuery(String query, queryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.doExecuteQuery(ps);
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23505")) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface queryExecutor<T> {
        T doExecuteQuery(PreparedStatement preparedStatement) throws SQLException;
    }
}
