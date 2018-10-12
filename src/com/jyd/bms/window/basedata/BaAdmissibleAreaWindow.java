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

import com.jyd.bms.bean.BaAdmissibleArea;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaAdmissibleAreaService;
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
 * @category Generated 2018-10-12 23:39:01 by GeneratedTool
 * @author mjy
 */
public class BaAdmissibleAreaWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label baAdmissibleAreaLabel;
	private Textbox baAdmissibleAreaTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baAdmissibleAreaListbox;
	private BaAdmissibleArea baAdmissibleArea;
	private BaAdmissibleAreaService baAdmissibleAreaService;
	private List<BaAdmissibleArea> baAdmissibleArealist = new ArrayList<BaAdmissibleArea>();
	private static final Logger log = LoggerFactory.getLogger(BaAdmissibleAreaWindow.class);
	private int edit = 0;

	public BaAdmissibleAreaWindow() {
		this.menuId = "ba_admissible_area";
	}

	public Listitem getSelectItem() {
		return baAdmissibleAreaListbox.getSelectedItem();
	}

	public void initUI() {
		baAdmissibleAreaService = getBean("BaAdmissibleAreaService");
		baAdmissibleAreaListbox.setItemRenderer(new BaAdmissibleAreaRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(
					baAdmissibleAreaService, "getPagingBaAdmissibleArea", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baAdmissibleAreaListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baAdmissibleAreaService.getBaAdmissibleAreaCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaAdmissibleAreaWindow", e);
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
		baAdmissibleAreaLabel.setVisible(!flag);
		baAdmissibleAreaTextbox.setVisible(flag);
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
		baAdmissibleAreaLabel.setValue("");
		baAdmissibleAreaTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaAdmissibleAreaValue(BaAdmissibleArea baAdmissibleArea) {
		baAdmissibleArea.setBaAdmissibleArea(baAdmissibleAreaTextbox.getValue());
		baAdmissibleArea.setRemark(remarkTextbox.getValue());
	}

	public void setBaAdmissibleAreaData(BaAdmissibleArea baAdmissibleArea) {
		baAdmissibleAreaLabel.setValue(baAdmissibleArea.getBaAdmissibleArea());
		baAdmissibleAreaTextbox.setValue(baAdmissibleArea.getBaAdmissibleArea());
		remarkLabel.setValue(baAdmissibleArea.getRemark());
		remarkTextbox.setValue(baAdmissibleArea.getRemark());
	}

	public void onSelect$baAdmissibleAreaListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baAdmissibleArea = getSelectItem().getValue();
		clearTextbox();
		setBaAdmissibleAreaData(baAdmissibleArea);
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
		if (baAdmissibleAreaTextbox.getValue().equals("")) {
			baAdmissibleAreaTextbox.focus();
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
				baAdmissibleArea = new BaAdmissibleArea();
				setBaAdmissibleAreaValue(baAdmissibleArea);
				baAdmissibleArea.setCreateDate(date);
				baAdmissibleArea.setCreateUser(user);
				baAdmissibleArea.setUpdateDate(date);
				baAdmissibleArea.setUpdateUser(user);
				baAdmissibleAreaService.add(baAdmissibleArea);
			} else {
				setBaAdmissibleAreaValue(baAdmissibleArea);
				baAdmissibleArea.setUpdateDate(date);
				baAdmissibleArea.setUpdateUser(user);
				baAdmissibleAreaService.update(baAdmissibleArea);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baAdmissibleAreaWindow", e);
		} catch (UpdateException e) {
			log.error("baAdmissibleAreaWindow", e);
		}
	}

	class BaAdmissibleAreaRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaAdmissibleArea baAdmissibleArea = (BaAdmissibleArea) arg1;
			Listcell baAdmissibleAreaCell = new Listcell();
			baAdmissibleAreaCell.setParent(arg0);
			new Label(baAdmissibleArea.getBaAdmissibleArea()).setParent(baAdmissibleAreaCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baAdmissibleArea.getRemark()).setParent(remarkCell);
			arg0.setValue(baAdmissibleArea);
		}
	}
}
