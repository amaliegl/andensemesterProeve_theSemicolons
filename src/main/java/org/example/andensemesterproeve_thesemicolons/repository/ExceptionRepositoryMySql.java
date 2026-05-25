package org.example.andensemesterproeve_thesemicolons.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class ExceptionRepositoryMySql {

    private final JdbcTemplate jdbcTemplate;

    public ExceptionRepositoryMySql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logExceptionInDatabase(Exception exception) {
        String sql = "INSERT INTO exception_log (exception, message, stackTrace) VALUES (?,?,?)";

        if (Arrays.toString(exception.getStackTrace()).length() > 1000) { //stacktrace column has a max length of 1000
            jdbcTemplate.update(sql, exception.getClass().getName(), exception.getMessage(),
                    Arrays.toString(exception.getStackTrace()).substring(0,1000));
        } else {
            jdbcTemplate.update(sql, exception.getClass().getName(), exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        }
    }
}
