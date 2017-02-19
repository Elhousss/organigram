package projet.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import projet.beans.Collaborateur;
import projet.beans.Entite;

public class EntiteDaoImpl implements EntiteDao {

	@Override
	public boolean enregistrerEntite(Entite entite) {
		Session session = null;
		SessionFactory sessionFactory = null;
		boolean saved = false;
		try {
			
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(entite);
			if(entite.getResponsable()!=null){
				Collaborateur responsable = entite.getResponsable();
				responsable.setEntite(entite);
				session.update(responsable);
			}
			session.getTransaction().commit();
			saved = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return saved;
	}

	@Override
	public Entite trouverEntite(int entiteID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			entite = session.get(Entite.class, entiteID);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return entite;
	}

	// @Override
	// public int getMaxID() {
	// String hql;
	// SessionFactory sessionFactory = null;
	// Session session = null;
	// int id = -1;
	// try{
	// sessionFactory = (new Configuration()).configure().buildSessionFactory();
	// session = sessionFactory.openSession();
	// hql = "select max(idEntite) from Entite";
	// Query query = session.createQuery(hql);
	// List<Object> results = query.getResultList();
	// if(results.get(0)==null){
	// id = 1;
	// }
	// else{
	// id = (int) results.get(0) + 1;
	// }
	// }
	// catch(Exception ex){
	// ex.printStackTrace();
	// }
	// finally{
	// sessionFactory.close();
	// session.close();
	// }
	// return id;
	// }

	@Override
	public List<Entite> getEntites() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> entites = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite";
			Query query = session.createQuery(hql);
			entites = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return entites;
	}

	@Override
	public void modifierEntite(Entite entite) {

		SessionFactory sessionFactory = null;
		Session session = null;
		Entite ent = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(entite);
			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally{
			session.close();
			sessionFactory.close();
		}
	}

	@Override
	public void supprimerEntite(int EntiteID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		System.out.println("l'id à supprimer est : " + EntiteID);
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Entite entite = trouverEntite(EntiteID);
			session.delete(entite);
			System.out.println("Entite " + entite.getNom() + "supprimé");
			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

	}

	@Override
	public boolean verifierNom(String nomEntite) {
		boolean estUnique = true;
		String hql;
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			hql = "from Entite where nom =:nomNouvelleEntite";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomNouvelleEntite", nomEntite);
			List result = query.getResultList();
			if (!result.isEmpty()) {
				estUnique = false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}

		return estUnique;
	}

	@Override
	public Entite trouverEntite(String nomEntite) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		String hql;
		try {
			hql = "from Entite where nom = :nomE";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("nomE", nomEntite);
			List<Entite> result = query.getResultList();
			session.getTransaction().commit();
			for(Entite e : result){
				entite = e;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		return entite;

	}

	@Override
	public List<Entite> getEntitesSansResponsable() {
		String hql;
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Entite> entiteSansResponsable = null;
		try {
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			hql = "from Entite where id_responsable is null";
			Query query = session.createQuery(hql);
			entiteSansResponsable = query.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sessionFactory.close();
			session.close();
		}
		return entiteSansResponsable;
	}

	@Override
	public void setResponsableToNull(int responsableID) {
		Session session = null;
		SessionFactory sessionFactory = null;
		Entite entite = null;
		String hql;
		try {
			hql = "from Entite where ID_Responsable = :responsableID";
			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("responsableID", responsableID);
			List<Entite> result = query.getResultList();
			for(Entite e : result){
				entite = e;
			}
			
			if(entite!=null){
				entite.setResponsable(null);
				session.update(entite);
			}
			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
		
	}
}
