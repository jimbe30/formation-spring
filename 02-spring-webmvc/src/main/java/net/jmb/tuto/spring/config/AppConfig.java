package net.jmb.tuto.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.jmb.tuto.spring.controller.ArticleControllerWeb;
import net.jmb.tuto.spring.controller.ClientControllerWeb;
import net.jmb.tuto.spring.controller.DevisControllerWeb;
import net.jmb.tuto.spring.factory.ControllerFactory;

@Configuration
@ComponentScan(basePackages = "net.jmb.tuto.spring.config.contexte${contexte}")
public class AppConfig {
	
	@Bean
	public ControllerFactory controllerFactory() {
		return ControllerFactory.getInstance();
	}
	@Bean
	public ClientControllerWeb clientControllerWeb() {
		return new ClientControllerWeb();
	}
	@Bean
	public ArticleControllerWeb articleControllerWeb() {
		return new ArticleControllerWeb();
	}
	@Bean
	public DevisControllerWeb devisControllerWeb() {
		return new DevisControllerWeb();
	}

}
