package net.jmb.tuto.spring;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.jmb.tuto.spring.controller.ArticleController;
import net.jmb.tuto.spring.controller.ClientController;
import net.jmb.tuto.spring.controller.DevisController;
import net.jmb.tuto.spring.entity.Client;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// On détermine quel est le contexte
		System.out.println("Quel est votre contexte ? (1 ou 2)");
		int contexte = scanner.nextInt();		
		System.out.println();

		
		// On détermine quel est le fichier de configuration du conteneur Spring en fonction du contexte
		ApplicationContext applicationContext = null;
		
		switch (contexte) {
			case 1:
				applicationContext = new ClassPathXmlApplicationContext("applicationContext-1.xml");
				break;
			case 2:
				applicationContext = new ClassPathXmlApplicationContext("applicationContext-2.xml");
				break;
		}
		
		
		if (applicationContext == null) {
			System.out.println("Ce contexte est inconnu de notre application");	
			
		} else {
			// Depuis le conteneur Spring :
			// - On ne récupère que les contrôleurs responsables des interactions avec l'utilisateur
			// - Tous les services et repositories nécessaires à leur fonctionnement ont été injectés à partir de la configuration
			
			ClientController clientController = applicationContext.getBean(ClientController.class);
			ArticleController articleController = applicationContext.getBean(ArticleController.class);
			DevisController devisController = applicationContext.getBean(DevisController.class);
			
			// On identifie le client
			Client client = clientController.identifierClient();

			// On propose un choix dans la liste des articles disponibles
			List<Integer> numArticles = articleController.choisirArticles();

			// On renvoie le devis correspondant
			devisController.afficherDevis(client, numArticles);

		}
		
		scanner.close();

	}

}
