package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaAccountType;
import com.jyd.bms.dao.BaAccountTypeDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:36:59 by GeneratedTool
 * @author mjy
 */
@Service("BaAccountTypeService")
public class BaAccountTypeService extends BaseService<BaAccountType> {
@Autowired(required = true)
private BaAccountTypeDAO baAccountTypeDAO;

 public int getBaAccountTypeCount(String condition) throws DAOException {
	return baAccountTypeDAO.getBaAccountTypeCount(condition);
 }

 public List<BaAccountType> getPagingBaAccountType(int firstResult, int maxResults, String condition) throws DAOException {
	return baAccountTypeDAO.getPagingBaAccountType(firstResult, maxResults, condition);
 }

 public List<BaAccountType> getAllBaAccountType() throws DAOException {
	return baAccountTypeDAO.getAllBaAccountType();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baAccountTypeDAO;
 }
}
