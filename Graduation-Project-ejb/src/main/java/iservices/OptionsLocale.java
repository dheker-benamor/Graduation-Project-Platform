package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Departement;
import entites.Options;
import entites.Staff;

@Local
public interface OptionsLocale {
	public int addOption(Options o,Staff s);
	public boolean updateOption(Options o);
	public boolean deleteOption(int id);
	public List<Options> ListOptions();
	public boolean affectOption(Departement d,int id);
	public List<Options> ListOptionsDepart(int d);
}
