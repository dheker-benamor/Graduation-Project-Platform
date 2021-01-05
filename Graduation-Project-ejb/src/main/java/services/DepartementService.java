package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Departement;
import entites.Role;
import entites.School;
import entites.Site;
import entites.Staff;
import iservices.DepartementLocale;
import iservices.DepartementRemote;


@Stateless
@LocalBean
public class DepartementService implements DepartementRemote,DepartementLocale{

	@PersistenceContext
	EntityManager em;

	@Override
	public int adddepartement(Departement s) {
		em.persist(s);
		return s.getId();
	}
	
	@Override
	public boolean updateDepartement(Departement s) {
		try {

			em.find(Departement.class, s.getId());

			em.merge(s);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteDepartement(int id) {
		try {
			Departement e = em.find(Departement.class, id);
			em.remove(e);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<Departement> ListDepartements() {
		TypedQuery<Departement> query = em.createQuery("SELECT x FROM Departement x", Departement.class);
		System.out.println(query);
		List<Departement> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	@Override
	public boolean affectDepartement(Site s, int ido) {

		s = em.find(Site.class, s.getId());

		Departement o = em.find(Departement.class, ido);
		s.getDepartements().add(o);;
		o.setSite(s);
		return true;
	}

	@Override
	public boolean affecterchief(int id, int ids) {
		Departement d= em.find(Departement.class,id);
		Staff s=em.find(Staff.class,ids);
		s.setRole(Role.DEPARTEMENT_MANAGER);
		s.setDepartement(d);
		d.setDepartementmanager(s);
		return true;
	}
	@Override
	public List<Departement> getListDepartementsbySite(int s) {
		TypedQuery<Departement> query = em.createQuery("SELECT x FROM Departement x WHERE x.site.id = :s", Departement.class).setParameter("s",s);
		System.out.println(query);
		List<Departement> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
}
