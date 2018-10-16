package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.Supplier;
import com.jyd.bms.dao.SupplierDAO;
/**
 * @category Generated 2018-10-15 22:39:51 by GeneratedTool
 * @author mjy
 */
@Repository
public class SupplierDAOImpl extends HibernateBaseTemplate<Supplier> implements SupplierDAO {

 public int getSupplierCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from Supplier";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from Supplier where contactName like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<Supplier> getPagingSupplier(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from Supplier";
	} else {
	hql = "from Supplier where contactName like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<Supplier> getAllSupplier() throws DAOException {
	String hql = "";
	hql = "from Supplier";
	return super.getQueryResult(hql.toString());
 }
}
