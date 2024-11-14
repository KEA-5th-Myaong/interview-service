package myaong.popolog.interviewservice.service;

import lombok.RequiredArgsConstructor;
import myaong.popolog.interviewservice.common.exception.ApiCode;
import myaong.popolog.interviewservice.common.exception.ApiException;
import myaong.popolog.interviewservice.dto.request.CreateInterviewRequest;
import myaong.popolog.interviewservice.dto.response.*;
import myaong.popolog.interviewservice.entity.Company;
import myaong.popolog.interviewservice.entity.Interview;
import myaong.popolog.interviewservice.entity.Message;
import myaong.popolog.interviewservice.enums.InterviewRole;
import myaong.popolog.interviewservice.repository.CompanyRepository;
import myaong.popolog.interviewservice.repository.InterviewRepository;
import myaong.popolog.interviewservice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {

    private final CompanyRepository companyRepository;
    private final InterviewRepository interviewRepository;
    private final MessageRepository messageRepository;



/*
    // 특정 회사의 면접 질문 리스트 조회
    @Transactional(readOnly = true)
    public List<String> getInterviewQuestions(Long companyId) {
        return interviewRepository.findByCompanyId(companyId).stream()
                .flatMap(interview -> interview.getMessages().stream())
                .map(Message::getContent)
                .collect(Collectors.toList());
    }
*/
    // 면접 생성
    @Transactional
    public CreateInterviewResponse createInterview(CreateInterviewRequest interviewRequest, Long memberId) {
        Company company = companyRepository.findById(interviewRequest.getCompanyId())
            .orElseThrow(() -> new ApiException(ApiCode.COMPANY_NOT_FOUND));

        // 면접 생성
        Interview interview = Interview.builder()
            .memberId(memberId)
            .company(company)
            .build();

        // 기본 메시지 생성
        Message message = Message.builder()
            .interview(interview)
            .interviewRole(InterviewRole.INTERVIEWER) // 고정된 역할
            .content("첫 질문") // 기본 메시지
            .build();

        // Interview와 Message 저장
        interview.getMessages().add(message);
        interviewRepository.save(interview);

        return new CreateInterviewResponse(interview.getId(), message.getId(), message.getContent());
}


    // 새 질문 생성
    @Transactional
    public NewMessageResponse generateNewQuestion(Long interviewId, Long memberId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND));

        if (!interview.getMemberId().equals(memberId)) {
            throw new ApiException(ApiCode.METHOD_NOT_ALLOWED);
        }
        String generatedQuestion = getDummyQuestion(interview);
        Message message = Message.builder()
                .interview(interview)
                .content(generatedQuestion)
                .interviewRole(InterviewRole.INTERVIEWER) // 면접관 역할로 설정
                .build();

        messageRepository.save(message);

        return NewMessageResponse.builder()
                .messageId(message.getId())
                .content(message.getContent())
                .build();
    }
    private String getDummyQuestion(Interview interview) {
        return "이 회사에서 일하는 동안 가장 중요한 기술적 도전은 무엇일까요?";
    }

    // 꼬리 질문 생성
    @Transactional
    public NewMessageResponse generateFollowUpQuestion(Long interviewId, Long memberId) {

        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND));

        Message lastMessage = messageRepository.findTopByInterviewOrderByIdDesc(interview)
                .orElseThrow(() -> new ApiException(ApiCode.DB_ERROR));

        String followUpQuestion = generateDummyFollowUpQuestion(lastMessage.getContent());

        Message followUpMessage = Message.builder()
                .interview(interview)
                .content(followUpQuestion)
                .interviewRole(InterviewRole.INTERVIEWER) // 면접관 역할
                .build();

        messageRepository.save(followUpMessage);

        return NewMessageResponse.builder()
                .messageId(followUpMessage.getId())
                .content(followUpMessage.getContent())
                .build();
    }
    // 더미 꼬리 질문 생성
    private String generateDummyFollowUpQuestion(String lastMessageContent) {
        return "마지막 문단에 대해 추가 설명해주실 수 있나요?";
    }

    // 면접 기록 목록 조회
    @Transactional(readOnly = true)
    public List<InterviewListResponse> getInterviewList(Long memberId) {
        List<Interview> interviews = interviewRepository.findByMemberIdWithCompany(memberId);

        return interviews.stream()
                .map(interview -> {
                    // 회사 이름과 면접 생성 시간을 기반으로 제목 생성
                    String companyName = interview.getCompany().getName();
                    String formattedDate = interview.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
                    String title = companyName + "_" + formattedDate;

                    return new InterviewListResponse(interview.getId(), title);
                })
                .collect(Collectors.toList());
    }


    // 면접 기록 조회
    @Transactional(readOnly = true)
    public List<DetailMessageResponse> getInterviewMessages(Long interviewId, Long memberId) {
        // 특정 회원의 인터뷰와 메시지를 Fetch Join으로 조회
        Interview interview = interviewRepository.findByIdAndMemberIdWithMessages(interviewId, memberId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND));

        // 메시지 변환
        return interview.getMessages().stream()
                .map(message -> new DetailMessageResponse(
                        message.getId(),  // messageId
                        message.getRole().name().toLowerCase(),  // role
                        message.getContent()  // content
                ))
                .collect(Collectors.toList());
    }


    // 면접 삭제
    @Transactional
    public void deleteInterview(Long interviewId, Long memberId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND)); // 면접 기록이 없으면 예외 발생

        if (!interview.getMemberId().equals(memberId)) {
            throw new ApiException(ApiCode.METHOD_NOT_ALLOWED); // 본인 기록이 아닌 경우 예외 발생
        }

        interviewRepository.delete(interview); // 면접 기록 삭제
    }



}
