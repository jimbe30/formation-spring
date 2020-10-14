package net.jmb.tuto.spring.service;

import java.util.Arrays;

import net.jmb.tuto.spring.entity.Client;

public class ClientSimpleService implements ClientServiceInterface {

	@Override
	public boolean isClientVIP(Client client) {
		
		return Arrays.asList(
			"gégé",
			"dédé",
			"mémé",
			"zézé"
		).contains(client.getNom().toLowerCase());

	}

}
