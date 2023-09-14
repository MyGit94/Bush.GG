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
public class Member {
	@NotBlank(message = "아이디는 필수 입력 항목입니다.")
	@Size(min = 6, max = 12)
	private String loginid;
	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	@Size(min = 6, max = 12)
	private String nickname;
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String passwd;
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	private String email;
	private String regdate;
	private String role;
}





