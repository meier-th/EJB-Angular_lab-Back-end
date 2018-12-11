package com.p3212;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="users")
@XmlRootElement
public class User {

	@Id
	@Column(name="login", length=15, nullable = false)
	private String login;
	
	@Column(name="password", length=15, nullable = false)
	private int password;
	
	@OneToMany(mappedBy="creator")
	private List<Point> points;
	
	public User() {}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.hashCode();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
}
