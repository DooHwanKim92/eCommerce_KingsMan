package com.example.ecommerce.global.init;

import com.example.ecommerce.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
@RequiredArgsConstructor
public class ServiceInit implements InitializingBean {

    private final CategoryService categoryService;


    @Override
    public void afterPropertiesSet() {
        this.init();

    }


    public void init() {
//        this.createCategory();
//        this.createDirectory();
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


}
