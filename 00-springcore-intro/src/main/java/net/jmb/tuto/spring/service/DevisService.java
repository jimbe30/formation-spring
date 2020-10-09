package net.jmb.tuto.spring.service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.entity.Client;
import net.jmb.tuto.spring.entity.DetailDevis;
import net.jmb.tuto.spring.entity.Devis;

public class DevisService {

	static final int DUREE_VALIDITE_DEVIS = 10;
	
	public Devis calculerDevis(String nomClient, List<Integer> numArticles) {

		Devis devis = new Devis();

		devis.setDate(new Date());
		devis.setDuree(DUREE_VALIDITE_DEVIS);
		devis.setClient(new Client(nomClient, null));

		CatalogService catalogService = new CatalogService(); // KO
		
		// Calcul quantité par article
		Map<Integer, Integer> articlesEtQuantites = new Hashtable<>(); // key: numéro d'article -> value: quantité
		for (Integer num : numArticles) {			
			Integer quantite = articlesEtQuantites.get(num);
			if (quantite != null) {
				quantite++;
			} else {
				quantite = 1;
			}
			articlesEtQuantites.put(num, quantite);
		}
		
		// Calcul détail devis par article
		articlesEtQuantites.forEach((numArticle, quantite) -> {
			Article article = catalogService.chercherArticle(numArticle);
			if (article != null) {
				DetailDevis detailDevis = calculerDetail(article, quantite);
				if (detailDevis != null) {
					devis.setMontantTotal(devis.getMontantTotal() + detailDevis.getMontant());
					devis.addDetailDevis(detailDevis);
				}
			}
		});
		
		devis.setMontantTotal(arrondirMontant(devis.getMontantTotal()));

		return devis;
	}
	
	public DetailDevis calculerDetail(Article article, int quantite) {
		
		if (article != null && quantite > 0) {
			double tarif = article.getTarif();
			double montant = ((double) quantite) * tarif;

			DetailDevis detailDevis = new DetailDevis();
			detailDevis.setProduit(article);
			detailDevis.setQuantite(quantite);
			detailDevis.setMontant(montant);
			
			return detailDevis;
		}
		return null;
	}
	
	protected void regrouperArticles() {
		
	}
	
	protected double arrondirMontant(double montant) {
		long l = Math.round(montant * 100);
		double result = (double) l / 100;
		return result;
	}
 
}
