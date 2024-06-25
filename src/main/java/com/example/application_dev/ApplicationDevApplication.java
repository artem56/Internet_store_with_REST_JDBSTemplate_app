package com.example.application_dev;

import jakarta.servlet.MultipartConfigElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ApplicationDevApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(com.example.application_dev.ApplicationDevApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApplicationDevApplication.class, args);
	}




	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public void run(String... strings) throws Exception {

		log.info("Creating tables");



		//log.info("Drop table users, brands, types, devices");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, email VARCHAR(35), password VARCHAR(100), role VARCHAR(35))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS brands (brand_id SERIAL PRIMARY KEY, name VARCHAR(35))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS types (type_id SERIAL PRIMARY KEY, name VARCHAR(35))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS devices (device_id SERIAL PRIMARY KEY, name VARCHAR(35), brand_brand_id INTEGER REFERENCES brands (brand_id), type_type_id INTEGER REFERENCES types (type_id), price INTEGER, rating INTEGER, img VARCHAR(200), info VARCHAR(200))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user_data (user_user_id INTEGER REFERENCES users (user_id), phone_number VARCHAR(15), name VARCHAR(35), address VARCHAR(100), CONSTRAINT unique_user_user_id UNIQUE (user_user_id))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS orders (order_id SERIAL PRIMARY KEY, user_user_id INTEGER REFERENCES users (user_id), order_date DATE, status VARCHAR(20))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS order_items (order_item_id SERIAL PRIMARY KEY, order_order_id INTEGER REFERENCES orders (order_id), device_device_id INTEGER REFERENCES devices (device_id), quantity INTEGER, price INTEGER)");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reviews (review_id SERIAL PRIMARY KEY, user_user_id INTEGER REFERENCES users (user_id), device_device_id INTEGER REFERENCES devices (device_id), rating INTEGER, comment VARCHAR(255), review_date DATE)");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS carts (cart_id SERIAL PRIMARY KEY, user_user_id INTEGER REFERENCES users (user_id))");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS cart_items (cart_item_id SERIAL PRIMARY KEY, cart_cart_id INTEGER REFERENCES carts (cart_id), device_device_id INTEGER REFERENCES devices (device_id), quantity INTEGER)");
		log.info("Create table users, brands, types, devices");


	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*") // Adjust this according to your needs
						.allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*");
			}
		};
	}


}
