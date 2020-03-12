package com.homolo.homolo.utils;/**
 * @Author: lsl
 * @Description:
 * @Date: Created on 上午10:51 20-3-12
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-3-12 上午10:51
 */
@Service
public class AsyncUtil {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Async()
	public void testAsyncMethod(int i) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("testAsyncMethod:" + i+",ThreadName:" + Thread.currentThread().getName());
	}
}
