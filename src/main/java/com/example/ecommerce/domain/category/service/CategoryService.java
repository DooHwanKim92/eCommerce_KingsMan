package com.example.ecommerce.domain.category.service;


import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.repository.CategoryRepository;
import com.example.ecommerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void create() {
        Category category1 = Category.builder()
                .name("의류/잡화")
                .build();

        Category category2 = Category.builder()
                .name("식품")
                .build();

        Category category3 = Category.builder()
                .name("생활용품")
                .build();

        Category category4 = Category.builder()
                .name("가전디지털")
                .build();

        Category category5 = Category.builder()
                .name("도서/음반/DVD")
                .build();

        Category category6 = Category.builder()
                .name("문구/오피스")
                .build();

        Category category7 = Category.builder()
                .name("헬스/건강식품")
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
