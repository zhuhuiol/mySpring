package com.homolo.homolo.test;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.util.LoggerNameUtil;
import org.slf4j.helpers.Util;

/**
 * 实现要求：
 * 1、根据以下代码片段，参考log4j/slf4j等公共日志库编写一个自定义的简易日志类；
 * 2、至少需要支持文件输出、控制台输出二种日志输出方式，并支持同时输出到文件和控制台；
 * 3、支持DEBUG/INFO/WARN/ERROR四种日志级别；
 * 4、请合理进行设计模式、接口类、抽象类等设计；
 * 5、注意代码注释书写。
 */
public class CustomLogTest {

	public static void main(String[] args) {
		KLLogger logger = CustomLoggerFactory.getLogger(KLLogger.class);

		// TODO:

		logger.debug("debug 1...");
		logger.debug("debug 2...");
		logger.info("info 1...");
		logger.warn("warn 1...");
		logger.error("error 1...");
	}
}

enum DebugLevel {
	DEBUG, INFO, WARN, ERROR;
}

class KLLogger implements CustomLog{

	@Override
	public void debug(String var1) {

	}

	@Override
	public void debug(String var1, Object var2) {

	}

	@Override
	public void debug(String var1, Object var2, Object var3) {

	}

	@Override
	public void debug(String var1, Object... var2) {

	}

	@Override
	public void debug(String var1, Throwable var2) {

	}

	@Override
	public void info(String var1) {

	}

	@Override
	public void info(String var1, Object var2) {

	}

	@Override
	public void info(String var1, Object var2, Object var3) {

	}

	@Override
	public void info(String var1, Object... var2) {

	}

	@Override
	public void info(String var1, Throwable var2) {

	}

	@Override
	public void warn(String var1) {

	}

	@Override
	public void warn(String var1, Object var2) {

	}

	@Override
	public void warn(String var1, Object... var2) {

	}

	@Override
	public void warn(String var1, Object var2, Object var3) {

	}

	@Override
	public void warn(String var1, Throwable var2) {

	}

	@Override
	public void error(String var1) {

	}

	@Override
	public void error(String var1, Object var2) {

	}

	@Override
	public void error(String var1, Object var2, Object var3) {

	}

	@Override
	public void error(String var1, Object... var2) {

	}

	@Override
	public void error(String var1, Throwable var2) {

	}
}

/**
 * 定义日志接口规范.
 */
interface CustomLog{
	void debug(String var1);

	void debug(String var1, Object var2);

	void debug(String var1, Object var2, Object var3);

	void debug(String var1, Object... var2);

	void debug(String var1, Throwable var2);


	void info(String var1);

	void info(String var1, Object var2);

	void info(String var1, Object var2, Object var3);

	void info(String var1, Object... var2);

	void info(String var1, Throwable var2);

	void warn(String var1);

	void warn(String var1, Object var2);

	void warn(String var1, Object... var2);

	void warn(String var1, Object var2, Object var3);

	void warn(String var1, Throwable var2);

	void error(String var1);

	void error(String var1, Object var2);

	void error(String var1, Object var2, Object var3);

	void error(String var1, Object... var2);

	void error(String var1, Throwable var2);
}


//日志工厂类 生产日志.
final class CustomLoggerFactory {
	public static KLLogger getLogger(Class<?> clazz) {
//		KLLogger logger = getLogger(clazz.getName());
		KLLogger logger = new KLLogger();
		return logger;
	}

}