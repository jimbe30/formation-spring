package net.jmb.tuto.spring.repository;

import java.util.ArrayList;
import java.util.List;

import net.jmb.tuto.spring.entity.Article;

public class ArticleRepository {
	
	static final List<Article> CATALOGUE_ARTICLES = new ArrayList<>();
	static {
		CATALOGUE_ARTICLES.add(new Article("Pneu Michelin 200", 95.00));
		CATALOGUE_ARTICLES.add(new Article("Pneu Michelin 215", 118.50));
		CATALOGUE_ARTICLES.add(new Article("Pneu Michelin 225", 135.90));
		CATALOGUE_ARTICLES.add(new Article("Pneu Bridgestone 185", 70));
		CATALOGUE_ARTICLES.add(new Article("Pneu Bridgestone 200", 78.90));
		CATALOGUE_ARTICLES.add(new Article("Pneu Goodyear 215", 193.70));
		CATALOGUE_ARTICLES.add(new Article("Pneu Pirelli 225", 145.60));
		
	}
	
	public Article findArticle(int num) {
		return (num >= 0 && num < CATALOGUE_ARTICLES.size()) ? CATALOGUE_ARTICLES.get(num) : null;
	}
	
	public List<Article> getAllArticles() {
		return CATALOGUE_ARTICLES;
	}

}
