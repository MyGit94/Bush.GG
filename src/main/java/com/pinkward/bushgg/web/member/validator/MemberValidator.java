//package com.pinkward.bushgg.web.member.validator;
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import com.pinkward.bushgg.domain.member.dto.Member;
//
//@Component
//public class MemberValidator implements Validator{
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return Member.class.isAssignableFrom(clazz);
//	}
//	
//	@Override
//	public void validate(Object target, Errors errors) {
//		Member member = (Member) target;
//		// 폼 필드 검증
//		if (!StringUtils.hasText(member.getId())) {
//			errors.rejectValue("id", "required");
//			
//		} else {
//			if (member.getId().length() < 6 || member.getId().length() > 12) {
//				errors.rejectValue("id", "range", new Object[]{6, 12}, null);
//			}
//		}
//		
//		if (!StringUtils.hasText(member.getPasswd())) {
//			errors.rejectValue("passwd", "required");
//		}
//		
//		if (!StringUtils.hasText(member.getName())) {
//		     errors.rejectValue("name", "required");
//		}
//		if (!StringUtils.hasText(member.getEmail())) {
//			errors.rejectValue("email", "required");
//		}
//		
//		// 특정 입력 필드가 아닌 복합적 데이터 검증 시
//		// 예) 쇼핑몰 상품 주문 시 (주문갯수 * 가격 = 10000원 이상이어야 하는 경우)
//		int totalPrice = 10000;
//		if(totalPrice < 10000) {
//			errors.reject("min.totalprice", new Object[]{10000, totalPrice}, null);
//		}
//	}
//}
//
