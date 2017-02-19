package com.omnishore.managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import projet.beans.Collaborateur;
import projet.beans.Entite;
import projet.dao.CollaborateurDao;
import projet.dao.CollaborateurDaoImpl;

@FacesConverter("collaborateurConverter")
public class CollaborateurConverter implements Converter {

	public CollaborateurDao collaborateurDao = new CollaborateurDaoImpl();

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		String[] values = value.split(" ");
		return collaborateurDao.findCollaborateur(values[0],values[1]);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {

		if (value == null || value == "") {
			return null;
		}
		if (!(value instanceof Collaborateur)) {
			throw new ConverterException("La valeur n'est pas valide: " + value);
		}

		String nomCollaborateur = ((Collaborateur) value).getNom();
		return (nomCollaborateur != null) ? String.valueOf(nomCollaborateur) : null;
	}

}
