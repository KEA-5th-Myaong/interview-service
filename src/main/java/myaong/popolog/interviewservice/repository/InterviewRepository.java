package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // 특정 회사 ID로 면접 질문을 조회하는 쿼리 메서드
    List<Interview> findByCompanyId(Long companyId);
}
