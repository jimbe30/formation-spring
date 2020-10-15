package net.jmb.tuto.spring.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

public class CatalogBasicService extends CatalogAbstractService implements CatalogServiceInterface {

	
	public CatalogBasicService() {
		super();
	}
	
	public CatalogBasicService(ArticleRepositoryInterface articleRepository) {
		super(articleRepository);
	}


	@Override
	public String afficherListeArticles() {
		
		List<Article> allArticles = articleRepository.getAllArticles();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Article article : allArticles) {
			String libelle = StringUtils.rightPad(article.getLibelle(), 30);
			sb.append(libelle).append(" [").append(++i).append("]").append("\n");
		}
		return sb.toString();
	}
	
		
	@Override
	public Article chercherArticle(int numArticle) {
		
		return articleRepository.findArticle(numArticle-1);
	}

}
