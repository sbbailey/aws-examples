package io.sbailey.blog.aws.examples.microservices.accounts;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class BankingAccountsApplication {
	
	private static final Logger appLog = LoggerFactory.getLogger("applicationLog");
	
	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				appLog.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				appLog.info("ServletContext destroyed");
			}

		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BankingAccountsApplication.class, args);
	}
}
