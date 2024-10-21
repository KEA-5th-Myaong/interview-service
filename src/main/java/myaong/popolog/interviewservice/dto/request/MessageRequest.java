package myaong.popolog.interviewservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MessageRequest {

    @NotBlank(message = "메시지를 전송하는 사람의 역할을 입력해주세요")
    private String sender;

    @NotBlank(message = "메시지 내용을 입력해주세요")
    private String content;
}
