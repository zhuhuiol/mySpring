package com.homolo.homolo.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSessionFactory {
	private UserSessionFactory() {
	}

	public static Authentication currentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static boolean isUserSession() {
		return SecurityContextHolder.getContext().getAuthentication() != null;
	}
}
