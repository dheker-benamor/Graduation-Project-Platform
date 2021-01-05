package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Departement;
import entites.Entreprise;
import entites.OffreEntreprise;
import entites.Options;
import entites.School;
import entites.Site;
import entites.Staff;
import entites.Student;
import iservices.OptionsLocale;
import iservices.OptionsRemote;
import iservices.SiteLocale;
import iservices.SiteRemote;

@Stateless
@LocalBean
public class Ooptionservice implements OptionsLocale,OptionsRemote{

	@PersistenceContext
	EntityManager em;

	@Override
	public int addOption(Options o,Staff s) {
		o.setDepartementchief(s);
		em.persist(o);
		return o.getId();
	}
	
	@Override
	public boolean updateOption(Options s) {
		try {

			em.find(Options.class, s.getId());

			em.merge(s);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteOption(int id) {
		try {
			Options e = em.find(Options.class, id);
			em.remove(e);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<Options> ListOptions() {
		TypedQuery<Options> query = em.createQuery("SELECT x FROM Options x", Options.class);
		System.out.println(query);
		List<Options> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	@Override
	public List<Options> ListOptionsDepart(int d) {
		TypedQuery<Options> query = em.createQuery("SELECT x FROM Options x WHERE x.departement.id = :d", Options.class).setParameter("d", d);
		System.out.println(query);
		List<Options> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	
	@Override
	public boolean affectOption(Departement d, int ido) {

		d = em.find(Departement.class, d.getId());

		Options o = em.find(Options.class, ido);
		d.getOptions().add(o);
		o.setDepartement(d);
		return true;
	}
}
