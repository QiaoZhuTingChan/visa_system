package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaAccountType;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:36:59 by GeneratedTool
 * @author mjy
 */
public interface BaAccountTypeDAO extends HibernateBase<BaAccountType> {
	public int getBaAccountTypeCount(String condition) throws DAOException;
	public List<BaAccountType> getPagingBaAccountType(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaAccountType> getAllBaAccountType() throws DAOException;
}