package com.jcb.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringUtils {

	private static BeanFactory factory;

	static {
		init();
	}

	private static void init() {
		ClassPathResource res = new ClassPathResource(
				SystemConfig.springBeansPath);
		factory = new XmlBeanFactory(res);
		PropertyPlaceholderConfigurer configurer = (PropertyPlaceholderConfigurer) factory
				.getBean("propertyPlaceholder");
		configurer.postProcessBeanFactory((XmlBeanFactory) factory);
	}

	public static BeanFactory getBeanFactory() {
		return factory;
	}

	public static Object getBean(String name) {
		return factory.getBean(name);
	}
}
