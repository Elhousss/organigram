package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import projet.beans.Collaborateur;
import projet.beans.Collaborateur;
import projet.beans.Entite;
import projet.dao.CollaborateurDao;
import projet.dao.CollaborateurDaoImpl;
import projet.dao.EntiteDao;
import projet.dao.EntiteDaoImpl;

//@Named
//@SessionScoped
@ManagedBean
@SessionScoped
public class MajCollaborateurBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695627561477414200L;
	
	private List<Collaborateur> collaborateursList;
	private List<Collaborateur> responsablesList;

	private Collaborateur collaborateur;
	private EntiteDao entiteDao;
	private CollaborateurDao collaborateurDao;
	private List<Collaborateur> availableResponsableList;


	public EntiteDao getEntiteDao() {
		return entiteDao;
	}

	public void setEntiteDao(EntiteDao entiteDao) {
		this.entiteDao = entiteDao;
	}

	public List<Collaborateur> getAvailableResponsableList() {
		return availableResponsableList;
	}

	public void setAvailableResponsableList(
			List<Collaborateur> availableResponsableList) {
		this.availableResponsableList = availableResponsableList;
	}

	@PostConstruct
	public void init() {
		entiteDao = new EntiteDaoImpl();
		collaborateurDao = new CollaborateurDaoImpl();
		availableResponsableList = collaborateurDao.getAvailableResponsables();
		collaborateursList = collaborateurDao.getCollaborateurs();
		responsablesList = collaborateurDao.getResponsables();
		collaborateur = new Collaborateur();
	}

	public List<Collaborateur> getCollaborateursList() {
		return collaborateursList;
	}

	public void setCollaborateursList(List<Collaborateur> collaborateursList) {
		this.collaborateursList = collaborateursList;
	}

	public List<Collaborateur> getResponsablesList() {
		return responsablesList;
	}

	
	public void setResponsablesList(List<Collaborateur> responsablesList) {
		this.responsablesList = responsablesList;
	}

	public Collaborateur getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}

	public CollaborateurDao getCollaborateurDao() {
		return collaborateurDao;
	}

	public void setCollaborateurDao(CollaborateurDao collaborateurDao) {
		this.collaborateurDao = collaborateurDao;
	}

	public List<Collaborateur> getTousEntites() {
		return collaborateurDao.getCollaborateurs();
	}

	// /////////////////////////////////////////////

	public String creerCollaborateur() {
		
		collaborateurDao.createCollaborateur(collaborateur);
		return "MajCollaborateur?faces-redirect=true";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			Collaborateur collaborateur = (Collaborateur) event.getObject();

			collaborateurDao.updateCollaborateur(collaborateur);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Collaborateur Modifié",
				((Collaborateur) event.getObject()).getIdCollaborateur() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Collaborateur) event.getObject()).getIdCollaborateur() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void removeCollaborateur(Collaborateur collaborateur) {
		collaborateurDao
				.deleteCollaborateur(collaborateur.getIdCollaborateur());
		collaborateursList.remove(collaborateur);
	}

}
