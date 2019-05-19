package com.spor.webapp.sql;

import com.spor.webapp.exception.ExistStorageException;
import com.spor.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private static ConnectionFactory connectionFactory;

    public static void getConnection(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static void execute(String command, executor executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(command)) {
            executor.doExecute(ps);
        } catch (SQLException e) {
            if (e instanceof PSQLException) {
                System.out.println(e.getMessage());
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }

    public static <T> T executeQuery(String query, queryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.doExecuteQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface queryExecutor<T> {
        T doExecuteQuery(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    public interface executor {
        void doExecute(PreparedStatement preparedStatement) throws SQLException;
    }
}
