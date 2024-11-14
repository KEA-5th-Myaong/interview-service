package myaong.popolog.interviewservice.service;

import lombok.RequiredArgsConstructor;
import myaong.popolog.interviewservice.common.exception.ApiCode;
import myaong.popolog.interviewservice.common.exception.ApiException;
import myaong.popolog.interviewservice.dto.request.MessageRequest;
import myaong.popolog.interviewservice.dto.request.MessageUpdateRequest;
import myaong.popolog.interviewservice.dto.response.MessageResponse;
import myaong.popolog.interviewservice.entity.Interview;
import myaong.popolog.interviewservice.entity.Message;
import myaong.popolog.interviewservice.enums.InterviewRole;
import myaong.popolog.interviewservice.repository.InterviewRepository;
import myaong.popolog.interviewservice.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final InterviewRepository interviewRepository;

    // 메시지 전송
    @Transactional
    public MessageResponse sendMessage(Long interviewId, Long memberId, MessageRequest messageRequest) {
        // 인터뷰와 회원 권한 검증
        Interview interview = interviewRepository.findByIdAndMemberIdWithMessages(interviewId, memberId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND));

        // 메시지 엔티티 생성 및 저장
        Message message = Message.builder()
                .interview(interview)
                .interviewRole(InterviewRole.valueOfLower(messageRequest.getSender()))
                .content(messageRequest.getContent())
                .build();

        messageRepository.save(message);

        return new MessageResponse(message.getId());
    }


    // 메시지 수정
    @Transactional
    public MessageResponse updateMessage(Long messageId, Long memberId, MessageUpdateRequest updateRequest) {
        // 메시지 조회 및 예외 처리
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ApiException(ApiCode.MESSAGE_NOT_FOUND));

        // 인터뷰 소유자 검증
        if (!message.getInterview().getMemberId().equals(memberId)) {
            throw new ApiException(ApiCode.INVALID_DATA);
        }
        // 메시지 내용 수정
        message.updateContent(updateRequest.getContent());

        return MessageResponse.builder()
                .messageId(message.getId())
                .build();
    }

}
