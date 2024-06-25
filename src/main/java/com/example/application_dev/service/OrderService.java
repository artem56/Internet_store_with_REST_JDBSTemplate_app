package com.example.application_dev.service;

import com.example.application_dev.Entity.Order;
import com.example.application_dev.Entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createOrder(int userId, List<Map<String, Object>> items) {
        String insertOrder = "INSERT INTO orders (user_user_id, order_date, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertOrder, userId, new Date(), "Pending");

        Integer orderId = jdbcTemplate.queryForObject("SELECT lastval()", Integer.class);
        if (orderId == null) {
            throw new RuntimeException("Failed to retrieve order ID");
        }

        String insertOrderItem = "INSERT INTO order_items (order_order_id, device_device_id, quantity, price) VALUES (?, ?, ?, ?)";
        for (Map<String, Object> item : items) {
            int deviceId = (int) item.get("deviceId");
            int quantity = (int) item.get("quantity");
            Integer price = jdbcTemplate.queryForObject("SELECT price FROM devices WHERE device_id = ?", Integer.class, deviceId);
            if (price == null) {
                throw new RuntimeException("Failed to retrieve price for device ID: " + deviceId);
            }
            jdbcTemplate.update(insertOrderItem, orderId, deviceId, quantity, price);
        }
    }

    public List<Order> getOrders(int userId) {
        String query = "SELECT o.order_id, o.order_date, o.status, oi.device_device_id, d.name, oi.quantity, oi.price " +
                "FROM orders o " +
                "JOIN order_items oi ON o.order_id = oi.order_order_id " +
                "JOIN devices d ON oi.device_device_id = d.device_id " +
                "WHERE o.user_user_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
            Order order = new Order();
            order.setOrderId(rs.getInt("order_id"));
            order.setOrderDate(rs.getDate("order_date")); // Убедимся, что это Date
            order.setStatus(rs.getString("status"));

            OrderItem item = new OrderItem();
            item.setDeviceId(rs.getInt("device_device_id"));
            item.setDeviceName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getInt("price"));

            order.addItem(item);
            return order;
        });
    }
}

