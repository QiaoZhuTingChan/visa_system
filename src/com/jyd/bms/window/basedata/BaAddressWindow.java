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
import com.jyd.bms.bean.BaAddress;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaAddressService;
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
 * @category Generated 2018-10-12 23:30:12 by GeneratedTool
 * @author mjy
 */
public class BaAddressWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label contactLabel;
	private Textbox contactTextbox;
	private Label contactPhoneLabel;
	private Textbox contactPhoneTextbox;
	private Label addressLabel;
	private Textbox addressTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baAddressListbox;
	private BaAddress baAddress;
	private BaAddressService baAddressService;
	private List<BaAddress> baAddresslist = new ArrayList<BaAddress>();
	private static final Logger log = LoggerFactory.getLogger(BaAddressWindow.class);
	private int edit = 0;

	public BaAddressWindow() {
		this.menuId = "ba_address";
	}

	public Listitem getSelectItem() {
		return baAddressListbox.getSelectedItem();
	}

	public void initUI() {
		baAddressService = getBean("BaAddressService");
		baAddressListbox.setItemRenderer(new BaAddressRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baAddressService,
					"getPagingBaAddress", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baAddressListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baAddressService.getBaAddressCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaAddressWindow", e);
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
		contactLabel.setVisible(!flag);
		contactTextbox.setVisible(flag);
		contactPhoneLabel.setVisible(!flag);
		contactPhoneTextbox.setVisible(flag);
		addressLabel.setVisible(!flag);
		addressTextbox.setVisible(flag);
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
		contactLabel.setValue("");
		contactTextbox.setValue("");
		contactPhoneLabel.setValue("");
		contactPhoneTextbox.setValue("");
		addressLabel.setValue("");
		addressTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaAddressValue(BaAddress baAddress) {
		baAddress.setContact(contactTextbox.getValue());
		baAddress.setContactPhone(contactPhoneTextbox.getValue());
		baAddress.setAddress(addressTextbox.getValue());
		baAddress.setRemark(remarkTextbox.getValue());
	}

	public void setBaAddressData(BaAddress baAddress) {
		contactLabel.setValue(baAddress.getContact());
		contactTextbox.setValue(baAddress.getContact());
		contactPhoneLabel.setValue(baAddress.getContactPhone());
		contactPhoneTextbox.setValue(baAddress.getContactPhone());
		addressLabel.setValue(baAddress.getAddress());
		addressTextbox.setValue(baAddress.getAddress());
		remarkLabel.setValue(baAddress.getRemark());
		remarkTextbox.setValue(baAddress.getRemark());
	}

	public void onSelect$baAddressListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baAddress = getSelectItem().getValue();
		clearTextbox();
		setBaAddressData(baAddress);
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
		if (contactTextbox.getValue().equals("")) {
			contactTextbox.focus();
			Messagebox.show("联系人不能为空！");
			flag = false;
		}
		if (contactPhoneTextbox.getValue().equals("")) {
			contactPhoneTextbox.focus();
			Messagebox.show("联系电话不能为空！");
			flag = false;
		}
		if (addressTextbox.getValue().equals("")) {
			addressTextbox.focus();
			Messagebox.show("详细地址不能为空！");
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
				baAddress = new BaAddress();
				setBaAddressValue(baAddress);
				baAddress.setCreateDate(date);
				baAddress.setCreateUser(user);
				baAddress.setUpdateDate(date);
				baAddress.setUpdateUser(user);
				baAddressService.add(baAddress);
			} else {
				setBaAddressValue(baAddress);
				baAddress.setUpdateDate(date);
				baAddress.setUpdateUser(user);
				baAddressService.update(baAddress);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baAddressWindow", e);
		} catch (UpdateException e) {
			log.error("baAddressWindow", e);
		}
	}

	class BaAddressRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaAddress baAddress = (BaAddress) arg1;
			Listcell contactCell = new Listcell();
			contactCell.setParent(arg0);
			new Label(baAddress.getContact()).setParent(contactCell);
			Listcell contactPhoneCell = new Listcell();
			contactPhoneCell.setParent(arg0);
			new Label(baAddress.getContactPhone()).setParent(contactPhoneCell);
			Listcell addressCell = new Listcell();
			addressCell.setParent(arg0);
			new Label(baAddress.getAddress()).setParent(addressCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baAddress.getRemark()).setParent(remarkCell);
			arg0.setValue(baAddress);
		}
	}
}
