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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/orderdetail")
@RequiredArgsConstructor
public class OrderDetailController {

    @Value("${api.key}")
    private String API_KEY;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderDetailService orderDetailService;

    private final ProductService productService;

    private final UserService userService;

    private final OrdersService ordersService;

    private final CartService cartService;

    @PostMapping("/{id}")
    //구매 확정
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

        return "/orders/checkout";
    }

    @PostMapping("/buy")
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


        for(int i = 0 ; i < productList.size(); i ++) {
            List<Orders> ordersList = this.ordersService.findByUserandProduct(user, productList.get(i));
            this.productService.purchase(productList.get(i), ordersList);
            OrderDetail orderDetail = this.orderDetailService.create(productList.get(i), user, ordersList, orderDetailCreateForm, this.ordersService.getTotalPrice(productList.get(i), user));
            model.addAttribute("orderDetail", orderDetail);
        }


        this.cartService.removeList(productList,user);

        return "/orders/checkout";
    }

    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
        // @docs https://docs.tosspayments.com/reference/using-api/api-keys
        String widgetSecretKey = API_KEY;

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        // 결제 승인 API를 호출하세요.
        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200 ? true : false;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // TODO: 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    /**
     * 인증성공처리
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String paymentRequest(HttpServletRequest request, Model model) throws Exception {
        return "/success";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) throws Exception {
        return "/checkout";
    }

    /**
     * 인증실패처리
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) throws Exception {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "/fail";
    }


}
