package com.example.ecommerce.global.init;

import com.example.ecommerce.domain.category.service.CategoryService;
import com.example.ecommerce.domain.user.UserCreateForm;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ServiceInit implements InitializingBean {

    private final CategoryService categoryService;

    private final UserService userService;


    @Override
    public void afterPropertiesSet() {
        this.init();

    }


    public void init() {
//        this.createCategory();
//        this.createDirectory();
//        this.createSampleUsers();
    }

    // 어플리케이션 실행 시 카테고리 생성
    public void createCategory() {
        this.categoryService.create();
    }

    // 이미지 파일 저장 디렉토리 생성
    public void createDirectory() {
        Path directoryArticle = Paths.get("C:\\kingsman\\file_upload\\product");
        Path directoryUser = Paths.get("C:\\kingsman\\file_upload\\user");
        Path directoryReview = Paths.get("C:\\kingsman\\file_upload\\review");
        Path directoryQuestion = Paths.get("C:\\kingsman\\file_upload\\question");
        Path directoryMain = Paths.get("C:\\kingsman\\file_upload\\main");

        try {
            Files.createDirectories(directoryArticle);
            Files.createDirectories(directoryUser);
            Files.createDirectories(directoryReview);
            Files.createDirectories(directoryQuestion);
            Files.createDirectories(directoryMain);
        } catch (FileAlreadyExistsException e) {
            System.out.println("디렉토리가 이미 존재합니다");
        } catch (NoSuchFileException e) {
            System.out.println("디렉토리 경로가 존재하지 않습니다");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 유저 샘플 데이터 100개 생성
    public void createSampleUsers() {
        UserCreateForm userCreateForm = new UserCreateForm();


        for (int i = 1; i <= 100; i++) {
            int randomAge = (int) (Math.random() * 100) + 1;
            char sex = '남';
            if(i%2==0) {
                sex = '여';
            }

            userCreateForm.setUsername("user"+i);
            userCreateForm.setPassword1("1234");
            userCreateForm.setPassword2("1234");
            userCreateForm.setNickname("유저"+i);
            userCreateForm.setSex(sex);
            userCreateForm.setAge(String.valueOf(randomAge));
            userCreateForm.setEmail("user"+i+"@gmail.com");
            userCreateForm.setPhoneNumber("01012341234");
            userCreateForm.setAddress(Collections.singletonList("대전광역시 서구 둔산대로 " + i + "번길 " + (i + 1)));


            this.userService.signup(userCreateForm);
        }


    }


}
