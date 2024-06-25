package com.example.application_dev.service;

import com.example.application_dev.Entity.BrandEntity;
import com.example.application_dev.Exeptions.BrandAlreadyExistException;
import com.example.application_dev.Exeptions.BrandNotFoundException;
import com.example.application_dev.Exeptions.BrandHasProductsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<BrandEntity> brandRowMapper = (rs, rowNum) -> {
        BrandEntity newbrand = new BrandEntity();
        newbrand.setId(rs.getLong("brand_id"));
        newbrand.setName(rs.getString("name"));
        return newbrand;
    };

    public BrandEntity create(BrandEntity brand) throws BrandAlreadyExistException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from brands where name = ? limit 1)", Boolean.class, brand.getName()) == Boolean.TRUE) {
            throw new BrandAlreadyExistException("Бренд с таким name уже существует");
        }
        jdbcTemplate.update("Insert into brands(name) values (?)", brand.getName());
        return brand;
    }

    public List<BrandEntity> getAll() {
        if (jdbcTemplate.queryForObject("select exists (select 1 from brands limit 1)", Boolean.class) == Boolean.FALSE) {
            return null;
        }
        return jdbcTemplate.query("Select brand_id, name from brands", brandRowMapper);
    }

    public BrandEntity update(BrandEntity brand) throws BrandNotFoundException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from brands where brand_id = ? limit 1)", Boolean.class, brand.getId()) == Boolean.FALSE) {
            throw new BrandNotFoundException("Бренд с таким ID не найден");
        }
        jdbcTemplate.update("Update brands set name = ? where brand_id = ?", brand.getName(), brand.getId());
        return brand;
    }

    public void delete(Long id) throws BrandNotFoundException, BrandHasProductsException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from brands where brand_id = ? limit 1)", Boolean.class, id) == Boolean.FALSE) {
            throw new BrandNotFoundException("Бренд с таким ID не найден");
        }

        // Проверка наличия продуктов для данного бренда
        if (jdbcTemplate.queryForObject("select exists (select 1 from devices where brand_brand_id = ? limit 1)", Boolean.class, id) == Boolean.TRUE) {
            throw new BrandHasProductsException("Бренд не может быть удален, так как для него существуют продукты");
        }

        jdbcTemplate.update("Delete from brands where brand_id = ?", id);
    }
}
