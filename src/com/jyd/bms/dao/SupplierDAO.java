package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.Supplier;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:39:51 by GeneratedTool
 * @author mjy
 */
public interface SupplierDAO extends HibernateBase<Supplier> {
	public int getSupplierCount(String condition) throws DAOException;
	public List<Supplier> getPagingSupplier(int firstResult, int maxResults, String condition)throws DAOException;
	public List<Supplier> getAllSupplier() throws DAOException;
}