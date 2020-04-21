package com.homolo.homolo.spring;

import com.homolo.homolo.constants.ReturnCode;
import com.homolo.homolo.entity.User;
import com.homolo.homolo.entity.logs.LoginLog;
import com.homolo.homolo.enums.LoginLogType;
import com.homolo.homolo.filters.CustomLoginFilter;
import com.homolo.homolo.provider.CustomProvider;
import com.homolo.homolo.result.ServiceResult;
import com.homolo.homolo.service.LoginLogService;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import com.homolo.homolo.utils.UUIDUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZH
 * @Description:
 * @Date: 19-9-6 下午7:47
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private UserDateilServiceImpl userDateilService;
	@Autowired
	private LoginLogService loginLogService;

	//security配置,EnableGlobalMethodSecurity 允许配置注解
	// 教程：http://www.spring4all.com/article/428
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//内存用户
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("zhuhui").password(new BCryptPasswordEncoder().encode("zhuhui@#")).roles("admin");
		auth.userDetailsService(userDateilService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//配置自定义过滤器
		http.addFilterAt(customLoginFilter(), UsernamePasswordAuthenticationFilter.class);
		//控制访问权限
		http.authorizeRequests()
				.antMatchers("/hello").authenticated()
				.antMatchers("/testI").authenticated()
				.antMatchers("/testBatchInsertProcedure").authenticated()
				.antMatchers("/ws/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/test/**").permitAll()
//				.antMatchers("/login").permitAll()
		.anyRequest().authenticated()


		.and()
		.cors()
		.and()
		.csrf().disable()
		.formLogin()
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/hello");

		http.logout()
				//请求方式指定为get
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.addLogoutHandler(this.logoutHandler())
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID").permitAll();


	}

	/**
	 * 配置加密方式.
	 * @return
	 */
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * 放过请求路径双斜杠.
	 * @return .
	 */
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

	/**
	 * 自定义认证登录.
	 * @return
	 */
	@Bean
	public CustomLoginFilter customLoginFilter() {
		CustomLoginFilter customLoginFilter = new CustomLoginFilter("/login", this.authenticationManager());
		customLoginFilter.setAllowSessionCreation(true);
//		customLoginFilter.setAuthenticationManager(this.authenticationManager());
		customLoginFilter.setAuthenticationFailureHandler(this.failureHandler());
		customLoginFilter.setAuthenticationSuccessHandler(this.successHandler());
		return customLoginFilter;
	}

	/**
	 * 初始化自定义provider.
	 * @return dao
	 */
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider(){
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(this.userDateilService);
//		return daoAuthenticationProvider;
//	}
	@Bean
	public CustomProvider authenticationProvider(){
		CustomProvider provider = new CustomProvider();
		return provider;
	}

	/**
	 * 设置自定义验证provider.
	 * @return au
	 */
	@Bean
	public AuthenticationManager authenticationManager(){
		List<AuthenticationProvider> list = new ArrayList<>();
		list.add(this.authenticationProvider());
		return new ProviderManager(list);
	}

	/**
	 *自定义登录成功处理器，成功返回一个带有成功信息的Json数据包装类
	 */
	private AuthenticationSuccessHandler successHandler() {
		return (request, response, authentication) -> {
			this.generateLoginLog(LoginLogType.LOGIN.name(), ReturnCode.SUCCESS, authentication);
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("code", ReturnCode.SUCCESS);
			out.write(object.toString());
			out.flush();
			out.close();
		};
	}
	/**
	 *自定义登录失败处理器，成功返回一个带有失败信息的Json数据包装类
	 */
	private AuthenticationFailureHandler failureHandler() {
		return (request, response, authentication) -> {
			this.generateLoginLog(LoginLogType.LOGIN.name(), ReturnCode.FAILURE, request, authentication.getMessage());
			response.setContentType("application/json;charset=utf-8");
			PrintWriter out = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("code", ReturnCode.FAILURE);
			object.put("message", authentication.getMessage());
			out.write(object.toString());
			out.flush();
			out.close();
		};
	}

	/**
	 * 登出处理器.
	 * @return
	 */
	private LogoutHandler logoutHandler() {
		return (request, response, authentication) -> {
			this.generateLoginLog(LoginLogType.LOGOUT.name(), ReturnCode.SUCCESS, authentication);
			response.setContentType("application/json;charset=utf-8");
			try (PrintWriter out = response.getWriter()) {
				JSONObject object = new JSONObject();
				object.put("code", ReturnCode.SUCCESS);
				out.write(object.toString());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}

	/**
	 * 记录登录/登出成功日志.
	 * @param loginType 登录日志类型.
	 * @param result 结果.
	 * @param authentication au.
	 */
	private void generateLoginLog(String loginType, int result, Authentication authentication) {
		LoginLog log = new LoginLog();
		Object username = authentication.getPrincipal();
		User user = (User) authentication.getDetails();
		WebAuthenticationDetails details = (WebAuthenticationDetails) user.getDetails();
		String ip = details.getRemoteAddress();
		log.setId(UUIDUtil.generateUUID(UUIDUtil.type.LOG));
		log.setUserName(username.toString());
		log.setIp(ip);
		log.setLoginDate(new Date());
		log.setLoginLogType(loginType);
		log.setResult(result);
		if (LoginLogType.LOGIN.name().equals(loginType)) {
			log.setMessage("登录成功");
		} else {
			log.setMessage("登出成功");
		}
		this.loginLogService.generateLog(log);
	}

	/**
	 * 登录失败处理器.
	 * @param loginType
	 * @param result
	 * @param request
	 * @param message
	 */
	private void generateLoginLog(String loginType, int result, HttpServletRequest request, String message) {
		LoginLog log = new LoginLog();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		log.setId(UUIDUtil.generateUUID(UUIDUtil.type.LOG));
		log.setUserName(request.getParameter("zhun"));
		log.setIp(ip);
		log.setLoginDate(new Date());
		log.setLoginLogType(loginType);
		log.setResult(result);
		log.setMessage(message);
		this.loginLogService.generateLog(log);
	}
}
