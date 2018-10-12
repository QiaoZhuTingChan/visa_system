package com.jyd.bms.window.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.User;
import com.jyd.bms.service.UserService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.UserSession;
import com.jyd.bms.window.LoginWindow;

public class ChangePasswordWindow extends BaseWindow {
	private Textbox oldPasswordTextbox;
	private Textbox newPasswordTextbox;
	private Textbox confirmPasswordTextbox;
	private Label loginNameLabel;
	private Button saveButton;
	private User user;
	private UserService userService;
	public ChangePasswordWindow() {
		super.menuId="index";
	}
	private static final Logger log = LoggerFactory.getLogger(ChangePasswordWindow.class);

	@Override
	public void initData() {
		user = UserSession.getUser();
		if (user == null) {
			Messagebox.error("当前操作用户为空，不可以操作，请重新登陆");
			saveButton.setVisible(false);
			return;
		}

		System.out.println("Login Name:" + user.getLoginName());

		loginNameLabel.setValue(user.getLoginName());
	}

	@Override
	public void initUI() {
		userService = getBean("UserService");
	}

	public void onClick$saveButton() {
		String oldPassword = oldPasswordTextbox.getText();

		try {
			if (LoginWindow.getMD5(oldPassword).equals(user.getPassword())) {
				if (newPasswordTextbox.getText().equals(confirmPasswordTextbox.getText())) {
					user.setPassword(LoginWindow.getMD5(newPasswordTextbox.getText()));
					userService.update(user);
					Messagebox.info("密码已修改");
				} else {
					Messagebox.error("确认密码不一致，请核对录入数据");
				}
			} else {
				Messagebox.error("旧密码不对");
			}
		} catch (WrongValueException e) {
			log.error("ChangePasswordWindow", e);
		} catch (Exception e) {
			log.error("ChangePasswordWindow", e);
		}

	}

}
