package com.qima.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qima.dto.ProductRequest;
import com.qima.dto.ProductResponse;
import com.qima.service.CategoryService;
import com.qima.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("categories", categoryService.get());
        return "new-product";
    }

    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) throws Exception {
        ProductRequest productRequest = productService.loadForm(id);
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("categories", categoryService.get());
        model.addAttribute("productId", id);
        return "edit-product";
    }

    @PostMapping("/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute ProductRequest productRequest, RedirectAttributes redirectAttributes) throws Exception {
        productService.edit(id, productRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        return "redirect:/products";
    }

    @PostMapping
    public String add(@ModelAttribute @Valid ProductRequest productRequest, RedirectAttributes redirectAttributes) throws Exception {
        this.productService.add(productRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Product created successfully");
        return "redirect:/products";
    }

    @GetMapping
    public String get(Model model) {
        List<ProductResponse> products = productService.get();
        model.addAttribute("products", products);
        return "list-products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) throws Exception {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/products";
    }

}
