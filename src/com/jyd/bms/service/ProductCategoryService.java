package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.ProductCategory;
import com.jyd.bms.dao.ProductCategoryDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-13 14:23:15 by GeneratedTool
 * @author mjy
 */
@Service("ProductCategoryService")
public class ProductCategoryService extends BaseService<ProductCategory> {
@Autowired(required = true)
private ProductCategoryDAO productCategoryDAO;

 public int getProductCategoryCount(String condition) throws DAOException {
	return productCategoryDAO.getProductCategoryCount(condition);
 }

 public List<ProductCategory> getPagingProductCategory(int firstResult, int maxResults, String condition) throws DAOException {
	return productCategoryDAO.getPagingProductCategory(firstResult, maxResults, condition);
 }

 public List<ProductCategory> getAllProductCategory() throws DAOException {
	return productCategoryDAO.getAllProductCategory();
 }
 @Override

 public void setDAO() {
	this.baseDAO = productCategoryDAO;
 }
}
