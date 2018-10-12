package com.jyd.bms.window;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.User;
import com.jyd.bms.common.Environment;
import com.jyd.bms.service.UserService;
import com.jyd.bms.tool.VerifyCodeUtils;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.Messagebox;

public class LoginWindow extends BaseWindow {
	private Textbox textboxLoginName;
	private Textbox textboxPassword;
	private Button buttonLogin;
	private Button buttonReset;
	private Image imageCode;
	private Textbox textboxImageCode;
	private int selectIndex = 0;
	private String verifyCode;
	private Label titleLabel;
	private Tr imageCodeTr;
	private int testTimes = 0;

	public LoginWindow() {
		this.menuId = "login";
	}

	public void onCreate() {
		Locale locale = java.util.Locale.CHINA;
		Sessions.getCurrent().setAttribute("px_preferred_locale", locale);
		initUI();
		initData();
	}

	public void initData() {
		Session session = Sessions.getCurrent();
		if (session.getAttribute("User") != null) {
			Executions.sendRedirect("/index.zul");
		}
		changeImageCode();
	}

	private void changeImageCode() {
		verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		BufferedImage bufferedImage = VerifyCodeUtils.createImage(200, 80, verifyCode);
		imageCode.setContent(bufferedImage);
	}

	public void onClick$imageCode() {
		changeImageCode();
	}

	public void initUI() {
		if (Environment.getNeedImageCode() == false) {
			imageCodeTr.setVisible(false);
		}
		titleLabel.setValue(Environment.getBmsTitle() + "-业务系统入口");
		textboxLoginName.select();
	}

	public void onOK$textboxLoginName() {
		textboxPassword.select();
	}

	public void onOK$textboxPassword() {
		if (Environment.getNeedImageCode() == true) {
			textboxImageCode.select();
		} else {
			onClick$buttonLogin();
		}
	}

	public void onOK$textboxImageCode() {
		onClick$buttonLogin();
	}

	public void onClick$buttonLogin() {
		if (Environment.getNeedImageCode() == true || testTimes >= 3) {
			if (textboxImageCode.getText().toLowerCase().equals(verifyCode.toLowerCase()) == false) {
				changeImageCode();
				Messagebox.show("验证码不正确，请重新输入！");
				textboxImageCode.setFocus(true);
				return;
			}
		}

		String loginName = textboxLoginName.getText();
		String password = textboxPassword.getText();
		if (loginName.equals("")) {
			Messagebox.info("请输入账号!");
			textboxLoginName.select();
			return;
		}

		if (password.equals("")) {
			Messagebox.info("请输入密码！");
			textboxLoginName.select();
			return;
		}

		password = getMD5(password);

		try {
			UserService userService = getBean("UserService");
			User user = userService.checkLogin(loginName, password);
			if (user != null) {
				userService.refresh(user);
				Session session = Sessions.getCurrent();
				session.setAttribute("User", user);
				Executions.sendRedirect("/index.zul");
			} else {
				testTimes++;
				if (testTimes >= 3) {
					imageCodeTr.setVisible(true);
				}
				textboxPassword.setValue("");
				changeImageCode();
				Messagebox.error("登录失败！");
			}
		} catch (DAOException e) {
			Messagebox.error(Labels.getLabel("useraccesscontrol.login.loginerrortelladministrator"));
		}
	}

	public void onClick$buttonReset() {
		textboxLoginName.setText("");
		textboxPassword.setText("");
		textboxLoginName.select();
	}

	public static String getMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			return "";
		}
	}
}
