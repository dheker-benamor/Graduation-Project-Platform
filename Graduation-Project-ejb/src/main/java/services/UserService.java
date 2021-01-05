package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entites.User;
import iservices.UserRemote;


@Stateless
@LocalBean
public class UserService implements UserRemote {
	@PersistenceContext
	EntityManager em;
	@Override
	public User getinfo(int id) {
		return em.createQuery("select e from User e where e.id = :id", User.class).setParameter("id", id).getSingleResult();
		
	}

}
