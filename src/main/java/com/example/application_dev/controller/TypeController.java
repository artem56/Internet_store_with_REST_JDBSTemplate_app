package com.example.application_dev.controller;

import com.example.application_dev.Entity.TypeEntity;
import com.example.application_dev.Exeptions.TypeHasProductsException;
import com.example.application_dev.service.TypeService;
import com.example.application_dev.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createType(@RequestBody TypeEntity type, @RequestHeader("Authorization") String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        String token = null;
        if (parts.length == 2) {
            token = parts[1];
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("auth token is null");
        }
        try {
            userService.verify_token(token);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

        try {
            typeService.create(type);
            return ResponseEntity.ok("Тип успешно сохранён");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("jdbc type insert failed");
        }
    }

    @GetMapping
    public ResponseEntity getAllTypes() {
        try {
            List<TypeEntity> types = typeService.getAll();
            if (types == null) {
                return ResponseEntity.ok("[]");
            }
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении типов");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateType(@PathVariable Long id, @RequestBody TypeEntity type, @RequestHeader("Authorization") String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        String token = null;
        if (parts.length == 2) {
            token = parts[1];
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("auth token is null");
        }
        try {
            userService.verify_token(token);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

        try {
            type.setId(id);
            typeService.update(type);
            return ResponseEntity.ok("Тип успешно обновлён");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при обновлении типа");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteType(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String[] parts = authorizationHeader.split(" ");
        String token = null;
        if (parts.length == 2) {
            token = parts[1];
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("auth token is null");
        }
        try {
            userService.verify_token(token);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }

        try {
            typeService.delete(id);
            return ResponseEntity.ok("Тип успешно удалён");
        } catch (TypeHasProductsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Тип не может быть удален, так как для него существуют продукты");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении типа");
        }
    }
}
