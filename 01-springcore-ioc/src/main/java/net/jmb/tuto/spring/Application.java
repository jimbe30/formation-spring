package net.jmb.tuto.spring;

import java.util.List;
import java.util.Scanner;

import net.jmb.tuto.spring.controller.ArticleController;
import net.jmb.tuto.spring.controller.ClientController;
import net.jmb.tuto.spring.controller.DevisController;
import net.jmb.tuto.spring.entity.Client;
import net.jmb.tuto.spring.repository.ArticleDatabaseRepository;
import net.jmb.tuto.spring.repository.ArticleMemoryRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogBasicService;
import net.jmb.tuto.spring.service.CatalogDetailService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.ClientServiceInterface;
import net.jmb.tuto.spring.service.ClientSimpleService;
import net.jmb.tuto.spring.service.DevisRemiseService;
import net.jmb.tuto.spring.service.DevisServiceInterface;
import net.jmb.tuto.spring.service.DevisSimpleService;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// On détermine quel est le contexte
		System.out.println("Quel est votre contexte ? (1 ou 2)");
		int contexte = scanner.nextInt();
		
		System.out.println();

		/** 
		 * On instancie et on relie entre eux par injection de dépendances tous les
		 * objets intervenant dans le traitement en fonction du contexte
		 */

		ArticleRepositoryInterface articleRepository = null;
		CatalogServiceInterface catalogService = null;
		ClientServiceInterface clientService = null;
		DevisServiceInterface devisService = null;

		switch (contexte) {
			case 1:
				// les objets responsables du traitement sont les mêmes que précédemment
				articleRepository = new ArticleMemoryRepository();
				catalogService = new CatalogBasicService(articleRepository);
				devisService = new DevisSimpleService(catalogService);
				break;
	
			case 2:
				// les objets responsables du traitement sont ceux nouvellement créés pour
				// implémenter le nouveau comportement
				articleRepository = new ArticleDatabaseRepository();
				catalogService = new CatalogDetailService(articleRepository);
				clientService = new ClientSimpleService();
				devisService = new DevisRemiseService(catalogService, clientService);
				break;
		}

		if (devisService == null) {
			System.out.println("Ce contexte est inconnu de notre application");

		} else {
			
			/**
			 * Le code d'interaction avec les utilisateurs a été déplacé dans les contrôleurs
			 * Ici ne figure plus que le code responsable du flot d'exécution
			 */

			// On identifie le client
			
			ClientController clientController = new ClientController();
			Client client = clientController.identifierClient();

			// On propose un choix dans la liste des articles disponibles
			
			ArticleController articleController = new ArticleController();
			articleController.setCatalogService(catalogService);
			List<Integer> numArticles = articleController.choisirArticles();

			// On renvoie le devis correspondant

			DevisController devisController = new DevisController();
			devisController.setDevisService(devisService);
			devisController.afficherDevis(client, numArticles);

		}
		
		scanner.close();

	}

}
