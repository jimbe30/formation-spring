package net.jmb.tuto.spring.repository;

import java.util.ArrayList;
import java.util.List;

import net.jmb.tuto.spring.entity.Article;

public class ArticleDatabaseRepository implements ArticleRepositoryInterface {
	
	static final List<Article> CATALOGUE_ARTICLES = new ArrayList<>();
	static {
		CATALOGUE_ARTICLES.add(new Article("Vinaigre blanc", 5.00));
		CATALOGUE_ARTICLES.add(new Article("Détachant avant lavage", 18.50));
		CATALOGUE_ARTICLES.add(new Article("Déboucheur liquide", 35.90));
		CATALOGUE_ARTICLES.add(new Article("soude caustique", 7.00));
		CATALOGUE_ARTICLES.add(new Article("Seau + serpillère", 8.90));
		CATALOGUE_ARTICLES.add(new Article("Gel dégraissant antitarte", 13.70));
		
	}
	
	@Override
	public Article findArticle(int num) {
		return (num >= 0 && num < CATALOGUE_ARTICLES.size()) ? CATALOGUE_ARTICLES.get(num) : null;
	}
	
	@Override
	public List<Article> getAllArticles() {
		return CATALOGUE_ARTICLES;
	}

}
