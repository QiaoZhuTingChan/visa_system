package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaAdmissibleArea;
import com.jyd.bms.dao.BaAdmissibleAreaDAO;
/**
 * @category Generated 2018-10-12 23:39:01 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaAdmissibleAreaDAOImpl extends HibernateBaseTemplate<BaAdmissibleArea> implements BaAdmissibleAreaDAO {

 public int getBaAdmissibleAreaCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaAdmissibleArea";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaAdmissibleArea where baAdmissibleArea like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaAdmissibleArea> getPagingBaAdmissibleArea(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaAdmissibleArea";
	} else {
	hql = "from BaAdmissibleArea where baAdmissibleArea like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaAdmissibleArea> getAllBaAdmissibleArea() throws DAOException {
	String hql = "";
	hql = "from BaAdmissibleArea";
	return super.getQueryResult(hql.toString());
 }
}
