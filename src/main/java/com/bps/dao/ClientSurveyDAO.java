package com.bps.dao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
				setIsResponded(session, clientSurveys);
				return clientSurveys.toArray(new ClientSurvey[clientSurveys.size()]);
			}
		} finally {
			SessionManager.closeSession(session);
		}
		return null;
	}

	private void setIsResponded(Session session, List<ClientSurvey> clientSurveys) {
		final String pattern = "''{0}'',";
		StringBuilder builder = new StringBuilder();
		for (ClientSurvey clientSurvey : clientSurveys) {
			if (clientSurvey != null && clientSurvey.getId() != null && !clientSurvey.getId().isEmpty()) {
				builder.append(MessageFormat.format(pattern, clientSurvey.getId()));
			}
		}
		String str = builder.toString();
		str = str.substring(0, str.length() - 1);
		String queryString = "SELECT s.surveyId, count(*) as count " + "FROM SurveyResponse s WHERE s.userId = '"
				+ super.getUserEmail() + "' and s.surveyId IN (" + str + ") group by surveyId";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<?> data = query.getResultList();
		Map<String, Boolean> map = new HashMap<>();
		if (data != null && !data.isEmpty()) {
			Iterator<?> it = data.iterator();
			while (it.hasNext()) {
				Object[] list = (Object[]) it.next();
				String surveyId = (String) list[0];
				Long count = (Long) list[1];
				Boolean isResponded = (count > 0) ? true : false;
				map.put(surveyId, isResponded);
			}
		}

		for (ClientSurvey clientSurvey : clientSurveys) {
			if (map.containsKey(clientSurvey.getId())) {
				clientSurvey.setResponded(map.get(clientSurvey.getId()));
			} else {
				clientSurvey.setResponded(false);
			}
		}
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		return null;
	}

}
