package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Interview;
import myaong.popolog.interviewservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findTopByInterviewOrderByIdDesc(Interview interview);
    @Query("SELECT MAX(m.id) FROM Message m WHERE m.interview.id = :interviewId")
    Long findMaxMessageIdByInterviewId(@Param("interviewId") Long interviewId);
    List<Message> findByInterviewId(Long interviewId);
}
