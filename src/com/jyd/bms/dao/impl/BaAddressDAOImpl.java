package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaAddress;
import com.jyd.bms.dao.BaAddressDAO;
/**
 * @category Generated 2018-10-12 23:30:12 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaAddressDAOImpl extends HibernateBaseTemplate<BaAddress> implements BaAddressDAO {

 public int getBaAddressCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaAddress";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaAddress where contact like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaAddress> getPagingBaAddress(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaAddress";
	} else {
	hql = "from BaAddress where contact like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaAddress> getAllBaAddress() throws DAOException {
	String hql = "";
	hql = "from BaAddress";
	return super.getQueryResult(hql.toString());
 }
}
