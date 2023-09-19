package com.pinkward.bushgg.web.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그 기록 인터셉터 구현
 *
 * @author 에너자이조 김기정
 * @since 2023. 9. 4.
 * @version 1.0
 */
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();

		// @Controller가 아니라 /resources/static와 같은 정적 리소스 요청의 경우, ResourceHttpRequestHandler가 핸들러 정보로 전달된다.
		if (handler instanceof ParameterizableViewController handlerMethod) {
			// @Controller , @RequestMapping을 활용한 핸들러 매핑 시 핸들러 정보로 HandlerMethod 전달된다.
			// 호출할 세부 컨트롤러 메서드의 모든 정보가 포함되어 있다.
			// log.info("세부 컨트롤러 정보 : {}", handlerMethod.toString());
		}
		log.info("[로그 인터셉터 실행] : HTTP 요청(세부 컨트롤러 호출 전) => {}, {}", requestURI, handler);

		return true; // false 진행 X
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("[로그 인터셉터 실행] : HTTP 요청(세부 컨트롤러 호출 후) => {}", modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)	throws Exception {
		String requestURI = request.getRequestURI();
		log.info("[로그 인터셉터 실행] : HTT 응답(뷰 렌더링 이후) => {}", requestURI);
		if (exception != null) {
			log.error("세부 컨트롤러 호출 후 오류 발생 : {}", exception);
		}
	}
}
