package com.homolo.homolo.annotations.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author: ZH
 * @Description: testAutowired 实现-默认.
 * @Date: 20-1-10 下午5:10
 */
@Component
public class TestAutowiredImpl implements BeanPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(TestAutowiredImpl.class);
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Field [] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			com.homolo.homolo.annotations.TestAutowired testAutowired = field.getAnnotation(com.homolo.homolo.annotations.TestAutowired.class);
			if (testAutowired == null) {
				continue;
			}
			logger.info("TestAutowired set name .....");
			field.setAccessible(true);
			try {
				field.set(bean, testAutowired.name());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//		logger.info("TestAutowired annotation After .....");
		return bean;
	}
}
