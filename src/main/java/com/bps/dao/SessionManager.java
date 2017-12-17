package com.bps.dao;

import org.hibernate.Session;

import com.bps.service.ProcessContextPool;

public class SessionManager {
	public static Session getSession() {
		return ProcessContextPool.get().getSessionFactory().openSession();
	}
	public static void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.clear();
			session.close();
		}
	}
}
