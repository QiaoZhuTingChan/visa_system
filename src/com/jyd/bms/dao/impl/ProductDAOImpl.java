package com.jyd.bms.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.bean.Product;
import com.jyd.bms.dao.ProductDAO;
/**
 * @category Generated 2018-10-16 21:37:46 by GeneratedTool
 * @author mjy
 */
@Repository
public class ProductDAOImpl extends HibernateBaseTemplate<Product> implements ProductDAO {

 public int getProductCount(String condition) throws DAOException {
	String hql = "";
	if (condition.equals("")) {
	hql = "select count(*) from Product";
	List<Long> lstCount = super.getQueryResult(hql);
	return lstCount.get(0).intValue();
	} else {
	hql = "select count(*) from Product where productName like :condition";
	Map map = new HashMap();
	map.put("condition", "%" + condition + "%");
	List<Long> lstCount = super.getQueryResult(hql, map);
	return lstCount.get(0).intValue();
	}
 }

 public List<Product> getPagingProduct(int firstResult, int maxResults, String condition)throws DAOException {
	String hql = "";
	Map map = new HashMap();
	if (condition.equals("")) {
	hql = "from Product";
	} else {
	hql = "from Product where productName like :condition";
	map.put("condition", "%" + condition + "%");
	}
	return super.getPagingQueryResult(hql.toString(), map, firstResult, maxResults);
 }

 public List<Product> getAllProduct() throws DAOException {
	String hql = "";
	hql = "from Product";
	return super.getQueryResult(hql.toString());
 }
}
