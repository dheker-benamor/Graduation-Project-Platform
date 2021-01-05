package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entites.Category;
import entites.Teacher;
import iservices.CategoryServiceInterface;

@Stateless
@LocalBean
public class CategoryService implements CategoryServiceInterface {
	@PersistenceContext
	EntityManager entityManager;
	

	@Override
	public List<Category> getAllCategoriesFromSheet(int idSheet) {
		// TODO Auto-generated method stub

		Query query = entityManager
				.createQuery("select distinct cat FROM Category cat join cat.sheets sh where sh.id_sheet =:sheetId");
		query.setParameter("sheetId", idSheet);
		List<Category> result = query.getResultList();
		System.out.println(result);
		return result;
	
	}

	@Override
	public List<Category> getAllCategoriesofTeacher(int idTeacher) {

		Query query = entityManager
				.createQuery("select distinct c FROM Category c join c.teachers t where t.id =:TeacherId");
		query.setParameter("TeacherId", idTeacher);
		List<Category> result = query.getResultList();
		System.out.println(result);
		return result;
	}

	@Override
	public List<Teacher> getAllTeachersWithCategory(int catid) {
	
		Query query = entityManager
				.createQuery("select distinct t FROM Teacher t join t.categories c where c.id=:categoryId");
		query.setParameter("categoryId", catid);
		List<Teacher> result = query.getResultList();
		return result;
	}

}
