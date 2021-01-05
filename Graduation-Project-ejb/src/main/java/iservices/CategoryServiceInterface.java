package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Category;
import entites.Teacher;
@Local
public interface CategoryServiceInterface {
	
	public List<Category> getAllCategoriesFromSheet(int idSheet);

	public List<Category> getAllCategoriesofTeacher(int idTeacher);
	
	public List<Teacher> getAllTeachersWithCategory(int cat);
	
	

}
