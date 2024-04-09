package com.example.application_dev.controller;

import com.example.application_dev.Entity.TypeEntity;
import com.example.application_dev.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping
    public ResponseEntity createType (@RequestBody TypeEntity type){

        try
        {
            typeService.create(type);
            return ResponseEntity.ok("Тип успешно сохранён");
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("jdbc type insert failed");
        }

    }


    @GetMapping
    public ResponseEntity getAllTypes ()
    {
        try
        {
            List<TypeEntity> types = typeService.getAll();
            if(types == null) {
                return ResponseEntity.ok("[]");
            }
            return ResponseEntity.ok(types);
        }

        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении типов ");
        }
    }
}
