package com.jyd.bms.tool.zk;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;

import com.jyd.bms.bean.GroupUser;
import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.User;
import com.jyd.bms.common.Environment;
import com.jyd.bms.service.GroupMenuService;
import com.jyd.bms.service.GroupUserService;
import com.jyd.bms.service.UserService;
import com.jyd.bms.tool.exception.DAOException;

public abstract class BaseWindow extends GenericForwardWindow {
	private double randomKey = Math.random();
	public String menuId = "";
	// 提供基本增、删、改、刷新、合并
	private Session session = Sessions.getCurrent();

	public void onCreate() {
		// 下面处理测试环境下，用户Session
		if (session.getAttribute("User") == null) {
			String loginName = Environment.getDefaultLoginName();
			if (loginName == null || loginName.equals("")) {
				return;
			} else {
				UserService userService = getBean("UserService");
				try {
					User user = userService.getUserByLoginName(loginName);
					session.setAttribute("User", user);
				} catch (DAOException e) {
					return;
				}
			}
		}
		menuVerification();
		Clients.evalJavaScript("InitEnter()");
	}

	/**
	 * 判断用户有哪些表单的访问权限
	 * 
	 * @author mjy
	 * @param User
	 * 
	 */
	public void menuVerification() {
		try {
			User user = (User) session.getAttribute("User");
			if (user == null)
				return;
			if (menuId.equals(""))
				return;
			GroupUserService groupUserService = getBean("GroupUserService");
			GroupMenuService groupMenuService = getBean("GroupMenuService");
			Menu menu = new Menu();
			menu.setMenuId(menuId);
			// 根据用户查找用户组
			boolean flag=false;
			List<GroupUser> groupList = groupUserService.getGroupUserByUser(user);
			for (GroupUser groupUser : groupList) {
				// 判断用户所在组是否具有菜单的权限
				if (groupMenuService.findGroupMenu(groupUser.getGroup(), menu) != null) {
					flag=true;
				}
			}
			if(flag||menuId.equals("index")) {
				firstLoad();
				return;
			}else {
				Executions.sendRedirect("/permissions.zul");
				return;
			}
		} catch (DAOException e) {
			return;
		}
	}

	/**
	 * 获取Service类
	 * 
	 * @param s
	 * @return
	 */
	public <T> T getBean(String s) {
		return (T) SpringUtil.getBean(s);
	}

	/**
	 * 获取根目录路径
	 * 
	 * @return
	 */
	public String getRootPath() {
		if (Environment.getFileProcessType().equals("webPath") == false) {
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			String path = request.getSession().getServletContext().getRealPath("/");
			return path;
		} else {
			return Environment.getFilePath();
		}
	}

	/**
	 * 获取Templates路径
	 */
	public String getTemplates() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		return path + "templates/";
	}

	public String getHttpPath() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		StringBuilder str = new StringBuilder();
		str.append(request.getScheme());
		str.append("://");
		str.append(request.getServerName());
		str.append(":" + request.getServerPort());
		str.append(request.getContextPath());
		return str.toString();
	}

	/**
	 * 获取输出文件路径
	 * 
	 * @returngetFontPath
	 */
	public String getOutputPath() {
		return this.getRootPath() + "output/";
	}
	/**
	 * 获取第三方html文件路径
	 * 
	 * @returngetFontPath
	 */
	public String getApiPath() {
		return this.getRootPath() + "api/";
	}

	/**
	 * 获取employeePhoto路径
	 */
	public String getEmployeePhoto() {
		return this.getRootPath() + "employeePhoto/";
	}

	/**
	 * 获取字体路径
	 * 
	 * @return
	 */
	public String getFontPath() {
		return this.getRootPath() + "fonts/simsun.ttc";
	}
}