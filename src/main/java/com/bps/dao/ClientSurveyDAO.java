package com.bps.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.service.exceptions.BaseException;
import com.bps.util.ClientSurvey;

public class ClientSurveyDAO extends DAO implements IBaseDAO {

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
		List<ClientSurvey> clientSurveys = null;
		Session session = null;
		List<?> data = null;
		try {
			session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			String queryString = "select s.id, s.name, (select u.name from User u where u.email = s.lifeCycle.createdBy) as owner from Survey s"
					+ " where s.id in (select link.surveyId from SurveyClientUserLink link where link.clientUserEmail = '"
					+ super.getUserEmail() + "')";
			Query<?> query = session.createQuery(queryString);
			query.setReadOnly(true);
			data = query.getResultList();
		} finally {
			SessionManager.closeSession(session);
		}
		if (data != null && !data.isEmpty()) {
			clientSurveys = new ArrayList<ClientSurvey>();
			Iterator<?> it = data.iterator();
			ClientSurvey clientSurvey = null;
			while (it.hasNext()) {
				Object[] list = (Object[]) it.next();
				String id = (String) list[0];
				String name = (String) list[1];
				String owner = (String) list[2];
				clientSurvey = new ClientSurvey();
				clientSurvey.setId(id);
				clientSurvey.setName(name);
				clientSurvey.setOwner(owner);
				clientSurveys.add(clientSurvey);
			}
			return clientSurveys.toArray(new ClientSurvey[clientSurveys.size()]);
		}
		return null;
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		return null;
	}

}
