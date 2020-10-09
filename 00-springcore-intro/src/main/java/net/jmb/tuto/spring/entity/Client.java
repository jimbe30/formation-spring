package net.jmb.tuto.spring.entity;

public class Client {

	protected String nom;
	protected String email;

	public Client() {
		super();
	}

	public Client(String nom, String email) {
		super();
		this.nom = nom;
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "[nom=" + nom + ", email=" + email + "]";
	}

}
