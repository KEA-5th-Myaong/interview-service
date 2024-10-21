package myaong.popolog.interviewservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageUpdateRequest {

    @NotBlank
    private String content;
}
