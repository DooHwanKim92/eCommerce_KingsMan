package com.example.ecommerce.domain.user.repository;


import com.example.ecommerce.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByNickname(String nickname);

    Optional<SiteUser> findByEmail(String email);
}
