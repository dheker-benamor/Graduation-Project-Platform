package iservices;

import javax.ejb.Local;
import javax.ejb.Remote;

import entites.User;



@Local
public interface UserRemote {

	
	public User getinfo(int id);
}
