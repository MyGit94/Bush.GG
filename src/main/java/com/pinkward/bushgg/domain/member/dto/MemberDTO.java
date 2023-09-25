package com.pinkward.bushgg.domain.member.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 가입 정보 DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MemberDTO {

	@NotBlank(message = "아이디는 필수 입력 항목입니다.")
//	@Size(min = 6, max = 12, message = "아이디는 6~12자 사이여야 합니다.")
	private String loginId;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$", message = "비밀번호는 8~16자 사이에 숫자, 소문자, 특수 문자(@#$%^&+=!)를 포함해야 합니다.")
	private String passwd;

    // 비밀번호 확인 필드 추가
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$", message = "비밀번호는 8~16자 사이에 숫자, 소문자, 특수 문자(@#$%^&+=!)를 포함해야 합니다.")
    private String checkpasswd;

	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
//	@Size(min = 6, max = 12, message = "닉네임은 6~12자 사이여야 합니다.")
	private String nickName;

	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	private String email;

	private String role;
	private String regdate;
	private Boolean remember;

	private boolean loginIdExists; // 아이디 중복 체크 결과를 저장할 변수
}




