package com.pinkward.bushgg.domain.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/**
 * 서비스 객체 로그 Aspect 구현
 *
 * @author  에너자이조 김기정
 * @since   2023. 9. 4.
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class LoggerAspect {
	
	@Around("execution(* com.ezen.springmvc.domain..*(..))")
	public Object aroundLog(ProceedingJoinPoint joinPoint) {
		Object obj = null;
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().toShortString();
		Object[] arsg = joinPoint.getArgs();
		log.info("=> " + className + "#" + methodName + "비즈니스 메소드 실행");
		for (Object object : arsg) {
			log.info("  -> 전달인자 : " + object.getClass().getName() + " / " + object);
		}
		try {
			obj = joinPoint.proceed();
		} catch (Throwable e) {
			log.warn(e.toString());
		}
		return obj;
	}
}











