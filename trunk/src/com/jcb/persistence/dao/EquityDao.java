package com.jcb.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.jcb.persistence.bean.Equity;

public class EquityDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public List<Equity> getAll() {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Equity.class);
				return criteria.list();
			}
		};
		return (List<Equity>) hibernateTemplate.execute(callback);
	}
	
	public Equity getEquity(final int id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.get(Equity.class, id);
			}
		};
		return (Equity) hibernateTemplate.execute(callback);
	}

	public void saveOrUpdate(final Equity equity) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.saveOrUpdate(equity);
				return null;
			}
		};
		hibernateTemplate.execute(callback);
	}

	public void deleteEquity(final Equity equity) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.delete(equity);
				return null;
			}
		};
		hibernateTemplate.execute(callback);
	}

}
