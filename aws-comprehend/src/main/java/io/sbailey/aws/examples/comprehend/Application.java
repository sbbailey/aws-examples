package io.sbailey.aws.examples.comprehend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class Application{
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	
	public static void main(String[] args) {
		logger.info("initialising example application");
//		SpringApplication.run(Application.class);
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
		logger.info("getting bean");
		ComprehendExample comprehendExample = ctx.getBean(ComprehendExample.class);
		comprehendExample.runExample();
	}
	
	
	
//	@Override
//	public void run(String... args) throws Exception {
//		logger.info("running...");
//	}
	
	
	
//	@Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }
	
	
//	@Bean
//	public ComprehendExample comprehendExample(){
//		return new ComprehendExample();
//	}
	

}
