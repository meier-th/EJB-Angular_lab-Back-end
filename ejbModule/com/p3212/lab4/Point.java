package com.p3212.lab4;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="points")
@NamedQuery(
name = "pointlist",
query = "SELECT p FROM Point p WHERE p.creator = :creator ORDER BY p.creationDate DESC")
public class Point {

	@Id
	@Column(name = "x")
	private double x;
	
	@Id
	@Column(name = "y")
	private double y;
	
	@Id
	@Column(name = "t")
	private double r;
	
	@Column(name = "hit")
	private boolean hit;
	
	@Id
	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name="creator")
	private User creator;
	
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
}
