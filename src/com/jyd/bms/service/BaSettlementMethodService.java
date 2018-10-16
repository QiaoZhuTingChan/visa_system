package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaSettlementMethod;
import com.jyd.bms.dao.BaSettlementMethodDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-15 22:37:08 by GeneratedTool
 * @author mjy
 */
@Service("BaSettlementMethodService")
public class BaSettlementMethodService extends BaseService<BaSettlementMethod> {
@Autowired(required = true)
private BaSettlementMethodDAO baSettlementMethodDAO;

 public int getBaSettlementMethodCount(String condition) throws DAOException {
	return baSettlementMethodDAO.getBaSettlementMethodCount(condition);
 }

 public List<BaSettlementMethod> getPagingBaSettlementMethod(int firstResult, int maxResults, String condition) throws DAOException {
	return baSettlementMethodDAO.getPagingBaSettlementMethod(firstResult, maxResults, condition);
 }

 public List<BaSettlementMethod> getAllBaSettlementMethod() throws DAOException {
	return baSettlementMethodDAO.getAllBaSettlementMethod();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baSettlementMethodDAO;
 }
}
