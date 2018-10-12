package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.dao.BaPositionDAO;
/**
 * @category Generated 2018-10-11 22:39:56 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaPositionDAOImpl extends HibernateBaseTemplate<BaPosition> implements BaPositionDAO {

 public int getBaPositionCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaPosition";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaPosition where baPosition like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaPosition> getPagingBaPosition(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaPosition";
	} else {
	hql = "from BaPosition where baPosition like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaPosition> getAllBaPosition() throws DAOException {
	String hql = "";
	hql = "from BaPosition";
	return super.getQueryResult(hql.toString());
 }
}
