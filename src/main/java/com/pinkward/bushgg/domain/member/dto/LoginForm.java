package com.pinkward.bushgg.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 회원 로그인 정보 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@ToString
@Builder
public class LoginForm {
	
	@NotBlank(message = "아이디를 입력해 주세요.")
	@Size(min = 6, max = 12, message = "아이디는 6~12자 사이여야 합니다.")
	private String loginId;
	
	@NotBlank(message = "비밀번호를 입력해 주세요")
	@Size(min = 8, max = 16, message = "비밀번호는 8~16자 사이여야 합니다.")
	private String passwd;
	
	private String nickName;

}





