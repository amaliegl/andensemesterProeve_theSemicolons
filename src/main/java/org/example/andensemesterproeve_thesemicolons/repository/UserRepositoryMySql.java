package org.example.andensemesterproeve_thesemicolons.repository;

import org.example.andensemesterproeve_thesemicolons.domain.Role_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryMySql implements IUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryMySql(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAllUsers() {
        String sql = """
                SELECT
                user_id,
                user_name,
                user_email,
                user_role,
                user_phone
                FROM users
                """;
        //Intentionally selecting specific columns to avoid fetching passwords

        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("id")
                ));

        return users;
    }
}
