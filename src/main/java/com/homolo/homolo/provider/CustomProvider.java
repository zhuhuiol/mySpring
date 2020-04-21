package com.homolo.homolo.provider;

import com.homolo.homolo.entity.User;
import com.homolo.homolo.service.impl.UserDateilServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: ZH
 * @Description: secuerty自定义provider验证.
 * @Date: 20-3-24 下午4:09
 */
public class CustomProvider implements AuthenticationProvider {

	@Autowired
	private UserDateilServiceImpl userDateilService;

	@Autowired
	PasswordEncoder passwordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		LOGGER.info("自定义provider验证用户,authentication:" + authentication.getName());
		try {
			User user = (User) this.userDateilService.loadUserByUsername((String) authentication.getPrincipal());
			UserDetails userDetails = user.getUser();
			if (userDetails == null) {
				throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
			}
			//判断用户密码
			this.checkUserPass(authentication, userDetails);
			//判定用户是否禁用，锁定
			this.check(userDetails);
			UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
			//保存用户信息时，去掉密码和里面的user登录认证信息
			user.setUser(null);
			user.setPassword(null);
			user.setDetails(authentication.getDetails());
			result.setDetails(user);
			return result;
		} catch (UsernameNotFoundException var4) {
			throw var4;
		} catch (InternalAuthenticationServiceException var5) {
			throw var5;
		} catch (Exception var6) {
			LOGGER.info("验证失败：{}.", var6.getMessage());
			throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
	}

	public void check(UserDetails user) {
		if (!user.isAccountNonLocked()) {
			throw new LockedException("User account is locked");
		} else if (!user.isEnabled()) {
			throw new DisabledException("User is disabled");
		} else if (!user.isAccountNonExpired()) {
			throw new AccountExpiredException("User account has expired");
		} else if (!user.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("User credentials have expired");
		}
	}

	public void checkUserPass(Authentication authentication, UserDetails userDetails) {
		//判定密码不为空
		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("密码为空");
		}
		//判定密码是否相等
		if (!this.passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
			throw new BadCredentialsException("密码错误");
		}
	}

}
