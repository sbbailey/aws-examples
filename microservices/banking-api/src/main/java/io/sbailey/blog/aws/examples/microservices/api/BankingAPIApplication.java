package io.sbailey.blog.aws.examples.microservices.api;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class BankingAPIApplication {
	
	@Value("${app.load.bal.address}")
	private String balancerAddress;
	
	private static final Logger appLog = LoggerFactory.getLogger("applicationLog");
	
	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				appLog.info("ServletContext initialized");
				appLog.info("Balancer Address: " + balancerAddress);
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				appLog.info("ServletContext destroyed");
			}

		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BankingAPIApplication.class, args);
	}
	
	
	@Bean
	public AsyncHttpClient asyncHttpClient(){		
		return Dsl.asyncHttpClient();		
	}
}
