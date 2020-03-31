package com.homolo.homolo.test;

import org.junit.Test;

import java.util.UUID;

public class UUIDTest {

	@Test
	public void test1() {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
