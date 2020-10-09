package net.jmb.tuto.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.jmb.tuto.spring.entity.Devis;
import net.jmb.tuto.spring.service.CatalogService;
import net.jmb.tuto.spring.service.DevisService;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// On identifie le client

		System.out.println("Saisissez le nom du client ");
		String nomClient = scanner.nextLine();

		// On propose un choix dans la liste des articles disponibles

		System.out.println("Choisissez un ou plusieurs articles dans la liste ci-dessous");
		System.out.println("--> Tapez les n° correspondants puis n'importe quelle lettre pour continuer");

		CatalogService catalogService = new CatalogService(); // KO
		System.out.println(catalogService.afficherListeArticles());

		// On lit les n° d'articles choisis

		List<Integer> numArticles = new ArrayList<Integer>();
		while (scanner.hasNextInt()) {
			numArticles.add(scanner.nextInt());

		}
		scanner.close();

		// On renvoie le devis correspondant

		if (numArticles.size() > 0) {
			DevisService devisService = new DevisService(); // KO
			Devis devis = devisService.calculerDevis(nomClient, numArticles);
			System.out.println(devis);

		} else {
			System.out.println("Impossible d'établir le devis : aucun article choisi");
		}

	}

}
