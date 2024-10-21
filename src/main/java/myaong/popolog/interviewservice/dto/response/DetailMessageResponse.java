package myaong.popolog.interviewservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailMessageResponse {
    private Long messageId;
    private String role;
    private String content;
}