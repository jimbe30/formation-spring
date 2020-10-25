package net.jmb.tuto.spring;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.jmb.tuto.spring.config.ApplicationConfig;
import net.jmb.tuto.spring.controller.ArticleController;
import net.jmb.tuto.spring.controller.ClientController;
import net.jmb.tuto.spring.controller.DevisController;
import net.jmb.tuto.spring.entity.Client;

public class Application {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		// L'élement central de la config Spring est la classe ApplicationConfig
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

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
