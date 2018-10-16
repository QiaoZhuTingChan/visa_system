package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaSettlementMethod;
import com.jyd.bms.dao.BaSettlementMethodDAO;
/**
 * @category Generated 2018-10-15 22:37:08 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaSettlementMethodDAOImpl extends HibernateBaseTemplate<BaSettlementMethod> implements BaSettlementMethodDAO {

 public int getBaSettlementMethodCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaSettlementMethod";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaSettlementMethod where baSettlementMethod like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaSettlementMethod> getPagingBaSettlementMethod(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaSettlementMethod";
	} else {
	hql = "from BaSettlementMethod where baSettlementMethod like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaSettlementMethod> getAllBaSettlementMethod() throws DAOException {
	String hql = "";
	hql = "from BaSettlementMethod";
	return super.getQueryResult(hql.toString());
 }
}
