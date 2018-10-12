package com.jyd.bms.window;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;
import org.zkoss.zul.theme.Themes;

import com.jyd.bms.bean.GroupMenu;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.bean.UserMenu;
import com.jyd.bms.common.Environment;
import com.jyd.bms.service.GroupMenuService;
import com.jyd.bms.service.GroupUserService;
import com.jyd.bms.service.MenuService;
import com.jyd.bms.service.UserMenuService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.BaseComposer;
import com.jyd.bms.tool.zk.UserSession;

public class IndexWindow extends BaseComposer {
	private Window indexWindow;
	private Tree menuTree;
	private Tabbox mainWindow;
	private Label messageLabel;
	private Label closeAllWindowLabel;
	private Menupopup systemSettingmenupopup;
	private Menupopup themeMeupopup;
	private Menuitem passwordMenuitem;
	private Menuitem closeAllMenuitem;
	private Menuitem exitMenuitem;
	private String path = "";
	private List<Menu> listMenu = new ArrayList();
	private User user;
	private static final Logger log = LoggerFactory.getLogger(IndexWindow.class);
	// private MessageTypeService messageTypeService;
	private Label titleLabel;

	public IndexWindow() {
		Locale locale = java.util.Locale.CHINA;
		Sessions.getCurrent().setAttribute("px_preferred_locale", locale);
		super.menuId = "index";
	}

	public void onClick$changePasswordImage() {
		Menu item = new Menu();
		item.setMenuId("SYSTEM_CONTROL_PASSWORDMENU");
		item.setMenuUrl("/admin/changepassword.zul");
		item.setName("修改密码");
		loadingPage(item);
	}

	public void onClickChooseMenu(Event event) {
		Menu entity = (Menu) event.getData();
		if (entity != null)
			loadingPage(entity);
	}

	public void onClick$userMenuitem() {
		Menu item = new Menu();
		item.setMenuId("SYSTEM_CONTROL_USERMENU");
		item.setMenuUrl("/admin/user.zul");
		item.setName("用户管理");
		loadingPage(item);
	}

	public void onClick$groupMenuitem() {
		Menu item = new Menu();
		item.setMenuId("SYSTEM_CONTROL_GROUPMENU");
		item.setMenuUrl("/admin/group.zul");
		item.setName("群组管理");
		loadingPage(item);
	}

	public void onClick$menuMenuitem() {
		Menu item = new Menu();
		item.setMenuId("SYSTEM_CONTROL_MENUMENU");
		item.setMenuUrl("/admin/menu.zul");
		item.setName("菜单管理");
		loadingPage(item);
	}

	public void onClick$passwordMenuitem() {
		Menu item = new Menu();
		item.setMenuId("SYSTEM_CONTROL_PASSWORDMENU");
		item.setMenuUrl("/admin/changepassword.zul");
		item.setName("修改密码");
		loadingPage(item);
	}

	public void onClick$closeAllMenuitem() {
		for (int i = mainWindow.getTabs().getChildren().size() - 1; i >= 0; i--) {
			mainWindow.getTabs().removeChild((Tab) mainWindow.getTabs().getChildren().get(i));
			mainWindow.getTabpanels().removeChild((Tabpanel) mainWindow.getTabpanels().getChildren().get(i));
		}
	}

	public void onClick$deleteWindowImage() {
		onClick$closeAllMenuitem();
	}

	public void onClick$exitMenuitem() {
		Session session = Sessions.getCurrent();
		session.setAttribute("User", null);
		Executions.sendRedirect("/login.zul");
	}

	public void onClick$exitImage() {
		Session session = Sessions.getCurrent();
		session.setAttribute("User", null);
		Executions.sendRedirect("/login.zul");
	}

	public void onClickChooseTheme(Event event) {
		onClick$closeAllMenuitem();
		String theme = (String) event.getData();
		Themes.setTheme(Executions.getCurrent(), theme);
		List<Menuitem> list = themeMeupopup.getChildren();

		for (Menuitem menuitem : list) {
			if (menuitem.getValue().equals(theme))
				menuitem.setChecked(true);
			else
				menuitem.setChecked(false);
		}

		Executions.sendRedirect("");
	}

	private void loadingPage(Menu item) {
		Session session = Sessions.getCurrent();
		if (session.getAttribute("User") == null)
			Executions.sendRedirect("/login.zul");

		List<Tab> list = mainWindow.getTabs().getChildren();
		for (Tab tab : list) {
			if (("tab" + item.getMenuUrl().replace("/", "")).equals(tab.getId())) {
				mainWindow.setSelectedTab(tab);
				return;
			}
		}

		if (item.getMenuUrl().equals(""))
			return;

		Tab tabWindow = new Tab();
		tabWindow.setId("tab" + item.getMenuUrl().replace("/", ""));
		tabWindow.setLabel(item.getName());
		tabWindow.setClosable(true);
		mainWindow.getTabs().getChildren().add(tabWindow);
		mainWindow.setSelectedTab(tabWindow);

		Tabpanel tabPanelWindow = new Tabpanel();
		tabPanelWindow.setId("tabPanel" + item.getMenuUrl().replace("/", ""));
		mainWindow.getTabpanels().getChildren().add(tabPanelWindow);

		Iframe frame = new Iframe();
		frame.setParent(tabPanelWindow);
		frame.setWidth("100%");
		frame.setHeight("100%");
		frame.setScrolling("auto");
		frame.setAttribute("frameborder", "no");
		frame.setSrc(item.getMenuUrl());
	}

	private void loadingTree() {
		if (menuTree.getTreechildren() == null) {
			new org.zkoss.zul.Treechildren().setParent(menuTree);
		}
		createTreeitem("", menuTree.getTreechildren());
	}

	private void createTreeitem(String parentId, Treechildren tc) {
		for (int i = 0; i < listMenu.size(); i++) {
			if (listMenu.get(i).getParentId().equals(parentId)) {
				Treeitem childItem = new Treeitem();
				showTreeItem(childItem, listMenu.get(i));
				childItem.setParent(tc);

				Boolean haveChild = false;
				for (int j = 0; j < listMenu.size(); j++) {
					if (listMenu.get(j).getParentId().equals(listMenu.get(i).getMenuId())) {
						haveChild = true;
						break;
					}
				}
				if (haveChild) {
					Treechildren temp = new Treechildren();
					temp.setParent(childItem);
					createTreeitem(listMenu.get(i).getMenuId(), temp);
				}

				childItem.setOpen(true);
			}
		}
	}

	private void showTreeItem(Treeitem item, Menu menu) {
		Treerow tr = item.getTreerow();
		if (tr == null) {
			tr = new Treerow();
			tr.setParent(item);
		}

		Treecell nameTreecell = new Treecell(menu == null ? "Null" : menu.getName());
		nameTreecell.setParent(tr);
		nameTreecell.addForward("onClick", (Component) null, "onClickChooseMenu", menu);
		item.setValue(menu);
	}

	@Override
	public void initData() {
		try {
			MenuService menuService = getBean("MenuService");
			UserMenuService userMenuService = getBean("UserMenuService");
			GroupUserService groupUserService = getBean("GroupUserService");
			GroupMenuService groupMenuService = getBean("GroupMenuService");

			List<Menu> listAll = menuService.getAllMenuAndForm();
			FindChildMenu("", listAll);

			List<UserMenu> listUserMenu = userMenuService.getUserMenu(user);
			List<GroupUser> listGroupUser = groupUserService.getGroupUserByUser(user);

			List<String> listTempMenu = new ArrayList();

			for (int i = 0; i < listUserMenu.size(); i++) {
				listTempMenu.add(listUserMenu.get(i).getMenuId());
			}

			for (int i = 0; i < listGroupUser.size(); i++) {
				List<GroupMenu> listGroupMenu = groupMenuService.getGroupMenu(listGroupUser.get(i).getGroup());
				for (int j = 0; j < listGroupMenu.size(); j++) {
					if (listTempMenu.contains(listGroupMenu.get(j).getMenuId()))
						continue;
					listTempMenu.add(listGroupMenu.get(j).getMenuId());
				}
			}

			for (int i = listMenu.size() - 1; i >= 0; i--) {
				if (listTempMenu.contains(listMenu.get(i).getMenuId()) == false) {
					if (listMenu.get(i).getType() >= 3)
						listMenu.remove(i);
				}
			}

		} catch (DAOException e) {
			e.printStackTrace();
		}
		loadingTree();
		Clients.evalJavaScript(
				"connectionServer(" + (UserSession.getUser() == null ? -1 : UserSession.getUser().getId()) + ","
						+ Environment.getWebsocketUrl() + ")");

	}

	private void FindChildMenu(String menuId, List<Menu> listAll) {
		for (int i = 0; i < listAll.size(); i++) {
			if (listAll.get(i).getParentId().equals(menuId)) {
				listMenu.add(listAll.get(i));
				FindChildMenu(listAll.get(i).getMenuId(), listAll);
			}
		}
	}

	@Override
	public void initUI() {
		titleLabel.setValue(Environment.getBmsTitle() + "-业务系统");
		Session session = Sessions.getCurrent();
		if (session.getAttribute("User") == null) {
			Executions.sendRedirect("/login.zul");
			return;
		} else {
			user = (User) session.getAttribute("User");
		}

		String[] themes = Themes.getThemes();
		String currentTheme = Themes.getCurrentTheme();

		for (String theme : themes) {
			Menuitem item = new Menuitem();
			item.setLabel(theme);
			item.setValue(theme);

			if (currentTheme != null && currentTheme.equals(theme) == true) {
				item.setChecked(true);
			}
			item.setParent(themeMeupopup);
			item.addForward("onClick", (Component) null, "onClickChooseTheme", theme);
		}

		if (currentTheme == null || currentTheme.equals("")) {
			Menuitem item = (Menuitem) themeMeupopup.getChildren().get(0);
			String themeName = themes[0];

			Themes.setTheme(Executions.getCurrent(), themeName);
		}
	}

	/**
	 * 加载新窗口
	 */
	public void onClick$loadPageButton() {
		Session session = Sessions.getCurrent();
		if (session.getAttribute("OpenMenu") != null) {
			Menu menu = (Menu) session.getAttribute("OpenMenu");
			loadingPage(menu);
			session.setAttribute("OpenMenu", null);
		}
	}

	/**
	 * 关闭当前Tab
	 */
	public void onClick$closeTabButton() {
		if (mainWindow.getSelectedTab() != null) {
			mainWindow.getSelectedTab().close();
		}
	}

	/**
	 * 未处理消息
	 */
	public void onClick$infoImage() {
		Menu menu = new Menu();
		menu.setMenuUrl("/message/messageList.zul");
		menu.setName("未读消息");

		loadingPage(menu);
	}

	/**
	 * 发送消息给用户，显示几条未读消息
	 * 
	 * @throws CreateException
	 */
	public void onMessage$indexWindow() {
//		MessageService messageService = getBean("MessageService");
//		try {
//			int total = messageService.getMessageCountByEmpAndState(UserSession.getUser().getEmployee());
//			ServerMessage serverMessage = new ServerMessage();
//			serverMessage.setType("totalMessage");
//			serverMessage.setContent(String.valueOf(total));
//
//			ISendMessage sendMessage = (ISendMessage) SpringUtil.getBean("SendMessage");
//			if (Environment.getWebsocketType().equals("otherWeb") == false) {
//				WebSocket.sendMessage(serverMessage, UserSession.getUser().getEmployee().getId());
//			} else {
//				com.jyd.message.ServerMessage otherServerMessage = new com.jyd.message.ServerMessage();
//				otherServerMessage.setType("totalMessage");
//				otherServerMessage.setContent(String.valueOf(total));
//				sendMessage.sendMessageToClient(otherServerMessage, UserSession.getUser().getEmployee().getId());
//			}
//		} catch (DAOException e) {
//			log.debug("IndexWindow", e);
//		} catch (CreateException e) {
//			log.debug("IndexWindow", e);
//		}
	}

	public void onViewMessage$indexWindow(Event event) {
//		try {
//			ForwardEvent eventx = (ForwardEvent) event;
//			JSONObject jsonData = (JSONObject) eventx.getOrigin().getData();
//			String type = (String) jsonData.get("type");
//			int value = (int) jsonData.get("value");
//
//			MessageService messageService = getBean("MessageService");
//
//			Message message = messageService.getById(value);
//			// TODO:改动
//			messageTypeService = getBean("MessageTypeService");
//			MessageType messageType = messageTypeService.getById(message.getMessageType().getId());
//			Class<?> c = Class.forName(messageType.getProcessClass());
//			MessageInterface messageInterface = (MessageInterface) c.newInstance();
//
//			Menu menu = messageInterface.process(message);
//			loadingPage(menu);
//
//			message.setState(StaticVariable.MESSAGE_STATUS_READ);
//			messageService.update(message);
//
//			onMessage$indexWindow();
//		} catch (ClassNotFoundException e) {
//			Messagebox.show("找不到处理类，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		} catch (InstantiationException e) {
//			Messagebox.show("转换出错，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		} catch (DataNotFoundException e) {
//			Messagebox.show("没有查询到数据，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		} catch (IllegalAccessException e) {
//			Messagebox.show("安全权限异常，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		} catch (DAOException e) {
//			Messagebox.show("查询数据出错，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		} catch (UpdateException e) {
//			Messagebox.show("更新数据出错，请联系系统管理员处理！");
//			log.error("IndexWindow", e);
//		}
	}

	public void onViewAllMessage$indexWindow() {
		onClick$infoImage();
	}
}
