package net.jmb.tuto.spring.factory;

import org.springframework.beans.factory.annotation.Autowired;

import net.jmb.tuto.spring.controller.ArticleControllerWeb;
import net.jmb.tuto.spring.controller.ClientControllerWeb;
import net.jmb.tuto.spring.controller.DevisControllerWeb;

public class ControllerFactory {
	
	private static ControllerFactory instance;	
	
	@Autowired
	ArticleControllerWeb articleControllerWeb;
	@Autowired
	ClientControllerWeb clientControllerWeb;
	@Autowired
	DevisControllerWeb devisControllerWeb;

	private ControllerFactory() {
		super();
	}
	
	public static ControllerFactory getInstance() {
		if (instance == null) {
			instance = new ControllerFactory();
		}
		return instance;
	}
	
	public static ArticleControllerWeb getArticleController() {
		return getInstance().articleControllerWeb;
	}
	public static ClientControllerWeb getClientController() {
		return getInstance().clientControllerWeb;
	}
	public static DevisControllerWeb getDevisController() {
		return getInstance().devisControllerWeb;
	}

}
