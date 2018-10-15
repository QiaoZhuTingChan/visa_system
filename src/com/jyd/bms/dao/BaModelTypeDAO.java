package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-14 19:51:29 by GeneratedTool
 * @author mjy
 */
public interface BaModelTypeDAO extends HibernateBase<BaModelType> {
	public int getBaModelTypeCount(String condition) throws DAOException;
	public List<BaModelType> getPagingBaModelType(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaModelType> getAllBaModelType() throws DAOException;
}