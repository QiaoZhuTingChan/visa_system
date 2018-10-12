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
import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaPositionService;
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
 * @category Generated 2018-10-11 22:39:56 by GeneratedTool
 * @author mjy
 */
public class BaPositionWindow extends BaseWindow {
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label baPositionLabel;
	private Textbox baPositionTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baPositionListbox;
	private BaPosition baPosition;
	private BaPositionService baPositionService;
	private List<BaPosition> baPositionlist = new ArrayList<BaPosition>();
	private static final Logger log = LoggerFactory.getLogger(BaPositionWindow.class);
	private int edit = 0;

	public BaPositionWindow() {
		this.menuId = "ba_position";
	}

	public Listitem getSelectItem() {
		return baPositionListbox.getSelectedItem();
	}

	public void initUI() {
		baPositionService = getBean("BaPositionService");
		baPositionListbox.setItemRenderer(new BaPositionRenderer());
	}

	@Override
	public void initData() {
		try {
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baPositionService,
					"getPagingBaPosition", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baPositionListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baPositionService.getBaPositionCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaPositionWindow", e);
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
		baPositionLabel.setVisible(!flag);
		baPositionTextbox.setVisible(flag);
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
		baPositionLabel.setValue("");
		baPositionTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
	}

	public void setBaPositionValue(BaPosition baPosition) {
		baPosition.setBaPosition(baPositionTextbox.getValue());
		baPosition.setRemark(remarkTextbox.getValue());
	}

	public void setBaPositionData(BaPosition baPosition) {
		baPositionLabel.setValue(baPosition.getBaPosition());
		baPositionTextbox.setValue(baPosition.getBaPosition());
		remarkLabel.setValue(baPosition.getRemark());
		remarkTextbox.setValue(baPosition.getRemark());
	}

	public void onSelect$baPositionListbox() throws SuspendNotAllowedException, InterruptedException {
		edit = -1;
		baPosition = getSelectItem().getValue();
		clearTextbox();
		setBaPositionData(baPosition);
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
		if (baPositionTextbox.getValue().equals("")) {
			baPositionTextbox.focus();
			Messagebox.show("职位名称不能为空！");
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
				baPosition = new BaPosition();
				setBaPositionValue(baPosition);
				baPosition.setCreateDate(date);
				baPosition.setCreateUser(user);
				baPosition.setUpdateDate(date);
				baPosition.setUpdateUser(user);
				baPositionService.add(baPosition);
			} else {
				setBaPositionValue(baPosition);
				baPosition.setUpdateDate(date);
				baPosition.setUpdateUser(user);
				baPositionService.update(baPosition);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baPositionWindow", e);
		} catch (UpdateException e) {
			log.error("baPositionWindow", e);
		}
	}

	class BaPositionRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaPosition baPosition = (BaPosition) arg1;
			Listcell baPositionCell = new Listcell();
			baPositionCell.setParent(arg0);
			new Label(baPosition.getBaPosition()).setParent(baPositionCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baPosition.getRemark()).setParent(remarkCell);
			arg0.setValue(baPosition);
		}
	}
}
