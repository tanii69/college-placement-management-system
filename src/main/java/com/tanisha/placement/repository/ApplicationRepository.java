package com.tanisha.placement.repository;
import com.tanisha.placement.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByStudentIdAndCompanyId(Long studentId, Long companyId);
}
