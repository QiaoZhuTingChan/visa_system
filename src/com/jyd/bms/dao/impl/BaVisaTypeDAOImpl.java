package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaVisaType;
import com.jyd.bms.dao.BaVisaTypeDAO;
/**
 * @category Generated 2018-10-12 22:37:10 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaVisaTypeDAOImpl extends HibernateBaseTemplate<BaVisaType> implements BaVisaTypeDAO {

 public int getBaVisaTypeCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaVisaType";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaVisaType where baVisaType like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaVisaType> getPagingBaVisaType(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaVisaType";
	} else {
	hql = "from BaVisaType where baVisaType like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaVisaType> getAllBaVisaType() throws DAOException {
	String hql = "";
	hql = "from BaVisaType";
	return super.getQueryResult(hql.toString());
 }
}
