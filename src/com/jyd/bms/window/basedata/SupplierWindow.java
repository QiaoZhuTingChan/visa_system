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
import com.jyd.bms.bean.Supplier;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.SupplierService;
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
 * @category Generated 2018-10-15 22:39:51 by GeneratedTool
 * @author mjy
 */
public class SupplierWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label contactNameLabel;
	private Textbox contactNameTextbox;
	private Label addressLabel;
	private Textbox addressTextbox;
	private Label companyLabel;
	private Textbox companyTextbox;
	private Label contactPhoneLabel;
	private Textbox contactPhoneTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox supplierListbox;
	private Supplier supplier;
	private SupplierService supplierService;
	private List<Supplier> supplierlist = new ArrayList<Supplier>();
	private static final Logger log = LoggerFactory.getLogger(SupplierWindow.class);
	private int edit = 0;

	public SupplierWindow() {
		this.menuId = "supplier";
	}

	public Listitem getSelectItem() {
		return supplierListbox.getSelectedItem();
	}

	public void initUI() {
		supplierService = getBean("SupplierService");
		supplierListbox.setItemRenderer(new SupplierRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(supplierService,
					"getPagingSupplier", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(supplierListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(supplierService.getSupplierCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("SupplierWindow", e);
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
		contactNameLabel.setVisible(!flag);
		contactNameTextbox.setVisible(flag);
		addressLabel.setVisible(!flag);
		addressTextbox.setVisible(flag);
		companyLabel.setVisible(!flag);
		companyTextbox.setVisible(flag);
		contactPhoneLabel.setVisible(!flag);
		contactPhoneTextbox.setVisible(flag);
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
		contactNameLabel.setValue("");
		contactNameTextbox.setValue("");
		addressLabel.setValue("");
		addressTextbox.setValue("");
		companyLabel.setValue("");
		companyTextbox.setValue("");
		contactPhoneLabel.setValue("");
		contactPhoneTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setSupplierValue(Supplier supplier) {
		supplier.setContactName(contactNameTextbox.getValue());
		supplier.setAddress(addressTextbox.getValue());
		supplier.setCompany(companyTextbox.getValue());
		supplier.setContactPhone(contactPhoneTextbox.getValue());
		supplier.setRemark(remarkTextbox.getValue());
	}

	public void setSupplierData(Supplier supplier) {
		contactNameLabel.setValue(supplier.getContactName());
		contactNameTextbox.setValue(supplier.getContactName());
		addressLabel.setValue(supplier.getAddress());
		addressTextbox.setValue(supplier.getAddress());
		companyLabel.setValue(supplier.getCompany());
		companyTextbox.setValue(supplier.getCompany());
		contactPhoneLabel.setValue(supplier.getContactPhone());
		contactPhoneTextbox.setValue(supplier.getContactPhone());
		remarkLabel.setValue(supplier.getRemark());
		remarkTextbox.setValue(supplier.getRemark());
	}

	public void onSelect$supplierListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		supplier = getSelectItem().getValue();
		clearTextbox();
		setSupplierData(supplier);
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
		if (contactNameTextbox.getValue().equals("")) {
			contactNameTextbox.focus();
			Messagebox.show("联系人不能为空！");
			flag = false;
		}
		if (addressTextbox.getValue().equals("")) {
			addressTextbox.focus();
			Messagebox.show("地址不能为空！");
			flag = false;
		}
		if (companyTextbox.getValue().equals("")) {
			companyTextbox.focus();
			Messagebox.show("所属公司不能为空！");
			flag = false;
		}
		if (contactPhoneTextbox.getValue().equals("")) {
			contactPhoneTextbox.focus();
			Messagebox.show("联系方式不能为空！");
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
				supplier = new Supplier();
				setSupplierValue(supplier);
				supplier.setCreateDate(date);
				supplier.setCreateUser(user);
				supplier.setUpdateDate(date);
				supplier.setUpdateUser(user);
				supplierService.add(supplier);
			} else {
				setSupplierValue(supplier);
				supplier.setUpdateDate(date);
				supplier.setUpdateUser(user);
				supplierService.update(supplier);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("supplierWindow", e);
		} catch (UpdateException e) {
			log.error("supplierWindow", e);
		}
	}

	class SupplierRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			Supplier supplier = (Supplier) arg1;
			Listcell contactNameCell = new Listcell();
			contactNameCell.setParent(arg0);
			new Label(supplier.getContactName()).setParent(contactNameCell);
			Listcell addressCell = new Listcell();
			addressCell.setParent(arg0);
			new Label(supplier.getAddress()).setParent(addressCell);
			Listcell companyCell = new Listcell();
			companyCell.setParent(arg0);
			new Label(supplier.getCompany()).setParent(companyCell);
			Listcell contactPhoneCell = new Listcell();
			contactPhoneCell.setParent(arg0);
			new Label(supplier.getContactPhone()).setParent(contactPhoneCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(supplier.getRemark()).setParent(remarkCell);
			arg0.setValue(supplier);
		}
	}
}
