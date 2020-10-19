package net.jmb.tuto.spring.config.contexte2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jmb.tuto.spring.repository.ArticleDatabaseRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogDetailService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.ClientServiceInterface;
import net.jmb.tuto.spring.service.ClientSimpleService;
import net.jmb.tuto.spring.service.DevisRemiseService;
import net.jmb.tuto.spring.service.DevisServiceInterface;

@Configuration
public class ContexteConfig {
	
	@Bean
	public CatalogServiceInterface catalogService() {
		return new CatalogDetailService();
	}
	@Bean
	public ClientServiceInterface clientService() {
		return new ClientSimpleService();
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleDatabaseRepository();
	}	
	@Bean
	public DevisServiceInterface devisService() {
		return new DevisRemiseService();
	}

}
