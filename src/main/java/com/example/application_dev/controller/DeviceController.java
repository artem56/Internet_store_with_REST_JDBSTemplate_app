package com.example.application_dev.controller;

import com.example.application_dev.Entity.DeviceEntity;
import com.example.application_dev.Exeptions.DeviceAlreadyExistException;
import com.example.application_dev.Exeptions.DeviceNotFoundException;
import com.example.application_dev.service.DeviceService;
import com.example.application_dev.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Value("${uploadDirectoryPath}")
    private String uploadDirectoryPath;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<String> createDevice(@RequestPart("name") String name,
                                               @RequestPart("price") String price,
                                               @RequestPart("img") MultipartFile img,
                                               @RequestPart("typeId") String typeId,
                                               @RequestPart("brandId") String brandId,
                                               @RequestPart("info") String info,
                                               @RequestHeader("Authorization") String authorizationHeader) {
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

        DeviceEntity device = new DeviceEntity();
        device.setName(name);
        device.setBrand_brand_id(Long.parseLong(brandId));
        device.setType_type_id(Long.parseLong(typeId));
        device.setPrice(Integer.parseInt(price));
        device.setRating(0);
        device.setInfo(info);

        try {
            String filename = UUID.randomUUID().toString() + ".jpg";
            String uploadDirectory = uploadDirectoryPath;
            try {
                Path directoryPath = Paths.get(uploadDirectory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                Path filePath = directoryPath.resolve(filename);
                Files.copy(img.getInputStream(), filePath);

                device.setImg(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }

            deviceService.create(device);
            return ResponseEntity.ok("device успешно сохранён");
        } catch (DeviceAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("jdbc device insert failed");
        }
    }

    @GetMapping
    public ResponseEntity getAllDevices() {
        try {
            String devices = deviceService.getAll();
            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении devices");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getDeviceById(@PathVariable Long id) {
        try {
            String device = deviceService.getById(id);
            return ResponseEntity.ok(device);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении device с id " + id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateDevice(@PathVariable Long id,
                                       @RequestPart("name") String name,
                                       @RequestPart("price") String price,
                                       @RequestPart("img") MultipartFile img,
                                       @RequestPart("typeId") String typeId,
                                       @RequestPart("brandId") String brandId,
                                       @RequestPart("info") String info,
                                       @RequestHeader("Authorization") String authorizationHeader) {
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

        DeviceEntity device = new DeviceEntity();
        device.setId(id);
        device.setName(name);
        device.setBrand_brand_id(Long.parseLong(brandId));
        device.setType_type_id(Long.parseLong(typeId));
        device.setPrice(Integer.parseInt(price));
        device.setRating(0);
        device.setInfo(info);

        try {
            String filename = UUID.randomUUID().toString() + ".jpg";
            String uploadDirectory = uploadDirectoryPath;
            try {
                Path directoryPath = Paths.get(uploadDirectory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                Path filePath = directoryPath.resolve(filename);
                Files.copy(img.getInputStream(), filePath);

                device.setImg(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }

            deviceService.update(device);
            return ResponseEntity.ok("device успешно обновлён");
        } catch (DeviceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("jdbc device update failed");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteDevice(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
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
            deviceService.delete(id);
            return ResponseEntity.ok("device успешно удалён");
        } catch (DeviceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("jdbc device delete failed");
        }
    }
}
