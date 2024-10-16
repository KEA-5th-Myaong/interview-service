package myaong.popolog.interviewservice.repository;

import myaong.popolog.interviewservice.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
