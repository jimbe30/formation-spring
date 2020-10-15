package net.jmb.tuto.spring.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;

public class CatalogDetailService extends CatalogBasicService implements CatalogServiceInterface {
	
	public CatalogDetailService() {
		super();
	}
	
	public CatalogDetailService(ArticleRepositoryInterface articleRepository) {
		super(articleRepository);
	}

	@Override
	public String afficherListeArticles() {

		List<Article> allArticles = getArticleRepository().getAllArticles();
		StringBuffer sb = new StringBuffer();
		int i = 0;		

		for (Article article : allArticles) {
			String libelle = StringUtils.rightPad(
				new String(article.getLibelle())
					.concat(" - ")
					.concat("" + article.getTarif())
					.concat(" € "),
				50);
			sb.append(libelle).append(" [").append(++i).append("]").append("\n");
		}
		return sb.toString();
	}


}
