package com.jyd.bms.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.dao.BaPositionDAO;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-11 22:39:56 by GeneratedTool
 * @author mjy
 */
@Service("BaPositionService")
public class BaPositionService extends BaseService<BaPosition> {
@Autowired(required = true)
private BaPositionDAO baPositionDAO;

 public int getBaPositionCount(String condition) throws DAOException {
	return baPositionDAO.getBaPositionCount(condition);
 }

 public List<BaPosition> getPagingBaPosition(int firstResult, int maxResults, String condition) throws DAOException {
	return baPositionDAO.getPagingBaPosition(firstResult, maxResults, condition);
 }

 public List<BaPosition> getAllBaPosition() throws DAOException {
	return baPositionDAO.getAllBaPosition();
 }
 @Override

 public void setDAO() {
	this.baseDAO = baPositionDAO;
 }
}
