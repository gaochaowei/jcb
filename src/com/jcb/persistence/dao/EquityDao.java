package com.jcb.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.jcb.persistence.po.Equity;
import com.jcb.util.HibernateUtil;

public class EquityDao {

	public static List<Equity> getAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Equity.class);
		return criteria.list();
	}

	public static Equity getEquity(final int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (Equity) session.get(Equity.class, id);
	}

	public static void saveOrUpdate(final Equity equity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.saveOrUpdate(equity);
	}

	public static void deleteEquity(final Equity equity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.delete(equity);
	}

}
