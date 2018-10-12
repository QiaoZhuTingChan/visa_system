package com.jyd.bms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jyd.bms.dao.HibernateBase;
import com.jyd.bms.tool.exception.CommitException;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.exception.UpdateException;

@Service("BaseService")
public abstract class BaseService<T> {
	public HibernateBase baseDAO;

	public abstract void setDAO();

	public void add(T object) throws CreateException {
		setDAO();
		baseDAO.add(object);
	}

	public void update(T object) throws UpdateException {
		setDAO();
		baseDAO.update(object);
	}

	public void delete(T object) throws DeleteException {
		setDAO();
		baseDAO.delete(object);
	}

	public <T> T getById(int id) throws DataNotFoundException {
		setDAO();
		return (T) baseDAO.getById(id);
	}
	
	public List getAll() throws DAOException{
		setDAO();
		return baseDAO.getAll();
	}

	public void refresh(T object) throws DataAccessException {
		setDAO();
		baseDAO.refresh(object);
	}

	public void merge(T object) throws UpdateException {
		setDAO();
		baseDAO.merge(object);
	}

	public void flush() throws CommitException {
		setDAO();
		baseDAO.flush();
	}

}
