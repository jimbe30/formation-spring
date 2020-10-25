package net.jmb.tuto.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.jmb.tuto.spring.controller.ArticleController;
import net.jmb.tuto.spring.controller.ClientController;
import net.jmb.tuto.spring.controller.DevisController;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.DevisServiceInterface;

@Configuration
@ComponentScan
public class ApplicationConfig {
	
	@Bean
	public ClientController clientController() {
		ClientController clientController = new ClientController();
		return clientController;
	}
	@Bean
	public ArticleController articleController(CatalogServiceInterface catalogService) {
		ArticleController articleController = new ArticleController();
		articleController.setCatalogService(catalogService);
		return articleController;
	}
	@Bean
	public DevisController devisController(DevisServiceInterface devisService) {
		DevisController devisController = new DevisController();
		devisController.setDevisService(devisService);
		return devisController;
	}
}
