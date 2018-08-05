package com.bps.persistence.tables;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role implements IBaseEntity {

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(length = 25, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "role")
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
