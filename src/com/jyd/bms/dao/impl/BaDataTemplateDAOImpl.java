package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.BaDataTemplate;
import com.jyd.bms.dao.BaDataTemplateDAO;
/**
 * @category Generated 2018-10-14 20:12:29 by GeneratedTool
 * @author mjy
 */
@Repository
public class BaDataTemplateDAOImpl extends HibernateBaseTemplate<BaDataTemplate> implements BaDataTemplateDAO {

 public int getBaDataTemplateCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from BaDataTemplate";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from BaDataTemplate where src like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<BaDataTemplate> getPagingBaDataTemplate(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from BaDataTemplate";
	} else {
	hql = "from BaDataTemplate where src like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<BaDataTemplate> getAllBaDataTemplate() throws DAOException {
	String hql = "";
	hql = "from BaDataTemplate";
	return super.getQueryResult(hql.toString());
 }
}
