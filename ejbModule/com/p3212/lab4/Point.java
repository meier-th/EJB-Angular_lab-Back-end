package com.p3212.lab4;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
name = "pointlist",
query = "SELECT p FROM Point p WHERE p.sessionid = :session ORDER BY p.created DESC")
public class Point {

	@Id
	@Column(name = "X")
	private double x;
	
	@Id
	@Column(name = "Y")
	private double y;
	
	@Id
	@Column(name = "R")
	private double r;
	
	@Column(name = "HIT")
	private boolean hit;
	
	@Id
	@Column(name = "CREATED ON")
	private Date creationDate;
	
	@Id
    @Column (name = "CREATOR")
    private String userName;
	
    public Point() {
    	this.creationDate = new Date();
    }

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
