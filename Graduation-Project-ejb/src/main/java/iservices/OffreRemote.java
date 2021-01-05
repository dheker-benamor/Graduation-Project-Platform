package iservices;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import entites.Entreprise;
import entites.OffreEntreprise;
import entites.Student;
import entites.User_offreentreprise;

@Remote
public interface OffreRemote {

	
	public int addOffre(OffreEntreprise e,int idE,int idC);
	public void updateOffre(OffreEntreprise e);
	public boolean deleteOffre(int id);
	public List<OffreEntreprise> ListOffre();
	public List<OffreEntreprise> Coffre(Entreprise x);
	public boolean affectStudentoffre(Student s,int ido);
	public List<User_offreentreprise> Listoffrestudent(int id);
	public List<Object[]> CountOffres(int id);
	public List<Object[]> Countperday(String date,int idE);
}
