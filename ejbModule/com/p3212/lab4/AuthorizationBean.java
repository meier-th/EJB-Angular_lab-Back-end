package com.p3212.lab4;

import javax.ejb.EJB;
import javax.ejb.Singleton;

@Singleton
@Path(value = "/auth")
public class AuthorizationBean {

	@EJB
	private UserServiceBean userService;
	
}
