package com.example.application_dev.controller;

import com.example.application_dev.Entity.BrandEntity;
//import com.example.application_dev.service.BrandService;
import com.example.application_dev.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity createBrand (@RequestBody BrandEntity brand){

        try
        {
            brandService.create(brand);
            return ResponseEntity.ok("Бренд успешно сохранён");
        }
        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("jdbc brand insert failed");
        }

    }


    @GetMapping
    public ResponseEntity getAllBrands ()
    {
        try
        {
            List<BrandEntity> brands = brandService.getAll();
            if(brands == null) {
                return ResponseEntity.ok("[]");
            }
            return ResponseEntity.ok(brands);
        }

        catch(Exception e)
        {
            return ResponseEntity.badRequest().body("Произошла ошибка при получении брендов ");
        }
    }


}



