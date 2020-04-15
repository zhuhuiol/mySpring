package com.homolo.homolo.filters;

import com.homolo.homolo.utils.Rc4Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomLoginFilter.class);

	public CustomLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
		setAuthenticationManager(authenticationManager);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		String username = request.getParameter("zhun");
		String password = request.getParameter("zhpw");
		password = Rc4Util.getDeData(password, Rc4Util.key);
		UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(username, password);
		token.setDetails(authenticationDetailsSource.buildDetails(request));
		return getAuthenticationManager().authenticate(token);
	}


}
