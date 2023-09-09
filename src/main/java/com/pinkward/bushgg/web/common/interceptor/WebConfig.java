package com.pinkward.bushgg.web.common.interceptor;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터 등록 자바 설정 클래스 구현
 *
 * @author 에너자이조 김기정
 * @since 2023. 9. 4.
 * @version 1.0
 * @see  com.pinkward.bushgg.web.common.interceptor.LoggerInterceptor
 * @see  com.pinkward.bushgg.web.common.interceptor.LoginCheckInterceptor
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	public List<String> loginEssential = Arrays.asList("/**");
	
	public List<String> loginNotEssential = Arrays.asList("/", "/**/*.ico", "/**/*.html", "/**/css/**", "/**/js/**", "/**/vendor/**", 
				"/error", "/member", "/member/register", "/member/login", "/member/logout", "/member/valid/{path}", "/member/rest/{path}");

	 @Override
	 public void addInterceptors(InterceptorRegistry registry) {
		 // 세부 컨트롤러 로그 기록 인터셉터 등록
		 //registry.addInterceptor(new LoggerInterceptor())
		 //.order(1)
		 // 하위 패스에 상관없이 모든 요청에 인터셉터 적용 
		 //.addPathPatterns("/**")
		 //인터셉터에서 제외할 패턴 설정
		 //.excludePathPatterns("/*.ico", "/*.html", "/css/**", "/vendor/**", "/error"); 
		 
		 // 세부 컨트롤러 실행 전 로그인 체크 인터셉터 등록
//		 registry.addInterceptor(new LoginCheckInterceptor())
//		 .order(1)
//		 .addPathPatterns(loginEssential)
//		 .excludePathPatterns(loginNotEssential);
	}
}





