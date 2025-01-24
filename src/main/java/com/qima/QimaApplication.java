package com.qima;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.qima.entity.CategoryEntity;
import com.qima.repository.CategoryRepository;

@SpringBootApplication
public class QimaApplication {
    private final CategoryRepository categoryRepository;

    public QimaApplication(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(QimaApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext ctx) {
        return args -> {
            CategoryEntity electronics = new CategoryEntity(null, "Electronics", null);
            CategoryEntity fashion = new CategoryEntity(null, "Fashion", null);
            CategoryEntity books = new CategoryEntity(null, "Books", null);
            categoryRepository.save(electronics);
            categoryRepository.save(fashion);
            categoryRepository.save(books);

            CategoryEntity shoes = new CategoryEntity(null, "Shoes", fashion);
            categoryRepository.save(shoes);

            CategoryEntity running = new CategoryEntity(null, "Running", shoes);
            categoryRepository.save(running);

        };
    }

}
