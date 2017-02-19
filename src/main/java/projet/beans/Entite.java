package projet.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Entite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9193828424545931894L;
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int idEntite;
	private String nom;
	private String description;
	
	@OneToOne
	@JoinColumn(name="ID_Responsable", unique=true)
	private Collaborateur responsable;
	
	@Transient
	private Collection<Collaborateur> collaborateurs = new ArrayList<Collaborateur>();
	
	@OneToOne
	@JoinColumn(name="ID_Entite_Mere")
	private Entite entiteMere;

	
	public Collaborateur getResponsable() {
		return responsable;
	}

	public void setResponsable(Collaborateur responsable) {
		this.responsable = responsable;
	}

	public Entite getEntiteMere() {
		return entiteMere;
	}

	public void setEntiteMere(Entite entiteMere) {
		this.entiteMere = entiteMere;
	}

	public Collection<Collaborateur> getCollaborateurs() {
		return collaborateurs;
	}

	public void setCollaborateurs(Collection<Collaborateur> collaborateurs) {
		this.collaborateurs = collaborateurs;
	}

	public int getIdEntite() {
		return idEntite;
	}

	public void setIdEntite(int idEntite) {
		this.idEntite = idEntite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Entite that = (Entite) o;
		if (idEntite != 0 ? idEntite != (that.idEntite) : that.idEntite != 0)
			return false;
		if (description != null ? !description.equals(that.description)
				: that.description != null)
			return false;
		if (!nom.equals(that.nom))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = nom.hashCode();
		result = 31 * result
				+ (description != null ? description.hashCode() : 0);
		return result;
	}

}
