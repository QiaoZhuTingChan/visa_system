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
import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.bean.User;
import com.jyd.bms.service.BaModelTypeService;
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
 * @category Generated 2018-10-14 19:51:29 by GeneratedTool
 * @author mjy
 */
public class BaModelTypeWindow extends BaseWindow {
private Button addButton;
private Button editButton;
private Button cancelButton;
private Button saveButton;
private GridPaging gridPaging;
private Textbox conditionTextbox;
private String condition = "";
private South southPaging;
private Label baModelTypeLabel;
private Textbox baModelTypeTextbox;
private Label remarkLabel;
private Textbox remarkTextbox;
private Listbox baModelTypeListbox;
private BaModelType baModelType;
private BaModelTypeService baModelTypeService;
private List<BaModelType> baModelTypelist = new ArrayList<BaModelType>();
private static final Logger log = LoggerFactory.getLogger(BaModelTypeWindow.class);
private int edit=0;
public BaModelTypeWindow() {
this.menuId = "ba_model_type";
}
public Listitem getSelectItem() {
return baModelTypeListbox.getSelectedItem();
}
public void initUI() {
baModelTypeService = getBean("BaModelTypeService");
baModelTypeListbox.setItemRenderer(new BaModelTypeRenderer());
}
@Override
public void initData() {
try {
PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baModelTypeService,"getPagingBaModelType", new Object[] { condition });
if (gridPaging == null) {
gridPaging = new GridPaging(baModelTypeListbox, pagingModelList, 20);
} else {
gridPaging.setPagingControlComponentModel(pagingModelList, 20);
}
gridPaging.setTotalSize(baModelTypeService.getBaModelTypeCount(condition));
gridPaging.setDetailed(true);gridPaging.setParent(southPaging);
} catch (DAOException e) {
log.error("BaModelTypeWindow", e);
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
public void enableTextbox(boolean flag){
baModelTypeLabel.setVisible(!flag);
baModelTypeTextbox.setVisible(flag);
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
public void clearTextbox(){
baModelTypeLabel.setValue("");
baModelTypeTextbox.setValue("");
remarkLabel.setValue("");
remarkTextbox.setValue("");
}
public void setBaModelTypeValue(BaModelType baModelType){
baModelType.setBaModelType(baModelTypeTextbox.getValue());
baModelType.setRemark(remarkTextbox.getValue());
}
public void setBaModelTypeData(BaModelType baModelType){
baModelTypeLabel.setValue(baModelType.getBaModelType());
baModelTypeTextbox.setValue(baModelType.getBaModelType());
remarkLabel.setValue(baModelType.getRemark());
remarkTextbox.setValue(baModelType.getRemark());
}
public void onSelect$baModelTypeListbox() throws SuspendNotAllowedException, InterruptedException {
edit = -1;
baModelType = getSelectItem().getValue();
clearTextbox();
setBaModelTypeData(baModelType);
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
if (baModelTypeTextbox.getValue().equals("")) {
baModelTypeTextbox.focus();
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
baModelType = new BaModelType();
setBaModelTypeValue(baModelType);
baModelType.setCreateDate(date);
baModelType.setCreateUser(user);
baModelType.setUpdateDate(date);
baModelType.setUpdateUser(user);
baModelTypeService.add(baModelType);
} else {
setBaModelTypeValue(baModelType);
baModelType.setUpdateDate(date);
baModelType.setUpdateUser(user);
baModelTypeService.update(baModelType);
}
onClick$cancelButton() ;
initData();
} catch (CreateException e) {
log.error("baModelTypeWindow", e);
} catch (UpdateException e) {
log.error("baModelTypeWindow", e);
}
}

class BaModelTypeRenderer implements ListitemRenderer {
public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
BaModelType baModelType = (BaModelType) arg1;
Listcell baModelTypeCell = new Listcell();
baModelTypeCell.setParent(arg0);
new Label(baModelType.getBaModelType()).setParent(baModelTypeCell);
Listcell remarkCell = new Listcell();
remarkCell.setParent(arg0);
new Label(baModelType.getRemark()).setParent(remarkCell);
arg0.setValue(baModelType);
}
}
}
