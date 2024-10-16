package myaong.popolog.interviewservice.enums;

public enum InterviewRole {

	INTERVIEWER, INTERVIEWEE;

	public static InterviewRole valueOfLower(String name) {
		return InterviewRole.valueOf(name.toUpperCase());
	}
}
