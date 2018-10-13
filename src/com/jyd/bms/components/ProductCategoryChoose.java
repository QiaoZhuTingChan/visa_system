package com.jyd.bms.components;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.jyd.bms.bean.ProductCategory;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.window.admin.component.ComponentInterface;

@SuppressWarnings("serial")
public class ProductCategoryChoose extends Bandbox implements ComponentInterface {
	private Component handleComponent;
	private Tree tree = new Tree();
	private Textbox conditionTextbox = new Textbox();
	@SuppressWarnings("unused")
	private String condition = "";
	private Button searchButton = new Button("查找");
	private List<ProductCategory> productCategorylist = new ArrayList<>();
	private List<ProductCategory> tempCategoryList = new ArrayList<>();
	private List<ProductCategory> searchCategorylist = new ArrayList<>();

	private List<ProductCategory> lsproductCategorylist = new ArrayList<>();

	private Bandpopup popup = new Bandpopup();
	private static final Logger log = LoggerFactory.getLogger(ProductCategoryChoose.class);
	private ProductCategory productCategory;

	public ProductCategory getProductCategory() {
		if (this.getText().equals(""))
			return null;
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
		this.setText(productCategory == null ? "" : productCategory.getName());
	}

	public Textbox getConditionTextbox() {
		return conditionTextbox;
	}

	public void setConditionTextbox(String conditionTextboxValues) {
		this.conditionTextbox.setValue(conditionTextboxValues);
	}

	public List<ProductCategory> getProductCategorylist() {
		return productCategorylist;
	}

	public void setProductCategorylist(List<ProductCategory> productCategorylist) {
		this.productCategorylist = productCategorylist;
	}

	public ProductCategoryChoose() {
		this.setButtonVisible(true);

		popup.setParent(this);
		popup.setHeight("300px");
		popup.setWidth("600px");

		initTreeCell();
		Borderlayout layout = new Borderlayout();
		layout.setParent(popup);
		layout.setHeight("300px");
		layout.setWidth("600px");

		North north = new North();
		north.setParent(layout);

		Hbox hbox = new Hbox();
		hbox.setParent(north);
		hbox.setPack("center");

		Label label = new Label();
		label.setValue("查询条件");
		label.setParent(hbox);
		new Space().setParent(hbox);
		conditionTextbox.setParent(hbox);
		searchButton.setParent(hbox);

		Center listCenter = new Center();
		listCenter.setFlex(true);
		listCenter.setParent(layout);
		listCenter.setAutoscroll(true);

		tree.setParent(listCenter);
		searchButton.addForward("onClick", (Component) this, "onClickOK", null);
		conditionTextbox.addForward("onOK", (Component) this, "onClickOK", null);
		this.addForward("onOK", (Component) this, "onClickSearch", this.getText());
		tree.addForward("onSelect", (Component) this, "onChooseDepartment", null);
	}

	public ProductCategoryChoose(ProductCategory productCategory) {
		this();
		this.productCategory = productCategory;
	}

	public void loadingTree(Tree tree) throws DataNotFoundException {
		tree.clear();
		tempCategoryList.clear();
		List<ProductCategory> lstRootDepartment = new ArrayList<>();
		for (ProductCategory cate : productCategorylist) {
			if (cate.getParentCategory() == null) {
				lstRootDepartment.add(cate);
			} else {
				tempCategoryList.add(cate);
			}
		}
		if (tree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(tree);
		}
		if (!lstRootDepartment.isEmpty()) {
			for (ProductCategory department : lstRootDepartment) {
				createTreeitem(department, tree.getTreechildren());
			}
		} else {
			for (ProductCategory cate : productCategorylist) {
				if (!hasNext(cate)) {
					createTreeitem(cate, tree.getTreechildren());
				}
			}
		}
	}

	public boolean hasNext(ProductCategory newDept) {
		boolean flag = false;
		for (ProductCategory oldDept : productCategorylist) {
			if (newDept.getParentCategory() != null) {
				if (newDept.getParentCategory().getId() == oldDept.getId()) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private void createTreeitem(ProductCategory department, Treechildren tc) throws DataNotFoundException {
		Treeitem treeitem = new Treeitem();
		showTreeItem(treeitem, department);
		Treechildren treechildren = treeitem.getTreechildren();
		for (ProductCategory temp : productCategorylist) {
			if (temp.getParentCategory() != null) {
				if (temp.getParentCategory().getId() == department.getId()) {
					if (treechildren == null) {
						treechildren = new org.zkoss.zul.Treechildren();
						treechildren.setParent(treeitem);
					}
					createTreeitem(temp, treechildren);
				}
			}
		}
		treeitem.setParent(tc);
	}

	private void showTreeItem(Treeitem item, ProductCategory department) throws DataNotFoundException {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}
		new Treecell(department.getName()).setParent(tr);
		new Treecell(department.getParentCategory() == null ? "" : department.getParentCategory().getName())
				.setParent(tr);
		new Treecell(department.getRemark()).setParent(tr);
		item.setValue(department);
		lsproductCategorylist.add(department);
	}

	public void onClickOK() throws DataNotFoundException {
		String condition = conditionTextbox.getValue();
		if (condition.trim().equals("")) {
			loadingTree(tree);
			return;
		}
		searchCategorylist.clear();
		for (ProductCategory department : tempCategoryList) {
			if (department.getName().toUpperCase().indexOf(condition.toUpperCase()) > -1) {
				searchCategorylist.add(department);
			}
		}
		tree.clear();
		createSearchTree();
		this.open();
	}

	public void onClickSearch() throws DataNotFoundException {
		String condition = this.getText();
		conditionTextbox.setValue(condition);
		if (condition.trim().equals("")) {
			loadingTree(tree);
			return;
		}
		searchCategorylist.clear();
		for (ProductCategory department : lsproductCategorylist) {
			if (department.getName().toUpperCase().indexOf(condition.toUpperCase()) > -1) {
				searchCategorylist.add(department);
			}
		}
		if (searchCategorylist.size() == 1) {
			setProductCategory(searchCategorylist.get(0));
			conditionTextbox.setValue(searchCategorylist.get(0).getName());
			this.setText(searchCategorylist.get(0).getName());
		} else if (searchCategorylist.size() == 0) {
			setProductCategory(null);
			Messagebox.info("没有找到");
			this.focus();
		} else {
			setProductCategory(productCategory);
			tree.clear();
			createSearchTree();
			this.open();
			conditionTextbox.setFocus(true);
		}
	}

	private void loadingMenu() throws DataNotFoundException {
		loadingTree(tree);
	}

	private void createSearchTree() {
		if (tree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(tree);
		}

		for (ProductCategory cate : searchCategorylist) {
			Treeitem treeitem = new Treeitem();
			showSearchTreeItem(treeitem, cate);
			treeitem.setParent(tree.getTreechildren());
		}
	}

	private void showSearchTreeItem(Treeitem item, ProductCategory cate) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}
		new Treecell(cate.getName()).setParent(tr);
		new Treecell(cate.getParentCategory() == null ? "" : cate.getParentCategory().getName()).setParent(tr);
		new Treecell(cate.getRemark()).setParent(tr);
		item.setValue(cate);
	}

	public void onChooseDepartment(Event event) throws SuspendNotAllowedException, InterruptedException {
		productCategory = (ProductCategory) tree.getSelectedItem().getValue();
		this.setText(productCategory.getName());
		this.conditionTextbox.setText(productCategory.getName());
		if (handleComponent == null) {
			Events.postEvent("onChooseDepartment", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onChooseDepartment", handleComponent, null);
		}
		this.close();
	}

	private void initTreeCell() {
		Treecols treecols = new Treecols();
		treecols.setParent(tree);
		treecols.setSizable(true);

		Treecol menuTreecol = new Treecol();
		menuTreecol.setParent(treecols);
		menuTreecol.setLabel("产品分类名称");
		menuTreecol.setWidth("50%");

		Treecol managerTreecol = new Treecol();
		managerTreecol.setParent(treecols);
		managerTreecol.setLabel("上级产品分类");
		managerTreecol.setWidth("25%");

		Treecol storeTreecol = new Treecol();
		storeTreecol.setParent(treecols);
		storeTreecol.setLabel("备注");
		storeTreecol.setWidth("25%");
	}

	public boolean checkCirculate(ProductCategory cate) throws DAOException, DataNotFoundException {
		if (cate == null)
			return false;
		return true;
	}

	@Override
	public Object getCurrentValue() {
		return getProductCategory();
	}

	@Override
	public void initComponent() {
		try {
			loadingMenu();
		} catch (DataNotFoundException e) {
			log.error("ProductCategoryChoose", e);
		}
	}
}