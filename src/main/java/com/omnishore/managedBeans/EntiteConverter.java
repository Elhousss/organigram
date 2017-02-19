package com.omnishore.managedBeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import projet.beans.Entite;
import projet.dao.EntiteDaoImpl;

@FacesConverter("entiteConverter")
public class EntiteConverter implements Converter {

	public EntiteDaoImpl entiteDaoImpl = new EntiteDaoImpl();

	@Override
	public Entite getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return entiteDaoImpl.trouverEntite(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null || value == "") {
			return null;
		}
		if (!(value instanceof Entite)) {
			throw new ConverterException("La valeur n'est pas valide: " + value);
		}

		String nomEntite = ((Entite) value).getNom();
		return (nomEntite != null) ? String.valueOf(nomEntite) : null;
	}

}
