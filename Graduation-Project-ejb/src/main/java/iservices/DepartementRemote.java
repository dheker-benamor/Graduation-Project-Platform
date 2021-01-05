package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.Departement;
import entites.Site;
import entites.Staff;
@Remote
public interface DepartementRemote {

	public int adddepartement(Departement d);
	public boolean updateDepartement(Departement d);
	public boolean deleteDepartement(int id);
	public List<Departement> ListDepartements();
	public boolean affectDepartement(Site s,int id);
	public boolean affecterchief(int id,int ids);
	public List<Departement> getListDepartementsbySite(int s);
}
