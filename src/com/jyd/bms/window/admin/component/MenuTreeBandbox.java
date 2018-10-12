package com.jyd.bms.window.admin.component;

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

import com.jyd.bms.bean.Menu;
import com.jyd.bms.service.MenuService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.window.admin.MenuWindow;

public class MenuTreeBandbox extends Bandbox implements ComponentInterface {
	private Menu menu;
	private Tree menuTree = new Tree();
	private Textbox conditionTextbox = new Textbox();
	@SuppressWarnings("unused")
	private String condition = "";
	private Button searchButton = new Button("查找");
	private List<Menu> lstAllMenu = new ArrayList<Menu>();
	private List<Menu> lstSearchMenu = new ArrayList<Menu>();
	private List<Menu> lstMenu = new ArrayList<Menu>();

	private Bandpopup popup = new Bandpopup();
	private MenuService menuService;
	private static final Logger log = LoggerFactory.getLogger(MenuTreeBandbox.class);

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public Menu getMenu() {
		if (this.getText().equals(""))
			return null;
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		this.setText(menu == null ? "" : menu.getName());
	}

	public Textbox getConditionTextbox() {
		return conditionTextbox;
	}

	public void setConditionTextbox(String conditionTextboxValues) {
		this.conditionTextbox.setValue(conditionTextboxValues);
	}

	public MenuTreeBandbox() {
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

		menuTree.setParent(listCenter);
		searchButton.addForward("onClick", (Component) this, "onClickOK", null);
		conditionTextbox.addForward("onOK", (Component) this, "onClickOK", null);
		this.addForward("onOK", (Component) this, "onClickSearch", this.getText());
		menuTree.addForward("onSelect", (Component) this, "onSelectMenu", null);
	}

	public MenuTreeBandbox(Menu menu) {
		this();
		this.menu = menu;
	}

	public void onClickOK() {
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

		menuTree.clear();
		createSearchTree();
		this.open();
	}

	public void onClickSearch() {
		String condition = this.getText();
		conditionTextbox.setValue(condition);
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
		if (lstSearchMenu.size() == 1) {
			Menu selectMenu = lstSearchMenu.get(0);
			try {
				menu = menuService.getById(selectMenu.getId());
			} catch (DataNotFoundException e) {
				log.error("MenuTreeBandbox", e);
				Messagebox.error("获取数据出错了!");
				return;
			}
			this.menu = menu;
			conditionTextbox.setValue(menu.getName());
			this.setValue(menu.getName());
		} else if (lstSearchMenu.size() == 0) {
			this.setMenu(null);
			Messagebox.info("没有找到");
			this.focus();
		} else {
			menuTree.clear();
			createSearchTree();
			this.open();
			conditionTextbox.setFocus(true);
		}
	}

	private void loadingMenu() {
		try {
			lstAllMenu.clear();
			lstAllMenu = menuService.getAllMenu();
			loadingTree();
		} catch (DAOException e) {
			e.printStackTrace();
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

	private void showSearchTreeItem(Treeitem item, Menu menu) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}
		new Treecell(menu.getName()).setParent(tr);

		String type = MenuWindow.getMenuType(menu.getType());
		new Treecell(type).setParent(tr);

		item.setValue(menu);
	}

	private void showTreeItem(Treeitem item, Menu menu) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}

		new Treecell(menu.getName()).setParent(tr);
		String type = MenuWindow.getMenuType(menu.getType());
		new Treecell(type).setParent(tr);
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

	public void onSelectMenu(Event event) throws SuspendNotAllowedException, InterruptedException {
		Menu selectMenu = (Menu) menuTree.getSelectedItem().getValue();
		try {
			menu = menuService.getById(selectMenu.getId());
		} catch (DataNotFoundException e) {
			log.error("MenuTreeBandbox", e);
			Messagebox.error("获取数据出错了!");
			return;
		}

		this.setText(menu.getName());
		this.conditionTextbox.setText(menu.getName());
		Events.postEvent("onChooseDepartment", (Component) this.getSpaceOwner(), null);
		this.close();
	}

	private void initTreeCell() {
		Treecols treecols = new Treecols();
		treecols.setParent(menuTree);
		treecols.setSizable(true);

		Treecol menuTreecol = new Treecol();
		menuTreecol.setParent(treecols);
		menuTreecol.setLabel("菜单名称");
		menuTreecol.setWidth("70%");

		Treecol managerTreecol = new Treecol();
		managerTreecol.setParent(treecols);
		managerTreecol.setLabel("类型");
		managerTreecol.setWidth("15%");
	}

	public boolean checkCirculate(Menu menu, Menu checkMenu) {
		while (checkMenu != null) {
			if (checkMenu.getParentId() == null)
				break;
			if (checkMenu.getParentId().equals(menu.getMenuId())) {
				return true;
			} else {
				try {
					checkMenu = menuService.getParentMenu(checkMenu);
				} catch (DAOException e) {
					log.error("MenuTreeBandbox", e);
					Messagebox.error("获取数据出错了!");
				}
			}
		}
		return false;
	}

	@Override
	public Object getCurrentValue() {
		return getMenu();
	}

	@Override
	public void initComponent() {
		loadingMenu();
	}
}