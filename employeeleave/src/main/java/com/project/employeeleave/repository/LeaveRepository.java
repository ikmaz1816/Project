package com.project.employeeleave.repository;

import com.project.employeeleave.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {
    Optional<Leave> findByEmpEmail(String email);
}
