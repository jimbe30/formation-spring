package net.jmb.tuto.spring.service;

import java.util.List;

import net.jmb.tuto.spring.entity.Devis;

public class DevisRemiseService extends DevisSimpleService implements DevisServiceInterface {

	ClientServiceInterface clientService;
	int remise = 10; // en vrai ça devrait être paramétré quelque part
	

	public DevisRemiseService() {
		super();
	}

	public DevisRemiseService(CatalogServiceInterface catalogService, ClientServiceInterface clientService) {
		super(catalogService);
		this.clientService = clientService;
	}
	
	@Override
	public Devis calculerDevis(String nomClient, List<Integer> numArticles) {

		Devis devis = super.calculerDevis(nomClient, numArticles);
		
		if (clientService.isClientVIP(devis.getClient())) {
			double montantTotal = devis.getMontantTotal();
			double montantRemise = (double) remise /100 * montantTotal;
			montantRemise = arrondirMontant(montantRemise);
			montantTotal = arrondirMontant(montantTotal - montantRemise);	
			
			devis.setRemise(remise);
			devis.setMontantRemise(montantRemise);
			devis.setMontantTotal(montantTotal);			
		}
		return devis;
	}
	
	public ClientServiceInterface getClientService() {
		return clientService;
	}

	public void setClientService(ClientServiceInterface clientService) {
		this.clientService = clientService;
	}

	public int getRemise() {
		return remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}
	
 
}
