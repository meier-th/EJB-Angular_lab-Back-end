package com.p3212.lab4;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Singleton
@Path(value = "/auth")
public class AuthorizationBean {

	@EJB
	private UserServiceBean userService;
	
	@Path("/registration")
	@POST
	public void createUser(@FormParam("login") String login,
						   @FormParam("password") String password,
						   @Context HttpServletResponse resp,
	                       @Context HttpServletRequest req) {
		try {
			boolean passed = checkUser(login, password) && !userService.checkIfNameIsOccupied(login);
			if (!passed)
				resp.sendRedirect("/lab4/signup.html");
			else {
				User user = new User();
				user.setLogin(login);
				user.setPassword(password);
				userService.createUser(user);
				req.getSession().setAttribute("login", login);
				resp.sendRedirect("/main.html");
			}
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	@Path("/login")
	@POST
	public void loginUser(@FormParam("login")String login, @FormParam("password") String password, @Context HttpServletResponse resp, @Context HttpServletRequest req) {
		try {
			User usr = userService.getUser(login);
			if (usr == null) {
				resp.sendRedirect("/login");
				return;
			}
			if (usr.getPassword() != password.hashCode()) {
				resp.sendRedirect("/login");
				return;
			}
			req.getSession().setAttribute("login", login);
			resp.sendRedirect("/main.html");
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	@Path("/logout")
	@POST
	public void logoutUser(@Context HttpServletResponse resp, @Context HttpServletRequest req) {
		try {
			req.getSession().invalidate();
			resp.sendRedirect("/index.html");
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	private boolean checkUser (String login, String password) {
		if (login == null || login.equals("") || login.length() > 15)
			return false;
		if (password == null || password.equals("") || password.length() > 15)
			return false;
		return true;
	}
	
}
