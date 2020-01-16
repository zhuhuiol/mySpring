package com.homolo.homolo.controller;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-9 下午12:40
 */
@Controller
public class LoginController {

	@Autowired
	private UserDateilServiceImpl userDetailService;

	@Autowired
	private User user;
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = {"/", "/hello"}, method = RequestMethod.GET)
	public String welcome() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		this.logger.info("用户信息:" + authentication.getName());
		this.logger.info("测试user信息:" + user.getUsername());
		return "hello";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "loginTest";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "loginTest";
	}

	@RequestMapping(value = "/testI", method = RequestMethod.GET)
	@ResponseBody
	public String testI(HttpServletRequest request) {
		String numStr = request.getParameter("num");
		int num = 1;
		if (StringUtils.isNotBlank(numStr)) {
			try {
				num = Integer.parseInt(numStr);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
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
