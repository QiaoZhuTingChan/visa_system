package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaAddress;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 23:30:12 by GeneratedTool
 * @author mjy
 */
public interface BaAddressDAO extends HibernateBase<BaAddress> {
	public int getBaAddressCount(String condition) throws DAOException;
	public List<BaAddress> getPagingBaAddress(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaAddress> getAllBaAddress() throws DAOException;
}