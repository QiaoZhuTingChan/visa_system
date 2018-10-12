package com.jyd.bms.window.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.User;
import com.jyd.bms.dto.UserCheckbox;
import com.jyd.bms.service.GroupUserService;
import com.jyd.bms.service.UserService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DeleteException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.PagingControlComponentModelList;
import com.jyd.bms.tool.zk.UserSession;

public class GroupUserWindow extends BaseWindow {
	private Group group;
	private Listbox userListbox;
	private Textbox conditionTextbox;
	private Button searchButton;
	private South southPaging;
	private GridPaging gridPaging;
	private List<User> listUser = new ArrayList<User>();
	private String condition = "";
	private UserService userService;
	private GroupUserService groupUserService;
	
	private static final Logger log = LoggerFactory.getLogger(GroupUserWindow.class);
	
	public GroupUserWindow(){
		super.menuId="sys_group";
	}
	@Override
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
			log.error("GroupUserWindow", e);
			Messagebox.error("获取数据出错了!");
		} catch (Exception e) {
			log.error("GroupUserWindow", e);
			Messagebox.error("未知错误！");
		}
	}

	@Override
	public void initUI() {
		userService = getBean("UserService");
		groupUserService = getBean("GroupUserService");
		userListbox.setItemRenderer(new UserListRenderer());
		try {
			listUser = groupUserService.getGroupUserByGroup(group);
		} catch (DAOException e) {
			log.error("GroupUserWindow", e);
			Messagebox.error("获取用户数据出错！");
		}
	}

	/**
	 * 搜索
	 */
	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		condition = conditionTextbox.getValue();
		initData();
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void onCheckChooseUser(Event event) {
		UserCheckbox tempCheckbox = (UserCheckbox) event.getData();
		User user = tempCheckbox.getUser();
		if (user != null) {
			Checkbox checkbox = tempCheckbox.getCheckbox();
			if (checkbox.isChecked()) {
				User sessionUser = UserSession.getUser();
				if (sessionUser == null) {
					Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
					return;
				}

				Timestamp date = new Timestamp(System.currentTimeMillis());
				GroupUser groupUser = new GroupUser();
				groupUser.setGroup(group);
				groupUser.setUser(user);
				groupUser.setCreateDate(date);
				groupUser.setUpdateDate(date);
				groupUser.setCreateUser(sessionUser.getLoginName());
				groupUser.setUpdateUser(sessionUser.getLoginName());

				try {
					groupUserService.add(groupUser);
				} catch (CreateException e) {
					log.error("GroupUserWindow", e);
					Messagebox.error("保存出错了");
				}
			} else {
				GroupUser tempGroupUser;
				try {
					tempGroupUser = groupUserService.findGroupUser(group, user);
					groupUserService.delete(tempGroupUser);
				} catch (DAOException | DeleteException e) {
					log.error("GroupUserWindow", e);
					Messagebox.error("删除出错了");
				}
			}
		}
	}

	class UserListRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			User user = (User) arg1;

			Listcell chooseCell = new Listcell();
			chooseCell.setParent(arg0);
			Checkbox checkbox = new Checkbox();

			UserCheckbox tempCheckbox = new UserCheckbox();
			tempCheckbox.setCheckbox(checkbox);
			tempCheckbox.setUser(user);

			checkbox.addForward("onCheck", (Component) null, "onCheckChooseUser", tempCheckbox);

			for (User temp : listUser) {
				if (temp.getId() == user.getId()) {
					checkbox.setChecked(true);
					break;
				}
			}

			checkbox.setParent(chooseCell);

			Listcell loginNameCell = new Listcell();
			loginNameCell.setParent(arg0);
			new Label(user.getLoginName()).setParent(loginNameCell);

			Listcell employeeNOCell = new Listcell();
			employeeNOCell.setParent(arg0);

			Listcell userNameCell = new Listcell();
			userNameCell.setParent(arg0);
			new Label(user.getUserName()).setParent(userNameCell);

			Listcell userTypeCell = new Listcell();
			userTypeCell.setParent(arg0);

			Listcell enableCell = new Listcell();
			enableCell.setParent(arg0);
			new Label(user.getEnable() == true ? "启用" : "禁用").setParent(enableCell);

			arg0.setValue(user);
		}
	}
}
