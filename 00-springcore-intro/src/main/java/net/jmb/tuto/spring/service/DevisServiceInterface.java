package net.jmb.tuto.spring.service;

import java.util.List;

import net.jmb.tuto.spring.entity.Article;
import net.jmb.tuto.spring.entity.DetailDevis;
import net.jmb.tuto.spring.entity.Devis;

public interface DevisServiceInterface {

	Devis calculerDevis(String nomClient, List<Integer> numArticles);

	DetailDevis calculerDetail(Article article, int quantite);

}