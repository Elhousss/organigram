package projet.dao;

import projet.beans.Collaborateur;
import projet.beans.Compte;

public interface CompteDao {
	
	public void createVisitorAccount(Compte compte);
	public Compte trouverCompte(String login);
	public boolean verifierPass(Compte compte);
//	public boolean estAdmin(String login);
	public Collaborateur findByCIN(String CIN);
	public void createCollaborateurAccount(Compte compte , Collaborateur collaborateur);
}
