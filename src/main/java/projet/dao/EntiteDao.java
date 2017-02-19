package projet.dao;

import java.util.List;

import projet.beans.Collaborateur;
import projet.beans.Entite;

public interface EntiteDao {
	
	public boolean enregistrerEntite(Entite entite);
	public Entite trouverEntite(int idEntite);
	public Entite trouverEntite(String nomEntite);
	public void setResponsableToNull(int responsableID);
	public List<Entite> getEntites();
	public List<Entite> getEntitesSansResponsable();
	public void supprimerEntite(int idEntite);
	public boolean verifierNom(String nomEntite);
	public void modifierEntite(Entite entite);
}
