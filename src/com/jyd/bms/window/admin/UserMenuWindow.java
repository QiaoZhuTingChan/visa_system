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

import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.bean.UserMenu;
import com.jyd.bms.service.GroupMenuService;
import com.jyd.bms.service.GroupUserService;
import com.jyd.bms.service.MenuService;
import com.jyd.bms.service.UserMenuService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.UserSession;

public class UserMenuWindow extends BaseWindow {

	private Tree menuTree;
	private Button searchButton;
	private Button saveButton;
	private Textbox conditionTextbox;
	private String condition = "";
	private List<Menu> lstAllMenu = new ArrayList<Menu>();
	private List<Menu> lstMenu = new ArrayList<Menu>();
	private List<Menu> lstSearchMenu = new ArrayList<Menu>();
	private List<UserMenu> lstUserMenu = new ArrayList<UserMenu>();
	private List<GroupMenu> lstGroupMenu = new ArrayList<GroupMenu>();

	private UserMenuService userMenuService;
	private GroupMenuService groupMenuService;
	private GroupUserService groupUserService;
	private MenuService menuService;

	private User user;
	private static final Logger log = LoggerFactory.getLogger(UserMenuWindow.class);
	
	public UserMenuWindow() {
		super.menuId="sys_user";
	}
	@Override
	public void initData() {
		loadingMenu();
	}

	@Override
	public void initUI() {
		userMenuService = getBean("UserMenuService");
		groupMenuService = getBean("GroupMenuService");
		groupUserService = getBean("GroupUserService");
		menuService = getBean("MenuService");
	}

	public void setUser(User user) {
		this.user = user;
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

		User sessionUser = UserSession.getUser();
		if (sessionUser == null) {
			Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
			;
			return;
		}

		Timestamp date = new Timestamp(System.currentTimeMillis());

		List<UserMenu> lstAddMenus = new ArrayList<UserMenu>();
		List<UserMenu> lstUpdateMenus = new ArrayList<UserMenu>();
		List<UserMenu> lstDeleteMenus = new ArrayList<UserMenu>();

		for (Treeitem item : lstSelected) {
			Menu menu = (Menu) item.getValue();

			boolean find = false;
			for (GroupMenu groupMenu : lstGroupMenu) {
				if (groupMenu.getMenuId().equals(menu.getMenuId())) {
					find = true;
					break;
				}
			}

			if (find) {
				continue;
			} else {
				find = false;
				for (UserMenu tempMenu : lstUserMenu) {
					if (tempMenu.getMenuId().equals(menu.getMenuId())) {
						find = true;

						if (tempMenu.getEnable() == false) {
							tempMenu.setEnable(true);
							lstUpdateMenus.add(tempMenu);
						}

						break;
					}
				}

				if (find == false) {
					UserMenu userMenu = new UserMenu();
					userMenu.setUser(user);
					userMenu.setMenuId(menu.getMenuId());
					userMenu.setEnable(true);
					userMenu.setCreateDate(date);
					userMenu.setUpdateDate(date);
					userMenu.setCreateUser(sessionUser.getLoginName());
					userMenu.setUpdateUser(sessionUser.getLoginName());
					lstAddMenus.add(userMenu);
				}
			}
		}

		for (UserMenu userMenu : lstUserMenu) {
			boolean find = false;
			for (Treeitem item : lstSelected) {
				Menu menu = (Menu) item.getValue();
				if (userMenu.getMenuId().equals(menu.getMenuId())) {
					find = true;
					break;
				}
			}

			if (find == false) {
				for (GroupMenu groupMenu : lstGroupMenu) {
					if (userMenu.getMenuId().equals(groupMenu.getMenuId())) {
						find = true;
						break;
					}
				}
				if (find == false) {
					lstDeleteMenus.add(userMenu);
				}
			}
		}

		for (GroupMenu groupMenu : lstGroupMenu) {
			boolean find = false;

			for (Treeitem item : lstSelected) {
				Menu menu = (Menu) item.getValue();
				if (menu.getMenuId().equals(groupMenu.getMenuId())) {
					find = true;
					break;
				}
			}

			if (find == false) {
				find = false;
				for (UserMenu userMenu : lstUserMenu) {
					if (userMenu.getMenuId().equals(groupMenu.getMenuId())) {
						if (userMenu.getEnable() == true) {
							userMenu.setEnable(false);
							lstUpdateMenus.add(userMenu);
						}
						find = true;
					}
				}

				if (find == false) {
					UserMenu userMenu = new UserMenu();
					userMenu.setUser(user);
					userMenu.setMenuId(groupMenu.getMenuId());
					userMenu.setEnable(false);
					userMenu.setCreateDate(date);
					userMenu.setUpdateDate(date);
					userMenu.setCreateUser(sessionUser.getLoginName());
					userMenu.setUpdateUser(sessionUser.getLoginName());
					lstAddMenus.add(userMenu);
				}
			}
		}

		try {
			for (UserMenu userMenu : lstAddMenus) {
				userMenuService.add(userMenu);
			}

			for (UserMenu userMenu : lstUpdateMenus) {
				boolean enable = userMenu.getEnable();

				userMenu = userMenuService.getById(userMenu.getId());
				userMenu.setEnable(enable);
				userMenuService.update(userMenu);
			}

			for (UserMenu userMenu : lstDeleteMenus) {
				userMenu = userMenuService.getById(userMenu.getId());
				userMenuService.delete(userMenu);
			}
		} catch (CreateException e) {
			log.error("UserMenuWindow", e);
			Messagebox.error("保存出错了");
		} catch (UpdateException e) {
			log.error("UserMenuWindow", e);
			Messagebox.error("修改出错了");
		} catch (DataNotFoundException e) {
			log.error("UserMenuWindow", e);
			Messagebox.error("查询出错了");
		} catch (DeleteException e) {
			log.error("UserMenuWindow", e);
			Messagebox.error("删除出错了");
		}
		Messagebox.show("保存成功！");
	}

	private void loadingMenu() {
		try {
			lstUserMenu = userMenuService.getUserMenu(user);
			List<GroupUser> listGroupUser = groupUserService.getGroupUserByUser(user);

			List<String> listTempMenu = new ArrayList();

			for (int i = 0; i < listGroupUser.size(); i++) {
				List<GroupMenu> listGroupMenu = groupMenuService.getGroupMenu(listGroupUser.get(i).getGroup());
				for (int j = 0; j < listGroupMenu.size(); j++) {
					if (listTempMenu.contains(listGroupMenu.get(j).getMenuId()))
						continue;
					listTempMenu.add(listGroupMenu.get(j).getMenuId());
					lstGroupMenu.add(listGroupMenu.get(j));
				}
			}

			for (int i = 0; i < lstUserMenu.size(); i++) {
				if (lstUserMenu.get(i).getEnable() == false) {
					if (listTempMenu.contains(lstUserMenu.get(i).getMenuId())) {
						int index = 0;
						for (; index < listTempMenu.size(); index++) {
							if (listTempMenu.get(index).equals(lstUserMenu.get(i).getMenuId()))
								break;
						}

						listTempMenu.remove(index);
					}
				} else {
					if (listTempMenu.contains(lstUserMenu.get(i).getMenuId()))
						continue;
					listTempMenu.add(lstUserMenu.get(i).getMenuId());
				}
			}

			lstAllMenu.clear();
			lstAllMenu = menuService.getAllMenu();

			for (Menu menu : lstAllMenu) {
				for (String menuId : listTempMenu) {
					if (menuId.equals(menu.getMenuId())) {
						menu.setChecked(true);
						break;
					}
				}
			}

			loadingTree();
		} catch (DAOException e) {
			log.error("UserMenuWindow", e);
			Messagebox.error("查询出错了");
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

		String type = MenuWindow.getMenuType(menu.getType());

		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getLanguageId()).setParent(tr);
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
		String type = MenuWindow.getMenuType(menu.getType());
		new Treecell(type).setParent(tr);
		new Treecell(menu.getIconName()).setParent(tr);
		new Treecell(menu.getDisplay() == true ? "显示" : "隐藏").setParent(tr);
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
