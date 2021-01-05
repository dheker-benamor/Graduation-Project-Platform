package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.tree.TreePath;

import org.hibernate.hql.internal.classic.Parser;

import entites.Entreprise;
import entites.Skills;
import entites.Student;
import entites.User;
import entites.user_skills;
import iservices.SkillsLocal;

@Stateless
@LocalBean
public class SkillsService implements SkillsLocal {
	@PersistenceContext
	EntityManager em;

	@Override
	public boolean affectStudentskills(Student s, user_skills x) {

		s = em.find(Student.class, s.getId());

		em.createNativeQuery("INSERT INTO `user_skills`( `skills_id`, `student_id`, `rate`) VALUES (?,?,?) ")
				.setParameter(1, x.getSkills_id()).setParameter(2, s).setParameter(3, x.getRate()).executeUpdate();

		return true;
	}

	@Override
	public List<Object> top3student(int id) {
		Query query = em.createNativeQuery("SELECT offreentreprise.annonce,rate,skills.skill,user.nom,user.NumTel,user.email FROM `user`,skills,user_skills,user_offreentreprise,offreentreprise,offreentreprise_skills where user_skills.student_id=user.id and user_skills.skills_id=skills.id and user_offreentreprise.student_id=user.id and offreentreprise.id=user_offreentreprise.offreentreprise_id and offreentreprise_skills.offreentreprise_id=offreentreprise.id and offreentreprise_skills.skills_id=skills.id and offreentreprise.id="+id+" order by rate DESC LIMIT 3"); 
	//SELECT offreentreprise.annonce,rate ,skills.skill,user.nom,user.NumTel,user.email FROM `user`,skills,user_skills,user_offreentreprise,offreentreprise,offreentreprise_skills where user_skills.student_id=user.id and user_skills.skills_id=skills.id and user_offreentreprise.student_id=user.id and offreentreprise.id=user_offreentreprise.offreentreprise_id and offreentreprise_skills.offreentreprise_id=offreentreprise.id and offreentreprise_skills.skills_id=skills.id and offreentreprise.id=1 order by rate DESC												
		List<Object> j = query.getResultList();
		return j;

	}

	@Override
	public List<Skills> getall() {
		TypedQuery<Skills> query=em.createQuery("SELECT c FROM Skills c",Skills.class);
		List<Skills> list = query.getResultList();
		return list;
	}

	@Override
	public List<user_skills> userskills() {
		TypedQuery<user_skills> query=em.createQuery("SELECT c FROM user_skills c",user_skills.class);
		List<user_skills> list = query.getResultList();
		return list;
	}

	@Override
	public boolean updateskillsstudent(Student s, user_skills x) {
		s = em.find(Student.class, s.getId());
		em.createNativeQuery("UPDATE `user_skills` SET `skills_id`="+x.getSkills_id()+",`student_id`="+s.getId()+",`rate`="+x.getRate()+" WHERE id="+x.getId())
		.executeUpdate();
		
		return true;
	}

	
	

}
