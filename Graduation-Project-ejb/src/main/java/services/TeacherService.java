package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.Teacher;
import iservices.TeacherServiceInterface;

@Stateless
@LocalBean
public class TeacherService implements TeacherServiceInterface {
	@PersistenceContext
	EntityManager em;
	@Override
	public int getCodeTeacherById(int idTeacher) {
		// TODO Auto-generated method stub
		 Query query1 = em.createQuery("Select e.codeTeacher from Teacher e where e.id=:idTeacher");
		 query1.setParameter("idTeacher", idTeacher);
	      int result = (int) query1.getSingleResult();
		return result;
	}
	@Override
	public void updateNumberDefense(int codeenseignant) {
		Query query = em.createNativeQuery("UPDATE teacher SET nbr_soutenance=nbr_soutenance+1 where code_teacher="+codeenseignant);
		int modified = query.executeUpdate();
		if(modified == 1){
			System.out.println("successfully updated");
		}else{
			System.out.println("failed to update");
		}
	}
	@Override
	public int getTheNumberOfeacher(int codeenseignant) {
		Query q = em.createNativeQuery("select nbr_soutenance from teacher where code_teacher=:code");
		q.setParameter("code", codeenseignant);
		 return (int)q.getSingleResult();
		
	}
	@Override
	public String getTheEmailOfEncadreurById(int idTeacher) {
		// TODO Auto-generated method stub
		Teacher student=em.find(Teacher.class, idTeacher);
		return student.getEmail();
	}
	@Override
	public Teacher getTheTeacherById(int id) {
		Teacher student=em.find(Teacher.class, id);
		return student;
	}

}
