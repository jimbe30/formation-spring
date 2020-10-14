package net.jmb.tuto.spring.entity;

public class Prestation extends Produit {

	protected double nbHeures;

	public Prestation() {
		super();
	}

	public Prestation(String libelle) {
		super(libelle);
	}

	public double getNbHeures() {
		return nbHeures;
	}

	public void setNbHeures(double nbHeures) {
		this.nbHeures = nbHeures;
	}

}
