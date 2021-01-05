package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Classes;
import entites.Options;
import entites.Staff;
import entites.Student;

@Local
public interface ClassesLocale {
	public int addclasse(Classes c,Staff s);
	public boolean updateClasse(Classes c);
	public boolean deleteClasse(int id);
	public List<Classes> ListClasses();
	public boolean affecterClasse(Options o,int id);
	public List<Classes> ListClassesOption(int o);
	public boolean affecterEtudiant(int s,int id);
}
