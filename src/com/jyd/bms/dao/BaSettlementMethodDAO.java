package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaSettlementMethod;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:37:08 by GeneratedTool
 * @author mjy
 */
public interface BaSettlementMethodDAO extends HibernateBase<BaSettlementMethod> {
	public int getBaSettlementMethodCount(String condition) throws DAOException;
	public List<BaSettlementMethod> getPagingBaSettlementMethod(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaSettlementMethod> getAllBaSettlementMethod() throws DAOException;
}