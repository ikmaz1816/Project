package com.project.idp.repository;

import com.project.idp.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentityRepository extends JpaRepository<Identity,Long> {
    Optional<Identity> findByEmail(String email);
}
