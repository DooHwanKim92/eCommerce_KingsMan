package com.example.ecommerce.global.request;


import com.example.ecommerce.domain.alarm.entity.Alarm;
import com.example.ecommerce.domain.alarm.service.AlarmService;
import com.example.ecommerce.domain.category.entity.Category;
import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.confirm.entity.Confirm;
import com.example.ecommerce.domain.confirm.service.ConfirmService;
import com.example.ecommerce.domain.notice.entity.Notice;
import com.example.ecommerce.domain.notice.service.NoticeService;
import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.product.service.ProductService;
import com.example.ecommerce.domain.question.entity.Question;
import com.example.ecommerce.domain.question.service.QuestionService;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import com.example.ecommerce.global.image.entity.Image;
import com.example.ecommerce.global.image.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class Request {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ConfirmService confirmService;
    private final QuestionService questionService;
    private final NoticeService noticeService;
    private final ProductService productService;
    private final ImageService imageService;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final HttpSession session;
    private User user;
    @Setter
    private SiteUser siteUser = null;

    public Request(UserService userService, CategoryService categoryService, ConfirmService confirmService, AlarmService alarmService, QuestionService questionService, NoticeService noticeService, ProductService productService, ImageService imageService, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.confirmService = confirmService;
        this.questionService = questionService;
        this.noticeService = noticeService;
        this.productService = productService;
        this.imageService = imageService;
        this.req = req;
        this.resp = resp;
        this.session = session;

        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        } else {
            this.user = null;
        }
    }

    public boolean isLogin() {
        return user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }

    public SiteUser getSiteUser() {
        if (isLogout()) {
            return null;
        }

        if (siteUser == null) {
            siteUser = userService.findByUsername(getLoginedSiteUserUsername());
        }

        return siteUser;
    }


    private String getLoginedSiteUserUsername() {
        if (isLogout()) return null;

        return user.getUsername();
    }

    public List<Category> getCategory() {

        List<Category> categoryList = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            categoryList.add(this.categoryService.findById((long) i));
        }

        return categoryList;
    }

    // 승인, 거절하지 않은 신청이 있는지
    public List<Confirm> isThereNewConfirm() {
        List<Confirm> confirmList = confirmService.findAll();
        if (confirmList.isEmpty()) {
            return null;
        }
        return confirmList;
    }

    // 답변하지 않은 문의가 있는지
    public boolean isThereNewQuestion() {
        List<Question> questionList = questionService.findAll();
        for(int i = 0; i < questionList.size(); i++) {
            if(questionList.get(i).getIsAnswered().equals("N")) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereNewAlarm() {
        if(getSiteUser()==null) {
            return false;
        }
        if(getSiteUser().getAlarmList()==null) {
            return false;
        }
        List<Alarm> alarmList = getSiteUser().getAlarmList();
        for(int i = 0; i < alarmList.size(); i++) {
            if(!alarmList.get(i).isChecked()) {
                // 확인하지 않은 알림이 있을 때,
                return true;
            }
        }
        return false;
    }

    public String howManyNewAlarm() {
        int a = 0;
        String newAlarm = "";
        if(getSiteUser()==null) {
            return null;
        }
        if(getSiteUser().getAlarmList()==null) {
            return null;
        }
        List<Alarm> alarmList = getSiteUser().getAlarmList();
        for(int i = 0; i < alarmList.size(); i++) {
            if(!alarmList.get(i).isChecked()) {
                a++;
            }
        }
        newAlarm = String.valueOf(a);
        return newAlarm;
    }

    public List<Notice> getNoticeList() {
        return noticeService.findAll();
    }

    public List<Product> getProductList() {
        return productService.findAll();
    }


}