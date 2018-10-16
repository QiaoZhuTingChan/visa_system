package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.Supplier;
import com.jyd.bms.dao.SupplierDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:39:51 by GeneratedTool
 * @author mjy
 */
@Service("SupplierService")
public class SupplierService extends BaseService<Supplier> {
@Autowired(required = true)
private SupplierDAO supplierDAO;

 public int getSupplierCount(String condition) throws DAOException {
	return supplierDAO.getSupplierCount(condition);
 }

 public List<Supplier> getPagingSupplier(int firstResult, int maxResults, String condition) throws DAOException {
	return supplierDAO.getPagingSupplier(firstResult, maxResults, condition);
 }

 public List<Supplier> getAllSupplier() throws DAOException {
	return supplierDAO.getAllSupplier();
 }
 @Override

 public void setDAO() {
	this.baseDAO = supplierDAO;
 }
}
