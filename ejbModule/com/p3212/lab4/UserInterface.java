package com.p3212.lab4;

import javax.ejb.Local;

@Local
public interface UserInterface {
	void createUser (User user);
	boolean checkIfNameIsOccupied(String name);
	User getUser (String name);
}
