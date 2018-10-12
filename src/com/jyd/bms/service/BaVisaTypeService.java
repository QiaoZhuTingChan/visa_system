package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaVisaType;
import com.jyd.bms.dao.BaVisaTypeDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 22:37:10 by GeneratedTool
 * @author mjy
 */
@Service("BaVisaTypeService")
public class BaVisaTypeService extends BaseService<BaVisaType> {
@Autowired(required = true)
private BaVisaTypeDAO baVisaTypeDAO;

 public int getBaVisaTypeCount(String condition) throws DAOException {
	return baVisaTypeDAO.getBaVisaTypeCount(condition);
 }

 public List<BaVisaType> getPagingBaVisaType(int firstResult, int maxResults, String condition) throws DAOException {
	return baVisaTypeDAO.getPagingBaVisaType(firstResult, maxResults, condition);
 }

 public List<BaVisaType> getAllBaVisaType() throws DAOException {
	return baVisaTypeDAO.getAllBaVisaType();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baVisaTypeDAO;
 }
}
