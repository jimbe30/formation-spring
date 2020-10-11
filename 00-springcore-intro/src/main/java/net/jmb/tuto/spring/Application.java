package net.jmb.tuto.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.jmb.tuto.spring.entity.Devis;
import net.jmb.tuto.spring.repository.ArticleDatabaseRepository;
import net.jmb.tuto.spring.repository.ArticleMemoryRepository;
import net.jmb.tuto.spring.repository.ArticleRepositoryInterface;
import net.jmb.tuto.spring.service.CatalogBasicService;
import net.jmb.tuto.spring.service.CatalogDetailService;
import net.jmb.tuto.spring.service.CatalogServiceInterface;
import net.jmb.tuto.spring.service.ClientServiceInterface;
import net.jmb.tuto.spring.service.ClientSimpleService;
import net.jmb.tuto.spring.service.DevisRemiseService;
import net.jmb.tuto.spring.service.DevisSimpleService;
import net.jmb.tuto.spring.service.DevisServiceInterface;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// On détermine quel est le contexte
		System.out.println("Quel est votre contexte ? (1 ou 2)");
		int contexte = scanner.nextInt();
		System.out.println();

		// On instancie et on relie entre eux par injection de dépendances tous les
		// objets intervenant dans le traitement

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

			// On identifie le client

			scanner.nextLine();
			System.out.println("1. Saisissez le nom du client ");			
			String nomClient = scanner.nextLine();
			System.out.println();

			// On propose un choix dans la liste des articles disponibles

			System.out.println("2. Choisissez un ou plusieurs articles dans la liste ci-dessous");
			System.out.println("   -> Tapez les n° correspondants puis n'importe quelle lettre pour continuer");
			System.out.println();

			System.out.println(catalogService.afficherListeArticles());

			// On lit les n° d'articles choisis

			List<Integer> numArticles = new ArrayList<Integer>();
			while (scanner.hasNextInt()) {
				numArticles.add(scanner.nextInt());

			}
			scanner.close();
			System.out.println();

			// On renvoie le devis correspondant

			if (numArticles.size() > 0) {
				Devis devis = devisService.calculerDevis(nomClient, numArticles);
				System.out.println(devis);

			} else {
				System.out.println("Impossible d'établir le devis : aucun article choisi");
			}

		}

	}

}
