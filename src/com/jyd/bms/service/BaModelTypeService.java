package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.dao.BaModelTypeDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-14 19:51:29 by GeneratedTool
 * @author mjy
 */
@Service("BaModelTypeService")
public class BaModelTypeService extends BaseService<BaModelType> {
@Autowired(required = true)
private BaModelTypeDAO baModelTypeDAO;

 public int getBaModelTypeCount(String condition) throws DAOException {
	return baModelTypeDAO.getBaModelTypeCount(condition);
 }

 public List<BaModelType> getPagingBaModelType(int firstResult, int maxResults, String condition) throws DAOException {
	return baModelTypeDAO.getPagingBaModelType(firstResult, maxResults, condition);
 }

 public List<BaModelType> getAllBaModelType() throws DAOException {
	return baModelTypeDAO.getAllBaModelType();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baModelTypeDAO;
 }
}
