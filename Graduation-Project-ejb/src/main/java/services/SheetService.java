package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entites.Defense;
import entites.Sheet;
import entites.Teacher;
import iservices.SheetServiceInterface;


@Stateless
@LocalBean
public class SheetService implements SheetServiceInterface {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Sheet> getAllSheetsWithMarks() {
		Query query = entityManager.createQuery("select s FROM Sheet s where s.mark != null and programmed_defense=0");
		List<Sheet> result = query.getResultList();
		return result;
	}

	@Override
	public int getTheEncadreurOfSheet(int idSheet) {
		Query query = entityManager.createNativeQuery("select encadreur_id FROM Sheet where id_sheet="+idSheet);
		return (int)query.getSingleResult();
	}

	@Override
	public void programAdefenseToSheet(int idSheet) {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("update sheet set programmed_defense=1 where id_sheet="+idSheet);
		query.executeUpdate();
	}

	@Override
	public int getTheIdOfStudentFromSheet(int idSheet) {
		// TODO Auto-generated method stub
		Query query = entityManager.createNativeQuery("select student_id FROM Sheet where id_sheet="+idSheet);
		return (int)query.getSingleResult();
		
		
	}

	@Override
	public Integer getTheIdOfSheetFromDefense(Defense d) {
		// TODO Auto-generated method stub
		TypedQuery<Integer> query = entityManager.createQuery("Select "
				+ "DISTINCT d.sheet.id_sheet from Defense d "
				+ "where d.id_defense=:idD", Integer.class);
		query.setParameter("idD", d.getId_defense());
		return query.getSingleResult();
	}

	@Override
	public List<Sheet> getAllSheets() {
		Query query = entityManager.createQuery("select s FROM Sheet s where programmed_defense=0");
		List<Sheet> result = query.getResultList();
		return result;
	}

}
