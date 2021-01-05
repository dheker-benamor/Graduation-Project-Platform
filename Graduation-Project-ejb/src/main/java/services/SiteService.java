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
import entites.Site;
import entites.Student;
import iservices.SiteLocale;
import iservices.SiteRemote;

@Stateless
@LocalBean
public class SiteService implements SiteRemote,SiteLocale{

	@PersistenceContext
	EntityManager em;

	@Override
	public int addsite(Site s) {
		em.persist(s);
		return s.getId();
	}
	
	@Override
	public boolean updateSite(Site s) {
		try {

			em.find(Site.class, s.getId());

			em.merge(s);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteSite(int id) {
		try {
			Site e = em.find(Site.class, id);
			em.remove(e);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<Site> ListSite() {
		TypedQuery<Site> query = em.createQuery("SELECT x FROM Site x", Site.class);
		System.out.println(query);
		List<Site> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	@Override
	public boolean affectSite(School s, int ido) {

		s = em.find(School.class, s.getId());

		Site o = em.find(Site.class, ido);
		s.getSites().add(o);;
		o.setSchool(s);
		return true;
	}
	@Override
	public List<Site> getSitesbySchool(int s) {
		
		TypedQuery<Site> query = em.createQuery("SELECT x FROM Site x WHERE x.School.id = :s", Site.class).setParameter("s",s);
		System.out.println(query);
		List<Site> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
}
