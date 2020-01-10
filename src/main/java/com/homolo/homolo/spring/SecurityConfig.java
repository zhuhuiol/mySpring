package com.homolo.homolo.spring;

import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//控制访问权限
		http.authorizeRequests()
				.antMatchers("/hello").authenticated()
				.antMatchers("/testI").authenticated()
				.antMatchers("/testBatchInsertProcedure").authenticated()
//				.antMatchers("/logout").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/hello");

		http.logout()
				//请求方式指定为get
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
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

}
