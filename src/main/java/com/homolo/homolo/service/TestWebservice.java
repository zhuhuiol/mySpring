package com.homolo.homolo.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @Author: zh
 * @Description: webservice 接口服務測試.
 * @Date: Created on 下午1:45 20-1-17
 */
@WebService(name = "TestWebservice")
public interface TestWebservice {
	@WebMethod
	String testOne(String xml);
}
