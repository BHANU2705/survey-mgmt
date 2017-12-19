package com.bps.service.core;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.bps.service.exceptions.BaseException;
import com.bps.service.exceptions.ErrorCode;

public class SessionFactoryManager {

	private SessionFactoryManager() {
	}
	private static SessionFactory sessionFactory = null;
	public static SessionFactory getSessionFactory() throws BaseException {
		if (sessionFactory == null) {
			System.out.println("Loading Session factory for first time.");
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure().build();
			try {
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
				BaseException ex = new BaseException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ErrorCode.DB_CONNECTION_FAILURE);
				ex.setCoreException(e);
				StandardServiceRegistryBuilder.destroy(registry);
				throw ex;
			}
		}
		return sessionFactory;
	}
}
