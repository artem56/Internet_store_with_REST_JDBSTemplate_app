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
//		jdbcTemplate.execute("Drop table if exists users ");
//		jdbcTemplate.execute("Drop table if exists devices ");
//		jdbcTemplate.execute("Drop table if exists brands ");
//		jdbcTemplate.execute("Drop table if exists types ");


		//log.info("Drop table users, brands, types, devices");
		jdbcTemplate.execute("Create table if not EXISTS users (user_id serial PRIMARY KEY, email varchar(35), password varchar(100), role varchar (35)) ");
		jdbcTemplate.execute("Create table if not EXISTS brands (brand_id serial PRIMARY KEY, name varchar(35)) ");
		jdbcTemplate.execute("Create table if not EXISTS types (type_id serial PRIMARY KEY, name varchar(35)) ");
		jdbcTemplate.execute("Create table if not EXISTS devices (device_id serial PRIMARY KEY, name varchar(35), brand_brand_id integer references brands (brand_id), type_type_id integer references types (type_id), price integer, rating integer , img varchar (200), info varchar (200)) ");
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
