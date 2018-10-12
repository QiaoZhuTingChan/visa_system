package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-11 22:39:56 by GeneratedTool
 * @author mjy
 */
public interface BaPositionDAO extends HibernateBase<BaPosition> {
	public int getBaPositionCount(String condition) throws DAOException;
	public List<BaPosition> getPagingBaPosition(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaPosition> getAllBaPosition() throws DAOException;
}