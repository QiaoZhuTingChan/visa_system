package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.dao.BaModelTypeDAO;
/**
 * @category Generated 2018-10-14 19:51:29 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaModelTypeDAOImpl extends HibernateBaseTemplate<BaModelType> implements BaModelTypeDAO {

 public int getBaModelTypeCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaModelType";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaModelType where baModelType like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaModelType> getPagingBaModelType(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaModelType";
	} else {
	hql = "from BaModelType where baModelType like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaModelType> getAllBaModelType() throws DAOException {
	String hql = "";
	hql = "from BaModelType";
	return super.getQueryResult(hql.toString());
 }
}
