package com.jyd.bms.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jyd.bms.tool.exception.CommitException;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.exception.UpdateException;

@Repository
public abstract class HibernateBaseTemplate<T> {
	private static final Logger log;
	private Class<T> classt;
	static {
		log = LoggerFactory.getLogger(HibernateBaseTemplate.class);
	}

	private HibernateTemplate hibernateTemplate;
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	protected HibernateBaseTemplate() {
		this.classt = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public Class<T> getClasst() {
		return classt;
	}

	public HibernateTemplate getHibernateTemplate() {
		if (hibernateTemplate == null)
			hibernateTemplate = new HibernateTemplate(sessionFactory);

		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void add(T object) throws CreateException {
		try {
		   // getHibernateTemplate().save(object);
			Session session = sessionFactory.getCurrentSession();

			System.out.println("Session:" + session);

			session.save(object);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new CreateException("新增失败！" + e.getMessage());
		}
	}

	public void update(T object) throws UpdateException {
		try {
			getHibernateTemplate().update(object);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new UpdateException("修改失败！");
		}
	}
	
	public int executeUpdate(final String hql, final Map parameterMap) throws DAOException {
        final Query query = this.setQueryParameters(hql, parameterMap);
        try {
            return query.executeUpdate();
        }
        catch (HibernateException ex) {
            throw new DAOException("更新失败!", (Throwable)ex);
        }
    }

	public void merge(T object) throws UpdateException {
		try {
			getHibernateTemplate().merge(object);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new UpdateException("修改失败！");
		}
	}

	public void delete(T object) throws DeleteException {
		try {
			getHibernateTemplate().delete(object);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DeleteException("删除失败！");
		}
	}

	@SuppressWarnings("unchecked")
	public T getById(int id) throws DataNotFoundException {
		try {
			return (T) getHibernateTemplate().get(classt, id);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DataNotFoundException("没有查找到数据！");
		}
	}

	public List getAll() throws DAOException {
		try {
			return getQueryResult("from " + classt.getName());
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("没有查找到数据！");
		}
	}

	public void flush() throws CommitException {
		try {
			getHibernateTemplate().flush();
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new CommitException("提交事务失败！");
		}
	}

	public void refresh(T object) throws DataAccessException {
		try {
			getHibernateTemplate().refresh(object);
		} catch (DataAccessException e) {
			log.error("HibernateBaseTemplate", e);
			throw e;
		}
	}

	private List getQueryResults(final Query query) throws DAOException {
		try {
			return query.list();
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	protected final List getQueryResult(final String hql) throws DAOException {
		try {
			return this.getQueryResults(this.createQuery(hql));
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	protected final List getQueryResult(final String hql, final Map parameterMap) throws DAOException {
		try {
			final Query query = this.setQueryParameters(hql, parameterMap);
			return this.getQueryResults(query);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	protected final List getPagingQueryResult(final String hql, final Map parameterMap, final int firstResult,
			final int maxResults) throws DAOException {
		try {
			final Query query = this.createQuery(hql);
			return this.getPagingQueryResult(query, parameterMap, firstResult, maxResults);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	protected final Object getUniqueResult(final String hql) throws DAOException {
		try {
			return this.createQuery(hql).uniqueResult();
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	protected final Object getUniqueResult(final String hql, final Map parameterMap) throws DAOException {
		final Query query = this.setQueryParameters(hql, parameterMap);
		try {
			return query.uniqueResult();
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}

	private Query createQuery(final String hql) {
		return this.sessionFactory.getCurrentSession().createQuery(hql);
	}

	private Query setQueryParameters(final String hql, final Map parameterMap) {
		return this.setQueryParameters(this.createQuery(hql), parameterMap);
	}

	private Query setQueryParameters(final Query query, final Map parameterMap) {
		final Iterator iter = parameterMap.keySet().iterator();
		String message = "";
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value = parameterMap.get(key);
			if (value == null) {
				throw new RuntimeException(
						"HQL binding parameter:" + key + ", its value is null!! Value CAN'T be null");
			}

			if (value instanceof Collection) {
				query.setParameterList(key, (Collection) value);
			} else {
				query.setParameter(key, value);
			}
		}
		return query;
	}

	private List getPagingQueryResult(final Query query, final Map parameterMap, final int firstResult,
			final int maxResults) throws DAOException {
		try {
			this.setQueryParameters(query, parameterMap);
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			return this.getQueryResults(query);
		} catch (HibernateException e) {
			log.error("HibernateBaseTemplate", e);
			throw new DAOException("查询出错", e);
		}
	}
}
