package iservices;

import java.util.List;

import javax.ejb.Local;

import entites.Encadreurentreprise;

@Local
public interface CompanystaffLocal {
	public int AddStaff(Encadreurentreprise e);
	public boolean DeleteStaff(int id);
	public List<Encadreurentreprise> ListStaff();
}
