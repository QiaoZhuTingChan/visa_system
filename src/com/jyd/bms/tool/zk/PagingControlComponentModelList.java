package com.jyd.bms.tool.zk;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.TreeModel;

public class PagingControlComponentModelList
{

    public PagingControlComponentModelList()
    {
        isQeuryDatabaseDirectly = true;
    }

    public PagingControlComponentModelList(Object daoFactoryInstance, String daoMethodName, String searchMethodName)
    {
        isQeuryDatabaseDirectly = true;
        this.daoFactoryInstance = daoFactoryInstance;
        this.daoMethodName = daoMethodName;
        this.searchMethodName = searchMethodName;
    }

    public PagingControlComponentModelList(Object daoFactoryInstance, String daoMethodName, String searchMethodName, Object searchMethodParams[])
    {
        this(daoFactoryInstance, daoMethodName, searchMethodName);
        this.searchMethodParams = searchMethodParams;
    }

    public PagingControlComponentModelList(Object className, String searchMethodName)
    {
        isQeuryDatabaseDirectly = true;
        isQeuryDatabaseDirectly = false;
        this.className = className;
        this.searchMethodName = searchMethodName;
    }

    public PagingControlComponentModelList(Object className, String searchMethodName, Object searchMethodParams[])
    {
        this(className, searchMethodName);
        this.searchMethodParams = searchMethodParams;
    }

    public ListModel getPagingControlListModel(int firstReult, int maxResults)
    {
        return new ListModelList(getPagingControlComponentDataSet(firstReult, maxResults));
    }

    public TreeModel getPagingControlTreeModel(int firstReult, int maxResults)
    {
        List treeModels = getPagingControlComponentDataSet(firstReult, maxResults);
        return (TreeModel)treeModels.get(0);
    }

    public List getPagingControlComponentDataSet(int firstResult, int maxResults)
    {
        List dataSet = new ArrayList();
        Object searchParams[] = ArrayUtils.clone(searchMethodParams);
        searchParams = ArrayUtils.add(searchParams, 0, Integer.valueOf(firstResult));
        searchParams = ArrayUtils.add(searchParams, 1, Integer.valueOf(maxResults));
        if(isQeuryDatabaseDirectly)
        {
            Object daoInstance = null;
            try
            {
                daoInstance = MethodUtils.invokeMethod(daoFactoryInstance, daoMethodName, null);
                dataSet = (List)MethodUtils.invokeMethod(daoInstance, searchMethodName, searchParams);
            }
            catch(Exception e)
            {
                throw new RuntimeException((new StringBuilder("methodName: ")).append(searchMethodName).append(", ").append(e.toString()).toString(), e);
            }
        } else
        {
            try
            {
                dataSet = (List)MethodUtils.invokeMethod(className, searchMethodName, searchParams);
            }
            catch(Exception e)
            {
            	System.out.println(className);
            	System.out.println(searchMethodName);
                throw new RuntimeException((new StringBuilder("methodName: ")).append(searchMethodName).append(", ").append(e.toString()).toString(), e);
            }
        }
        return dataSet;
    }

    private static final Logger log = LoggerFactory.getLogger(PagingControlComponentModelList.class);
    private Object daoFactoryInstance;
    private String daoMethodName;
    private String searchMethodName;
    private Object searchMethodParams[];
    private boolean isQeuryDatabaseDirectly;
    private Object className;

}
