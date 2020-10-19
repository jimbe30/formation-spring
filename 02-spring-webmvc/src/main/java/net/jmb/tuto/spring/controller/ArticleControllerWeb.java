package net.jmb.tuto.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;

import net.jmb.tuto.spring.service.CatalogServiceInterface;

public class ArticleControllerWeb {
	
	@Autowired
	CatalogServiceInterface catalogService;	
	
	public String choisirArticles() {

		if (catalogService != null) {
			
			String listeArticles = catalogService.afficherListeArticles();
			
			System.out.println(listeArticles);

			// On lit les n° d'articles choisis
			return listeArticles;
		}
		return null;
	}


	

}
