package com.jyd.bms.window.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.GroupMenuService;
import com.jyd.bms.service.MenuService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.UserSession;

public class GroupMenuWindow extends BaseWindow {
	private Tree menuTree;
	private Button searchButton;
	private Button saveButton;
	private Textbox conditionTextbox;
	private String condition = "";
	private List<Menu> lstAllMenu = new ArrayList<Menu>();
	private List<Menu> lstMenu = new ArrayList<Menu>();
	private List<Menu> lstSearchMenu = new ArrayList<Menu>();
	private List<GroupMenu> lstGroupMenu = new ArrayList<GroupMenu>();

	private Group group;
	private GroupMenuService groupMenuService;
	private MenuService menuService;
	private static final Logger log = LoggerFactory.getLogger(GroupMenuWindow.class);
	public GroupMenuWindow(){
		super.menuId="sys_menu";
	}
	@Override
	public void initData() {
		loadingMenu();
	}

	@Override
	public void initUI() {
		groupMenuService = getBean("GroupMenuService");
		menuService = getBean("MenuService");
		try {
			System.out.println("Init Group Id:" + group.getId());

			lstGroupMenu = groupMenuService.getGroupMenu(group);
		} catch (DAOException e) {
			log.error("GroupMenuWindow", e);
		}
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void onOK$conditionTextbox() throws SuspendNotAllowedException, InterruptedException {
		onClick$searchButton();
	}

	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		String condition = conditionTextbox.getValue();
		if (condition.trim().equals("")) {
			loadingTree();
			return;
		}

		lstSearchMenu.clear();
		for (Menu menu : lstAllMenu) {
			if (menu.getName().toUpperCase().indexOf(condition.toUpperCase()) > -1
					|| menu.getMenuUrl().toUpperCase().indexOf(condition.toUpperCase()) > -1) {
				lstSearchMenu.add(menu);
			}
		}
		if (lstSearchMenu.size() == 0) {
			Messagebox.info(Labels.getLabel("useraccesscontrol.system.notfind"));
		} else {
			menuTree.clear();
			createSearchTree();
			conditionTextbox.setFocus(true);
		}
	}

	public void onClick$saveButton() throws SuspendNotAllowedException, InterruptedException {
		Set<Treeitem> lstSelected = menuTree.getSelectedItems();

		User user = UserSession.getUser();
		if (user == null) {
			Messagebox.show(Labels.getLabel("useraccesscontrol.system.userisnull"));
			return;
		}

		Timestamp date = new Timestamp(System.currentTimeMillis());

		List<GroupMenu> lstSelectMenus = new ArrayList<GroupMenu>();

		for (Treeitem item : lstSelected) {
			Menu menu = (Menu) item.getValue();

			GroupMenu groupMenu = new GroupMenu();
			groupMenu.setGroup(group);
			groupMenu.setMenuId(menu.getMenuId());
			groupMenu.setCreateDate(date);
			groupMenu.setUpdateDate(date);
			groupMenu.setCreateUser(user.getLoginName());
			groupMenu.setUpdateUser(user.getLoginName());
			lstSelectMenus.add(groupMenu);
		}

		for (GroupMenu groupMenu : lstGroupMenu) {
			boolean find = false;
			for (GroupMenu temp : lstSelectMenus) {
				if (temp.getMenuId().equals(groupMenu.getMenuId())) {
					find = true;
					break;
				}
			}
			if (find == false) {
				try {
					groupMenuService.delete(groupMenu);
				} catch (DeleteException e) {
					log.error("GroupMenuWindow",e);
					Messagebox.error("删除出错了！");
				}
			}
		}

		for (GroupMenu groupMenu : lstSelectMenus) {
			boolean find = false;
			for (GroupMenu temp : lstGroupMenu) {
				if (temp.getMenuId().equals(groupMenu.getMenuId())) {
					find = true;
					break;
				}
			}
			if (find == false) {
				try {
					groupMenuService.add(groupMenu);
				} catch (CreateException e) {
					Messagebox.error("保存出错了！");
					log.error("GroupMenuWindow",e);
					break;
				}
			}
		}
		this.detach();
	}

	private void loadingMenu() {
		try {
			lstAllMenu.clear();
			lstAllMenu = menuService.getAllMenu();

			for (Menu menu : lstAllMenu) {
				for (GroupMenu groupMenu : lstGroupMenu) {
					if (groupMenu.getMenuId().equals(menu.getMenuId())) {
						menu.setChecked(true);
						break;
					}
				}
			}

			loadingTree();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public void loadingTree() {
		menuTree.clear();
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

		// 名称,类型,图标,地址,方法,语言标签,是否显示,排序号
		new Treecell(menu.getName()).setParent(tr);

		String type = "";
		type = MenuWindow.getMenuType(menu.getType());

		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getDisplay() == true ? "显示" : "隐藏").setParent(tr);
		new Treecell(String.valueOf(menu.getSort())).setParent(tr);

		if (menu.isChecked())
			item.setSelected(true);

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
		type = MenuWindow.getMenuType(menu.getType());
		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getDisplay() == true ? Labels.getLabel("useraccesscontrol.menu.show")
				: Labels.getLabel("useraccesscontrol.menu.hidden")).setParent(tr);
		new Treecell(String.valueOf(menu.getSort())).setParent(tr);

		if (menu.isChecked())
			item.setSelected(true);
		item.setValue(menu);
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
}
