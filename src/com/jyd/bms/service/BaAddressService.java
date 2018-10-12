package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaAddress;
import com.jyd.bms.dao.BaAddressDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 23:30:12 by GeneratedTool
 * @author mjy
 */
@Service("BaAddressService")
public class BaAddressService extends BaseService<BaAddress> {
@Autowired(required = true)
private BaAddressDAO baAddressDAO;

 public int getBaAddressCount(String condition) throws DAOException {
	return baAddressDAO.getBaAddressCount(condition);
 }

 public List<BaAddress> getPagingBaAddress(int firstResult, int maxResults, String condition) throws DAOException {
	return baAddressDAO.getPagingBaAddress(firstResult, maxResults, condition);
 }

 public List<BaAddress> getAllBaAddress() throws DAOException {
	return baAddressDAO.getAllBaAddress();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baAddressDAO;
 }
}
