package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    /* // 특정 회사 ID로 면접 질문을 조회하는 쿼리 메서드
    List<Interview> findByCompanyId(Long companyId);
     */
    // 특정 회원이 생성한 모든 면접 기록 조회
    @Query("SELECT i FROM Interview i JOIN FETCH i.company WHERE i.memberId = :memberId")
    List<Interview> findByMemberIdWithCompany(@Param("memberId") Long memberId);

    // 특정 회원이 생성한 특정 면접 기록을 조회
    @Query("SELECT i FROM Interview i JOIN FETCH i.messages WHERE i.id = :interviewId AND i.memberId = :memberId")
    Optional<Interview> findByIdAndMemberIdWithMessages(@Param("interviewId") Long interviewId, @Param("memberId") Long memberId);


}
