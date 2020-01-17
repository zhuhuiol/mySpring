package com.homolo.homolo.spring;

import com.homolo.homolo.service.impl.TestWebserviceImpl;
import com.homolo.homolo.service.impl.TestWebserviceImplV2;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @Author: ZH
 * @Description: 默认.
 * @Date: 20-1-17 下午2:28
 */
@Configuration
public class WebserviceCxfConfig {

	private static final Logger logger = LoggerFactory.getLogger(WebserviceCxfConfig.class);

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}
//	@Bean
//	public ServletRegistrationBean dispatcherServlet() {
//		return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
//	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), new TestWebserviceImpl());
		endpoint.publish("/TestWebservice");
		this.logger.info("发布WebService服务......");
		return endpoint;
	}
	@Bean
	public Endpoint endpoint2() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), new TestWebserviceImplV2());
		endpoint.publish("/TestWebserviceV2");
		this.logger.info("发布WebServiceV2服务......");
		return endpoint;
	}

}
