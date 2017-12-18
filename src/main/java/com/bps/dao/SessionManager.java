package com.bps.dao;

import org.hibernate.Session;

import com.bps.service.core.ProcessContextPool;
import com.bps.service.core.SessionFactoryManager;

public class SessionManager {
	public static Session getSession() {
		if(ProcessContextPool.get().getSessionFactory() == null) {
			ProcessContextPool.get().setSessionFactory(SessionFactoryManager.getSessionFactory());
		}
		return ProcessContextPool.get().getSessionFactory().openSession();
	}
	public static void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.clear();
			session.close();
		}
	}
}
