package myaong.popolog.interviewservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateInterviewRequest {
    @NotNull
    private Long companyId;
}
