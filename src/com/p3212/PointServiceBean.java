package com.p3212;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PointServiceBean {

	@PersistenceContext
    private EntityManager em;
	
    public void addPoint(Point point) {
    	em.persist(point);
    }
    
    public List<Point> getUsersPoints(User usr) {
    	List<Point> lst = em.createNamedQuery("pointlist").setParameter("creator", usr).getResultList();
    	return lst;
    }

}
