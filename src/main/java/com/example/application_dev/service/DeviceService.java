package com.example.application_dev.service;

import com.example.application_dev.Entity.DeviceEntity;
import com.example.application_dev.Exeptions.DeviceAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private final RowMapper<DeviceEntity> deviceRowMapper =  (rs, rowNum) ->
    {
        DeviceEntity newdevice = new DeviceEntity();
        newdevice.setId(rs.getLong("device_id"));
        newdevice.setName(rs.getString("name"));
        newdevice.setBrand_brand_id(rs.getInt("brand_brand_id"));
        newdevice.setType_type_id(rs.getInt("type_type_id"));
        newdevice.setPrice(rs.getInt("price"));
        newdevice.setRating(rs.getInt("rating"));
        newdevice.setImg(rs.getString("img"));
        newdevice.setInfo(rs.getString("info"));
        return newdevice;
    };

    public DeviceEntity create (DeviceEntity device) throws DeviceAlreadyExistException {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from devices where name = ? limit 1)", Boolean.class , device.getName()) == Boolean.TRUE)
        {
            throw new DeviceAlreadyExistException("Device с таким name уже существует");
        }
        jdbcTemplate.update("Insert into devices(name, brand_brand_id, type_type_id, price, rating, img, info) values (?,?,?,?,?,?,?)", device.getName(), device.getBrand_brand_id(), device.getType_type_id(), device.getPrice(), device.getRating(), device.getImg(), device.getInfo());
        return device;

    }

    public String getAll () throws Exception {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from devices limit 1)", Boolean.class ) == Boolean.FALSE)
        {
            return "{\"count\":0,\"rows\":[]}";
        }
        int count = jdbcTemplate.queryForObject("Select count(*) from devices",Integer.class);
        List<DeviceEntity> devices = jdbcTemplate.query("Select device_id, name, brand_brand_id, type_type_id, price, rating, img, info from devices", deviceRowMapper);
        String jsonResult = null;
        try {
            jsonResult = objectMapper.writeValueAsString(devices);
        } catch (Exception e) {
            throw new Exception("не удалось преобразовать в json");
        }
        return "{\"count\":" + count + ",\"rows\":" + jsonResult + "}";
    }

    public String getById (long device_id) throws Exception {

        if ( jdbcTemplate.queryForObject("select exists (select 1 from devices where device_id = ? limit 1)", Boolean.class, device_id ) == Boolean.FALSE)
        {
            return null;
        }
        DeviceEntity device = jdbcTemplate.queryForObject("Select device_id, name, price, rating, img, brand_brand_id, type_type_id, info from devices where device_id = ?", deviceRowMapper, device_id);
        String jsonResult = null;
        try {
            jsonResult = objectMapper.writeValueAsString(device);
            //Убираем escape символ "\"
            jsonResult = jsonResult.replace("\\","");
            jsonResult = jsonResult.replace("type_type_id","typeId");
            jsonResult = jsonResult.replace("brand_brand_id","brandId");
            jsonResult = jsonResult.replace("\"[{","[{");
            jsonResult = jsonResult.replace("}]\"","}]");
        } catch (Exception e) {
            throw new Exception("не удалось преобразовать в json");
        }
        return jsonResult;
    }
}
