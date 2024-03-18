package com.example.ecommerce.domain.orderdetail.controller;


import com.example.ecommerce.domain.cart.service.CartService;
import com.example.ecommerce.domain.orderdetail.OrderDetailCreateForm;
import com.example.ecommerce.domain.orderdetail.entity.OrderDetail;
import com.example.ecommerce.domain.orderdetail.service.OrderDetailService;
import com.example.ecommerce.domain.orders.entity.Orders;
import com.example.ecommerce.domain.orders.service.OrdersService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orderdetail")
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    private final ProductService productService;

    private final UserService userService;

    private final OrdersService ordersService;

    private final CartService cartService;

    @PostMapping("/{id}")
    // 상품 단건 구매
    // 상품 판매 회수 증가 필요, 해당 상품에 대한 구매 유저의 구매 여부 true 변경 필요
    public String orderDetail(Model model, @PathVariable(value = "id") Long id, Principal principal, @Valid OrderDetailCreateForm orderDetailCreateForm, BindingResult bindingResult) {
        Product product = this.productService.findById(id);
        SiteUser user = this.userService.findByUsername(principal.getName());

        int totalPrice = 0;

        totalPrice = this.ordersService.getTotalPrice(product, user);

        if (totalPrice == 0) {
            return String.format("redirect:/product/detail/%d", id);
        }

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);

        if (bindingResult.hasErrors()) {
            return "/orders/detail";
        }

        List<Orders> ordersList = this.ordersService.findByUserandProduct(user, product);
        OrderDetail orderDetail = this.orderDetailService.create(product, user, ordersList, orderDetailCreateForm, totalPrice);
        this.productService.purchase(product, ordersList);

        model.addAttribute("orderDetail", orderDetail);

        this.cartService.removeByProduct(product,user);

        return "/orders/complete";
    }

    @PostMapping("/buy")
    // 여러 상품 구매
    public String orderDetailManyProducts(Model model, Principal principal, @Valid OrderDetailCreateForm orderDetailCreateForm, BindingResult bindingResult) {

        SiteUser user = this.userService.findByUsername(principal.getName());
        List<Product> productList = this.orderDetailService.getProductListById(orderDetailCreateForm.getProductId());

        int totalPrice = 0;

        for ( int i = 0 ; i < productList.size(); i ++) {
            totalPrice += this.ordersService.getTotalPrice(productList.get(i),user);
        }

        model.addAttribute("productList", productList);
        model.addAttribute("user", user);
        model.addAttribute("totalPrice", totalPrice);

        if (bindingResult.hasErrors()) {
            return "/orders/detail";
        }

        OrderDetail orderDetail = new OrderDetail();

        for(int i = 0 ; i < productList.size(); i ++) {
            List<Orders> ordersList = this.ordersService.findByUserandProduct(user, productList.get(i));
            this.productService.purchase(productList.get(i), ordersList);
            orderDetail = this.orderDetailService.create(productList.get(i), user, ordersList, orderDetailCreateForm, this.ordersService.getTotalPrice(productList.get(i), user));
            model.addAttribute("orderDetail", orderDetail);
        }


        this.cartService.removeList(productList,user);
        this.userService.addPoint(user,orderDetail.getSavingPoint());

        return "/orders/complete";
    }

    @GetMapping("/remove/{id}")
    public String remove(Model model, @PathVariable(value = "id") Long id) {
        this.orderDetailService.removeById(id);
        return "redirect:/";
    }

}
