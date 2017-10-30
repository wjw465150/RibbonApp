package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class RibbonApplication implements EnvironmentAware {
	private static Logger log = LoggerFactory.getLogger(RibbonApplication.class);

	public static void main(String[] args) {
		// SpringApplication.run(RibbonApplication.class, args);
		new SpringApplicationBuilder(RibbonApplication.class).web(true).run(args);
	}

	@Override
	public void setEnvironment(Environment env) {
		String[] dProfiles = env.getDefaultProfiles();
		String[] aProfiles = env.getActiveProfiles();

		System.out.println("Spring DefaultProfiles:" + java.util.Arrays.asList(dProfiles));
		System.out.println("Spring ActiveProfiles:" + java.util.Arrays.asList(aProfiles));
		if (aProfiles.length == 0) {
			System.out.println("Please set 'spring.profiles.active'");
			System.exit(-1);
		}

		log.debug("当前ActiveProfiles:" + env.getActiveProfiles().toString());
	}

	@Value("${security.user.name}")
	String user;

	@Value("${security.user.password}")
	String pass;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		BasicAuthRestTemplate result = new BasicAuthRestTemplate(user, pass);
		return result;
	}
}
