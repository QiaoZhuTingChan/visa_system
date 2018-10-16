package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaAccountType;
import com.jyd.bms.dao.BaAccountTypeDAO;
/**
 * @category Generated 2018-10-15 22:36:59 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaAccountTypeDAOImpl extends HibernateBaseTemplate<BaAccountType> implements BaAccountTypeDAO {

 public int getBaAccountTypeCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaAccountType";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaAccountType where baAccountType like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaAccountType> getPagingBaAccountType(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaAccountType";
	} else {
	hql = "from BaAccountType where baAccountType like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaAccountType> getAllBaAccountType() throws DAOException {
	String hql = "";
	hql = "from BaAccountType";
	return super.getQueryResult(hql.toString());
 }
}
