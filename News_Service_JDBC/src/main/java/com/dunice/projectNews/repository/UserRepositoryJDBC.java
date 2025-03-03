package com.dunice.projectNews.repository;

import com.dunice.projectNews.exception.CustomException;
import com.dunice.projectNews.models.User;
import com.dunice.projectNews.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserRepositoryJDBC {

    private final JdbcTemplate jdbcTemplate;

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getObject("users_id", UUID.class));
        user.setAvatar(rs.getString("avatar"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        return user;
    }

    public void checkEmail(String email) {
        String checkEmailQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(checkEmailQuery, Integer.class, email);
        if (count > 0) {
            throw new CustomException(ServerErrorCodes.USER_ALREADY_EXISTS);
        }
    }

    public void saveUser(User user) {
        String insertUserQuery = "INSERT INTO users (avatar,email, password, name,role) VALUES (?, ?, ?,?,?)";
        jdbcTemplate.update(insertUserQuery, user.getAvatar(), user.getEmail(), user.getPassword(), user.getName(), user.getRole());
    }

    public Optional<User> findByEmail(String email) {
        String getUserByEmailQuery = "SELECT users_id, avatar, email, password, name, role FROM users WHERE email = ?";
        User user = jdbcTemplate.queryForObject(getUserByEmailQuery, this::mapRowToUser, email);
        return Optional.ofNullable(user);
    }

    public List<User> getAllUser() {
        String getUsersQuery = "SELECT users_id, avatar, email, password, name, role FROM users";
        return jdbcTemplate.query(getUsersQuery, this::mapRowToUser);
    }

    public void deleteUserByEmail(String email) {
        String deleteQuery = "DELETE FROM users WHERE email = ?";
        jdbcTemplate.update(deleteQuery, email);
    }

    public Optional<User> findById(UUID id) {
        String getUserByIdQuery = "SELECT users_id,avatar,email,password, name,role FROM users WHERE users_id = ?";
        User user = jdbcTemplate.queryForObject(getUserByIdQuery, this::mapRowToUser, id);
        return Optional.of(user);
    }
}