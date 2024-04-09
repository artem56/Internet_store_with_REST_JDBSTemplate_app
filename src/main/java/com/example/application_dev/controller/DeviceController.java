package com.example.application_dev.controller;

import com.example.application_dev.Entity.DeviceEntity;
import com.example.application_dev.Exeptions.DeviceAlreadyExistException;
import com.example.application_dev.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping( method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public ResponseEntity<String> createDevice (  @RequestPart("name") String name,
                                                  @RequestPart("price") String price,
                                                  @RequestPart("img") MultipartFile img,
                                                  @RequestPart("typeId") String typeId,
                                                  @RequestPart("brandId") String brandId,
                                                  @RequestPart("info") String info
    ){

        DeviceEntity device = new DeviceEntity();

//        //Получаем поля устройства и файл
        device.setName(name);
        device.setBrand_brand_id(Long.parseLong(brandId));
        device.setType_type_id(Long.parseLong(typeId));
        device.setPrice(Integer.parseInt(price));
        device.setRating(0);
        device.setInfo(info);


        try
        {
            //Сохраняем файл под рандомным названием, путь до файла пишем в бд
            String filename = UUID.randomUUID().toString() + ".jpg";
            String uploadDirectory = "C:\\Users\\Admin\\my_projects\\internet_store_back_on_java_jdbs\\src\\main\\resources\\static";
            try {
                // Create directory if it doesn't exist
                Path directoryPath = Paths.get(uploadDirectory);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }

                // Construct file path and copy the file
                Path filePath = directoryPath.resolve(filename);
                Files.copy(img.getInputStream(), filePath);

                // Set the file path in the device object
                device.setImg(filename);
            } catch (IOException e) {
                // Handle any IO exception
                e.printStackTrace();
            }

            deviceService.create(device);
            return ResponseEntity.ok("device успешно сохранён");
        }
        catch(DeviceAlreadyExistException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("jdbc device insert failed");
        }

    }

    @GetMapping
    public ResponseEntity getAllDevices ()
    {
        try
        {
            String devices = deviceService.getAll();
            return ResponseEntity.ok(devices);
        }

        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении devices ");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getDeviceById (@PathVariable Long id)
    {
        try
        {
            String device = deviceService.getById(id);
            return ResponseEntity.ok(device);
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении device с id " + id);
        }
    }
}
