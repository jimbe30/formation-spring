package net.jmb.tuto.spring.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Devis {
	
	protected Client client;
	protected Date date;
	protected int duree;	
	protected List<DetailDevis> listeDetailsDevis;
	protected int remise;
	protected double montantTotal;
	protected double montantRemise;

	public double getMontantRemise() {
		return montantRemise;
	}

	public void setMontantRemise(double montantRemise) {
		this.montantRemise = montantRemise;
	}

	public Devis() {
		super();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public int getRemise() {
		return remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public String getDate() {
		if (date != null) {
			DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
			return df.format(date);
		}
		return null;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public List<DetailDevis> getListeDetailsDevis() {
		if (listeDetailsDevis == null) {
			listeDetailsDevis = new ArrayList<DetailDevis>();
		}
		return listeDetailsDevis;
	}

	public void setListeDetailsDevis(List<DetailDevis> detailsDevis) {
		this.listeDetailsDevis = detailsDevis;
	}

	public void addDetailDevis(DetailDevis detailDevis) {
		getListeDetailsDevis().add(detailDevis);
	}

	@Override
	public String toString() {
		return "Devis [client=" + client + ", date=" + getDate() + ", remise=" + montantRemise + " (" + remise + "%), total à payer=" + montantTotal 
				+ ", detailsDevis=" + listeDetailsDevis + "]";
	}

}
