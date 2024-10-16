package myaong.popolog.interviewservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myaong.popolog.interviewservice.enums.InterviewRole;

@Entity
@Table(name = "`message`")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;

	// 대상 면접
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interview_id", nullable = false, updatable = false)
	private Interview interview;

	// 메시지 역할
	@Enumerated(EnumType.STRING)
	@Column(name = "interview_role", nullable = false, updatable = false)
	private InterviewRole role;

	@Lob
	@Column(name = "content", nullable = false)
	private String content;

	@Builder
	public Message(Interview interview, InterviewRole interviewRole, String content) {
		this.interview = interview;
		this.role = interviewRole;
		this.content = content;

		interview.getMessages().add(this);
	}
}
