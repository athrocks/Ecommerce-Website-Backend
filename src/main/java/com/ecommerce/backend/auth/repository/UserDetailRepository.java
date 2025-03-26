package com.ecommerce.backend.auth.repository;

import com.ecommerce.backend.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<User, Long> {
    public User findByEmail(String username);
}

