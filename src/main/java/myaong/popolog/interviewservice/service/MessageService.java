package myaong.popolog.interviewservice.service;

import lombok.RequiredArgsConstructor;
import myaong.popolog.interviewservice.common.exception.ApiCode;
import myaong.popolog.interviewservice.common.exception.ApiException;
import myaong.popolog.interviewservice.dto.request.MessageRequest;
import myaong.popolog.interviewservice.dto.request.MessageUpdateRequest;
import myaong.popolog.interviewservice.dto.response.MessageResponse;
import myaong.popolog.interviewservice.dto.response.NewMessageResponse;
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
    public MessageResponse sendMessage(Long interviewId, MessageRequest messageRequest) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new ApiException(ApiCode.INTERVIEW_NOT_FOUND));

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
    public MessageResponse updateMessage(Long messageId, MessageUpdateRequest request) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ApiException(ApiCode.DB_ERROR));

        message.updateContent(request.getContent());

        return MessageResponse.builder()
                .messageId(message.getId())
                .build();
    }
}
