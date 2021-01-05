package iservices;

import java.util.List;

import javax.ejb.Remote;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;

import entites.Entreprise;
import entites.Message;


@Remote
public interface EntrepriseRemote {
	void consommation();

	public int addentreprise(Entreprise e);
	

	public Entreprise authentification(String identifiant, String password);
	public Entreprise getinfo(int id);

	public boolean updateEntreprise(Entreprise e);
	public boolean deleteEntreprise(int id);
	public List<Entreprise> Listentreprises();
	public void approveCompany(int id) throws MessagingException;
	public List<Entreprise> approvedComapnies();
	public void sendMessageToClient(Message message) throws MessagingException;
	
}
