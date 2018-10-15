package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaDataTemplate;
import com.jyd.bms.dao.BaDataTemplateDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-14 20:12:29 by GeneratedTool
 * @author mjy
 */
@Service("BaDataTemplateService")
public class BaDataTemplateService extends BaseService<BaDataTemplate> {
@Autowired(required = true)
private BaDataTemplateDAO baDataTemplateDAO;

 public int getBaDataTemplateCount(String condition) throws DAOException {
	return baDataTemplateDAO.getBaDataTemplateCount(condition);
 }

 public List<BaDataTemplate> getPagingBaDataTemplate(int firstResult, int maxResults, String condition) throws DAOException {
	return baDataTemplateDAO.getPagingBaDataTemplate(firstResult, maxResults, condition);
 }

 public List<BaDataTemplate> getAllBaDataTemplate() throws DAOException {
	return baDataTemplateDAO.getAllBaDataTemplate();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baDataTemplateDAO;
 }
}
