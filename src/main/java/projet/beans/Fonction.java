package projet.beans;

import java.util.ArrayList;
import java.util.Collection;

public class Fonction {
	
	private int codeFonction;
	private String libelleFonction;
	private Collection<Collaborateur> collaborateurs = new ArrayList<Collaborateur>();
	
	
	
	public Collection<Collaborateur> getCollaborateurs() {
		return collaborateurs;
	}
	public void setCollaborateurs(Collection<Collaborateur> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}
	public int getCodeFonction() {
		return codeFonction;
	}
	public void setCodeFonction(int codeFonction) {
		this.codeFonction = codeFonction;
	}
	public String getLibelleFonction() {
		return libelleFonction;
	}
	public void setLibelleFonction(String libelleFonction) {
		this.libelleFonction = libelleFonction;
	}
	
	
}
