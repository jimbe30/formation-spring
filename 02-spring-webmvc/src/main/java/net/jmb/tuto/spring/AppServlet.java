package net.jmb.tuto.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import net.jmb.tuto.spring.controller.ArticleControllerWeb;

public class AppServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static String view_prefix = "/WEB-INF/views/";
	private static String view_suffix = ".jsp";

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		switch (action) {
			case "identifierClient" : 
				identifierClient(request, response);
				break;
			case "choisirArticles" :
				choisirArticles(request, response);
				break;
			case "afficherDevis" : 
				afficherDevis(request, response);
				break;
			default : 
				identifierClient(request, response);							
		}
	}
	
	protected void forward(HttpServletRequest request, HttpServletResponse response, String viewName) throws ServletException, IOException {
		String path = view_prefix + viewName + view_suffix;
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	protected void identifierClient(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomClient = request.getParameter("nomClient");
		if (StringUtils.isBlank(nomClient)) {
			forward(request, response, "identificationClient");
		} else {
			choisirArticles(request, response);
		}
	}
	
	protected void choisirArticles(HttpServletRequest request, HttpServletResponse response) throws IOException {
		WebApplicationContext applicationContext = RequestContextUtils.findWebApplicationContext(request);
		ArticleControllerWeb controller = applicationContext.getBean(ArticleControllerWeb.class);
		String listeArticles = controller.choisirArticles();
		
		response.getWriter().append(listeArticles).flush();
		
		
	}
	
	protected void afficherDevis(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	

}
