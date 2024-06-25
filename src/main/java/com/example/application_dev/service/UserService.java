package com.example.application_dev.service;

import com.example.application_dev.Entity.UserData;
import com.example.application_dev.Entity.UserEntity;
import com.example.application_dev.Exeptions.UserAlreadyExistExeption;
import com.example.application_dev.Exeptions.UserNotExistExeption;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${app.secretKey}")
    private String secretKey;

    private final RowMapper<UserEntity> userRowMapper = (rs, rowNum) -> {
        UserEntity newuser = new UserEntity();
        newuser.setId(rs.getLong("user_id"));
        newuser.setEmail(rs.getString("email"));
        newuser.setPassword(rs.getString("password"));
        newuser.setRole(rs.getString("role"));
        return newuser;
    };

    public String registration(UserEntity user) throws UserAlreadyExistExeption {
        if (jdbcTemplate.queryForObject("select exists (select 1 from users where email = ? limit 1)", Boolean.class, user.getEmail()) == Boolean.TRUE) {
            throw new UserAlreadyExistExeption("Пользователь с таким email уже существует");
        }
        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        jdbcTemplate.update("Insert into users(email, password, role) values (?,?,?)", user.getEmail(), hashedPassword, user.getRole());
        user.setPassword(hashedPassword);
        return this.generateJwt(user);
    }

    public String login(UserEntity user) throws UserNotExistExeption {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new UserNotExistExeption("Не введён логин или пароль");
        }
        if (jdbcTemplate.queryForObject("select exists (select 1 from users where email = ? limit 1)", Boolean.class, user.getEmail()) == Boolean.FALSE) {
            throw new UserNotExistExeption("Пользователя с таким email не существует");
        }

        UserEntity dbUser = jdbcTemplate.queryForObject("Select user_id, email, password, role from users where email = ?", userRowMapper, user.getEmail());
        String hashedPassword = dbUser.getPassword();
        String password = user.getPassword();

        if (!BCrypt.checkpw(password, hashedPassword)) {
            throw new UserNotExistExeption("Некорректный логин или пароль");
        }

        dbUser.setPassword(hashedPassword); // Убедитесь, что хешированный пароль используется для генерации токена
        return this.generateJwt(dbUser);
    }

    public UserEntity getUser(Long user_id) throws UserNotExistExeption {
        if (jdbcTemplate.queryForObject("select exists (select 1 from users where user_id = ? limit 1)", Boolean.class, user_id) == Boolean.FALSE) {
            throw new UserNotExistExeption("Пользователя с таким Id не существует");
        }
        return jdbcTemplate.queryForObject("Select user_id, email, password, role from users where user_id = ?", userRowMapper, user_id);
    }

    public String generateJwt(UserEntity user) {
        long expirationMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        // Calculate expiration time
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationMillis);

        String token = Jwts.builder()
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return "{\"jwt\": \"" + token + "\"}"; // token to json format
    }

    public UserEntity verify_token(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            UserEntity newuser = new UserEntity();
            newuser.setId(claims.getBody().get("userId", Long.class));
            newuser.setEmail(claims.getBody().get("email", String.class));
            newuser.setRole(claims.getBody().get("role", String.class));

            return newuser;
        } catch (JwtException e) {
            // Handle JWT related exceptions
            throw new JwtException("Invalid token", e); // Example: Rethrow exception with custom message
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("An error occurred while verifying the token", e); // Example: Rethrow exception with custom message
        }
    }

    public void removeUser(Long user_id) throws UserNotExistExeption {
        if (jdbcTemplate.queryForObject("select exists (select 1 from users where user_id = ? limit 1)", Boolean.class, user_id) == Boolean.FALSE) {
            throw new UserNotExistExeption("Пользователя с таким Id не существует");
        }
        jdbcTemplate.update("Delete from users where user_id = ?", user_id);
    }

    public UserData getUserData(int userId) {
        String query = "SELECT phone_number, name, address FROM user_data WHERE user_user_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{userId}, (rs, rowNum) -> {
            UserData userData = new UserData();
            userData.setPhoneNumber(rs.getString("phone_number"));
            userData.setName(rs.getString("name"));
            userData.setAddress(rs.getString("address"));
            return userData;
        });
    }

    public void updateUserData(int userId, String phoneNumber, String name, String address) {
        String query = "INSERT INTO user_data (user_user_id, phone_number, name, address) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (user_user_id) DO UPDATE SET phone_number = EXCLUDED.phone_number, name = EXCLUDED.name, address = EXCLUDED.address";
        jdbcTemplate.update(query, userId, phoneNumber, name, address);
    }
}
