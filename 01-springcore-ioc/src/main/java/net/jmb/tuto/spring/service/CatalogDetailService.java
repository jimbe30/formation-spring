package net.jmb.tuto.spring.service;

import java.util.List;
import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

public class CatalogDetailService extends CatalogBasicService implements CatalogServiceInterface {
	
	public CatalogDetailService(ArticleRepositoryInterface articleRepository) {
		super(articleRepository);
	}

	@Override
	public String afficherListeArticles() {

		List<Article> allArticles = getArticleRepository().getAllArticles();
		StringBuffer sb = new StringBuffer();
		int i = 0;		

		for (Article article : allArticles) {
			sb.append(article.getLibelle())
			.append(" - ")
			.append(article.getTarif() + " € ")
			.append(" [").append(++i).append("]").append("\n");
		}
		return sb.toString();
	}


}
