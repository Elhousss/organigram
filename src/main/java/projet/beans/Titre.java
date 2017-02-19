package projet.beans;

import java.util.ArrayList;
import java.util.Collection;

public class Titre {
	
	private int codeTitre;
	private String libelleTitre;
	private Collection<Collaborateur> collaborateurs = new ArrayList<Collaborateur>();
	
	
	public Collection<Collaborateur> getCollaborateurs() {
		return collaborateurs;
	}
	public void setCollaborateurs(Collection<Collaborateur> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}
	public int getCodeTitre() {
		return codeTitre;
	}
	public void setCodeTitre(int codeTitre) {
		this.codeTitre = codeTitre;
	}
	public String getLibelleTitre() {
		return libelleTitre;
	}
	public void setLibelleTitre(String libelleTitre) {
		this.libelleTitre = libelleTitre;
	}
	
	
	
}
