package com.tung.productwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductWebappApplication.class, args);
    }

}
