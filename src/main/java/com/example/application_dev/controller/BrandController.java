package com.example.application_dev.controller;

import com.example.application_dev.Entity.BrandEntity;
import com.example.application_dev.service.BrandService;
import com.example.application_dev.service.UserService;
import com.example.application_dev.Exeptions.BrandHasProductsException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createBrand(@RequestBody BrandEntity brand, @RequestHeader("Authorization") String authorizationHeader) {
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
            brandService.create(brand);
            return ResponseEntity.ok("Бренд успешно сохранён");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("jdbc brand insert failed");
        }
    }

    @GetMapping
    public ResponseEntity getAllBrands() {
        try {
            List<BrandEntity> brands = brandService.getAll();
            if (brands == null) {
                return ResponseEntity.ok("[]");
            }
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении брендов");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBrand(@PathVariable Long id, @RequestBody BrandEntity brand, @RequestHeader("Authorization") String authorizationHeader) {
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
            brand.setId(id);
            brandService.update(brand);
            return ResponseEntity.ok("Бренд успешно обновлён");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при обновлении бренда");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBrand(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
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
            brandService.delete(id);
            return ResponseEntity.ok("Бренд успешно удалён");
        } catch (BrandHasProductsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Бренд не может быть удален, так как для него существуют продукты");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении бренда");
        }
    }
}
