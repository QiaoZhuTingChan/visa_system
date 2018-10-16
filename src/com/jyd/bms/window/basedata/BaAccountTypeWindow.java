package com.jyd.bms.window.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import com.jyd.bms.bean.BaAccountType;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaAccountTypeService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.PagingControlComponentModelList;
import com.jyd.bms.tool.zk.UserSession;

/**
 * @category Generated 2018-10-15 22:36:59 by GeneratedTool
 * @author mjy
 */
public class BaAccountTypeWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label baAccountTypeLabel;
	private Textbox baAccountTypeTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baAccountTypeListbox;
	private BaAccountType baAccountType;
	private BaAccountTypeService baAccountTypeService;
	private List<BaAccountType> baAccountTypelist = new ArrayList<BaAccountType>();
	private static final Logger log = LoggerFactory.getLogger(BaAccountTypeWindow.class);
	private int edit = 0;

	public BaAccountTypeWindow() {
		this.menuId = "ba_account_type";
	}

	public Listitem getSelectItem() {
		return baAccountTypeListbox.getSelectedItem();
	}

	public void initUI() {
		baAccountTypeService = getBean("BaAccountTypeService");
		baAccountTypeListbox.setItemRenderer(new BaAccountTypeRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baAccountTypeService,
					"getPagingBaAccountType", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baAccountTypeListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baAccountTypeService.getBaAccountTypeCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaAccountTypeWindow", e);
			Messagebox.error("获取数据出错了!");
		} catch (Exception e) {
			log.error("AssetsTypeWindow", e);
			Messagebox.error("未知错误！");
		}
	}

	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
		condition = conditionTextbox.getValue();
		initData();
	}

	public void onOKsearchButton() throws SuspendNotAllowedException, InterruptedException {
		onClick$searchButton();
	}

	public void enableTextbox(boolean flag) {
		baAccountTypeLabel.setVisible(!flag);
		baAccountTypeTextbox.setVisible(flag);
		remarkLabel.setVisible(!flag);
		remarkTextbox.setVisible(flag);
	}

	public void enableButton(String type) {
		if (type.equals("add")) {
			addButton.setVisible(false);
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		}
		if (type.equals("update")) {
			addButton.setVisible(false);
			editButton.setVisible(false);
			saveButton.setVisible(true);
			cancelButton.setVisible(true);
		}
		if (type.equals("cancel")) {
			addButton.setVisible(true);
			editButton.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
		if (type.equals("save")) {
			addButton.setVisible(true);
			editButton.setVisible(false);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
		if (type.equals("select")) {
			addButton.setVisible(true);
			editButton.setVisible(true);
			saveButton.setVisible(false);
			cancelButton.setVisible(false);
		}
	}

	public void clearTextbox() {
		baAccountTypeLabel.setValue("");
		baAccountTypeTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaAccountTypeValue(BaAccountType baAccountType) {
		baAccountType.setBaAccountType(baAccountTypeTextbox.getValue());
		baAccountType.setRemark(remarkTextbox.getValue());
	}

	public void setBaAccountTypeData(BaAccountType baAccountType) {
		baAccountTypeLabel.setValue(baAccountType.getBaAccountType());
		baAccountTypeTextbox.setValue(baAccountType.getBaAccountType());
		remarkLabel.setValue(baAccountType.getRemark());
		remarkTextbox.setValue(baAccountType.getRemark());
	}

	public void onSelect$baAccountTypeListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baAccountType = getSelectItem().getValue();
		clearTextbox();
		setBaAccountTypeData(baAccountType);
		enableTextbox(false);
		enableButton("select");
	}

	public void onClick$cancelButton() {
		enableButton("cancel");
		enableTextbox(false);
		clearTextbox();
	}

	public void onClick$addButton() {
		edit = 0;
		enableTextbox(true);
		enableButton("add");
		clearTextbox();
	}

	public void onClick$editButton() {
		edit = -1;
		enableTextbox(true);
		enableButton("update");
	}

	public boolean checkInput() {
		boolean flag = true;
		if (baAccountTypeTextbox.getValue().equals("")) {
			baAccountTypeTextbox.focus();
			Messagebox.show("账户名称不能为空！");
			flag = false;
		}
		return flag;
	}

	public void onClick$saveButton() {
		try {
			User sessionUser = UserSession.getUser();
			if (sessionUser == null) {
				Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
				return;
			}
			Timestamp date = new Timestamp(System.currentTimeMillis());
			String user = sessionUser.getLoginName();
			if (!checkInput()) {
				return;
			}
			if (edit == 0) {
				baAccountType = new BaAccountType();
				setBaAccountTypeValue(baAccountType);
				baAccountType.setCreateDate(date);
				baAccountType.setCreateUser(user);
				baAccountType.setUpdateDate(date);
				baAccountType.setUpdateUser(user);
				baAccountTypeService.add(baAccountType);
			} else {
				setBaAccountTypeValue(baAccountType);
				baAccountType.setUpdateDate(date);
				baAccountType.setUpdateUser(user);
				baAccountTypeService.update(baAccountType);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baAccountTypeWindow", e);
		} catch (UpdateException e) {
			log.error("baAccountTypeWindow", e);
		}
	}

	class BaAccountTypeRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaAccountType baAccountType = (BaAccountType) arg1;
			Listcell baAccountTypeCell = new Listcell();
			baAccountTypeCell.setParent(arg0);
			new Label(baAccountType.getBaAccountType()).setParent(baAccountTypeCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baAccountType.getRemark()).setParent(remarkCell);
			arg0.setValue(baAccountType);
		}
	}
}
