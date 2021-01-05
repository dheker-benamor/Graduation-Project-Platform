package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Entreprise;
import entites.OffreEntreprise;
import entites.School;
import iservices.SchoolLocale;
import iservices.SchoolRemote;

@Stateless
@LocalBean
public class SchoolService implements SchoolRemote,SchoolLocale{

	@PersistenceContext
	EntityManager em;

	@Override
	public int addSchool(School s) {
		em.persist(s);
		return s.getId();
	}
	
	@Override
	public boolean updateSchool(School s) {
		try {

			em.find(School.class, s.getId());

			em.merge(s);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteSchool(int id) {
		try {
			School e = em.find(School.class, id);
			em.remove(e);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<School> ListSchool() {
		TypedQuery<School> query = em.createQuery("SELECT x FROM School x", School.class);
		System.out.println(query);
		List<School> list = query.getResultList();
		System.out.println(list);

		return list;
	}
	@Override
	public List<School> findbyname(String schoolname) {
		TypedQuery<School> query = em.createQuery("SELECT x FROM School x WHERE x.schoolname = :schoolname", School.class).setParameter("schoolname", schoolname);
		System.out.println(query);
		List<School> list = query.getResultList();
		System.out.println(list);

		return list;
	}
}
