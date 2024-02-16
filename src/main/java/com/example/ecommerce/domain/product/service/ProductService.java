package com.example.ecommerce.domain.product.service;

import com.example.ecommerce.domain.product.ProductCreateForm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.repository.ProductRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


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

    public void createProduct(ProductCreateForm productCreateForm, SiteUser user) {
        Product product = Product.builder()
                .category(productCreateForm.getCategory())
                .name(productCreateForm.getName())
                .amount(Integer.valueOf(productCreateForm.getAmount()))
                .content(productCreateForm.getContent())
                .price(Integer.valueOf(productCreateForm.getPrice()))
                .discount(Integer.valueOf(productCreateForm.getDiscount()))
                .purchasing(0)
                .isNew(true)
                .sellerName(user.getNickname())
                .build();

        this.productRepository.save(product);
    }

    public void removeProduct(Long id) {
        Product product = findById(id);

        this.productRepository.delete(product);
    }
}
