package com.jyd.bms.dao;
import java.util.List;
import com.jyd.bms.bean.BaDataTemplate;
import com.jyd.bms.tool.exception.DAOException;
/**
 * @category Generated 2018-10-14 20:12:29 by GeneratedTool
 * @author mjy
 */
public interface BaDataTemplateDAO extends HibernateBase<BaDataTemplate> {
	public int getBaDataTemplateCount(String condition) throws DAOException;
	public List<BaDataTemplate> getPagingBaDataTemplate(int firstResult, int maxResults, String condition)throws DAOException;
	public List<BaDataTemplate> getAllBaDataTemplate() throws DAOException;
}