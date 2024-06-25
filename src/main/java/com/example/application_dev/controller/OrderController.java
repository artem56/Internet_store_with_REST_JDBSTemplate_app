package com.example.application_dev.controller;

import com.example.application_dev.Entity.Order;
import com.example.application_dev.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> payload) {
        int userId = (int) payload.get("userId");
        List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
        orderService.createOrder(userId, items);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@RequestParam("userId") int userId) {
        List<Order> orders = orderService.getOrders(userId);
        return ResponseEntity.ok(orders);
    }
}
