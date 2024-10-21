package myaong.popolog.interviewservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewMessageResponse {
    private Long messageId;
    private String content;
}
