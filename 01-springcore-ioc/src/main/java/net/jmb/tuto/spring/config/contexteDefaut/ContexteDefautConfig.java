package net.jmb.tuto.spring.config.contexteDefaut;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import net.jmb.tuto.spring.repository.ArticleMemoryRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogBasicService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.DevisServiceInterface;
import net.jmb.tuto.spring.service.DevisSimpleService;

@Configuration
@ConditionalOnProperty(value = "contexte", matchIfMissing = true, havingValue = "1")
public class ContexteDefautConfig {
	
	@Bean
	@Scope(
		scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE 
		, proxyMode = ScopedProxyMode.INTERFACES
	)
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogBasicService catalogBasicService = new CatalogBasicService();
		catalogBasicService.setArticleRepository(articleRepository);
		return catalogBasicService;
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleMemoryRepository();
	}	
	@Bean
	public DevisServiceInterface devisService(CatalogServiceInterface catalogService) {
		DevisSimpleService devisSimpleService = new DevisSimpleService();
		devisSimpleService.setCatalogService(catalogService);
		return devisSimpleService;
	}

}
