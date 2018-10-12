package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaAdmissibleArea;
import com.jyd.bms.dao.BaAdmissibleAreaDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-12 23:39:01 by GeneratedTool
 * @author mjy
 */
@Service("BaAdmissibleAreaService")
public class BaAdmissibleAreaService extends BaseService<BaAdmissibleArea> {
@Autowired(required = true)
private BaAdmissibleAreaDAO baAdmissibleAreaDAO;

 public int getBaAdmissibleAreaCount(String condition) throws DAOException {
	return baAdmissibleAreaDAO.getBaAdmissibleAreaCount(condition);
 }

 public List<BaAdmissibleArea> getPagingBaAdmissibleArea(int firstResult, int maxResults, String condition) throws DAOException {
	return baAdmissibleAreaDAO.getPagingBaAdmissibleArea(firstResult, maxResults, condition);
 }

 public List<BaAdmissibleArea> getAllBaAdmissibleArea() throws DAOException {
	return baAdmissibleAreaDAO.getAllBaAdmissibleArea();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baAdmissibleAreaDAO;
 }
}
