## KingsMan(의류 쇼핑몰 이커머스 웹 서비스)

### 서비스 설명 및 기획 의도

- 주제 : e-Commerce (전자상거래) 기능이 있는 웹 서비스
- 서비스명 : KingsMan(남성정장 온라인 쇼핑몰)
- 선정이유 : 보편적 → 참고 가능한 서비스가 많다.
- 프로젝트 목표 (혼자하는 이유가 잘해서다? 절대 X, 오히려 부족하기 때문)
  - 이전 프로젝트 부족한 부분 보완 (시간 대비 노력 부족, 목표치 도달못함)
  - 스프링 숙련도 향상(매 번 부족함을 느낌), 새로운 API 활용(ex.Daum 주소 API)


[[기능정의서](https://velog.io/@asdf4321/%EC%A4%91%EA%B0%84-2%EC%B0%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4%EC%87%BC%ED%95%91%EB%AA%B0-%EA%B8%B0%EB%8A%A5%EC%A0%95%EC%9D%98)]

---

## 🛠 개발환경

1. 사용언어 : JAVA , MariaDB(MySQL), HTML5 , CSS , JS
2. JDK : Amazon Corretto version 17.0.9
3. 에디터 : IntelliJ IDEA, DBeaver
4. DB종류 : MariaDB(MySQL), Server(local)
5. 프레임워크 : SpringBoot
6. 라이브러리 : JPA, thymeleaf, security, lombok, Java-email, kakao, bootstrap, jquery


---

## ☁️ ERD [링크](https://dbdiagram.io/d/%EC%9D%B4%EC%BB%A4%EB%A8%B8%EC%8A%A4-DB-%EC%8A%A4%EC%BC%80%EC%B9%98-65cc60a5ac844320ae196fd9)

![이커머스 DB 스케치 (2)](https://github.com/DooHwanKim92/eCommerce_KingsMan/assets/144447216/28bde2bc-8904-46e4-8481-f8cb90fbfce3)

---

## 📊 FLOW-CHART

<img width="3312" alt="이커머스 workflow (2)" src="https://github.com/DooHwanKim92/eCommerce_KingsMan/assets/144447216/39952a44-88a1-4c02-ba62-765ea9d78e9f">



---

## 👀 시연영상

[![Video Label](https://img.youtube.com/vi/w_ZyOFJ8lcU/0.jpg)](https://youtu.be/w_ZyOFJ8lcU?si=YmwOaaZKSilxI9h3)

제작 후 업로드 예정

---

## 🔥 트러블 슈팅

---

### 🚨 Issue 1
### 🚧 익숙함에 속아 (JPA)쿼리를 잊지 말자!


#### 💭 [이슈 내역]

많이 팔린 상품, 신상품, 평점 높은 상품 리스트업을 어떻게 하지?
findAll()에서 가져온 List<Product>를 정렬시켜서… 10개만 저장 시키는 로직을 만들어…?


#### 🚥 [해결]

![image](https://github.com/DooHwanKim92/eCommerce_KingsMan/assets/144447216/176a0072-af07-4f2c-ae2e-d6d67e07154a)


JPA 쿼리를 사용하면 데이터를 두 번 가공할 필요 없이 필요한 데이터를 바로 추출할 수 있다.


