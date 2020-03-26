package com.homolo.homolo.filters;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ZH
 * @Description: 自定义用户登录过滤器.
 * @Date: 20-1-17 上午9:44
 */
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {


	public CustomLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
		setAuthenticationManager(authenticationManager);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
//		checkUser(username, password);
//		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//		simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(username, password);
		return getAuthenticationManager().authenticate(token);
	}


}
