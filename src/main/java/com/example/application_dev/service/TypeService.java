package com.example.application_dev.service;

import com.example.application_dev.Entity.TypeEntity;
import com.example.application_dev.Exeptions.TypeAlreadyExistException;
import com.example.application_dev.Exeptions.TypeNotFoundException;
import com.example.application_dev.Exeptions.TypeHasProductsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<TypeEntity> typeRowMapper = (rs, rowNum) -> {
        TypeEntity newtype = new TypeEntity();
        newtype.setId(rs.getLong("type_id"));
        newtype.setName(rs.getString("name"));
        return newtype;
    };

    public TypeEntity create(TypeEntity type) throws TypeAlreadyExistException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from types where name = ? limit 1)", Boolean.class, type.getName()) == Boolean.TRUE) {
            throw new TypeAlreadyExistException("Тип с таким name уже существует");
        }
        jdbcTemplate.update("Insert into types(name) values (?)", type.getName());
        return type;
    }

    public List<TypeEntity> getAll() {
        if (jdbcTemplate.queryForObject("select exists (select 1 from types limit 1)", Boolean.class) == Boolean.FALSE) {
            return null;
        }
        return jdbcTemplate.query("Select type_id, name from types", typeRowMapper);
    }

    public TypeEntity update(TypeEntity type) throws TypeNotFoundException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from types where type_id = ? limit 1)", Boolean.class, type.getId()) == Boolean.FALSE) {
            throw new TypeNotFoundException("Тип с таким ID не найден");
        }
        jdbcTemplate.update("Update types set name = ? where type_id = ?", type.getName(), type.getId());
        return type;
    }

    public void delete(Long id) throws TypeNotFoundException, TypeHasProductsException {
        if (jdbcTemplate.queryForObject("select exists (select 1 from types where type_id = ? limit 1)", Boolean.class, id) == Boolean.FALSE) {
            throw new TypeNotFoundException("Тип с таким ID не найден");
        }

        // Проверка наличия продуктов для данного типа
        if (jdbcTemplate.queryForObject("select exists (select 1 from devices where type_type_id = ? limit 1)", Boolean.class, id) == Boolean.TRUE) {
            throw new TypeHasProductsException("Тип не может быть удален, так как для него существуют продукты");
        }

        jdbcTemplate.update("Delete from types where type_id = ?", id);
    }
}
