package net.jmb.tuto.spring.entity;

public class Produit {

	protected String libelle;

	public Produit() {
		super();
	}

	public Produit(String libelle) {
		super();
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return libelle;
	}

}
