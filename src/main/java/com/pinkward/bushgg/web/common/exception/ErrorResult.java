package com.pinkward.bushgg.web.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 오류코드와 오류메시지 저장
 */
@AllArgsConstructor
@Data
public class ErrorResult {
	private String code;
	private String message;
}
