package com.example.ecommerce.domain.category.service;


import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.repository.CategoryRepository;
import com.example.ecommerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }
    public Category findByname(String category) {
        return this.categoryRepository.findByname(category);
    }

    public Category findById(Long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isEmpty()) {
            return null;
        }
        return category.get();
    }

    public void create() {
        Category category1 = Category.builder()
                .name("카테고리1")
                .build();

        Category category2 = Category.builder()
                .name("카테고리2")
                .build();

        Category category3 = Category.builder()
                .name("카테고리3")
                .build();

        Category category4 = Category.builder()
                .name("카테고리4")
                .build();

        Category category5 = Category.builder()
                .name("카테고리5")
                .build();

        Category category6 = Category.builder()
                .name("카테고리6")
                .build();

        Category category7 = Category.builder()
                .name("카테고리7")
                .build();

        this.categoryRepository.save(category1);
        this.categoryRepository.save(category2);
        this.categoryRepository.save(category3);
        this.categoryRepository.save(category4);
        this.categoryRepository.save(category5);
        this.categoryRepository.save(category6);
        this.categoryRepository.save(category7);
    }

    public void addProduct(Category category, Product product) {

        List<Product> productList = category.getProductList();
        productList.add(product);
        Category addProductCategory = category.toBuilder()
                .productList(productList)
                .build();

        this.categoryRepository.save(addProductCategory);
    }
}