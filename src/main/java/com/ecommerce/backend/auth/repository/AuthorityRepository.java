package com.ecommerce.backend.auth.repository;

import com.ecommerce.backend.auth.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    public Authority findByRoleCode(String user);
}
