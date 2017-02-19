package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projet.beans.Compte;
import projet.dao.CompteDao;
import projet.dao.CompteDaoImpl;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1893802795923896266L;

	private CompteDao compteDao;
	private Compte compte;
	private String erreurMessage;

	@PostConstruct
	private void init() {
		compteDao = new CompteDaoImpl();
		compte = new Compte();
		erreurMessage = new String();
	}

	public String getErreurMessage() {
		return erreurMessage;
	}

	public void setErreurMessage(String erreurMessage) {
		this.erreurMessage = erreurMessage;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public CompteDao getCompteDao() {
		return compteDao;
	}

	public void setCompteDao(CompteDao compteDao) {
		this.compteDao = compteDao;
	}

	public String afficherPage() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String btnId = params.get("btnId");
		System.out.println(btnId);
		if (btnId.equals("connexion")) {
			System.out.println("Le bouton cliqué est " + btnId);
			return "Accueil";
		} else {
			System.out.println("Le bouton cliqué est " + btnId);
			return "Inscription1";

		}
	}

	public boolean seConnecter() {
		if (!compte.getLogin().equals("")) {
			if (compteDao.verifierPass(compte)) {
				compte = compteDao.trouverCompte(compte.getLogin());
				compte.setLoggedIn(true);
				HttpServletRequest req = (HttpServletRequest) (FacesContext
						.getCurrentInstance().getExternalContext().getRequest());
				HttpSession session = req.getSession();
				return true;
			} else {
				erreurMessage = "Login ou mot de passe incorrect";
			}
		} else if (compte.getLogin().equals("")
				|| compte.getLogin().equals(null)) {
			erreurMessage = "Merci de saisir un login et un mot de passe";
		}
		return false;

	}

	public void seDeconnecter() {
		HttpServletRequest req = (HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		HttpSession session = req.getSession();
		session.invalidate();
	}
}
