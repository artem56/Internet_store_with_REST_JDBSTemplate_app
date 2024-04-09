package com.example.application_dev.service;

import com.example.application_dev.Entity.BrandEntity;
import com.example.application_dev.Exeptions.BrandAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BrandService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<BrandEntity> brandRowMapper =  (rs, rowNum) ->
    {
        BrandEntity newbrand = new BrandEntity();
        newbrand.setId(rs.getLong("brand_id"));
        newbrand.setName(rs.getString("name"));
        return newbrand;
    };

    public BrandEntity create (BrandEntity brand) throws BrandAlreadyExistException {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from brands where name = ? limit 1)", Boolean.class , brand.getName()) == Boolean.TRUE)
        {
            throw new BrandAlreadyExistException("Бренд с таким name уже существует");
        }
        jdbcTemplate.update("Insert into brands(name) values (?)", brand.getName());
        return brand;

    }

    public List<BrandEntity> getAll ()
    {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from brands limit 1)", Boolean.class ) == Boolean.FALSE)
        {
            return null;
        }
        return jdbcTemplate.query("Select brand_id, name from brands", brandRowMapper);
    }
}
