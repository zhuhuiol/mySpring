package com.homolo.homolo.controller;

import com.homolo.homolo.constants.ReturnCode;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import com.homolo.homolo.utils.Rc4Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-9 下午12:40
 */
@Controller
@Service
public class LoginController {

	@Autowired
	private UserDateilServiceImpl userDetailService;


	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = {"/", "/hello"}, method = RequestMethod.GET)
	public String welcome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.logger.info("用户:[{}]登录成功", authentication.getName());
		return "hello";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {

		return "loginTest";
	}
	@RequestMapping(value = "/api/pwdEncrypt")
	@ResponseBody
	public Object pwdEncrypt(HttpServletRequest request, @RequestBody Map<Object, Object> params)  {
		String zhpw = (String) params.get("password");
		Map<String, Object> map = new HashMap<>();
		map.put("code", ReturnCode.FAILURE);
		if (zhpw == null) {
			map.put("msg", "参数为空");
			return map;
		}
		String password = null;
		try {
			password = Rc4Util.getEnData(zhpw, Rc4Util.key);
			map.put("password", password);
			logger.info("password:" + password);
			map.put("code", ReturnCode.SUCCESS);
			return map;
		} catch (UnsupportedEncodingException e) {
			this.logger.error("获取加密密码失败：{}", e.getMessage(), e);
			map.put("msg", "加密密码失败");
			return map;
		}
	}


	@RequestMapping(value = "/testI", method = RequestMethod.GET)
	@ResponseBody
	public String testI(HttpServletRequest request) {
		String numStr = request.getParameter("num");
		int num = 1;
		if (StringUtils.isNotBlank(numStr)) {
			num = Integer.parseInt(numStr);
		}
		this.userDetailService.testI(num);
		logger.info("执行插入操作完毕，共" + num + "条");
		return "执行完成,共" + num + "条";
	}

	@RequestMapping(value = "/testBatchInsertProcedure", method = RequestMethod.GET)
	@ResponseBody
	public String testBatchInsertProcedure(HttpServletRequest request) {
		String numStr = request.getParameter("num");
		int num = 1;
		if (StringUtils.isNotBlank(numStr)) {
			try {
				num = Integer.parseInt(numStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		this.userDetailService.testBatchInsertProcedure(num);
		logger.info("执行插入操作完毕，共" + num + "条");
		return "执行完成,共" + num + "条";
	}




}
