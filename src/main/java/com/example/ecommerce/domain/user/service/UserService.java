package com.example.ecommerce.domain.user.service;


import com.example.ecommerce.domain.user.UserCreateForm;
import com.example.ecommerce.domain.user.entity.SiteUser;
import com.example.ecommerce.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signupCheckError(UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2","passwordIncorrect","2개의 패스워드가 일치하지 않습니다.");
        }
    }

    public void signup(UserCreateForm userCreateForm) {
        SiteUser user = SiteUser.builder()
                .username(userCreateForm.getUsername())
                .nickname(userCreateForm.getNickname())
                .password(passwordEncoder.encode(userCreateForm.getPassword2()))
                .phoneNumber(userCreateForm.getPhoneNumber())
                .email(userCreateForm.getEmail())
                .sex(userCreateForm.getSex())
                .age(userCreateForm.getAge())
                .address(userCreateForm.getAddress())
                .build();

        this.userRepository.save(user);
    }
}
