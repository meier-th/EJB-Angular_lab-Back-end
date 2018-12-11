package com.p3212;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Lock(LockType.WRITE)
@Path("/point")
@Produces(MediaType.APPLICATION_JSON)
public class PointBean {
    
    @EJB
    PointServiceBean pointService;
    
    @EJB
    UserServiceBean userService;
    
    @POST
    public Response checkAndSave(@FormParam("X") float x,
            @FormParam("Y") float y,
            @FormParam("R") float r,
            @Context HttpServletRequest req,
            @Context HttpServletResponse resp) {
        try {
            //String login = (String) req.getSession().getAttribute("login");
            boolean passed = false;
            Cookie cookies[] = req.getCookies();
            if (cookies == null)
                return Response.status(Response.Status.FORBIDDEN).entity("User is not logged in").build();
            Cookie username = new Cookie("a","b");
            for (Cookie ck : cookies) {
                if (ck.getName().equals("login")) {
                    username = ck;
                    passed = true;
                    break;
                }
            }
            //if (login == null) {
            if (!passed) {
                return Response.status(Response.Status.FORBIDDEN).entity("User is not logged in").build();
            }
            boolean hit;
            if (x > 0 && y > 0) {
                hit = false;
            } else if (x <= 0 && y >= 0) {
                if (y - x > r) {
                    hit = false;
                } else {
                    hit = true;
                }
            } else if (x <= 0 && y <= 0) {
                if (x < -r || y < -r / 2) {
                    hit = false;
                } else {
                    hit = true;
                }
            } else if (x * x + y * y > r * r / 4) {
                hit = false;
            } else {
                hit = true;
            }
            Point pnt = new Point();
            pnt.setHit(hit);
            pnt.setR(r);
            pnt.setX(x);
            pnt.setY(y);
            pnt.setCreator(userService.getUser(username.getValue()));
            pointService.addPoint(pnt);
            return Response.ok("added").build();
        } catch (Exception exc) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exc.getMessage()).build();
        }
    }
    
    @GET
    public Response getUsersPoints(@Context HttpServletRequest req, @Context HttpServletResponse resp) {
        //String login = (String) req.getSession().getAttribute("login");
        
        boolean passed = false;
            Cookie cookies[] = req.getCookies();
            if (cookies == null)
                return Response.status(Response.Status.FORBIDDEN).entity("User is not logged in.").build();
            Cookie username = new Cookie("a","b");
            for (Cookie ck : cookies) {
                if (ck.getName().equals("login")) {
                    username = ck;
                    passed = true;
                    break;
                }
            }
        
        //if (login == null) {
        if (!passed) {
            return Response.status(Response.Status.FORBIDDEN).entity("User is not logged in.").build();
        }
        List<Point> pnts = pointService.getUsersPoints(userService.getUser(username.getValue()));
        String rsp = "[";
        for (Point pnt : pnts) {
            rsp += (pnt.toString() + ",\n");
        }
        if (pnts.size() > 0)
            rsp = rsp.substring(0, rsp.length() - 2);
        rsp += "]";
        return Response.ok(rsp).build();
    }
}
