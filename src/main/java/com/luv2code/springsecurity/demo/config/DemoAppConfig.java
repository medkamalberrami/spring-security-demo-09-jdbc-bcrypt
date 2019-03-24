package com.luv2code.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.luv2code.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {

	// set up variable to hold the properties
	@Autowired
	private Environment env;

	// set up a logger for diagnostic

	private Logger log = Logger.getLogger(getClass().getName());

	// define a bean for ViewResolver

	@Bean
	public ViewResolver viewResolver() {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;

	}

	// define a bean for our security

	@Bean			  
	public DataSource securityDatasource() {

		// create connection pool
		ComboPooledDataSource securityDatasource = new ComboPooledDataSource();

		// set JDBC driver class
		try {
			securityDatasource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {

			throw new RuntimeException(e);
		}

		// log the connection properties

		log.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
		log.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));
		log.info(">>> jdbc.password=" + env.getProperty("jdbc.password"));

		// set database connection properties

		securityDatasource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDatasource.setUser(env.getProperty("jdbc.user"));
		securityDatasource.setPassword(env.getProperty("jdbc.password"));

		// set connection pool properties

		securityDatasource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDatasource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDatasource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDatasource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));


		return securityDatasource;
	}

	private int getIntProperty(String propertyName) {

		String property = env.getProperty(propertyName);

		int intPropertyValue = Integer.parseInt(property);

		return intPropertyValue;

	}

}
