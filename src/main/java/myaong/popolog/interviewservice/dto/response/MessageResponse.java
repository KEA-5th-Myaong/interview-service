package myaong.popolog.interviewservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Long messageId;   // 생성된 메시지 ID
}
