package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Sheet;
import entites.Staff;
import entites.User;

@Local
public interface StaffLocal {
	public int addStaff(Staff e);
	void consommation();
	

}
