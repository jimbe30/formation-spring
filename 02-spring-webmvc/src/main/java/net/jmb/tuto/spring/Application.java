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

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// Pas besoin de déterminer le contexte : il est fourni en ligne de commande en passant
		// la variable d'environnement 'contexte' ( -Dcontexte=2 par exemple )

		
		// Plus besoin de déterminer le fichier de configuration du conteneur Spring en fonction du contexte
		// C'est Spring qui importe la config spécique grâce à la variable d'environnement
		// Le fichier est donc unique quel que soit le contexte
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		// Depuis le conteneur Spring :
		// - On ne récupère que les contrôleurs responsables des interactions avec l'utilisateur
		// - Tous les services et repositories nécessaires à leur fonctionnement ont été
		//   injectés à partir de la configuration

		ClientController clientController = applicationContext.getBean(ClientController.class);
		ArticleController articleController = applicationContext.getBean(ArticleController.class);
		DevisController devisController = applicationContext.getBean(DevisController.class);

		// On identifie le client
		Client client = clientController.identifierClient();

		// On propose un choix dans la liste des articles disponibles
		List<Integer> numArticles = articleController.choisirArticles();

		// On renvoie le devis correspondant
		devisController.afficherDevis(client, numArticles);

		scanner.close();

	}

}
