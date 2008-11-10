package com.jcb.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final Log log = LogFactory.getLog(HibernateUtil.class);

	private static SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			log.error("Initial SessionFactory creation failed.", ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
