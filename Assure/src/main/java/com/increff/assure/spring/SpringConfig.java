package com.increff.assure.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc

@ComponentScan("com.increff.assure")
@PropertySources({ //
	@PropertySource("classpath:Assure.properties") //
})
public class SpringConfig {

}
