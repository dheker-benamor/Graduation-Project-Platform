package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entites.Classes;
import entites.Options;
import entites.Staff;
import entites.Student;
import iservices.ClassesLocale;
import iservices.ClassesRemote;
@LocalBean
@Stateless
public class Cclasseservice implements ClassesLocale,ClassesRemote {
	@PersistenceContext
	EntityManager em;

	@Override
	public int addclasse(Classes c,Staff s) {
		c.setDepartementchief(s);
		em.persist(c);
		return c.getId();
	}
	
	@Override
	public boolean updateClasse(Classes c) {
		try {

			em.find(Classes.class, c);

			em.merge(c);

			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public boolean deleteClasse(int id) {
		try {
			Classes e = em.find(Classes.class, id);
			em.remove(e);
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	@Override
	public List<Classes> ListClasses() {
		TypedQuery<Classes> query = em.createQuery("SELECT x FROM Classes x", Classes.class);
		System.out.println(query);
		List<Classes> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	@Override
	public List<Classes> ListClassesOption(int o) {
		TypedQuery<Classes> query = em.createQuery("SELECT x FROM Classes x WHERE x.Option.id = :o", Classes.class).setParameter("o", o);
		System.out.println(query);
		List<Classes> liste = query.getResultList();
		System.out.println(liste);

		return liste;
	}
	
	@Override
	public boolean affecterClasse(Options s, int ido) {

		s = em.find(Options.class, s.getId());

		Classes o = em.find(Classes.class, ido);
		s.getClassess().add(o);
		o.setOption(s);
		return true;
	}
	@Override
	public boolean affecterEtudiant(int e,int id)
	{	int tot=0;
		Student s= em.find(Student.class,e);
		Classes c =em.find(Classes.class,id);
		if(c.getNumberofstudents()<2)
		{
			
			s.setClasse(c);
			c.getStudent().add(s);
			c.setNumberofstudents(c.getStudent().size());
			return true;
		}
		else 
		{
			Classes c1 = new Classes("5eme",c.getOption());
			c1.setDepartementchief(c.getDepartementchief());
			int i=0;
			System.out.println("taille initiale"+c.getStudent().size());
			for( i =0; i<c.getStudent().size();i++)
			{
					/*System.out.println("il reste "+i);
					c1.getStudent().add(c.getStudent());
					c1.setNumberofstudents(c1.getStudent().size()+1);
					c1.getStudent().get(i).setClasse(c1);
					c.getStudent().remove(c.getStudent().get(i));
					c.setNumberofstudents(c.getStudent().size());*/
					//em.merge(c);
			}
			s.setClasse(c1);
			em.persist(c1);	
			return true;
		}
	}
}
