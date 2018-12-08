package com.p3212.lab4;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

@Stateful
@Path("/point")
public class PointBean {

	@EJB
	PointServiceBean pointService;
	
	@EJB
	UserServiceBean userService;
	
	@Path("/check")
	@POST
	public void checkAndSave(@FormParam("X") float x,
            				 @FormParam("Y") float y ,
            				 @FormParam("R") float r,
            				 @Context HttpServletRequest req,
            				 @Context HttpServletResponse resp) {
		try {
			String login = (String)req.getSession().getAttribute("login");
			if (login == null)
				resp.sendRedirect("/login");
			boolean hit;
			if (x > 0 && y > 0)
				hit = false;
			else 
			if (x <= 0 && y >=0)
				if (y - x > r)
					hit = false;
				else
					hit = true;
			else
			if (x<=0 && y<=0)
				if (x < -r || y < -r/2)
					hit = false;
				else
					hit = true;
			else
				if (x*x + y*y > r*r/4)
					hit = false;
				else
					hit = true;
			Point pnt = new Point();
			pnt.setHit(hit);
			pnt.setR(r);
			pnt.setX(x);
			pnt.setY(y);
			pnt.setCreator(userService.getUser(login));
			pointService.addPoint(pnt);
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	@Path("/get")
	@GET
	public List<Point>getUsersPoints(@Context HttpServletRequest req, @Context HttpServletResponse resp) {
		try {
			String login = (String)req.getSession().getAttribute("login");
			if (login == null) {
				resp.sendRedirect("/login");
				return null;
			}
			List<Point> points = pointService.getUsersPoints(userService.getUser(login));
			return points;
		} catch (IOException exc) {
			System.err.println(exc.getMessage());
			return null;
		}
	}
	
		
		
}
