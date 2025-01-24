package com.qima.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qima.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
