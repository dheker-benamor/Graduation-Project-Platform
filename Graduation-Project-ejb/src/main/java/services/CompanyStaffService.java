package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Encadreurentreprise;
import iservices.CompanystaffLocal;


@Stateless
@LocalBean
public class CompanyStaffService implements CompanystaffLocal {
	@PersistenceContext
	EntityManager em;

	@Override
	public int AddStaff(Encadreurentreprise e) {
		em.persist(e);
		return e.getId();
	}

	@Override
	public boolean DeleteStaff(int id) {
		Encadreurentreprise e= em.find(Encadreurentreprise.class,id);
		em.remove(e);
		return true;
	}

	@Override
	public List<Encadreurentreprise> ListStaff() {
		TypedQuery<Encadreurentreprise> query = em.createQuery("SELECT x FROM Encadreurentreprise x",Encadreurentreprise.class);
		List<Encadreurentreprise> list = query.getResultList();
		return list;
	}

}
