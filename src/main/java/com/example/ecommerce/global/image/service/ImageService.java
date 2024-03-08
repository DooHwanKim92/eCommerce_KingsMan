package com.example.ecommerce.global.image.service;


import com.example.ecommerce.domain.product.entity.Product;
import com.example.ecommerce.domain.review.entity.Review;
import com.example.ecommerce.global.image.entity.Image;
import com.example.ecommerce.global.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public Image createProductRepImg(Product product, MultipartFile representImg) throws IOException {

        // 프로젝트 외부 저장
        // C://kingsman//file_upload//product
        String thumnailPath = "";
        String thunmail = "";

        if (representImg.isEmpty()) {
            thumnailPath = "/images/기본이미지.jpg";
        } else if (!representImg.isEmpty()) {
            thunmail = "product/" + UUID.randomUUID().toString() + ".jpg";
            File representImgFile = new File(fileDirPath + "/" + thunmail);
            representImg.transferTo(representImgFile);
            thumnailPath = "/file/" + thunmail;
        }

        Image img = Image.builder()
                .typeName("product")
                .typeId(product.getId())
                .FileName(representImg.getOriginalFilename())
                .FilePath(thumnailPath)
                .product(product)
                .build();

        this.imageRepository.save(img);

        return img;
    }

    public List<Image> createProductDetailImg(Product product, List<MultipartFile> detailImg) throws IOException {

        String thumnailPath = "";
        String thunmail = "";

        List<Image> imageList = new ArrayList<>();

        for ( int i = 0; i < detailImg.size(); i++) {
            if (detailImg.get(i).isEmpty()) {
                thumnailPath = "/images/기본이미지.jpg";
            } else if (!detailImg.get(i).isEmpty()) {
                thunmail = "product/" + UUID.randomUUID().toString() + ".jpg";
                File representImgFile = new File(fileDirPath + "/" + thunmail);
                detailImg.get(i).transferTo(representImgFile);
                thumnailPath = "/file/" + thunmail;
            }

            Image img = Image.builder()
                    .typeName("product")
                    .typeId(product.getId())
                    .FileName(detailImg.get(i).getOriginalFilename())
                    .FilePath(thumnailPath)
                    .product(product)
                    .build();

            this.imageRepository.save(img);
            imageList.add(img);
        }

        return imageList;

    }

    public void createMainPageImg(List<MultipartFile> mainImg) throws IOException {

        String imgPath = "";
        String img = "";

        for ( int i = 0; i < mainImg.size(); i++) {
            if (mainImg.get(i).isEmpty()) {
                imgPath = "/images/기본이미지.jpg";
            } else if (!mainImg.get(i).isEmpty()) {
                img = "main/" + UUID.randomUUID().toString() + ".jpg";
                File representImgFile = new File(fileDirPath + "/" + img);
                mainImg.get(i).transferTo(representImgFile);
                imgPath = "/file/" + img;
            }

            Image mainThumnail = Image.builder()
                    .typeName("main")
                    .FileName(mainImg.get(i).getOriginalFilename())
                    .FilePath(imgPath)
                    .build();

            this.imageRepository.save(mainThumnail);
        }
    }

    public List<Image> getMainImageList() {
        List<Image> imageListAll = this.imageRepository.findAll();
        List<Image> imageList = new ArrayList<>();

        for ( int i = 0 ; i < imageListAll.size(); i++) {
            if ( imageListAll.get(i).getTypeName().equals("main")) {
                imageList.add((imageListAll.get(i)));
            }
        }
        return imageList;
    }

    public void createReviewImg(Review review, MultipartFile reviewImg) throws IOException {

        // 프로젝트 외부 저장
        // C://kingsman//file_upload//review
        String thumnailPath = "";
        String thunmail = "";

        if (reviewImg.isEmpty()) {
            thumnailPath = "/images/기본이미지.jpg";
        } else if (!reviewImg.isEmpty()) {
            thunmail = "review/" + UUID.randomUUID().toString() + ".jpg";
            File representImgFile = new File(fileDirPath + "/" + thunmail);
            reviewImg.transferTo(representImgFile);
            thumnailPath = "/file/" + thunmail;
        }

        Image img = Image.builder()
                .typeName("review")
                .typeId(review.getId())
                .FileName(reviewImg.getOriginalFilename())
                .FilePath(thumnailPath)
                .review(review)
                .build();

        this.imageRepository.save(img);

    }

}
