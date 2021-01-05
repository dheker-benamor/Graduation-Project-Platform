package iservices;

import javax.ejb.Remote;

import entites.User;
@Remote
public interface AuthenticationServiceRemote {
	
	public User authentificationUser(String email, String password);
}
