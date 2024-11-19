package myaong.popolog.interviewservice.service;

import lombok.RequiredArgsConstructor;
import myaong.popolog.interviewservice.dto.response.CompanyResponse;
import myaong.popolog.interviewservice.entity.Company;
import myaong.popolog.interviewservice.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    // 기업 조회
    @Transactional(readOnly = true)
    public List<CompanyResponse> getCompanies(String search, Long memberId) {
        List<Company> companies;

        if (search == null || search.isBlank()) {
            companies = companyRepository.findAll();  // 전체 기업 조회
        } else {
            companies = companyRepository.findByNameContainingIgnoreCase(search);  // 검색된 기업 조회
        }

        return companies.stream()
                .map(company -> new CompanyResponse(company.getId(), company.getName()))
                .collect(Collectors.toList());
    }
}
