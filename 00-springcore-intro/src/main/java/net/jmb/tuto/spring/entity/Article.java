package net.jmb.tuto.spring.entity;

public class Article extends Produit {

	protected double tarif;

	public Article() {
		super();
	}

	public Article(String libelle, double tarif) {
		super(libelle);
		this.tarif = tarif;
	}

	public double getTarif() {
		return tarif;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

	@Override
	public String toString() {
		return "[libelle=" + libelle + ", tarif=" + tarif + "]";
	}

}
