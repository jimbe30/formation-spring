package net.jmb.tuto.spring.config.contexte2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import net.jmb.tuto.spring.repository.ArticleDatabaseRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogDetailService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.ClientServiceInterface;
import net.jmb.tuto.spring.service.ClientSimpleService;
import net.jmb.tuto.spring.service.DevisRemiseService;
import net.jmb.tuto.spring.service.DevisServiceInterface;

@Configuration
@ConditionalOnProperty(name = "contexte", havingValue = "2")
@PropertySource(value = "classpath:/application.properties")
public class Contexte2Config {
	
	@Value("${app.contexte2.remise}")
	int remise = 10;
	
	@Bean
	@Scope(
		scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE
		, proxyMode = ScopedProxyMode.INTERFACES
	)
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogDetailService catalogDetailService = new CatalogDetailService();
		catalogDetailService.setArticleRepository(articleRepository);
		return catalogDetailService;
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleDatabaseRepository();
	}	
	@Bean
	public DevisServiceInterface devisService(CatalogServiceInterface catalogService, ClientServiceInterface clientService) {
		DevisRemiseService devisRemiseService = new DevisRemiseService();
		devisRemiseService.setCatalogService(catalogService);
		devisRemiseService.setClientService(clientService);
		devisRemiseService.setRemise(remise);
		return devisRemiseService;
	}
	
	@Bean
	public ClientServiceInterface clientService() {
		ClientSimpleService clientSimpleService = new ClientSimpleService();
		return clientSimpleService;
	}

}
