package com.jyd.bms.window.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.MenuService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.UserSession;
import com.jyd.bms.window.admin.component.MenuTreeBandbox;

public class MenuWindow extends BaseWindow {
	private boolean editFlag = false;
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Label menuIdLabel;
	private Label nameLabel;
	private Label parentMenuLabel;
	private Label typeLabel;
	private Label iconLabel;
	private Label urlLabel;
	private Label callMethodLabel;
	private Label displayLabel;
	private Label sortLabel;
	private Listbox typeListbox;
	private int menuId = 0;
	private Textbox menuIdTextbox;
	private Textbox nameTextbox;
	private Textbox iconTextbox;
	private Textbox urlTextbox;
	private Textbox callMethodTextbox;
	private Textbox conditionTextbox;
	private Intbox sortIntbox;
	private Checkbox displayCheckbox;
	private String condition = "";
	private South southPaging;
	private Menu menu;
	private MenuTreeBandbox parentMenuBandbox;
	private Tree menuTree;

	private List<Menu> lstAllMenu = new ArrayList<Menu>();
	private List<Menu> lstSearchMenu = new ArrayList<Menu>();
	private List<Menu> lstMenu = new ArrayList<Menu>();

	private MenuService menuService;
	private static final Logger log = LoggerFactory.getLogger(MenuWindow.class);
	public MenuWindow() {
		super.menuId="sys_menu";
	}
	/**
	 * 初始化数据绑定
	 */
	public void initUI() {
		typeListbox.setMold("select");
		typeListbox.setItemRenderer(new TypeListRenderer());
		menuTree.addForward("onSelect", (Component) this, "onSelectMenu", null);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		menuService = getBean("MenuService");
		parentMenuBandbox.setMenuService(menuService);
		parentMenuBandbox.initComponent();

		List<Integer> listType = new ArrayList<Integer>();
		listType.add(com.jyd.bms.common.StaticVariable.MENU_TYPE_SYSTEM);
		listType.add(com.jyd.bms.common.StaticVariable.MENU_TYPE_MODULE);
		listType.add(com.jyd.bms.common.StaticVariable.MENU_TYPE_PAGE);
		listType.add(com.jyd.bms.common.StaticVariable.MENU_TYPE_BUTTON);
		listType.add(com.jyd.bms.common.StaticVariable.MENU_TYPE_SPECIAL);

		typeListbox.setModel(new ListModelList(listType, true));

		loadingMenu();
	}

	/**
	 * 搜索
	 */
	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		String condition = conditionTextbox.getValue();
		if (condition.trim().equals("")) {
			loadingTree();
			return;
		}

		lstSearchMenu.clear();
		for (Menu menu : lstMenu) {
			if (menu.getName().toUpperCase().indexOf(condition.toUpperCase()) > -1
					|| menu.getMenuUrl().toUpperCase().indexOf(condition.toUpperCase()) > -1) {
				lstSearchMenu.add(menu);
			}
		}
		if (lstSearchMenu.size() == 0) {
			//Messagebox.info(Labels.getLabel("useraccesscontrol.system.notfind"));
			Messagebox.info("查无结果");
			return;
		} else {
			menuTree.clear();
			createSearchTree();
			conditionTextbox.setFocus(true);
		}
	}

	public void onOKsearchButton() throws SuspendNotAllowedException, InterruptedException {
		onClick$searchButton();
	}

	private void loadingMenu() {
		try {
			lstAllMenu.clear();
			lstAllMenu = menuService.getAllMenu();
			loadingTree();
		} catch (DAOException e) {
			log.error("MenuWindow", e);
			Messagebox.error("获取数据出错了!");
		}
	}

	public void loadingTree() {
		menuTree.clear();
		lstMenu.clear();
		List<Menu> lstRootDepartment = new ArrayList<Menu>();
		for (Menu temp : lstAllMenu) {
			if (temp.getParentId().equals("")) {
				lstRootDepartment.add(temp);
			}
		}

		if (menuTree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(menuTree);
		}

		for (Menu menu : lstRootDepartment) {
			createTreeitem(menu, menuTree.getTreechildren());
		}
	}

	private void createTreeitem(Menu menu, Treechildren tc) {
		Treeitem treeitem = new Treeitem();
		showTreeItem(treeitem, menu);
		Treechildren treechildren = treeitem.getTreechildren();
		for (Menu temp : lstAllMenu) {
			if (temp.getParentId().equals(menu.getMenuId())) {
				if (treechildren == null) {
					treechildren = new org.zkoss.zul.Treechildren();
					treechildren.setParent(treeitem);
				}
				createTreeitem(temp, treechildren);
			}
		}
		treeitem.setParent(tc);
	}

	public static String getMenuType(int typeId) {
		String type = "";
		switch (typeId) {
		case com.jyd.bms.common.StaticVariable.MENU_TYPE_SYSTEM:
			type = "系统";
			break;
		case com.jyd.bms.common.StaticVariable.MENU_TYPE_MODULE:
			type = "模块";
			break;
		case com.jyd.bms.common.StaticVariable.MENU_TYPE_PAGE:
			type = "页面";
			break;
		case com.jyd.bms.common.StaticVariable.MENU_TYPE_BUTTON:
			type = "按钮";
			break;
		case com.jyd.bms.common.StaticVariable.MENU_TYPE_SPECIAL:
			type = "特殊值";
			break;
		}

		return type;
	}

	private void showSearchTreeItem(Treeitem item, Menu menu) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}

		// 名称,类型,图标,地址,方法,语言标签,是否显示,排序号
		new Treecell(menu.getName()).setParent(tr);

		String type = "";
		type = getMenuType(menu.getType());

		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getLanguageId()).setParent(tr);
		new Treecell(menu.getDisplay() == true ? "显示":"隐藏").setParent(tr);
		new Treecell(String.valueOf(menu.getSort())).setParent(tr);

		item.setValue(menu);
	}

	private void showTreeItem(Treeitem item, Menu menu) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}

		new Treecell(menu.getName()).setParent(tr);
		String type = "";
		type = getMenuType(menu.getType());
		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getLanguageId()).setParent(tr);
		new Treecell(menu.getDisplay() == true ? "显示" : "隐藏").setParent(tr);
		new Treecell(String.valueOf(menu.getSort())).setParent(tr);
		item.setValue(menu);
		lstMenu.add(menu);
	}

	private void createSearchTree() {
		if (menuTree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(menuTree);
		}

		for (Menu menu : lstSearchMenu) {
			Treeitem treeitem = new Treeitem();
			showSearchTreeItem(treeitem, menu);
			treeitem.setParent(menuTree.getTreechildren());
		}
	}

	/**
	 * 选定
	 */
	public void onSelectMenu() throws SuspendNotAllowedException, InterruptedException {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);

		menuIdLabel.setVisible(true);
		nameLabel.setVisible(true);
		parentMenuLabel.setVisible(true);
		typeLabel.setVisible(true);
		iconLabel.setVisible(true);
		urlLabel.setVisible(true);
		callMethodLabel.setVisible(true);
		displayLabel.setVisible(true);
		sortLabel.setVisible(true);

		typeListbox.setVisible(false);
		menuIdTextbox.setVisible(false);
		nameTextbox.setVisible(false);
		iconTextbox.setVisible(false);
		urlTextbox.setVisible(false);
		callMethodTextbox.setVisible(false);
		sortIntbox.setVisible(false);
		displayCheckbox.setVisible(false);
		parentMenuBandbox.setVisible(false);

		menu = (Menu) menuTree.getSelectedItem().getValue();
		menuId = menu.getId();
		if (menu != null) {
			menuIdLabel.setValue(menu.getMenuId());
			nameLabel.setValue(menu.getName());
			Menu parentMenu = null;
			try {
				parentMenu = menuService.getParentMenu(menu);
				parentMenuLabel.setValue(parentMenu == null ? "" : parentMenu.getName());
			} catch (DAOException e) {
				parentMenuLabel.setValue("");
			}

			String type = "";
			
			System.out.println("Type:" + menu.getType());
			
			type = getMenuType(menu.getType());

			typeLabel.setValue(type);
			iconLabel.setValue(menu.getIconName());
			urlLabel.setValue(menu.getMenuUrl());
			callMethodLabel.setValue(menu.getCallMethod());
			displayLabel.setValue(menu.getDisplay() == true ? "显示" : "隐藏");
			sortLabel.setValue(String.valueOf(menu.getSort()));
			typeListbox.setSelectedIndex(menu.getType() - 1);
			menuIdTextbox.setValue(menu.getMenuId());
			nameTextbox.setValue(menu.getName());
			iconTextbox.setValue(menu.getIconName());
			urlTextbox.setValue(menu.getMenuUrl());
			callMethodTextbox.setValue(menu.getCallMethod());
			sortIntbox.setValue(menu.getSort());
			displayCheckbox.setChecked(menu.getDisplay());
			parentMenuBandbox.setMenu(parentMenu);
		}
	}

	/**
	 * 编辑
	 */
	public void onClick$editButton() {
		editFlag = true;
		addButton.setVisible(false);
		editButton.setVisible(false);
		cancelButton.setVisible(true);
		saveButton.setVisible(true);

		menuIdLabel.setVisible(false);
		nameLabel.setVisible(false);
		parentMenuLabel.setVisible(false);
		typeLabel.setVisible(false);
		iconLabel.setVisible(false);
		urlLabel.setVisible(false);
		callMethodLabel.setVisible(false);
		displayLabel.setVisible(false);
		sortLabel.setVisible(false);

		typeListbox.setVisible(true);
		menuIdTextbox.setVisible(true);
		nameTextbox.setVisible(true);
		iconTextbox.setVisible(true);
		urlTextbox.setVisible(true);
		callMethodTextbox.setVisible(true);
		sortIntbox.setVisible(true);
		displayCheckbox.setVisible(true);
		parentMenuBandbox.setVisible(true);

		menu = (Menu) menuTree.getSelectedItem().getValue();
		menuId = menu.getId();
		menuIdLabel.setValue(menu.getMenuId());
		nameLabel.setValue(menu.getName());
		Menu parentMenu = null;
		try {
			parentMenu = menuService.getParentMenu(menu);
			parentMenuLabel.setValue(parentMenu == null ? "" : parentMenu.getName());
		} catch (DAOException e) {
			parentMenuLabel.setValue("");
		}

		String type = "";
		type = getMenuType(menu.getType());

		typeLabel.setValue(type);
		iconLabel.setValue(menu.getIconName());
		urlLabel.setValue(menu.getMenuUrl());
		callMethodLabel.setValue(menu.getCallMethod());
		displayLabel.setValue(menu.getDisplay() == true ? "显示" : "隐藏");
		sortLabel.setValue(String.valueOf(menu.getSort()));
		typeListbox.setSelectedIndex(menu.getType() - 1);
		menuIdTextbox.setValue(menu.getMenuId());
		nameTextbox.setValue(menu.getName());
		iconTextbox.setValue(menu.getIconName());
		urlTextbox.setValue(menu.getMenuUrl());
		callMethodTextbox.setValue(menu.getCallMethod());
		sortIntbox.setValue(menu.getSort());
		displayCheckbox.setChecked(menu.getDisplay());
		parentMenuBandbox.setMenu(parentMenu);
		menuIdTextbox.select();
	}

	/**
	 * 取消编辑
	 */
	public void onClick$cancelButton() {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(false);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);

		menuIdLabel.setVisible(true);
		nameLabel.setVisible(true);
		parentMenuLabel.setVisible(true);
		typeLabel.setVisible(true);
		iconLabel.setVisible(true);
		urlLabel.setVisible(true);
		callMethodLabel.setVisible(true);
		displayLabel.setVisible(true);
		sortLabel.setVisible(true);

		typeListbox.setVisible(false);
		menuIdTextbox.setVisible(false);
		nameTextbox.setVisible(false);
		iconTextbox.setVisible(false);
		urlTextbox.setVisible(false);
		callMethodTextbox.setVisible(false);
		sortIntbox.setVisible(false);
		displayCheckbox.setVisible(false);
		parentMenuBandbox.setVisible(false);

		menuIdLabel.setValue("");
		nameLabel.setValue("");
		parentMenuLabel.setValue("");
		typeLabel.setValue("");
		iconLabel.setValue("");
		urlLabel.setValue("");
		callMethodLabel.setValue("");
		displayLabel.setValue("");
		sortLabel.setValue("");
		initData();
	}

	/**
	 * 添加
	 */
	public void onClick$addButton() {
		menuId = 0;
		editFlag = false;
		addButton.setVisible(false);
		editButton.setVisible(false);
		cancelButton.setVisible(true);
		saveButton.setVisible(true);
		menuIdLabel.setVisible(false);
		nameLabel.setVisible(false);
		parentMenuLabel.setVisible(false);
		typeLabel.setVisible(false);
		iconLabel.setVisible(false);
		urlLabel.setVisible(false);
		callMethodLabel.setVisible(false);
		displayLabel.setVisible(false);
		sortLabel.setVisible(false);

		typeListbox.setVisible(true);
		menuIdTextbox.setVisible(true);
		nameTextbox.setVisible(true);
		iconTextbox.setVisible(true);
		urlTextbox.setVisible(true);
		callMethodTextbox.setVisible(true);
		sortIntbox.setVisible(true);
		displayCheckbox.setVisible(true);
		parentMenuBandbox.setVisible(true);

		typeListbox.setSelectedIndex(0);
		menuIdTextbox.setValue("");
		nameTextbox.setValue("");
		iconTextbox.setValue("");
		urlTextbox.setValue("");
		callMethodTextbox.setValue("");
		sortIntbox.setValue(0);
		displayCheckbox.setChecked(true);
		parentMenuBandbox.setMenu(null);
		menuIdTextbox.setFocus(true);
	}

	/**
	 * 保存数据
	 */
	public void onClick$saveButton() {
		try {
			if (menuIdTextbox.getValue().equals("")) {
				Messagebox.info("菜单唯一编号不可以为空");
				menuIdTextbox.select();
				return;
			}
			if (urlTextbox.getValue().equals("")) {
				Messagebox.info("路径不能为空");
				urlTextbox.select();
				return;
			}

			User user = UserSession.getUser();
			if (user == null) {
				Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
				return;
			}

			Timestamp date = new Timestamp(System.currentTimeMillis());
			if (menuId == 0 && editFlag == false) {
				menu = new Menu();
				menu.setMenuId(menuIdTextbox.getValue());
				menu.setName(nameTextbox.getValue());
				menu.setParentId(parentMenuBandbox.getMenu() == null ? "" : parentMenuBandbox.getMenu().getMenuId());
				menu.setType((int) typeListbox.getSelectedValue());
				menu.setIconName(iconTextbox.getValue() == null ? "" : iconTextbox.getValue());
				menu.setMenuUrl(urlTextbox.getValue());
				menu.setCallMethod(callMethodTextbox.getValue() == null ? "" : callMethodTextbox.getValue());
				menu.setDisplay(displayCheckbox.isChecked());
				menu.setSort(sortIntbox.getValue());

				menuService.add(menu);
				menuId = 0;
			} else if (editFlag == true) {
				menu = menuService.getById(menuId);

				// 验证回环
				if (parentMenuBandbox.checkCirculate(menu, parentMenuBandbox.getMenu())) {
					Messagebox.show(Labels.getLabel("useraccesscontrol.menu.existcircle"));
					return;
				}

				menu.setMenuId(menuIdTextbox.getValue());
				menu.setName(nameTextbox.getValue());
				menu.setParentId(parentMenuBandbox.getMenu() == null ? "" : parentMenuBandbox.getMenu().getMenuId());
				menu.setType((int) typeListbox.getSelectedValue());
				menu.setIconName(iconTextbox.getValue() == null ? "" : iconTextbox.getValue());
				menu.setMenuUrl(urlTextbox.getValue());
				menu.setCallMethod(callMethodTextbox.getValue() == null ? "" : callMethodTextbox.getValue());
				menu.setDisplay(displayCheckbox.isChecked());
				menu.setSort(sortIntbox.getValue());
				menuService.update(menu);
			}

			addButton.setVisible(true);
			editButton.setVisible(true);
			cancelButton.setVisible(false);
			saveButton.setVisible(false);
			menuIdLabel.setVisible(true);
			nameLabel.setVisible(true);
			parentMenuLabel.setVisible(true);
			typeLabel.setVisible(true);
			iconLabel.setVisible(true);
			urlLabel.setVisible(true);
			callMethodLabel.setVisible(true);
			displayLabel.setVisible(true);
			sortLabel.setVisible(true);

			typeListbox.setVisible(false);
			menuIdTextbox.setVisible(false);
			nameTextbox.setVisible(false);
			iconTextbox.setVisible(false);
			urlTextbox.setVisible(false);
			callMethodTextbox.setVisible(false);
			sortIntbox.setVisible(false);
			displayCheckbox.setVisible(false);
			parentMenuBandbox.setVisible(false);

			menuIdLabel.setValue("");
			nameLabel.setValue("");
			parentMenuLabel.setValue("");
			typeLabel.setValue("");
			iconLabel.setValue("");
			urlLabel.setValue("");
			callMethodLabel.setValue("");
			displayLabel.setValue("");
			sortLabel.setValue("");
			flashSouth();
			initData();
		} catch (CreateException e) {
			log.error("MenuWindow", e);
			Messagebox.error("保存出错了");
		} catch (UpdateException e) {
			log.error("MenuWindow", e);
			Messagebox.error("修改出错了");
		} catch (DataNotFoundException e) {
			log.error("MenuWindow", e);
			Messagebox.error("查询出错了");
		}
	}

	class TypeListRenderer implements ListitemRenderer {
		public void render(Listitem item, Object typeObject, int arg2) throws Exception {
			int typeValue = (int) typeObject;
			item.setValue(typeValue);

			String type = "";
			type = getMenuType(typeValue);
			item.setLabel(type);
		}
	}

	/**
	 * 清理南部区域
	 * 
	 */
	public void flashSouth() {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(false);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		menuIdLabel.setVisible(true);
		nameLabel.setVisible(true);
		parentMenuLabel.setVisible(true);
		typeLabel.setVisible(true);
		iconLabel.setVisible(true);
		urlLabel.setVisible(true);
		callMethodLabel.setVisible(true);
		displayLabel.setVisible(true);
		sortLabel.setVisible(true);

		typeListbox.setVisible(false);
		menuIdTextbox.setVisible(false);
		nameTextbox.setVisible(false);
		iconTextbox.setVisible(false);
		urlTextbox.setVisible(false);
		callMethodTextbox.setVisible(false);
		sortIntbox.setVisible(false);
		displayCheckbox.setVisible(false);
		parentMenuBandbox.setVisible(false);

		menuIdLabel.setValue("");
		nameLabel.setValue("");
		parentMenuLabel.setValue("");
		typeLabel.setValue("");
		iconLabel.setValue("");
		urlLabel.setValue("");
		callMethodLabel.setValue("");
		displayLabel.setValue("");
		sortLabel.setValue("");
		menuId = 0;
		menu = new Menu();
	}
}
