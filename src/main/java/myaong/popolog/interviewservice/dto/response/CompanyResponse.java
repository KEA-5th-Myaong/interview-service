package myaong.popolog.interviewservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyResponse {
    private Long companyId; // 회사 아이디
    private String companyName; // 회사 이름
}