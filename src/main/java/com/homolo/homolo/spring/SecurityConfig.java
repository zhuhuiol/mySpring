package com.homolo.homolo.spring;

import com.homolo.homolo.constants.ReturnCode;
import com.homolo.homolo.filters.CustomLoginFilter;
import com.homolo.homolo.provider.CustomProvider;
import com.homolo.homolo.result.ServiceResult;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
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
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
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
	UserDateilServiceImpl userDateilService;

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
				.logoutSuccessUrl("/hello")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.addLogoutHandler(new LogoutHandler() {
					@Override
					public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
						LOGGER.info("后置处理事件");
					}
				})
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

}
