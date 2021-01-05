package iservices;

import java.util.List;

import javax.ejb.Remote;

import entites.School;
import entites.Staff;
@Remote
public interface SchoolRemote {

	public int addSchool(School s);
	public boolean updateSchool(School s);
	public boolean deleteSchool(int id);
	public List<School> ListSchool();
	public List<School> findbyname(String name);
}
