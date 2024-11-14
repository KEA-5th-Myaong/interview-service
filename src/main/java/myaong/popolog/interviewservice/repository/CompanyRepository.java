package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // 기업 이름에 검색어가 포함된 데이터만 조회하는 메서드 추가
    @Query("SELECT c FROM Company c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Company> findByNameContainingIgnoreCase(@Param("search") String search);

}
