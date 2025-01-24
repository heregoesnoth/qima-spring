package com.qima.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qima.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
