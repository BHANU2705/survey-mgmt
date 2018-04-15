package com.bps.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.Role;
import com.bps.service.exceptions.BaseException;

public class RolesDAO extends DAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		return null;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		return false;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		return false;
	}

	@Override
	public IBaseEntity[] read() throws BaseException {
		return null;
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> fetchAssignedRoles() throws BaseException {
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT s.roles FROM User s WHERE s.email = '" + super.getUserEmail() +"'";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<Role> roles = (List<Role>) query.getResultList();
		SessionManager.closeSession(session);
		return roles;
	}

}
