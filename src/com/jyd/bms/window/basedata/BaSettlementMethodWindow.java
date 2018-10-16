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
import com.jyd.bms.bean.BaSettlementMethod;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaSettlementMethodService;
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
 * @category Generated 2018-10-15 22:37:08 by GeneratedTool
 * @author mjy
 */
public class BaSettlementMethodWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label baSettlementMethodLabel;
	private Textbox baSettlementMethodTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baSettlementMethodListbox;
	private BaSettlementMethod baSettlementMethod;
	private BaSettlementMethodService baSettlementMethodService;
	private List<BaSettlementMethod> baSettlementMethodlist = new ArrayList<BaSettlementMethod>();
	private static final Logger log = LoggerFactory.getLogger(BaSettlementMethodWindow.class);
	private int edit = 0;

	public BaSettlementMethodWindow() {
		this.menuId = "ba_settlement_method";
	}

	public Listitem getSelectItem() {
		return baSettlementMethodListbox.getSelectedItem();
	}

	public void initUI() {
		baSettlementMethodService = getBean("BaSettlementMethodService");
		baSettlementMethodListbox.setItemRenderer(new BaSettlementMethodRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(
					baSettlementMethodService, "getPagingBaSettlementMethod", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baSettlementMethodListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baSettlementMethodService.getBaSettlementMethodCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaSettlementMethodWindow", e);
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
		baSettlementMethodLabel.setVisible(!flag);
		baSettlementMethodTextbox.setVisible(flag);
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
		baSettlementMethodLabel.setValue("");
		baSettlementMethodTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaSettlementMethodValue(BaSettlementMethod baSettlementMethod) {
		baSettlementMethod.setBaSettlementMethod(baSettlementMethodTextbox.getValue());
		baSettlementMethod.setRemark(remarkTextbox.getValue());
	}

	public void setBaSettlementMethodData(BaSettlementMethod baSettlementMethod) {
		baSettlementMethodLabel.setValue(baSettlementMethod.getBaSettlementMethod());
		baSettlementMethodTextbox.setValue(baSettlementMethod.getBaSettlementMethod());
		remarkLabel.setValue(baSettlementMethod.getRemark());
		remarkTextbox.setValue(baSettlementMethod.getRemark());
	}

	public void onSelect$baSettlementMethodListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baSettlementMethod = getSelectItem().getValue();
		clearTextbox();
		setBaSettlementMethodData(baSettlementMethod);
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
		if (baSettlementMethodTextbox.getValue().equals("")) {
			baSettlementMethodTextbox.focus();
			Messagebox.show("结算方式名称不能为空！");
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
				baSettlementMethod = new BaSettlementMethod();
				setBaSettlementMethodValue(baSettlementMethod);
				baSettlementMethod.setCreateDate(date);
				baSettlementMethod.setCreateUser(user);
				baSettlementMethod.setUpdateDate(date);
				baSettlementMethod.setUpdateUser(user);
				baSettlementMethodService.add(baSettlementMethod);
			} else {
				setBaSettlementMethodValue(baSettlementMethod);
				baSettlementMethod.setUpdateDate(date);
				baSettlementMethod.setUpdateUser(user);
				baSettlementMethodService.update(baSettlementMethod);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baSettlementMethodWindow", e);
		} catch (UpdateException e) {
			log.error("baSettlementMethodWindow", e);
		}
	}

	class BaSettlementMethodRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaSettlementMethod baSettlementMethod = (BaSettlementMethod) arg1;
			Listcell baSettlementMethodCell = new Listcell();
			baSettlementMethodCell.setParent(arg0);
			new Label(baSettlementMethod.getBaSettlementMethod()).setParent(baSettlementMethodCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baSettlementMethod.getRemark()).setParent(remarkCell);
			arg0.setValue(baSettlementMethod);
		}
	}
}
