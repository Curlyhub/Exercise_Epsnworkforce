package com.epsnworkforce.homework;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


@SpringBootApplication
public class HomeworkApplication {


	/*
	 * Are you up?? - http://localhost:8080/actuator/health
	 * Wanna look at some tables?? - http://localhost:8080/h2-console/
	 * You can add Swag in order to verify all the actions,but we're done in test
	 */
	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	/*
	 * Pass the beans please...necessary to pass the test!!!
	 * ..look up at all the beans loaded into the app context
	 */
	@Bean
	@Profile("!test")
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {
			String[] beans = appContext.getBeanDefinitionNames();
			Arrays.stream(beans).sorted().forEach(System.out::println);
		};
	}

}
