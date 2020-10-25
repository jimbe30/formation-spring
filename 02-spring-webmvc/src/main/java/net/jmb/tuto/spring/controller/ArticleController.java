package net.jmb.tuto.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import net.jmb.tuto.spring.service.CatalogServiceInterface;

public class ArticleController {
	
	@Resource(name = "catalogService")
	CatalogServiceInterface service;
	
	public List<Integer> choisirArticles() {

		if (service != null) {
			
			List<Integer> numArticles = new ArrayList<Integer>();
			
			// On propose un choix dans la liste des articles disponibles

			System.out.println("2. Choisissez un ou plusieurs articles dans la liste ci-dessous");
			System.out.println("   -> Tapez les n° correspondants puis n'importe quelle lettre pour continuer");
			System.out.println();

			System.out.println(service.afficherListeArticles());

			// On lit les n° d'articles choisis

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextInt()) {
				numArticles.add(scanner.nextInt());
			}
			System.out.println();

			return numArticles;
		}
		return null;
	}

}