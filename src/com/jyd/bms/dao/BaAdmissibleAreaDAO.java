package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaAdmissibleArea;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 23:39:01 by GeneratedTool
 * @author mjy
 */
public interface BaAdmissibleAreaDAO extends HibernateBase<BaAdmissibleArea> {
	public int getBaAdmissibleAreaCount(String condition) throws DAOException;
	public List<BaAdmissibleArea> getPagingBaAdmissibleArea(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaAdmissibleArea> getAllBaAdmissibleArea() throws DAOException;
}