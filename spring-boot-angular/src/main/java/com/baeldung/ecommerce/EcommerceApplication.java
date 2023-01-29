package com.baeldung.ecommerce;

import com.baeldung.ecommerce.model.Product;
import com.baeldung.ecommerce.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductService productService) {
        return args -> {
            productService.save(new Product(1L, "TV Set", 300.00, "assets/tv.jpg"));
            productService.save(new Product(2L, "Game Console", 200.00, "assets/gaming.jpg"));
            productService.save(new Product(3L, "Sofa", 100.00, "assets/sofa.jpg"));
            productService.save(new Product(4L, "Icecream", 5.00, "assets/icecream.jpg"));
            productService.save(new Product(5L, "Beer", 3.00, "assets/beer.jpg"));
            productService.save(new Product(6L, "Phone", 500.00, "assets/phone.jpg"));
            productService.save(new Product(7L, "Watch", 30.00, "assets/watch.jpg"));
            productService.save(new Product(8L, "Pencil", 0.1, "assets/pencil.png"));
        };
    }
}
