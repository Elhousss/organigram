package projet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import projet.beans.Collaborateur;
import projet.beans.Entite;

public class CollaborateurDaoImpl implements CollaborateurDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9143635338440557472L;
	
	public EntiteDao entiteDao = new EntiteDaoImpl();

	@Override
	public void createCollaborateur(Collaborateur collaborateur) {

		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			Entite entite = null;
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();

			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(collaborateur);
			if(collaborateur.getIsResponsable()){
				if (collaborateur.getEntite()!=null) {
					entite = collaborateur.getEntite();
					entite.setResponsable(collaborateur);
					session.update(entite);
				}
			}
			session.getTransaction().commit();
			System.out.println("Collaborateur enregistré");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	@Override
	public Collaborateur findCollaborateur(int collaborateurID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			collaborateur = session.get(Collaborateur.class, collaborateurID);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;
	}

	@Override
	public List<Collaborateur> getResponsables() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> responsables = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where isResponsable is true";
			Query query = session.createQuery(hql);
			responsables = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return responsables;
	}

	@Override
	public List<Collaborateur> getCollaborateurs() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> collaborateurs = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur";
			Query query = session.createQuery(hql);
			collaborateurs = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return collaborateurs;
	}

	@Override
	public void updateCollaborateur(Collaborateur collaborateur) {
		SessionFactory sessionFactory = null;
		Session session = null;

		try {
			/*
			
			I must  fetch the old entity and set its is_responsable to null before setting the new entity's id_responsable to collaborateur .
			
			/////////////////////////*/
			if(collaborateur.getEntite()!=null){
				entiteDao.setResponsableToNull(collaborateur.getIdCollaborateur());
			}
			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(collaborateur);
			
			if(collaborateur.getEntite()!=null){
				collaborateur.getEntite().setResponsable(collaborateur);
				session.update(collaborateur.getEntite());
			}
			session.getTransaction().commit();
			System.out.println("reponsable (" + collaborateur.getNom() + " "
					+ collaborateur.getPrenom() + ") modifiée");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteCollaborateur(int collaborateurID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		System.out.println("l'id à supprimer est : " + collaborateurID);
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Collaborateur collaborateur = findCollaborateur(collaborateurID);
			session.delete(collaborateur);
			System.out.println("Responsable " + collaborateur.getNom()
					+ " supprimé");

			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	@Override
	public List<Collaborateur> getAvailableResponsables() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Collaborateur> availableResponsables = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Collaborateur where isResponsable is true and id_entite is null";
			Query query = session.createQuery(hql);
			availableResponsables = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return availableResponsables;
	}

	@Override
	public Collaborateur findCollaborateur(String nomCollaborateur) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		String hql;
		try {
			hql = "from Collaborateur where nom = :nomCollab";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomCollab", nomCollaborateur);
			List<Collaborateur> result = query.getResultList();
			session.getTransaction().commit();
			for (Collaborateur coll : result) {
				collaborateur = coll;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;

	}

	@Override
	public Collaborateur findCollaborateur(String prenomCollaborateur,
			String nomCollaborateur) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Collaborateur collaborateur = null;
		String hql;
		try {
			hql = "from Collaborateur where nom = :nomCollab and prenom = :prenomCollab";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomCollab", nomCollaborateur);
			query.setParameter("prenomCollab", prenomCollaborateur);
			List<Collaborateur> result = query.getResultList();
			session.getTransaction().commit();
			for (Collaborateur coll : result) {
				collaborateur = coll;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return collaborateur;

	}
}
