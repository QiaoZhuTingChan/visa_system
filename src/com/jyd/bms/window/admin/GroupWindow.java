package com.jyd.bms.window.admin;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.Group;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.GroupService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.ContinueProcessAfterComponentCreated;
import com.jyd.bms.tool.zk.Executions;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.PagingControlComponentModelList;
import com.jyd.bms.tool.zk.UserSession;

public class GroupWindow extends BaseWindow implements ContinueProcessAfterComponentCreated {
	private boolean editFlag = false;
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private Button groupMenuButton;
	private Button groupUserButton;
	private GridPaging gridPaging;
	private Label remarkLabel;
	private Label nameLabel;
	private Listbox groupListbox;
	private int groupId = 0;
	private Textbox nameTextbox;
	private Textbox remarkTextbox;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Group group;
	private GroupService groupService;

	private static final Logger log = LoggerFactory.getLogger(GroupWindow.class);
	public GroupWindow() {
		super.menuId="sys_group";
	}
	public Listitem getSelectItem() {
		return groupListbox.getSelectedItem();
	}

	/**
	 * 弹出窗口参数传递
	 */
	public void processAfterComponentCreated(Component paramComponent) {
		if (paramComponent instanceof GroupUserWindow) {
			GroupUserWindow groupUserWindow = (GroupUserWindow) paramComponent;
			groupUserWindow.setGroup(group);
		}
		if (paramComponent instanceof GroupMenuWindow) {
			GroupMenuWindow groupMenuWindow = (GroupMenuWindow) paramComponent;
			groupMenuWindow.setGroup(group);
		}
	}

	/**
	 * 初始化数据绑定
	 */
	public void initUI() {
		groupService = getBean("GroupService");
		groupListbox.setItemRenderer(new GroupListRenderer());
		groupListbox.addForward("onSelect", (Component) null, "onSelectGroup", null);
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(groupService,
					"getPagingGroup", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(groupListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(groupService.getGroupCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("GroupWindow", e);
			Messagebox.error("获取数据出错了!");
		} catch (Exception e) {
			log.error("GroupWindow", e);
			Messagebox.error("未知错误！");
		}
	}

	public void onClick$groupMenuButton() throws SuspendNotAllowedException, InterruptedException {
		GroupMenuWindow groupMenuWindow = (GroupMenuWindow) Executions.doModal("/admin/groupmenu.zul", null, null,
				false, this);
	}

	public void onClick$groupUserButton() throws SuspendNotAllowedException, InterruptedException {
		GroupUserWindow groupUserWindow = (GroupUserWindow) Executions.doModal("/admin/groupuser.zul", null, null,
				false, this);
	}

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
	 */
	public void onSelectGroup() throws SuspendNotAllowedException, InterruptedException {
		editFlag = false;
		addButton.setVisible(true);
		editButton.setVisible(true);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		groupMenuButton.setVisible(true);
		groupUserButton.setVisible(true);
		nameTextbox.setVisible(false);
		remarkTextbox.setVisible(false);

		nameLabel.setVisible(true);
		remarkLabel.setVisible(true);

		group = (Group) groupListbox.getSelectedItem().getValue();
		groupId = group.getId();
		if (group != null) {
			nameLabel.setValue(group.getName());
			remarkLabel.setValue(group.getRemark());

			nameTextbox.setValue(group.getName());
			remarkTextbox.setValue(group.getRemark());
		}
		groupListbox.setSelectedItem(null);
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
		groupMenuButton.setVisible(true);
		groupUserButton.setVisible(true);
		nameLabel.setVisible(false);
		remarkLabel.setVisible(false);
		nameTextbox.setVisible(true);
		remarkTextbox.setVisible(true);

		nameTextbox.setValue(group.getName());
		remarkTextbox.setValue(group.getRemark());
		nameTextbox.select();
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
		groupMenuButton.setVisible(false);
		groupUserButton.setVisible(false);
		nameTextbox.setVisible(false);
		remarkTextbox.setVisible(false);
		nameLabel.setVisible(true);
		remarkLabel.setVisible(true);

		nameLabel.setValue("");
		remarkLabel.setValue("");
		initData();
	}

	/**
	 * 添加
	 */
	public void onClick$addButton() {
		groupId = 0;
		editFlag = false;
		addButton.setVisible(false);
		editButton.setVisible(false);
		cancelButton.setVisible(true);
		saveButton.setVisible(true);
		groupMenuButton.setVisible(false);
		groupUserButton.setVisible(false);
		nameLabel.setVisible(false);
		remarkLabel.setVisible(false);
		nameTextbox.setVisible(true);
		remarkTextbox.setVisible(true);

		nameTextbox.setValue("");
		remarkTextbox.setValue("");
		nameTextbox.setFocus(true);
	}

	/**
	 * 保存数据
	 */
	public void onClick$saveButton() {
		try {
			if (nameTextbox.getValue().equals("")) {
				Messagebox.info("组名称为空，请输入");
				nameTextbox.select();
				return;
			}
			User user = UserSession.getUser();
			if (user == null) {
				Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
				return;
			}

			Timestamp date = new Timestamp(System.currentTimeMillis());
			if (groupId == 0 && editFlag == false) {
				group = new Group();
				group.setName(nameTextbox.getValue());
				group.setRemark(remarkTextbox.getValue());
				group.setCreateUser(user.getLoginName());
				group.setCreateDate(date);
				group.setUpdateUser(user.getLoginName());
				group.setUpdateDate(date);
				groupService.add(group);
				groupId = 0;
			} else if (editFlag == true) {
				group = groupService.getById(groupId);
				group.setName(nameTextbox.getValue());
				group.setRemark(remarkTextbox.getValue());
				group.setUpdateUser(user.getLoginName());
				group.setUpdateDate(date);
				groupService.update(group);
			}
			addButton.setVisible(true);
			editButton.setVisible(true);
			cancelButton.setVisible(false);
			saveButton.setVisible(false);
			groupMenuButton.setVisible(false);
			groupUserButton.setVisible(false);
			nameTextbox.setVisible(false);
			remarkTextbox.setVisible(false);
			nameLabel.setVisible(true);
			remarkLabel.setVisible(true);
			nameLabel.setValue(nameTextbox.getValue());
			remarkLabel.setValue(remarkTextbox.getValue());
			flashSouth();
			initData();
		} catch (CreateException e) {
			log.error("GroupWindow", e);
			Messagebox.error("保存出错了");
		} catch (UpdateException e) {
			log.error("GroupWindow", e);
			Messagebox.error("修改出错了");
		} catch (DataNotFoundException e) {
			log.error("GroupWindow", e);
			Messagebox.error("查询出错了");
		}
	}

	/**
	 * 处理Group数据绑定
	 */
	class GroupListRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			Group group = (Group) arg1;

			Listcell nameCell = new Listcell();
			nameCell.setParent(arg0);
			new Label(group.getName()).setParent(nameCell);

			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(group.getRemark()).setParent(remarkCell);

			arg0.setValue(group);
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
		groupMenuButton.setVisible(false);
		groupUserButton.setVisible(false);
		nameTextbox.setVisible(false);
		remarkTextbox.setVisible(false);
		nameLabel.setVisible(true);
		remarkLabel.setVisible(true);

		nameLabel.setValue("");
		remarkLabel.setValue("");
		groupId = 0;
		nameTextbox.setValue("");
		remarkTextbox.setValue("");
		group = new Group();
	}
}
