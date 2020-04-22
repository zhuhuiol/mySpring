package com.homolo.homolo.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionFactory {
	private static Authentication authentication;
	private UserSessionFactory() {
	}

	public static Authentication currentUser() {
		if (authentication == null) {
			authentication = SecurityContextHolder.getContext().getAuthentication();
		}
		return authentication;
	}

	public static boolean isUserSession() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}
}
