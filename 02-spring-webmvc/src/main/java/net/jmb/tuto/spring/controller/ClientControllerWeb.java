package net.jmb.tuto.spring.controller;

import java.util.Scanner;

import net.jmb.tuto.spring.entity.Client;

public class ClientControllerWeb {

	
	public Client identifierClient() {
		
		Client client = null;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Saisissez le nom du client ");	
		if (scanner.hasNext()) {
			String nomClient = scanner.nextLine();
			System.out.println();

			if (nomClient != null && nomClient.trim().length() > 0) {
				client = new Client(nomClient, null);
			}
		}
		return client;
	}
}
