package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import projet.beans.Collaborateur;
import projet.beans.Compte;
import projet.dao.CompteDao;
import projet.dao.CompteDaoImpl;
import projet.services.ServiceInscription;

@ManagedBean
@SessionScoped
public class InscriptionBean implements Serializable{
	
	private Compte compte;
	private ServiceInscription serviceInscription;
	private CompteDao compteDao;
	private Map<String,String> erreurs;

	@PostConstruct
	private void init() {
		
		compteDao = new CompteDaoImpl();
		serviceInscription = new ServiceInscription();
		compte = new Compte();
		erreurs = serviceInscription.getErreurs();
	}
	
	
	public CompteDao getCompteDao() {
		return compteDao;
	}


	public void setCompteDao(CompteDao compteDao) {
		this.compteDao = compteDao;
	}


	public ServiceInscription getServiceInscription() {
		return serviceInscription;
	}
	
	public void setServiceInscription(ServiceInscription serviceInscription) {
		this.serviceInscription = serviceInscription;
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	
	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public boolean creerCompte(){
		
		boolean checked = serviceInscription.checkErreurs(compte);
		
		
		if(checked){
			Collaborateur collaborateur = compteDao.findByCIN(compte.getCIN());
			if(collaborateur==null){
				compteDao.createVisitorAccount(compte);
			}
			else{
				compteDao.createCollaborateurAccount(compte, collaborateur);
			}
		}
		else{
			
			System.out.println("There is an error !");
		}
		return checked;
	}
	
	public String combinedFunctions(){
		boolean sansErreur = creerCompte();
		if(sansErreur){
			return "Accueil?faces-redirect=true";
		}
		return "Inscription1?faces-redirect=true";
	}
}










