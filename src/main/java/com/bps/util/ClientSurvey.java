package com.bps.util;

import com.bps.persistence.tables.IBaseEntity;

public class ClientSurvey implements IBaseEntity {
	private String id;
	private String name;
	private String owner;
	private boolean isResponded;

	public ClientSurvey() {
	}

	public ClientSurvey(String id, String name, String owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
	}

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isResponded() {
		return isResponded;
	}

	public void setResponded(boolean isResponded) {
		this.isResponded = isResponded;
	}
}
