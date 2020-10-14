package net.jmb.tuto.spring.service;

import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

public abstract class CatalogAbstractService implements CatalogServiceInterface {

	ArticleRepositoryInterface articleRepository;
	
	public CatalogAbstractService(ArticleRepositoryInterface articleRepository) {
		super();
		this.articleRepository = articleRepository;
	}

	public ArticleRepositoryInterface getArticleRepository() {		
		return articleRepository;
	}

	public void setArticleRepository(ArticleRepositoryInterface articleRepository) {
		this.articleRepository = articleRepository;
	}	


}
