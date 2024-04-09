package com.example.application_dev.controller;

import com.example.application_dev.Entity.UserEntity;
import com.example.application_dev.Exeptions.UserAlreadyExistExeption;
import com.example.application_dev.Exeptions.UserNotExistExeption;
import com.example.application_dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration")

    public ResponseEntity registration (@RequestBody UserEntity user){

        try
        {

            return ResponseEntity.ok(userService.registration(user));
        }
        catch(UserAlreadyExistExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("jdbc user insert failed");
        }

    }

    //@PostMapping("/login")
    @RequestMapping(value = "/login")
    public ResponseEntity login (@RequestBody UserEntity user){

        try
        {
            return ResponseEntity.ok(userService.login(user));
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("login failed");
        }

    }

    //@PostMapping("/auth")
    @RequestMapping(value = "/auth")
    public ResponseEntity auth (@RequestHeader("Authorization") String authorizationHeader){

        String[] parts = authorizationHeader.split(" ");
        String token = null;
        if (parts.length == 2) {
            token = parts[1];
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("auth token is null");
        }

        try
        {
            UserEntity newuser = userService.verify_token(token);
            return ResponseEntity.ok(userService.generateJwt(newuser));
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("auth failed");
        }

    }


       @GetMapping
    public ResponseEntity getUser(@RequestParam Long user_id)
    {
       try
       {
         UserEntity user = userService.getUser(user_id);
        return ResponseEntity.ok(user);
       }
       catch(UserNotExistExeption e)
       {
           return ResponseEntity.badRequest().body(e.getMessage());
       }
       catch(Exception e)
       {
        return ResponseEntity.badRequest().body("Произошла ошибка при получении пользователя с id " + user_id);
       }
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestParam Long user_id)
    {
        try
        {
            userService.removeUser(user_id);
            return ResponseEntity.ok("Пользователь с id " + user_id + " успешно удалён");
        }
        catch(UserNotExistExeption e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении пользователя с id " + user_id);
        }
    }
    }