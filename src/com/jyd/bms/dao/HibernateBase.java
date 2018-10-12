package com.jyd.bms.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.jyd.bms.tool.exception.CommitException;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.exception.UpdateException;

public interface HibernateBase<T> {
	public void add(T object) throws CreateException;

	public void update(T object) throws UpdateException;

	public void delete(T object) throws DeleteException;

	public T getById(int id) throws DataNotFoundException;

	public List getAll() throws DAOException;

	public void refresh(T object) throws DataAccessException;

	public void merge(T object) throws UpdateException;

	public void flush() throws CommitException;

}
