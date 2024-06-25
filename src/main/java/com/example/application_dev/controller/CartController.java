package com.example.application_dev.controller;

import com.example.application_dev.Entity.CartItem;
import com.example.application_dev.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam("userId") int userId, @RequestParam("deviceId") int deviceId, @RequestParam("quantity") int quantity) {
        cartService.addToCart(userId, deviceId, quantity);
        return ResponseEntity.ok("Device added to cart");
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam("userId") int userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestParam("userId") int userId, @RequestParam("deviceId") int deviceId) {
        cartService.removeFromCart(userId, deviceId);
        return ResponseEntity.ok("Device removed from cart");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestParam("userId") int userId, @RequestParam("deviceId") int deviceId, @RequestParam("quantity") int quantity) {
        cartService.updateCartItem(userId, deviceId, quantity);
        return ResponseEntity.ok("Device quantity updated");
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam Long userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok("Cart cleared");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clear cart");
        }
    }
}


