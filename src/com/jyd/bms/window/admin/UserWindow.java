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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.bean.User;
import com.jyd.bms.components.BaPositionListbox;
import com.jyd.bms.service.BaPositionService;
import com.jyd.bms.service.UserService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.ContinueProcessAfterComponentCreated;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.PagingControlComponentModelList;
import com.jyd.bms.tool.zk.UserSession;
import com.jyd.bms.window.LoginWindow;

@SuppressWarnings({ "serial", "unused" })
public class UserWindow extends BaseWindow implements ContinueProcessAfterComponentCreated {
	private boolean editFlag = false;
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private Button userMenuButton;
	private GridPaging gridPaging;
	private Label loginNameLabel;
	private Label passwordLabel;
	private Label userNameLabel;
	private Label emailLabel;
	private Label remarkLabel;
	private Label enableLabel;
	private Listbox userListbox;
	private long userId = 0;
	private Textbox loginNameTextbox;
	private Textbox passwordTextbox;
	private Textbox userNameTextbox;
	private Textbox emailTextbox;
	private Textbox remarkTextbox;
	private Textbox conditionTextbox;
	private Checkbox enableCheckbox;
	private Label positionLabel;
	private BaPositionListbox baPositionListbox;
	private BaPositionService baPositionService;

	private String condition = "";
	private South southPaging;
	private User user;

	private UserService userService;

	private static final Logger log = LoggerFactory.getLogger(UserWindow.class);

	public UserWindow() {
		super.menuId = "sys_user";
	}

	public Listitem getSelectItem() {
		return userListbox.getSelectedItem();
	}

	/**
	 * 弹出窗口参数传递
	 */
	public void processAfterComponentCreated(Component paramComponent) {
		if (paramComponent instanceof UserMenuWindow) {
			UserMenuWindow userMenuWindow = (UserMenuWindow) paramComponent;
			userMenuWindow.setUser(user);
		}
	}

	/**
	 * 初始化数据绑定
	 */
	public void initUI() {
		userService = getBean("UserService");
		baPositionService = getBean("BaPositionService");
		userListbox.setItemRenderer(new UserListRenderer());
		userListbox.addForward("onSelect", (Component) null, "onSelectUser", null);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(userService,
					"getPagingUser", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(userListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(userService.getUserCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("UserWindow", e);
			Messagebox.error("获取数据出错了!");
		} catch (Exception e) {
			log.error("UserWindow", e);
			Messagebox.error("未知错误！");
		}

		List<Integer> listUserType = new ArrayList<Integer>();
		listUserType.add(0);
		listUserType.add(1);

		baPositionListbox.setBaPositionService(baPositionService);
		baPositionListbox.initComponent();
	}

	// public void onClick$userMenuButton() throws SuspendNotAllowedException,
	// InterruptedException {
	// Executions.doModal("/admin/usermenu.zul", null, null, false, this);
	// }

	/**
	 * 搜索
	 */
	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		condition = conditionTextbox.getValue();
		initData();
	}

	public void onOKsearchButton() throws SuspendNotAllowedException, InterruptedException {
		onClick$searchButton();
	}

	/**
	 * 选定
	 * 
	 * @throws DataNotFoundException
	 */
	public void onSelectUser() throws SuspendNotAllowedException, InterruptedException, DataNotFoundException {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		// userMenuButton.setVisible(true);
		loginNameTextbox.setVisible(false);
		passwordTextbox.setVisible(false);
		positionLabel.setVisible(true);
		baPositionListbox.setVisible(false);

		userNameTextbox.setVisible(false);
		emailTextbox.setVisible(false);
		remarkTextbox.setVisible(false);
		enableCheckbox.setVisible(false);
		loginNameLabel.setVisible(true);
		passwordLabel.setVisible(false);
		userNameLabel.setVisible(true);
		emailLabel.setVisible(true);
		remarkLabel.setVisible(true);
		enableLabel.setVisible(true);

		user = (User) userListbox.getSelectedItem().getValue();

		userService.refresh(user);

		userId = user.getId();
		if (user != null) {
			loginNameLabel.setValue(user.getLoginName());
			userNameLabel.setValue(user.getUserName());
			emailLabel.setValue(user.getEmail());

			remarkLabel.setValue(user.getRemark());
			enableLabel.setValue(user.getEnable() == true ? Labels.getLabel("useraccesscontrol.system.enable")
					: Labels.getLabel("useraccesscontrol.system.disable"));

			loginNameTextbox.setValue(user.getLoginName());
			passwordTextbox.setValue(user.getPassword());

			userNameTextbox.setValue(user.getUserName());
			emailTextbox.setValue(user.getEmail());

			BaPositionService baPositionService = getBean("BaPositionService");
			BaPosition baPosition = baPositionService.getById(user.getBaPosition().getId());
			positionLabel.setValue(baPosition.getBaPosition());
			baPositionListbox.setBaPosition(user.getBaPosition());

			remarkTextbox.setValue(user.getRemark());
			enableCheckbox.setChecked(user.getEnable());

			enableCheckbox.setChecked(user.getEnable());
		}
		userListbox.setSelectedItem(null);
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
		// userMenuButton.setVisible(true);
		loginNameLabel.setVisible(false);
		userNameLabel.setVisible(false);
		emailLabel.setVisible(false);
		remarkLabel.setVisible(false);
		enableLabel.setVisible(false);
		loginNameTextbox.setVisible(true);
		passwordTextbox.setVisible(true);
		userNameTextbox.setVisible(true);
		emailTextbox.setVisible(true);
		remarkTextbox.setVisible(true);
		enableCheckbox.setVisible(true);
		enableCheckbox.setVisible(true);
		positionLabel.setVisible(false);
		baPositionListbox.setVisible(true);
		loginNameTextbox.setValue(user.getLoginName());
		passwordTextbox.setValue(user.getPassword());
		userNameTextbox.setValue(user.getUserName());
		baPositionListbox.setBaPosition(user.getBaPosition());
		emailTextbox.setValue(user.getEmail());
		remarkTextbox.setValue(user.getRemark());
		enableCheckbox.setChecked(user.getEnable());
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
		// userMenuButton.setVisible(false);
		loginNameLabel.setVisible(true);
		userNameLabel.setVisible(true);
		emailLabel.setVisible(true);
		remarkLabel.setVisible(true);
		enableLabel.setVisible(true);
		loginNameTextbox.setVisible(false);
		passwordTextbox.setVisible(false);
		userNameTextbox.setVisible(false);
		emailTextbox.setVisible(false);
		remarkTextbox.setVisible(false);
		enableCheckbox.setVisible(false);
		enableCheckbox.setVisible(false);
		positionLabel.setVisible(true);
		baPositionListbox.setVisible(false);
		loginNameLabel.setValue("");
		userNameLabel.setValue("");
		emailLabel.setValue("");
		remarkLabel.setValue("");
		enableLabel.setValue("");
		initData();
	}

	/**
	 * 添加
	 */
	public void onClick$addButton() {
		userId = 0;
		editFlag = false;
		addButton.setVisible(false);
		editButton.setVisible(false);
		cancelButton.setVisible(true);
		saveButton.setVisible(true);
		// userMenuButton.setVisible(false);
		loginNameLabel.setVisible(false);
		userNameLabel.setVisible(false);
		emailLabel.setVisible(false);
		remarkLabel.setVisible(false);
		enableLabel.setVisible(false);
		loginNameTextbox.setVisible(true);
		passwordTextbox.setVisible(true);
		userNameTextbox.setVisible(true);
		emailTextbox.setVisible(true);
		remarkTextbox.setVisible(true);
		enableCheckbox.setVisible(true);
		enableCheckbox.setVisible(true);
		loginNameTextbox.setValue("");
		passwordTextbox.setValue("");
		userNameTextbox.setValue("");
		emailTextbox.setValue("");
		remarkTextbox.setValue("");
		enableCheckbox.setChecked(true);
		positionLabel.setVisible(false);
		baPositionListbox.setVisible(true);
	}

	/**
	 * 保存数据
	 */
	public void onClick$saveButton() {
		try {
			if (loginNameTextbox.getValue().equals("")) {
				Messagebox.info("登陆名不能为空");
				loginNameTextbox.select();
				return;
			}
			if (passwordTextbox.getValue().equals("")) {
				Messagebox.info("密码不能为空");
				passwordTextbox.select();
				return;
			}

			if (userNameTextbox.getValue().equals("")) {
				Messagebox.info("姓名不能为空");
				userNameTextbox.select();
				return;
			}

			User sessionUser = UserSession.getUser();
			if (sessionUser == null) {
				Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
				return;
			}

			Timestamp date = new Timestamp(System.currentTimeMillis());
			if (userId == 0 && editFlag == false) {
				user = new User();
				user.setLoginName(loginNameTextbox.getValue());
				user.setPassword(LoginWindow.getMD5(passwordTextbox.getValue()));

				user.setUserName(userNameTextbox.getValue());
				user.setEmail(emailTextbox.getValue() == null ? "" : emailTextbox.getValue());

				user.setBaPosition(baPositionListbox.getBaPosition());
				user.setCreateDate(date);
				user.setUpdateDate(date);
				user.setCreateUser(sessionUser.getLoginName());
				user.setUpdateUser(sessionUser.getLoginName());
				user.setRemark(remarkTextbox.getValue() == null ? "" : remarkTextbox.getValue());
				user.setEnable(enableCheckbox.isChecked());
				user.setAdmin(false);

				userService.add(user);
				userId = 0;
			} else if (editFlag == true) {
				user = userService.getById(user.getId());
				if (user.getPassword().equals(passwordTextbox.getValue()) == false) {
					user.setPassword(LoginWindow.getMD5(passwordTextbox.getValue()));
				}
				user.setBaPosition(baPositionListbox.getBaPosition());
				user.setUserName(userNameTextbox.getValue());
				user.setEmail(emailTextbox.getValue() == null ? "" : emailTextbox.getValue());
				user.setUpdateDate(date);
				user.setUpdateUser(sessionUser.getLoginName());
				user.setRemark(remarkTextbox.getValue() == null ? "" : remarkTextbox.getValue());
				user.setEnable(enableCheckbox.isChecked());
				userService.update(user);
			}
			flashSouth();
			initData();
		} catch (CreateException e) {
			log.error("UserWindow", e);
			Messagebox.error("保存出错了");
		} catch (UpdateException e) {
			log.error("UserWindow", e);
			Messagebox.error("修改出错了");
		} catch (DataNotFoundException e) {
			log.error("UserWindow", e);
			Messagebox.error("读取数据出错了");
		}
	}

	@SuppressWarnings("rawtypes")
	class UserTypeListRenderer implements ListitemRenderer {
		public void render(Listitem item, Object userTypeObject, int arg2) throws Exception {
			int userType = (int) userTypeObject;
			item.setValue(userType);
			item.setLabel(userType == 0 ? Labels.getLabel("useraccesscontrol.user.type.factoryuser")
					: Labels.getLabel("useraccesscontrol.user.type.other"));
		}
	}

	/**
	 * 处理数据绑定
	 */
	@SuppressWarnings("rawtypes")
	class UserListRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			User user = (User) arg1;

			Listcell loginNameCell = new Listcell();
			loginNameCell.setParent(arg0);
			new Label(user.getLoginName()).setParent(loginNameCell);

			Listcell userNameCell = new Listcell();
			userNameCell.setParent(arg0);
			new Label(user.getUserName()).setParent(userNameCell);

			Listcell emailCell = new Listcell();
			emailCell.setParent(arg0);
			new Label(user.getEmail()).setParent(emailCell);

			Listcell enableCell = new Listcell();
			enableCell.setParent(arg0);
			new Label(user.getEnable() == true ? "启用" : "禁用").setParent(enableCell);

			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(user.getRemark()).setParent(remarkCell);

			arg0.setValue(user);
		}
	}

	/**
	 * 清理南部区域
	 * 
	 */
	public void flashSouth() {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		// userMenuButton.setVisible(false);
		loginNameLabel.setVisible(true);
		userNameLabel.setVisible(true);
		emailLabel.setVisible(true);
		remarkLabel.setVisible(true);
		enableLabel.setVisible(true);
		loginNameTextbox.setVisible(false);
		passwordTextbox.setVisible(false);
		userNameTextbox.setVisible(false);
		emailTextbox.setVisible(false);
		remarkTextbox.setVisible(false);
		enableCheckbox.setVisible(false);
		enableCheckbox.setVisible(false);
		baPositionListbox.setVisible(false);
		positionLabel.setVisible(true);
		loginNameLabel.setValue("");
		userNameLabel.setValue("");
		emailLabel.setValue("");
		remarkLabel.setValue("");
		enableLabel.setValue("");
		userId = 0;
		user = new User();
	}
}
