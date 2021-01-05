package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import entites.Entreprise;
import entites.Message;

import iservices.EntrepriseLocale;
import iservices.EntrepriseRemote;

import utilities.CryptPasswordMD5;

@Stateless
@LocalBean
public class EntrepriseService implements EntrepriseLocale, EntrepriseRemote {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public void consommation() {

		// create new JAX-RS Client
		Client client = ClientBuilder.newClient();
		// The base URL of Web service
		WebTarget target = client.target("http://localhost:9080/Graduation-Project-web/api/");
		// WebTarget hello = target.path("Entreprise");

		Response response = target.request().get();
		String result = response.readEntity(String.class);
		System.out.println(result);
		response.close();

	}

	@Override
	public int addentreprise(Entreprise e) {

		e.setPassword(CryptPasswordMD5.cryptWithMD5(e.getPassword()));
		e.setAutorise(0);
		e.setLogoentreprise("http://localhost/X.jpg");
		System.out.println(e);

		try {
			em.persist(e);
			System.out.println("add successfull");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return e.getId();
	}

	private CryptPasswordMD5 cryptPasswordMD5 = new CryptPasswordMD5();
	public static Entreprise LoggedPerson;

	@Override
	public Entreprise authentification(String identifiant, String password) {
		Entreprise entreprise = new Entreprise();
		try {

			TypedQuery<Entreprise> query = em.createQuery("select e from Entreprise e where e.identifiant=:identifiant",
					Entreprise.class);
			query.setParameter("identifiant", identifiant);
			entreprise = query.getSingleResult();
			if (entreprise.getPassword().equals(cryptPasswordMD5.cryptWithMD5(password))) {
				LoggedPerson = entreprise;
				System.out.println(entreprise.getId());
				return LoggedPerson;
			} else {
				System.out.println(entreprise);
				return new Entreprise();
			}

		} catch (Exception ex) {
			System.out.println(ex);

		}
		return entreprise;
	}

	@Override
	public boolean updateEntreprise(Entreprise e) {
		try {

			em.find(Entreprise.class, e.getId());

			em.merge(e);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteEntreprise(int id) {
		try {
			Entreprise e = em.find(Entreprise.class, id);
			em.remove(e);
			System.out.println("delete successfull");
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public List<Entreprise> Listentreprises() {

		TypedQuery<Entreprise> query = em.createQuery("SELECT x FROM Entreprise x", Entreprise.class);
		List<Entreprise> list = query.getResultList();

		return list;
	}

	@Override
	public void approveCompany(int id) throws MessagingException {
		Entreprise e = new Entreprise();
		em.find(Entreprise.class, e.getId());

		em.createQuery("UPDATE Entreprise e SET e.autorise =1 WHERE e.id = :idE").setParameter("idE", id)
				.executeUpdate();
		MailSender mailSender = new MailSender();
		mailSender.sendMessage("smtp.gmail.com", "mohamedraed.safsaf@esprit.tn", "183JMT0189", "587", "true", "true",
				"safsafraed@gmail.com", "Your Company approvement request" + ": " + "APPROVED", "Approved");
		//em.persist(message);
		System.out.println(e);

	}


	@Override
	public List<Entreprise> approvedComapnies() {
		TypedQuery<Entreprise> query = em.createQuery("SELECT x FROM Entreprise x WHERE x.autorise=1",
				Entreprise.class);
		List<Entreprise> list = query.getResultList();
		System.out.println(list);
		return list;
	}

	@Override
	public void sendMessageToClient(Message message) throws MessagingException {
		MailSender mailSender = new MailSender();
		mailSender.sendMessage("smtp.gmail.com", "mohamedraed.safsaf@esprit.tn", "*******", "587", "true", "true",
				message.getToUserEmail(), message.getSubject() + ": " + message.getType(), message.getMessage());
		em.persist(message);

	}

	@Override
	public Entreprise getinfo(int id) {
		
		return em.createQuery("select e from Entreprise e where e.id = :id", Entreprise.class).setParameter("id", id).getSingleResult();
	}

}
