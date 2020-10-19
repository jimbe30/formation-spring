package net.jmb.tuto.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.jmb.tuto.spring.entity.Client;
import net.jmb.tuto.spring.entity.Devis;
import net.jmb.tuto.spring.service.DevisServiceInterface;

public class DevisControllerWeb {
	
	@Autowired
	DevisServiceInterface devisService;
	
	
	public void afficherDevis(Client client, List<Integer> numArticles) {
		
		if (devisService == null) {
			System.out.println("Impossible d'établir le devis : service indisponible");
		} else if (numArticles.size() > 0) {
			Devis devis = devisService.calculerDevis(client.getNom(), numArticles);
			System.out.println(devis);
		} else {
			System.out.println("Impossible d'établir le devis : aucun article choisi");
		}
	}
	
	
	public DevisServiceInterface getDevisService() {
		return devisService;
	}

	public void setDevisService(DevisServiceInterface devisService) {
		this.devisService = devisService;
	}
	
	
	

}
