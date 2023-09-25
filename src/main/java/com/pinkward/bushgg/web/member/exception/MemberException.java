package com.pinkward.bushgg.web.member.exception;

/**
 *  사용자 정의 예외를 처리하고 예외가 발생한 원인을 설명하기 위한 클래스
 */
public class MemberException extends RuntimeException {
	
	public MemberException() {
		super();
	}

	public MemberException(String message) {
		super(message);
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
	}
}