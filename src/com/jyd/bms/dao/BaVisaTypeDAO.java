package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaVisaType;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 22:37:10 by GeneratedTool
 * @author mjy
 */
public interface BaVisaTypeDAO extends HibernateBase<BaVisaType> {
	public int getBaVisaTypeCount(String condition) throws DAOException;
	public List<BaVisaType> getPagingBaVisaType(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaVisaType> getAllBaVisaType() throws DAOException;
}