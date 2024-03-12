package com.example.ecommerce.domain.product.controller;


import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.option.OptionCreateForm;
import com.example.ecommerce.domain.option.entity.Option;
import com.example.ecommerce.domain.orders.OrdersCreateForm;
import com.example.ecommerce.domain.product.ProductCreateForm;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.review.entity.Review;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.domain.wishlist.service.WishlistService;
import com.example.ecommerce.global.image.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {


    private final UserService userService;

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ImageService imageService;

    private final WishlistService wishlistService;

    @GetMapping("/list")
    public String searchProductList(Model model, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Product> productList = this.productService.findAllByKeyword(kw);
        model.addAttribute("productList",productList);
        model.addAttribute("kw", kw);
        return "/product/search_list";
    }

    @GetMapping("/list/seller/{id}")
    public String sellerProductList(Model model, @PathVariable(value = "id") Long id) {
        SiteUser user = this.userService.findById(id);
        List<Product> productList = user.getSellProductList();
        model.addAttribute("user",user);
        model.addAttribute("productList",productList);
        return "/product/seller_list";
    }

    @GetMapping("/list/category/{id}")
    public String categoryProductList(Model model, @PathVariable(value = "id") Long id) {
        List<Product> productList = this.productService.findByCategoryId(id);
        Category category = this.categoryService.findById(id);
        model.addAttribute("productList",productList);
        model.addAttribute("category",category);
        return "/product/category_list";
    }

    @GetMapping("/create")
    public String productCreate(Model model, ProductCreateForm productCreateForm) {
        List<Category> categoryList = this.categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        return "/product/create";
    }

    @PostMapping("/create")
    public String productCreatePost(@Valid ProductCreateForm productCreateForm, BindingResult bindingResult, Principal principal, Model model, OptionCreateForm optionCreateForm,
                                    @RequestParam(value = "representImg")MultipartFile representImg, @RequestParam(value = "detailImg") List<MultipartFile> detailImg) throws IOException {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Category category = this.categoryService.findByname(productCreateForm.getCategory());
        if (bindingResult.hasErrors()) {
            return "/product/create";
        }
        if(productCreateForm.getCategory().equals("상품 카테고리 선택")) {
            bindingResult.rejectValue("category","categoryIncorrect","카테고리를 선택해주세요.");
            return "/product/create";
        }
        Product product = this.productService.createProduct(productCreateForm,user,category);
        this.productService.addImages(product, this.imageService.createProductRepImg(product,representImg),this.imageService.createProductDetailImg(product,detailImg));
        this.userService.createSellProduct(user, product);

        model.addAttribute("product",product);

        return "/product/create_option";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(Model model, @PathVariable(value = "id") Long id, OrdersCreateForm ordersCreateForm) {
        this.productService.scoreSum(this.productService.findById(id));

        Product product = this.productService.findById(id);

        model.addAttribute("dcprice",product.getDCPrice());
        model.addAttribute("product",product);
        model.addAttribute("reviewList",product.getReviewList());
        model.addAttribute("questionList",product.getQuestionList());

        return "/product/detail";
    }

    @GetMapping("/remove/{id}")
    public String productRemove(@PathVariable(value = "id") Long id) {
        this.productService.removeProduct(id);
        return "redirect:/product/management";
    }

    @GetMapping("/management")
    public String productManagement(Model model, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.productService.getIncome(user.getSellProductList());
        List<Product> productList = user.getSellProductList();
        model.addAttribute("productList",productList);
        return "/product/management";
    }



}
