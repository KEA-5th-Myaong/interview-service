package myaong.popolog.interviewservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import myaong.popolog.interviewservice.common.exception.ApiResponse;
import myaong.popolog.interviewservice.dto.request.CreateInterviewRequest;
import myaong.popolog.interviewservice.dto.request.MessageRequest;
import myaong.popolog.interviewservice.dto.request.MessageUpdateRequest;
import myaong.popolog.interviewservice.dto.response.*;
import myaong.popolog.interviewservice.service.InterviewService;
import myaong.popolog.interviewservice.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;
    private final MessageService messageService;

    // 선택 가능한 회사 목록 조회 API (검색 기능 추가)
    @Operation(summary = "API 명세서 v0.3 line 53", description = "기업 목록 조회")
    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getAvailableCompanies() {
        List<CompanyResponse> response = interviewService.getAvailableCompanies();
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Operation(summary = "API 명세서 v0.3 line 54", description = "기업 검색")
    @GetMapping("/companies/search")
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> searchCompanies(@RequestParam String search) {
        List<CompanyResponse> response = interviewService.searchCompanies(search);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 특정 회사의 면접 질문 리스트 조회 API
    @Operation(summary = "API 명세서 v0.3 line 55", description = "면접 질문 리스트 조회")
    @GetMapping("/{companyId}/questions")
    public ResponseEntity<ApiResponse<List<String>>> getInterviewQuestions(@PathVariable Long companyId) {
        List<String> questions = interviewService.getInterviewQuestions(companyId);
        return ResponseEntity.ok(ApiResponse.onSuccess(questions));
    }

    // 면접 생성 API
    @Operation(summary = "API 명세서 v0.3 line 56", description = "모의 면접 생성")
    @PostMapping("/")
    public ResponseEntity<ApiResponse<CreateInterviewResponse>> createInterview(
            @Valid @RequestBody CreateInterviewRequest interviewRequest) {
        CreateInterviewResponse response = interviewService.createInterview(interviewRequest);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 메시지 전송 API
    @Operation(summary = "API 명세서 v0.3 line 57", description = "모의 면접 메시지 작성")
    @PostMapping("/messages")
    public ResponseEntity<ApiResponse<MessageResponse>> sendMessage(
            @RequestParam Long interviewId,
            @Valid @RequestBody MessageRequest messageRequest) {
        MessageResponse response = messageService.sendMessage(interviewId, messageRequest);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 메시지 수정 API
    @Operation(summary = "API 명세서 v0.3 line 58", description = "모의 면접 메시지 수정")
    @PutMapping("/messages/{messageId}")
    public ResponseEntity<ApiResponse<MessageResponse>> updateMessage(
            @PathVariable Long messageId,
            @RequestBody @Valid MessageUpdateRequest request) {
        // 주입받은 messageService 인스턴스를 통해 메서드 호출
        MessageResponse response = messageService.updateMessage(messageId, request);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 새 질문 생성 API
    @Operation(summary = "API 명세서 v0.3 line 59", description = "새 질문 생성")
    @GetMapping("/{interviewId}/q")
    public ResponseEntity<ApiResponse<NewMessageResponse>> generateNewQuestion(
            @PathVariable Long interviewId) {
        NewMessageResponse newQuestion = interviewService.generateNewQuestion(interviewId);
        return ResponseEntity.ok(ApiResponse.onSuccess(newQuestion));
    }

    // 꼬리 질문 생성 API
    @Operation(summary = "API 명세서 v0.3 line 60", description = "꼬리 질문 반환")
    @GetMapping("/{interviewId}/follow-up-q")
    public ResponseEntity<ApiResponse<NewMessageResponse>> generateFollowUpQuestion(
            @PathVariable Long interviewId) {
        NewMessageResponse response = interviewService.generateFollowUpQuestion(interviewId);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    // 면접 기록 목록 조회 API
    @Operation(summary = "API 명세서 v0.3 line 61", description = "면접 기록 목록 조회")
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<InterviewListResponse>>> getInterviewList() {
        List<InterviewListResponse> interviewList = interviewService.getInterviewList();
        return ResponseEntity.ok(ApiResponse.onSuccess(interviewList));
    }

    // 면접 기록 조회 API
    @Operation(summary = "API 명세서 v0.3 line 62", description = "면접 기록 조회")
    @GetMapping("/{interviewId}/messages")
    public ResponseEntity<ApiResponse<List<DetailMessageResponse>>> getInterviewMessages(
            @PathVariable Long interviewId) {
        List<DetailMessageResponse> messages = interviewService.getInterviewMessages(interviewId);
        return ResponseEntity.ok(ApiResponse.onSuccess(messages));
    }

    // 면접 기록 삭제 API
    @Operation(summary = "API 명세서 v0.3 line 63", description = "면접 기록 삭제")
    @DeleteMapping("/{interviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteInterview(@PathVariable Long interviewId) {
        interviewService.deleteInterview(interviewId);  // 서비스에서 면접 삭제 호출
        return ResponseEntity.ok(ApiResponse.onSuccess(null));  // 성공 응답
    }


}