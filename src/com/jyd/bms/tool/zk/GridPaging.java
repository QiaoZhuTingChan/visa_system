package com.jyd.bms.tool.zk;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;

public class GridPaging extends Paging {
	public static final Logger log;
	public static final int INTI_FIRST_RESULT = 0;
	private Component pagingControlComponent;
	private PagingControlComponentModelList pagingControlModelList;
	private boolean isTreeModel;
	private Collection exitObjects;
	private Integer PAGE_SIZE;
	private boolean isKeepState;

	public boolean isKeepState() {
		return isKeepState;
	}

	public void setKeepState(boolean isKeepState) {
		this.isKeepState = isKeepState;
	}

	static {
		log = LoggerFactory.getLogger((Class) GridPaging.class);
	}

	public GridPaging() {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
	}

	public GridPaging(final Grid pagingControlGrid, final PagingControlComponentModelList pagingControlModelList,
			final Collection exitObjects, final int pageSize) throws Exception {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
		this.pagingControlComponent = (Component) pagingControlGrid;
		this.pagingControlModelList = pagingControlModelList;
		this.exitObjects = exitObjects;
		this.PAGE_SIZE = pageSize;
		this.onCreate();
	}

	public GridPaging(final Grid pagingControlGrid, final PagingControlComponentModelList pagingControlModelList,
			final int pageSize) throws Exception {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
		this.pagingControlComponent = (Component) pagingControlGrid;
		this.pagingControlModelList = pagingControlModelList;
		this.PAGE_SIZE = pageSize;
		this.onCreate();
	}

	public GridPaging(final Listbox pagingControlListbox, final PagingControlComponentModelList pagingControlModelList,
			final int pageSize) throws Exception {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
		this.pagingControlComponent = (Component) pagingControlListbox;
		this.pagingControlModelList = pagingControlModelList;
		this.PAGE_SIZE = pageSize;
		this.onCreate();
	}

	public GridPaging(final Listbox pagingControlListbox, final PagingControlComponentModelList pagingControlModelList,
			final Collection exitObjects, final int pageSize) throws Exception {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
		this.pagingControlComponent = (Component) pagingControlListbox;
		this.pagingControlModelList = pagingControlModelList;
		this.exitObjects = exitObjects;
		this.PAGE_SIZE = pageSize;
		this.onCreate();
	}

	public GridPaging(final boolean isTreeModel, final Tree pagingControlTree,
			final PagingControlComponentModelList pagingControlModelList, final int pageSize) throws Exception {
		this.exitObjects = null;
		this.PAGE_SIZE = null;
		this.isTreeModel = isTreeModel;
		this.pagingControlComponent = (Component) pagingControlTree;
		this.pagingControlModelList = pagingControlModelList;
		this.PAGE_SIZE = pageSize;
		this.onCreate();
	}

	public void onCreate() throws Exception {
		this.initUI();
		this.initData();
	}

	public void initUI() {
	}

	public void initData() {
		if (this.PAGE_SIZE == null) {
			this.PAGE_SIZE = 20;
		}
		this.setPagingControlComponentModel(this.pagingControlModelList, this.PAGE_SIZE);
		this.addForward("onPaging", (Component) null, "onPaging");
	}

	public void onPaging() {
		final int currentPageNo = this.getActivePage();
		final int pageSize = this.getPageSize();
		final int firstResult = currentPageNo * pageSize;
		this.pagingControlComponent.invalidate();
		if (this.pagingControlComponent != null && this.pagingControlModelList != null) {
			if (!this.isTreeModel) {
				this.setCompoentModel(this.pagingControlModelList.getPagingControlListModel(firstResult, pageSize));
			} else {
				this.setCompoentModel(this.pagingControlModelList.getPagingControlTreeModel(firstResult, pageSize));
			}
		}
	}

	public void setPagingControlComponentModel(final PagingControlComponentModelList pagingControlModelList,
			final int pageSize) {
		this.pagingControlModelList = pagingControlModelList;
		if (!this.isTreeModel) {
			ListModel listModel = (ListModel) new ListModelList();
			if (pagingControlModelList != null) {
				if (!isKeepState) {
					this.setPageSize(pageSize);
					listModel = pagingControlModelList.getPagingControlListModel(0, this.getPageSize());
					this.setActivePage(0);
				} else {
					this.setPageSize(pageSize);
					listModel = pagingControlModelList.getPagingControlListModel(this.getActivePage(),
							this.getPageSize());
				}
			}
			this.setCompoentModel(listModel);
		}
		// else {
		// TreeModel treeModel = (TreeModel)new SimpleTreeModel(new
		// SimpleTreeNode((Object)null, (List)new ArrayList()));
		// if (pagingControlModelList != null) {
		// this.setPageSize(pageSize);
		// treeModel = pagingControlModelList.getPagingControlTreeModel(0,
		// this.getPageSize());
		// this.setActivePage(0);
		// }
		// this.setCompoentModel(treeModel);
		// }
	}

	public void setCompoentModel(final Object model) {
		if (this.pagingControlComponent instanceof Grid) {
			final ListModelList listModel = (ListModelList) model;
			if (this.exitObjects != null) {
				listModel.removeAll(this.exitObjects);
			}
			((Grid) this.pagingControlComponent).setModel((ListModel) listModel);
		} else if (this.pagingControlComponent instanceof Listbox) {
			final ListModelList listModel = (ListModelList) model;
			if (this.exitObjects != null) {
				listModel.removeAll(this.exitObjects);
			}
			((Listbox) this.pagingControlComponent).setModel((ListModel) listModel);
		} else if (this.pagingControlComponent instanceof Tree) {
			((Tree) this.pagingControlComponent).setModel((TreeModel) model);
		}
	}

	public PagingControlComponentModelList getPagingControlModelList() {
		return this.pagingControlModelList;
	}

	public void setPagingControlModelList(final PagingControlComponentModelList pagingControlModelList) {
		this.pagingControlModelList = pagingControlModelList;
	}
}