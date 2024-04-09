package com.example.application_dev.service;

import com.example.application_dev.Entity.UserEntity;
import com.example.application_dev.Exeptions.UserAlreadyExistExeption;
import com.example.application_dev.Exeptions.UserNotExistExeption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


@Service
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${app.secretKey}")
    private String secretKey;

    private final RowMapper<UserEntity> userRowMapper =  (rs, rowNum) ->
    {
        UserEntity newuser = new UserEntity();
        newuser.setId(rs.getLong("user_id"));
        newuser.setEmail(rs.getString("email"));
        newuser.setPassword(rs.getString("password"));
        newuser.setRole(rs.getString("role"));
        return newuser;
    };

    public String registration (UserEntity user) throws UserAlreadyExistExeption
    {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from users where email = ? limit 1)", Boolean.class , user.getEmail()) == Boolean.TRUE)
        {
            throw new UserAlreadyExistExeption("Пользователь с таким email уже существует");
        }
        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            jdbcTemplate.update("Insert into users(email, password, role) values (?,?,?)", user.getEmail(), hashedPassword, user.getRole());
            return this.generateJwt(user);

    }

    public String login (UserEntity user) throws  UserNotExistExeption {



        if(user.getEmail() == null ||user.getPassword() == null){

            throw new UserNotExistExeption("Не введён логин или пароль");
        }
        if ( jdbcTemplate.queryForObject("select exists (select 1 from users where email = ? limit 1)", Boolean.class , user.getEmail()) == Boolean.FALSE)
        {
            throw new UserNotExistExeption("Пользователя с таким email не существует");
        }

        String hashedPassword = jdbcTemplate.queryForObject("Select user_id, email, password, role from users where email = ?", userRowMapper, user.getEmail()).getPassword();
        String password = user.getPassword();

        if(!BCrypt.checkpw(password, hashedPassword)){
            throw new UserNotExistExeption("Некорректный логин или пароль");
        }


        return this.generateJwt(user);

    }

    public UserEntity getUser (Long User_id) throws UserNotExistExeption
    {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from users where user_id = ? limit 1)", Boolean.class , User_id ) == Boolean.FALSE)
        {
            throw new UserNotExistExeption("Пользователя с таким Id не существует");
        }
        return jdbcTemplate.queryForObject("Select user_id, email, password, role from users where user_id = ?", userRowMapper, User_id);

    }

    public UserEntity verify_token (String token)
    {

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);

        UserEntity newuser = new UserEntity();
        newuser.setEmail(claims.getBody().get("email", String.class));
        newuser.setPassword(claims.getBody().get("password", String.class));
        newuser.setRole(claims.getBody().get("role", String.class));

        return newuser;
    }

    public String generateJwt (UserEntity user) {

        long expirationMillis = 24 * 60 * 60 * 1000; // 24 hours in milliseconds

        // Calculate expiration time
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expirationMillis);

        String token = Jwts.builder()
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .claim("role", user.getRole())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return "{\"jwt\": \"" + token + "\"}"; //token to json format
    }



    public void removeUser (Long User_id) throws  UserNotExistExeption
    {
        if ( jdbcTemplate.queryForObject("select exists (select 1 from users where user_id = ? limit 1)", Boolean.class , User_id ) == Boolean.FALSE)
        {
            throw new UserNotExistExeption("Пользователя с таким Id не существует");
        }
        jdbcTemplate.update("Delete from users where user_id = ?", User_id);

    }

}
