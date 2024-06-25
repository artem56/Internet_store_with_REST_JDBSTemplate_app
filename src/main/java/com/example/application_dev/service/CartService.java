package com.example.application_dev.service;

import com.example.application_dev.Entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void addToCart(int userId, int deviceId, int quantity) {
        String findCartQuery = "SELECT cart_id FROM carts WHERE user_user_id = ?";
        Integer cartId;
        try {
            cartId = jdbcTemplate.queryForObject(findCartQuery, new Object[]{userId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            cartId = null;
        }

        if (cartId == null) {
            String createCartQuery = "INSERT INTO carts (user_user_id) VALUES (?) RETURNING cart_id";
            cartId = jdbcTemplate.queryForObject(createCartQuery, new Object[]{userId}, Integer.class);
        }

        String findCartItemQuery = "SELECT cart_item_id, quantity FROM cart_items WHERE cart_cart_id = ? AND device_device_id = ?";
        List<CartItem> cartItems = jdbcTemplate.query(findCartItemQuery, new Object[]{cartId, deviceId}, (rs, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(rs.getInt("cart_item_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            return cartItem;
        });

        if (cartItems.isEmpty()) {
            String addCartItemQuery = "INSERT INTO cart_items (cart_cart_id, device_device_id, quantity) VALUES (?, ?, ?)";
            jdbcTemplate.update(addCartItemQuery, cartId, deviceId, quantity);
        } else {
            CartItem existingCartItem = cartItems.get(0);
            int newQuantity = existingCartItem.getQuantity() + quantity;
            String updateCartItemQuery = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";
            jdbcTemplate.update(updateCartItemQuery, newQuantity, existingCartItem.getId());
        }
    }

    public List<CartItem> getCartItems(int userId) {
        String query = "SELECT ci.cart_item_id, ci.device_device_id, ci.quantity, d.name AS device_name, d.info AS device_info " +
                "FROM cart_items ci " +
                "JOIN carts c ON ci.cart_cart_id = c.cart_id " +
                "JOIN devices d ON ci.device_device_id = d.device_id " +
                "WHERE c.user_user_id = ?";
        List<CartItem> cartItems = jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
            CartItem cartItem = new CartItem();
            cartItem.setId(rs.getInt("cart_item_id"));
            cartItem.setDeviceId(rs.getInt("device_device_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setDeviceName(rs.getString("device_name"));
            cartItem.setDeviceInfo(rs.getString("device_info"));
            return cartItem;
        });


        return cartItems;
    }

    public void removeFromCart(int userId, int deviceId) {
        String findCartQuery = "SELECT cart_id FROM carts WHERE user_user_id = ?";
        Integer cartId;
        try {
            cartId = jdbcTemplate.queryForObject(findCartQuery, new Object[]{userId}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            cartId = null;
        }

        if (cartId != null) {
            String deleteCartItemQuery = "DELETE FROM cart_items WHERE cart_cart_id = ? AND device_device_id = ?";
            jdbcTemplate.update(deleteCartItemQuery, cartId, deviceId);
        }
    }

    public void updateCartItem(int userId, int deviceId, int quantity) {
        String query = "UPDATE cart_items SET quantity = ? WHERE cart_cart_id = (SELECT cart_id FROM carts WHERE user_user_id = ?) AND device_device_id = ?";
        jdbcTemplate.update(query, quantity, userId, deviceId);
    }
    public void clearCart(Long userId) {
        jdbcTemplate.update("DELETE FROM cart_items WHERE cart_cart_id = (SELECT cart_id FROM carts WHERE user_user_id = ?)", userId);
    }

}



