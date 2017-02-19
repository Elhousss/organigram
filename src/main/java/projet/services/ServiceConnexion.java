package projet.services;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projet.beans.Compte;
import projet.dao.CompteDaoImpl;

public class ServiceConnexion {
	
//	private String messageDeConnexion;
//
//	public String getMessageDeConnexion() {
//		return messageDeConnexion;
//	}
//	
//	public void setMessageDeConnexion(String messageDeConnexion) {
//		this.messageDeConnexion = messageDeConnexion;
//	}
//	
//
//	public Compte connecterCompte(HttpServletRequest req){
//		String login = req.getParameter("login");
//		String pass = req.getParameter("pass");
//		Compte compte = null;
//		CompteDaoImpl compteDaoImpl = new CompteDaoImpl();
//		if(compteDaoImpl.verifierPass(login, pass)){
//			compte = compteDaoImpl.trouverCompte(login);
//		}
//		return compte;
//	}
}
