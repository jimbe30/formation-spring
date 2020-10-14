package net.jmb.tuto.spring.repository;

import java.util.List;

import net.jmb.tuto.spring.entity.Article;

public interface ArticleRepositoryInterface {

	Article findArticle(int num);

	List<Article> getAllArticles();

}