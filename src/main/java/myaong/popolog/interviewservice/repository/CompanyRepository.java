package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
