package com.homolo.homolo.annotations.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-1-10 下午5:10
 */
@Component
public class TestAutowired implements BeanPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(TestAutowired.class);
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		logger.info("TestAutowired annotation Before .....");
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		logger.info("TestAutowired annotation After .....");
		return bean;
	}
}
