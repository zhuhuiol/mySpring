package com.homolo.homolo.service.impl;

import com.homolo.homolo.service.TestWebservice;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-1-17 下午1:47
 */
@Service
@WebService(endpointInterface = "com.homolo.homolo.service.TestWebservice", serviceName = "TestWebservice")
public class TestWebserviceImpl implements TestWebservice {
	@Override
	public String testOne(String xml) {
		return "接口測試:" + xml;
	}
}
