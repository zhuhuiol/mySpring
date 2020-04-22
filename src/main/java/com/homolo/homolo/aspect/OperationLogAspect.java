package com.homolo.homolo.aspect;

import com.homolo.homolo.annotations.SystemLog;
import com.homolo.homolo.entity.User;
import com.homolo.homolo.entity.logs.OperationLog;
import com.homolo.homolo.enums.LogType;
import com.homolo.homolo.enums.OperationLogType;
import com.homolo.homolo.service.OperationLogService;
import com.homolo.homolo.spring.UserSessionFactory;
import com.homolo.homolo.utils.UUIDUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class OperationLogAspect {

	@Autowired
	private OperationLogService operationLogService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationLogAspect.class);

	/**
	 * 切入点,扫描自定义注解.
	 */
//	@Pointcut("execution(public * com.homolo.homolo.service.impl.*.*(..)) && @annotation(com.homolo.homolo.annotations.SystemLog)")
	@Pointcut("@annotation(com.homolo.homolo.annotations.SystemLog)")
	public void controllerAspect() {
	}

	@Before("controllerAspect()")
	public void doBefor(JoinPoint joinPoint) {
		LOGGER.info("前置通知-------:" + joinPoint);
	}
//  注意：有环绕通知就不会触发异常通知和前置通知
//	@Around("controllerAspect()")
//	public Object doAround(ProceedingJoinPoint joinPoint) {
//		LOGGER.info("环绕通知-------");
//		Object result = null;
//		try {
//			//执行切面切入的方法
//			result = joinPoint.proceed();
//		} catch (Throwable throwable) {
//			throwable.printStackTrace();
//		}
//		return result;
//	}

	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
		LOGGER.info("后置通知-------:" + joinPoint);
	}

	@AfterReturning("controllerAspect() && @annotation(systemLog)")
	public void afterReturn(JoinPoint joinPoint, SystemLog systemLog) {
		generateLog(joinPoint, null, systemLog);

	}

	@AfterThrowing(pointcut = "controllerAspect() && @annotation(systemLog)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e, SystemLog systemLog) {
		generateLog(joinPoint, e, systemLog);
	}


	private void generateLog(JoinPoint joinPoint, Exception e, SystemLog systemLog) {
		String operationName = systemLog.operationName();
		if (e != null) {
			operationName += "-->出错了：" + e.getMessage();
		}
		OperationLogType operationLogType = systemLog.operationType();
		Authentication authentication = UserSessionFactory.currentUser();
		Object username = authentication.getPrincipal();
		User user = (User) authentication.getDetails();
		WebAuthenticationDetails details = (WebAuthenticationDetails) user.getDetails();
		String ip = details.getRemoteAddress();
		if (LogType.OPERATION.equals(systemLog.type())) {
			//记录操作日志
			OperationLog log = new OperationLog();
			log.setCreateDate(new Date());
			log.setDescription(operationName);
			log.setId(UUIDUtil.generateUUID(UUIDUtil.type.LOG));
			log.setMethod(joinPoint.getSignature().getName());
			log.setOperationType(operationLogType.name());
			log.setRequestIp(ip);
			log.setUserName(username.toString());
			//入库
			this.operationLogService.generateLog(log);
		}
//		else if (LogType.OTHER.equals(systemLog.type())) {
//			//其他日志
//
//		}
	}

}
