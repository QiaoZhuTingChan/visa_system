package com.jyd.bms.window.basedata;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.jyd.bms.bean.ProductCategory;
import com.jyd.bms.bean.User;
import com.jyd.bms.components.ProductCategoryChoose;
import com.jyd.bms.service.ProductCategoryService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.UserSession;

/**
 * @category Generated 2018-10-13 14:23:15 by GeneratedTool
 * @author mjy
 */
public class ProductCategoryWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private Textbox conditionTextbox;
	private String condition = "";
	private Label nameLabel;
	private Textbox nameTextbox;
	private Label parentIdLabel;
	private ProductCategoryChoose productCategoryChoose;
	private Label sortIndexLabel;
	private Textbox sortIndexTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private ProductCategory productCategory;
	private ProductCategoryService productCategoryService;
	private List<ProductCategory> productCategorylist = new ArrayList<>();
	private List<ProductCategory> tempCategoryList = new ArrayList<>();
	private List<ProductCategory> searchCategorylist = new ArrayList<>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Tree productCategoryTree;

	private static final Logger log = LoggerFactory.getLogger(ProductCategoryWindow.class);
	private int edit = 0;

	public ProductCategoryWindow() {
		this.menuId = "product_category";
	}

	public void initUI() {
		productCategoryService = getBean("ProductCategoryService");
	}

	@Override
	public void initData() {
		productCategoryChoose.setProductCategorylist(productCategorylist);
		productCategoryChoose.initComponent();
			try {
				loadingParent();
			} catch (DataNotFoundException | DAOException e) {
				log.error(this.getClass().getSimpleName(), e);
			}
			
	}

	/**
	 * 载树
	 * 
	 * @throws DataNotFoundException
	 * @throws DAOException
	 */
	public void loadingParent() throws DataNotFoundException, DAOException {
		productCategorylist = productCategoryService.getAllProductCategory();
		loadingTree();
	}

	private void loadingTree() throws DataNotFoundException {
		productCategoryTree.clear();
		tempCategoryList.clear();
		List<ProductCategory> rootCateList = new ArrayList<ProductCategory>();
		for (ProductCategory cate : productCategorylist) {
			if (cate.getParentCategory() == null) {
				rootCateList.add(cate);
			} else {
				tempCategoryList.add(cate);
			}
		}

		if (productCategoryTree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(productCategoryTree);
		}
		for (ProductCategory cate : rootCateList) {
			createTreeitem(cate, productCategoryTree.getTreechildren());
		}
		productCategoryTree.clearSelection();
	}

	private void createTreeitem(ProductCategory cate, Treechildren tc) throws DataNotFoundException {
		Treeitem treeitem = new Treeitem();
		showTreeItem(treeitem, cate);
		Treechildren treechildren = treeitem.getTreechildren();
		for (ProductCategory temp : productCategorylist) {
			if (temp.getParentCategory() != null) {
				if (temp.getParentCategory().getId() == cate.getId()) {
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

	private void createSearchTree() throws DataNotFoundException {
		if (productCategoryTree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(productCategoryTree);
		}
		for (ProductCategory cate : searchCategorylist) {
			Treeitem treeitem = new Treeitem();
			showTreeItem(treeitem, cate);
			treeitem.setParent(productCategoryTree.getTreechildren());
		}
	}

	private void showTreeItem(Treeitem item, ProductCategory cate) throws DataNotFoundException {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}
		new Treecell(cate.getName()).setParent(tr);
		new Treecell(cate.getParentCategory() == null ? "" : cate.getParentCategory().getName()).setParent(tr);
		new Treecell(sdf.format(cate.getUpdateDate())).setParent(tr);
		new Treecell(cate.getUpdateUser()).setParent(tr);
		new Treecell(cate.getRemark()).setParent(tr);
		item.setValue(cate);
	}

	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		condition = conditionTextbox.getValue();
		initData();
	}

	public void onOKsearchButton() throws SuspendNotAllowedException, InterruptedException {
		onClick$searchButton();
	}

	public void enableTextbox(boolean flag) {
		nameLabel.setVisible(!flag);
		nameTextbox.setVisible(flag);
		parentIdLabel.setVisible(!flag);
		productCategoryChoose.setVisible(flag);
		sortIndexLabel.setVisible(!flag);
		sortIndexTextbox.setVisible(flag);
		remarkLabel.setVisible(!flag);
		remarkTextbox.setVisible(flag);
	}

	public void enableButton(String type) {
		if (type.equals("add")) {
			addButton.setVisible(false);
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		}
		if (type.equals("update")) {
			addButton.setVisible(false);
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		}
		if (type.equals("cancel")) {
			addButton.setVisible(true);
			editButton.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
		if (type.equals("save")) {
			addButton.setVisible(true);
			editButton.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
		if (type.equals("select")) {
			addButton.setVisible(true);
			editButton.setVisible(true);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
	}

	public void clearTextbox() {
		nameLabel.setValue("");
		nameTextbox.setValue("");
		parentIdLabel.setValue("");
		productCategoryChoose.setValue("");
		sortIndexLabel.setValue("");
		sortIndexTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setProductCategoryValue(ProductCategory productCategory) {
		productCategory.setName(nameTextbox.getValue());
//		productCategory.setParentId(parentIdTextbox.getValue());
//		productCategory.setSortIndex(sortIndexTextbox.getValue());
		productCategory.setRemark(remarkTextbox.getValue());
	}

	public void setProductCategoryData(ProductCategory productCategory) {
		nameLabel.setValue(productCategory.getName());
		nameTextbox.setValue(productCategory.getName());
//		parentIdLabel.setValue(productCategory.getParentId());
//		parentIdTextbox.setValue(productCategory.getParentId());
//		sortIndexLabel.setValue(productCategory.getSortIndex());
//		sortIndexTextbox.setValue(productCategory.getSortIndex());
		remarkLabel.setValue(productCategory.getRemark());
		remarkTextbox.setValue(productCategory.getRemark());
	}

	public void onSelect$productCategoryListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		// productCategory = getSelectItem().getValue();
		clearTextbox();
		setProductCategoryData(productCategory);
		enableTextbox(false);
		enableButton("select");
	}

	public void onClick$cancelButton() {
		enableButton("cancel");
		enableTextbox(false);
		clearTextbox();
	}

	public void onClick$addButton() {
		edit = 0;
		enableTextbox(true);
		enableButton("add");
		clearTextbox();
	}

	public void onClick$editButton() {
		edit = -1;
		enableTextbox(true);
		enableButton("update");
	}

	public boolean checkInput() {
		boolean flag = true;
		if (nameTextbox.getValue().equals("")) {
			nameTextbox.focus();
			Messagebox.show("产品分类名称不能为空！");
			flag = false;
		}
//		if (parentIdTextbox.getValue().equals("")) {
//			parentIdTextbox.focus();
//			Messagebox.show("父类id不能为空！");
//			flag = false;
//		}
		if (sortIndexTextbox.getValue().equals("")) {
			sortIndexTextbox.focus();
			Messagebox.show("排序不能为空！");
			flag = false;
		}
		return flag;
	}

	public void onClick$saveButton() {
		try {
			User sessionUser = UserSession.getUser();
			if (sessionUser == null) {
				Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
				return;
			}
			Timestamp date = new Timestamp(System.currentTimeMillis());
			String user = sessionUser.getLoginName();
			if (!checkInput()) {
				return;
			}
			if (edit == 0) {
				productCategory = new ProductCategory();
				setProductCategoryValue(productCategory);
				productCategory.setCreateDate(date);
				productCategory.setCreateUser(user);
				productCategory.setUpdateDate(date);
				productCategory.setUpdateUser(user);
				productCategoryService.add(productCategory);
			} else {
				setProductCategoryValue(productCategory);
				productCategory.setUpdateDate(date);
				productCategory.setUpdateUser(user);
				productCategoryService.update(productCategory);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("productCategoryWindow", e);
		} catch (UpdateException e) {
			log.error("productCategoryWindow", e);
		}
	}

}
