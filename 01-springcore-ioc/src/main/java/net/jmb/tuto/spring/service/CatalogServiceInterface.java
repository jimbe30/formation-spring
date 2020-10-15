package net.jmb.tuto.spring.service;

import net.jmb.tuto.spring.entity.Article;

public interface CatalogServiceInterface {

	String afficherListeArticles();

	Article chercherArticle(int numArticle);

}