package com.jyd.bms.window.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;

import com.jyd.bms.bean.BaVisaType;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaVisaTypeService;
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
 * @category Generated 2018-10-12 22:37:10 by GeneratedTool
 * @author mjy
 */
public class BaVisaTypeWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label baVisaTypeLabel;
	private Textbox baVisaTypeTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baVisaTypeListbox;
	private BaVisaType baVisaType;
	private BaVisaTypeService baVisaTypeService;
	private List<BaVisaType> baVisaTypelist = new ArrayList<BaVisaType>();
	private static final Logger log = LoggerFactory.getLogger(BaVisaTypeWindow.class);
	private int edit = 0;

	public BaVisaTypeWindow() {
		this.menuId = "ba_visa_type";
	}

	public Listitem getSelectItem() {
		return baVisaTypeListbox.getSelectedItem();
	}

	public void initUI() {
		baVisaTypeService = getBean("BaVisaTypeService");
		baVisaTypeListbox.setItemRenderer(new BaVisaTypeRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baVisaTypeService,
					"getPagingBaVisaType", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baVisaTypeListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baVisaTypeService.getBaVisaTypeCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaVisaTypeWindow", e);
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
		baVisaTypeLabel.setVisible(!flag);
		baVisaTypeTextbox.setVisible(flag);
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
		baVisaTypeLabel.setValue("");
		baVisaTypeTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaVisaTypeValue(BaVisaType baVisaType) {
		baVisaType.setBaVisaType(baVisaTypeTextbox.getValue());
		baVisaType.setRemark(remarkTextbox.getValue());
	}

	public void setBaVisaTypeData(BaVisaType baVisaType) {
		baVisaTypeLabel.setValue(baVisaType.getBaVisaType());
		baVisaTypeTextbox.setValue(baVisaType.getBaVisaType());
		remarkLabel.setValue(baVisaType.getRemark());
		remarkTextbox.setValue(baVisaType.getRemark());
	}

	public void onSelect$baVisaTypeListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baVisaType = getSelectItem().getValue();
		clearTextbox();
		setBaVisaTypeData(baVisaType);
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
		if (baVisaTypeTextbox.getValue().equals("")) {
			baVisaTypeTextbox.focus();
			Messagebox.show("签证类型不能为空！");
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
				baVisaType = new BaVisaType();
				setBaVisaTypeValue(baVisaType);
				baVisaType.setCreateDate(date);
				baVisaType.setCreateUser(user);
				baVisaType.setUpdateDate(date);
				baVisaType.setUpdateUser(user);
				baVisaTypeService.add(baVisaType);
			} else {
				setBaVisaTypeValue(baVisaType);
				baVisaType.setUpdateDate(date);
				baVisaType.setUpdateUser(user);
				baVisaTypeService.update(baVisaType);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baVisaTypeWindow", e);
		} catch (UpdateException e) {
			log.error("baVisaTypeWindow", e);
		}
	}

	class BaVisaTypeRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaVisaType baVisaType = (BaVisaType) arg1;
			Listcell baVisaTypeCell = new Listcell();
			baVisaTypeCell.setParent(arg0);
			new Label(baVisaType.getBaVisaType()).setParent(baVisaTypeCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baVisaType.getRemark()).setParent(remarkCell);
			arg0.setValue(baVisaType);
		}
	}
}
