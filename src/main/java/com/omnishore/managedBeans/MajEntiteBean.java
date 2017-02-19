package com.omnishore.managedBeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import projet.beans.Collaborateur;
import projet.beans.Entite;
import projet.dao.EntiteDao;
import projet.dao.EntiteDaoImpl;
import projet.services.ServiceInscription;

/**
 * @author stagiaire2
 *
 */
/**
 * @author stagiaire2
 * 
 */
@ManagedBean
@RequestScoped
public class MajEntiteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695627561477414200L;
	private List<Entite> listeEntites;
	private List<Entite> listeEntitesSansResponsable;
	private Entite entite;
	private EntiteDao entiteDao;
	

	@PostConstruct
	public void init() {
		
		entite = new Entite();
		entiteDao = new EntiteDaoImpl();
		listeEntitesSansResponsable = entiteDao.getEntitesSansResponsable();
		listeEntites = entiteDao.getEntites();
	}


	public List<Entite> getListeEntitesSansResponsable() {
		return listeEntitesSansResponsable;
	}

	public void setListeEntitesSansResponsable(
			List<Entite> listeEntitesSansResponsable) {
		this.listeEntitesSansResponsable = listeEntitesSansResponsable;
	}
	
	public EntiteDao getEntiteDao() {
		return entiteDao;
	}



	public void setEntiteDao(EntiteDao entiteDao) {
		this.entiteDao = entiteDao;
	}



	public List<Entite> getListeEntites() {
		return listeEntites;
	}

	public void setListeEntites(List<Entite> listeEntites) {
		this.listeEntites = listeEntites;
	}

	public Entite getEntite() {
		return entite;
	}

	public void setEntite(Entite entite) {
		this.entite = entite;
	}

	/**
	 * Methode d'ajout de l'entite dans la base et dans la liste des entites
	 * 
	 * @return String
	 */
	public String creerEntite() {
		
		boolean saved = entiteDao.enregistrerEntite(entite);
		if (saved == true) {
			listeEntites.add(entite);
		}
		return "MajEntite?faces-redirect=true";
	}

	public void onRowEdit(RowEditEvent event) {
		try {
			Entite entite = (Entite) event.getObject();
			
			entiteDao.modifierEntite(entite);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Entité Modifiée",
				((Entite) event.getObject()).getIdEntite() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled",
				((Entite) event.getObject()).getIdEntite() + "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void supprimerEntite(Entite entite) {
		entiteDao.supprimerEntite(entite.getIdEntite());
		listeEntites.remove(entite);
	}

//	public void findSelectedEntite() {
//		Map<String, String> params = FacesContext.getCurrentInstance()
//				.getExternalContext().getRequestParameterMap();
//		String nom_entite = (!params.get("nom").equals(null)) ? params
//				.get("nom") : null;
//		// Loop through the persons array
//		for (Entite e : listeEntites) {
//			if (e.getNom().equals(nom_entite)) {
//				selectedEntite = e;
//				break;
//			}
//		}
//
//	}
}
