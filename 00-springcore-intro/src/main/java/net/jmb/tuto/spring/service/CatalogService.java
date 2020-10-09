package net.jmb.tuto.spring.service;

import java.util.List;

import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.repository.ArticleRepository;

public class CatalogService {

	
	// KO : le formattage de la sortie est à placer dans un contrôleur plutôt qu'un service (interaction utilisateur)
	
	public String afficherListeArticles() {

		ArticleRepository articleRepository = new ArticleRepository(); // KO

		List<Article> allArticles = articleRepository.getAllArticles();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Article article : allArticles) {
			sb.append(article.getLibelle()).append(" [").append(++i).append("]").append("\n");
		}
		return sb.toString();
	}
	
		
	public Article chercherArticle(int numArticle) {
		
		ArticleRepository articleRepository = new ArticleRepository(); // KO
		
		return articleRepository.findArticle(numArticle-1);
	}

}
