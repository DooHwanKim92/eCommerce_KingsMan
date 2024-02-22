package com.example.ecommerce.domain.product.service;

import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.product.ProductCreateForm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.repository.ProductRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;


    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if(product.isEmpty()) {
            return null;
        }
        return product.get();
    }

    public List<Product> getList() {
        return this.productRepository.findAll();
    }

    public void createProduct(ProductCreateForm productCreateForm, SiteUser user, Category category) {

        Product product = Product.builder()
                .user(user)
                .category(category)
                .name(productCreateForm.getName())
                .content(productCreateForm.getContent())
                .sellerName(user.getConfirm().getSellerName())
                .discount(Integer.valueOf(productCreateForm.getDiscount()))
                .purchasing(0)
                .isNew(true)
                .build();

        this.productRepository.save(product);

        this.categoryService.addProduct(category, product);
    }

    public void removeProduct(Long id) {
        Product product = findById(id);

        this.productRepository.delete(product);
    }
}
