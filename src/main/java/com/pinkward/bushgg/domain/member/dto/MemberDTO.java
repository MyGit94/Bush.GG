package com.pinkward.bushgg.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDTO {
	@NotBlank(message = "아이디는 필수 입력 항목입니다.")
	@Size(min = 4, max = 12, message = "아이디는 6~12자 사이여야 합니다.")
	private String loginId;
	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	@Size(min = 2, max = 12)
	private String nickName;
	@Size(min = 4, max = 8, message = "비밀번호는 4~8자 사이여야 합니다.")
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String passwd;
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	private String email;
	private String role;
	private String regdate;
}





