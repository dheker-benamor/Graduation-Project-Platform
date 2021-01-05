package services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Category;
import entites.Entreprise;
import entites.OffreEntreprise;
import entites.Student;
import entites.User_offreentreprise;
import iservices.OffreLocale;
import iservices.OffreRemote;

@Stateless
@LocalBean
public class OffreService implements OffreRemote, OffreLocale {
	@PersistenceContext
	EntityManager em;

	@Override
	public int addOffre(OffreEntreprise e,int idE,int idC) {
		Entreprise x =em.find(Entreprise.class, idE);
		Category y = em.find(Category.class, 2);
		e.setEntreprise(x);
		e.setCategories(y);
		em.persist(e);
		return e.getId();
	}

	@Override
	public void updateOffre(OffreEntreprise e) {
		em.merge(e);

//		int query = em.createQuery(
//				"update OffreEntreprise o set o.annonce = :annonce , o.localisation = :localisation where o.id = :id")
//				.setParameter("annonce", e.getAnnonce()).setParameter("localisation", e.getLocalisation())
//				.setParameter("id", e.getId()).executeUpdate();

	}

	@Override
	public boolean deleteOffre(int id) {
		try {
			
			//OffreEntreprise e = em.find(OffreEntreprise.class, id);
			//em.remove(e);
			Query query = em.createNativeQuery("delete from offreentreprise where id="+id);
			query.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<OffreEntreprise> ListOffre() {

		TypedQuery<OffreEntreprise> query = em.createQuery("SELECT x FROM OffreEntreprise x", OffreEntreprise.class);
		System.out.println(query);
		List<OffreEntreprise> list = query.getResultList();
		System.out.println(list);

		return list;
	}

	@Override
	public boolean affectStudentoffre(Student s, int ido) {

		s = em.find(Student.class, s.getId());

		OffreEntreprise o = em.find(OffreEntreprise.class, ido);
		s.getOffreentreprise().add(o);
		o.getStudent().add(s);
		return true;
	}

	@Override
	public List<User_offreentreprise> Listoffrestudent(int id) {
		Query query = em.createNativeQuery(
				"SELECT user.email,user.nom,offreentreprise.annonce FROM user,offreentreprise,user_offreentreprise WHERE user.id=user_offreentreprise.student_id and user_offreentreprise.offreentreprise_id=offreentreprise.id and offreentreprise.id="
						+ id);

		List<User_offreentreprise> yalla = query.getResultList();
		System.out.println(yalla);
		return yalla;
	}

	@Override
	public List<Object[]> CountOffres(int id) {
		Query query = em.createNativeQuery("SELECT COUNT(*) FROM user_offreentreprise WHERE offreentreprise_id ="+id);
		List<Object[]> yalla =query.getResultList();
		 System.out.println(query.getResultList());
	return yalla;
	}

	
	@Override
	public List<Object[]> Countperday(String date,int idE) {
		Query query = em.createNativeQuery("SELECT o.datedebut,COUNT(*) FROM offreentreprise o WHERE o.entreprise_id="+ idE +" AND o.datedebut= "+"\""+date+"\""+" GROUP BY o.datedebut");
		
		//System.out.println(query.getResultList());
		List<Object[]> yalla = query.getResultList();
		System.out.println(yalla);
		return yalla;
	}

	@Override
	public List<OffreEntreprise> Coffre(Entreprise x) {
		em.find(Entreprise.class, x.getId());
		OffreEntreprise e = new OffreEntreprise();
		em.find(OffreEntreprise.class, e.getId());
		TypedQuery<OffreEntreprise> query = em.createQuery("SELECT x FROM OffreEntreprise x WHERE entreprise_id="+x.getId(), OffreEntreprise.class);
		System.out.println(query);
		List<OffreEntreprise> list = query.getResultList();
		System.out.println(list);

		return list;
		
	}

}
