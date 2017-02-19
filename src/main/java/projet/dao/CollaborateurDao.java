package projet.dao;

import java.util.List;

import projet.beans.Collaborateur;
import projet.beans.Entite;

public interface CollaborateurDao {
	
	public void createCollaborateur(Collaborateur collaborateur);
	public Collaborateur findCollaborateur(int collaborateurID);
	public List<Collaborateur> getResponsables();
	public List<Collaborateur> getCollaborateurs();
	public void updateCollaborateur(Collaborateur collaborateur);
	public void deleteCollaborateur(int collaborateurID);
	public List<Collaborateur> getAvailableResponsables();
	public Collaborateur findCollaborateur(String nomCollaborateur);
	public Collaborateur findCollaborateur(String prenomCollaborateur, String nomCollaborateur);

}
