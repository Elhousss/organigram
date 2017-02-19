package projet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import projet.beans.Collaborateur;
import projet.beans.Compte;

public class CompteDaoImpl implements CompteDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2017671193358043740L;

	@Override
	public void createVisitorAccount(Compte c) {
		Session session = null;
		try {
			String pass = c.getMotDePasse();

			ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
			cpe.setAlgorithm("SHA-256");
			cpe.setPlainDigest(false);

			String passCrypte = cpe.encryptPassword(pass);
			c.setMotDePasse(passCrypte);

			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			session.save(c);
			session.getTransaction().commit();
			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	@Override
	public Compte trouverCompte(String login) {
		Session session = null;
		Compte compte = null;
		String hql;
		try {
			hql = "from Compte where login = :login";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("login", login);
			List<Compte> comptes = query.getResultList();
			if(!comptes.isEmpty()){
				compte = comptes.get(0);
			}
			session.getTransaction().commit();
			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return compte;
	}

	@Override
	public boolean verifierPass(Compte compte) {
		Session session = null;
		boolean correct = false;
		Compte compteBase = null;
		String hql;
		try {

			hql = "from Compte where login = :login";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("login", compte.getLogin());
			List<Compte> result = query.getResultList();

			if (!result.isEmpty()) {
				compteBase = result.get(0);
				ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
				cpe.setAlgorithm("SHA-256");
				cpe.setPlainDigest(false);
				if (cpe.checkPassword(compte.getMotDePasse(),
						compteBase.getMotDePasse())) {
					correct = true;
				}

			}

			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return correct;
	}

	//
	// @Override
	// public boolean estAdmin(String login) {
	// boolean admin = false;
	// Session session = null;
	// String hql;
	// try {
	// hql = "select estAdmin from Compte where login = :login";
	// SessionFactory sf = (new Configuration()).configure()
	// .buildSessionFactory();
	// session = sf.openSession();
	// session.beginTransaction();
	// Query query = session.createQuery(hql);
	// query.setParameter("login", login);
	// List result = query.getResultList();
	// if (result.get(0).equals(Boolean.valueOf(true))) {
	// admin = true;
	// }
	// session.close();
	// sf.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// session.getTransaction().rollback();
	// }
	// return admin;
	// }

	@Override
	public Collaborateur findByCIN(String CIN) {
		Collaborateur collaborateur = null;
		Session session = null;
		String hql;
		try {
			hql = "from Collaborateur where CIN = :CIN";
			SessionFactory sf = (new Configuration()).configure()
					.buildSessionFactory();
			session = sf.openSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("CIN", CIN);
			List<Collaborateur> result = query.getResultList();
			if (!result.isEmpty()) {
				if (result.get(0) != null) {
					collaborateur = result.get(0);
				}
			}

			session.close();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return collaborateur;
	}

	@Override
	public void createCollaborateurAccount(Compte compte,
			Collaborateur collaborateur) {
		Session session = null;
		SessionFactory sessionFactory = null;
		String pass;
		try {
			pass = compte.getMotDePasse();
			ConfigurablePasswordEncryptor cpe = new ConfigurablePasswordEncryptor();
			cpe.setAlgorithm("SHA-256");
			cpe.setPlainDigest(false);
			String passCrypte = cpe.encryptPassword(pass);

			compte.setMotDePasse(passCrypte);
			compte.setCollaborateur(collaborateur);

			collaborateur.setCompte(compte);

			sessionFactory = (new Configuration()).configure()
					.buildSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(collaborateur);
			session.save(compte);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

}
