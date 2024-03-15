package com.example.ecommerce.domain.product.service;

import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.option.service.OptionService;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.service.OrdersService;
import com.example.ecommerce.domain.product.ProductCreateForm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.repository.ProductRepository;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.global.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryService categoryService;

    private final OptionService optionService;

    private final OrdersService ordersService;

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

    public Product createProduct(ProductCreateForm productCreateForm, SiteUser user, Category category) {

        Product product = Product.builder()
                .user(user)
                .category(category)
                .name(productCreateForm.getName())
                .content(productCreateForm.getContent())
                .price(productCreateForm.getPrice())
                .discount(productCreateForm.getDiscount())
                .purchasing(0)
                .isNew(true)
                .DCPrice(0)
                .score(0)
                .income(0)
                .build();

        this.productRepository.save(product);

        if(!product.getDiscount().equals("0")) {
            int DCPrice = Integer.parseInt(product.getPrice().replace(",","")) - Integer.parseInt(product.getPrice().replace(",",""))*Integer.parseInt(product.getDiscount())/100;
            Product DCproduct = product.toBuilder()
                    .DCPrice(DCPrice)
                    .build();

            this.productRepository.save(DCproduct);
        }


        this.categoryService.addProduct(category, product);

        return product;
    }

    public void removeProduct(Long id) {
        Product product = findById(id);

        this.productRepository.delete(product);
    }

    public void addOption(Product product, Option option) {
        List<Option> optionList = product.getOptionList();
        optionList.add(option);
        Product productOption = product.toBuilder()
                .optionList(optionList)
                .build();

        this.productRepository.save(productOption);
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findByOptionId(Long id) {
        return this.optionService.findById(id).getProduct();
    }

    public List<Product> getBestSeller() {
        return this.productRepository.getBestSeller();
    }

    public List<Product> getNewProducts() {
        List<Product> productList = this.productRepository.getNewProducts();
        for ( int i = 0 ; i < productList.size(); i++) {
            if (!productList.get(i).isNew()) {
                productList.remove(productList.get(i));
            }
        }
        return productList;
    }

    public void addImages(Product product, Image productRepImg, List<Image> productDetailImg) {

        Product addImages = product.toBuilder()
                .representImg(productRepImg)
                .imageList(productDetailImg)
                .build();

        this.productRepository.save(addImages);
    }

    public List<Product> findByCategoryId(Long id) {
        List<Product> productAllList = this.productRepository.findAll();

        List<Product> productList = new ArrayList<>();

        for ( int i = 0 ; i < productAllList.size(); i++) {
            if (Objects.equals(productAllList.get(i).getCategory().getId(), id)) {
                productList.add(productAllList.get(i));
            }
        }

        return productList;
    }

    public Product findByOrdersId(Long id) {
        Orders orders = this.ordersService.findById(id);
        Product product = this.findById(orders.getProduct().getId());

        return product;
    }

    public List<Product> findAllByKeyword(String kw) {
        return this.productRepository.findAllByKeyword(kw);
    }


    public void purchase(Product product, List<Orders> ordersList) {
        //상품 구매시 판매량 증가
        int amount = 0;

        for(int i = 0 ; i < ordersList.size(); i ++) {
            amount += Integer.parseInt(ordersList.get(i).getAmount());
        }

        int purchasingnum = product.getPurchasing() + amount;

        Product productPurchase = product.toBuilder()
                .purchasing(purchasingnum)
                .build();

        this.productRepository.save(productPurchase);
    }

    public void scoreSum(Product product) {
        float scoreSummary = 0;
        int ss = 0;
        if(!product.getReviewList().isEmpty()) {
            for(int i = 0 ; i < product.getReviewList().size(); i++) {
                ss += product.getReviewList().get(i).getScore();
            }
            scoreSummary = (float) ss /product.getReviewList().size();
        }

        Product scoreSum = product.toBuilder()
                .score(scoreSummary)
                .build();

        this.productRepository.save(scoreSum);
    }

    public List<Product> getHighQualityProducts() {
        return this.productRepository.getHighQualityProducts();
    }

    public void getIncome(List<Product> sellProductList) {
        int income = 0;
        for ( int i = 0; i < sellProductList.size(); i++) {
            if(sellProductList.get(i).getDiscount().equals("0")) {
                income = sellProductList.get(i).getPurchasing() * Integer.parseInt(sellProductList.get(i).getPrice().replace(",",""));
            } else {
                income = sellProductList.get(i).getPurchasing() * sellProductList.get(i).getDCPrice();
            }

            Product updateIncome = sellProductList.get(i).toBuilder()
                    .income(income)
                    .build();

            this.productRepository.save(updateIncome);
        }
    }
}
