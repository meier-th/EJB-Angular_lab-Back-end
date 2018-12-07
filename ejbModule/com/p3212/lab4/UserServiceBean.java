package com.p3212.lab4;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserServiceBean implements UserInterface {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public void createUser(User user) {
		em.persist(em);
	}

	@Override
	public boolean checkIfNameIsOccupied(String name) {
		User usr = em.find(User.class, name);
		return (usr != null);
	}

	@Override
	public User getUser(String name) {
		return em.find(User.class, name);
	}
	
	
	
}
