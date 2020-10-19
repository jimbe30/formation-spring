package net.jmb.tuto.spring.config.contexte1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.jmb.tuto.spring.repository.ArticleMemoryRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogBasicService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.DevisServiceInterface;
import net.jmb.tuto.spring.service.DevisSimpleService;

@Configuration
public class ContexteConfig {
	
	@Bean
	public CatalogServiceInterface catalogService() {
		return new CatalogBasicService();
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleMemoryRepository();
	}	
	@Bean
	public DevisServiceInterface devisService() {
		return new DevisSimpleService();
	}

}
