package com.pinkward.bushgg.web.member.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.pinkward.bushgg.domain.member.dto.MemberDTO;

@Component
public class MemberValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberDTO.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		MemberDTO member = (MemberDTO) target;
		// 폼 필드 검증
		if (!StringUtils.hasText(member.getLoginId())) {
			errors.rejectValue("id", "required");
			
		} else {
			if (member.getLoginId().length() < 6 || member.getLoginId().length() > 12) {
				errors.rejectValue("id", "range", new Object[]{6, 12}, null);
			}
		}
		
		if (!StringUtils.hasText(member.getPasswd())) {
			errors.rejectValue("passwd", "required");
		}
		
		if (!StringUtils.hasText(member.getNickName())) {
		     errors.rejectValue("nickName", "required");
		}
		if (!StringUtils.hasText(member.getEmail())) {
			errors.rejectValue("email", "required");
		}
		
	}
}

