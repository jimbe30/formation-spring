package net.jmb.tuto.spring.entity;

public class DetailDevis {

	Produit produit;
	int quantite;
	double montant;

	public DetailDevis() {
		super();
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	@Override
	public String toString() {
		return "[" + produit + ", quantite=" + quantite + ", montant=" + montant + "]";
	}

}
