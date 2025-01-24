package com.qima.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.qima.dto.ProductRequest;
import com.qima.dto.ProductResponse;
import com.qima.entity.CategoryEntity;
import com.qima.entity.ProductEntity;
import com.qima.repository.CategoryRepository;
import com.qima.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> get() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toProductResponse(ProductEntity productEntity) {
        String fullCategoryPath = getFullCategoryPath(productEntity.getCategory());

        return new ProductResponse(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getAvailable(),
                fullCategoryPath
        );
    }

    private String getFullCategoryPath(CategoryEntity categoryEntity) {
        if (categoryEntity.getParentCategory() == null) {
            return categoryEntity.getName();
        } else {
            return getFullCategoryPath(categoryEntity.getParentCategory()) + " > " + categoryEntity.getName();
        }
    }

    private ProductEntity toProductEntity(ProductRequest productRequest) throws Exception {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(productRequest.getCategoryId());

        if (categoryEntityOptional.isEmpty()) {
            throw new Exception("Category not found");
        }

        ProductEntity product = new ProductEntity();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setAvailable(productRequest.getAvailable());
        product.setCategory(categoryEntityOptional.get());
        return product;
    }

    public Long add(ProductRequest productRequest) throws Exception {
        ProductEntity product = productRepository.save(toProductEntity(productRequest));
        return product.getId();
    }

    public ProductRequest loadForm(Long productId) throws Exception {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);

        if (productEntity == null) {
            throw new Exception("Product not found");
        }

        return new ProductRequest(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getAvailable(),
                productEntity.getCategory().getId()
        );

    }

    public void edit(Long id, ProductRequest productRequest) throws Exception {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(productRequest.getCategoryId());

        if (categoryEntityOptional.isEmpty()) {
            throw new Exception("Category not found");
        }

        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();

            productEntity.setName(productRequest.getName());
            productEntity.setDescription(productRequest.getDescription());
            productEntity.setPrice(productRequest.getPrice());
            productEntity.setAvailable(productRequest.getAvailable());
            productEntity.setCategory(categoryEntityOptional.get());
            productRepository.save(productEntity);
        }

    }

    public void delete(Long id) throws Exception {

        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            throw new Exception("Product not found");
        }
        productRepository.delete(productOptional.get());

    }
}