//package com.jyd.bms.window.business;
//package com.jyd.bms.window.department;
//
//import java.beans.IntrospectionException;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.zkoss.zk.ui.Session;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.SuspendNotAllowedException;
//import org.zkoss.zk.ui.WrongValueException;
//import org.zkoss.zkplus.spring.SpringUtil;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Filedownload;
//import org.zkoss.zul.Intbox;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.ListModelSet;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Tree;
//import org.zkoss.zul.Treecell;
//import org.zkoss.zul.Treechildren;
//import org.zkoss.zul.Treeitem;
//import org.zkoss.zul.Treerow;
//
//import com.jyd.bms.bean.Department;
//import com.jyd.bms.bean.EducationType;
//import com.jyd.bms.bean.Employee;
//import com.jyd.bms.bean.Menu;
//import com.jyd.bms.bean.PositionType;
//import com.jyd.bms.bean.Staff;
//import com.jyd.bms.bean.Store;
//import com.jyd.bms.bean.User;
//import com.jyd.bms.components.DepartmentChoose;
//import com.jyd.bms.components.EmployeeChoose;
//import com.jyd.bms.components.PositionListbox;
//import com.jyd.bms.components.StoreListbox;
//import com.jyd.bms.dto.DepartmentDTO;
//import com.jyd.bms.dto.EmployeeDTO;
//import com.jyd.bms.service.DepartmentService;
//import com.jyd.bms.service.EducationTypeService;
//import com.jyd.bms.service.EmployeeService;
//import com.jyd.bms.service.PositionTypeService;
//import com.jyd.bms.service.StaffService;
//import com.jyd.bms.service.StoreService;
//import com.jyd.bms.tool.DownloadUtil;
//import com.jyd.bms.tool.exception.CreateException;
//import com.jyd.bms.tool.exception.DAOException;
//import com.jyd.bms.tool.exception.DataNotFoundException;
//import com.jyd.bms.tool.exception.DeleteException;
//import com.jyd.bms.tool.exception.UpdateException;
//import com.jyd.bms.tool.report.JxlsUtil;
//import com.jyd.bms.tool.zk.BaseWindow;
//import com.jyd.bms.tool.zk.Clients;
//import com.jyd.bms.tool.zk.Listbox;
//import com.jyd.bms.tool.zk.Messagebox;
//import com.jyd.bms.tool.zk.UserSession;
//
///**
// * @category 部门窗口
// * @author mjy
// */
//@SuppressWarnings("serial")
//public class DepartmentEditWindow extends BaseWindow {
//
//	private static final Logger log = LoggerFactory.getLogger(DepartmentEditWindow.class);
//
//	/*
//	 * 门店
//	 */
//	private Label storeLabel;
//	private StoreListbox storeListbox;
//	private StoreService storeService;
//	private Store store;
//
//	private List<DepartmentDTO> deptDTOList = new ArrayList<DepartmentDTO>();
//	private List<DepartmentDTO> tempDTOList = new ArrayList<DepartmentDTO>();
//	private List<DepartmentDTO> searchDTOList = new ArrayList<DepartmentDTO>();
//	/*
//	 * 部门Label定义
//	 */
//	private Label departmentNameLabel;
//	private Label departmentIdLabel;
//	private Label departmentParentIdLabel;
//	private Label departmentSuperVisorLabel;
//	private Label departmentRemrakLabel;
//	/*
//	 * 部门Box定义
//	 */
//	private Textbox departmentNameTextbox;
//	private Textbox departmentIdTextbox;
//	private Listbox staffListbox;
//	private EmployeeChoose employeeChoose;
//	private Textbox departmentRemarkTextbox;
//	private Textbox conditionTextbox;
//	/*
//	 * 员工Label
//	 */
//	private Label staffDepartPositionNameLabel;
//	private Label staffNumsLabel;
//	private Label staffRemarkLabel;
//	/*
//	 * 员工Box
//	 */
//	private Textbox staffRemarkTextbox;
//	private PositionListbox positionListbox;
//	private Intbox staffNumsIntbox;
//	/* 部门按钮定义 */
//
//	private Button addButton;
//	private Button editButton;
//	private Button cancerButton;
//	private Button saveButton;
//	private Button delButton;
//
//	/* 编制按钮定义 */
//	private Button addStaffButton;
//	private Button editStaffButton;
//	private Button delStaffButton;
//	private Button cancelStaffButton;
//	private Button saveStaffButton;
//
//	private Tree departmentTree;
//	private DepartmentService departmentService;
//	private Department department;
//	private Staff staff;
//	private Set<Staff> staffSet = new HashSet<Staff>();
//	private DepartmentChoose departmentChoose;
//	private PositionTypeService positiontypeService;
//	private EducationTypeService educationTypeService;
//	private StaffService staffService;
//	// public List<Department> listDepartmentAll = new ArrayList<Department>();
//	// public List<Department> listParentAll = new ArrayList<Department>();
//	// private List<Department> lstSearchDepart = new ArrayList<Department>();
//	private Set<Staff> deleteStaffSet = new HashSet<Staff>();
//	private PositionType positionType;
//	private int departmentId = 0;
//	private int staffId = 0;
//	private EmployeeService employeeService;
//
//	public Store getStore() {
//		return store;
//	}
//
//	public void setStore(Store store) {
//		this.store = store;
//	}
//
//	public DepartmentEditWindow() {
//		super.menuId = "hr_department";
//	}
//
//	@Override
//	public void initUI() {
//		departmentService = getBean("DepartmentService");
//		positiontypeService = getBean("PositionTypeService");
//		staffService = getBean("StaffService");
//		employeeService = getBean("EmployeeService");
//		storeService = getBean("StoreService");
//		educationTypeService = getBean("EducationTypeService");
//
//		employeeChoose.setEmployeeService(employeeService);
//		departmentChoose.setDepartmentService(departmentService);
//		staffListbox.setItemRenderer(new StaffListboxListRenderer());
//		positionListbox.setPositionTypeService(positiontypeService);
//		storeListbox.setStoreService(storeService);
//	}
//
//	@Override
//	public void initData() {
//		try {
//			storeListbox.initComponent();
//			positionListbox.initComponent();
//			employeeChoose.initComponent();
//			departmentChoose.initComponent();
//			loadingParent(store);
//		} catch (DataNotFoundException e) {
//			log.error("DepartmentEditWindow", e);
//		} catch (DAOException e) {
//			log.error("DepartmentEditWindow", e);
//		}
//	}
//
//	public void onClick$freshButton() {
//		try {
//			departmentService.updateNumberDepartments();
//			initData();
//		} catch (DAOException e) {
//			log.error("DepartmentEditWindow", e);
//		} catch (UpdateException e) {
//			log.error("DepartmentEditWindow", e);
//		} catch (DataNotFoundException e) {
//			log.error("DepartmentEditWindow", e);
//		}
//	}
//
//	@SuppressWarnings("rawtypes")
//	class StaffListboxListRenderer implements ListitemRenderer {
//		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
//			Staff staff = (Staff) arg1;
//
//			PositionTypeService positionTypeService = (PositionTypeService) SpringUtil.getBean("PositionTypeService");
//			PositionType positionType = positionTypeService.getById(staff.getPosition().getId());
//			Listcell PositionNameCell = new Listcell();
//			PositionNameCell.setParent(arg0);
//			new Label(positionType == null ? "" : positionType.getPositionType()).setParent(PositionNameCell);
//
//			Listcell NumsCell = new Listcell();
//			NumsCell.setParent(arg0);
//			new Label(String.valueOf(staff.getNums())).setParent(NumsCell);
//
//			Listcell RemarkCell = new Listcell();
//			RemarkCell.setParent(arg0);
//			new Label(staff.getRemark()).setParent(RemarkCell);
//
//			Listcell CreateUserCell = new Listcell();
//			CreateUserCell.setParent(arg0);
//			new Label(staff.getCreateUser()).setParent(CreateUserCell);
//
//			Listcell UpadteUserCell = new Listcell();
//			UpadteUserCell.setParent(arg0);
//			new Label(staff.getUpdateUser()).setParent(UpadteUserCell);
//
//			Listcell CreateDateCell = new Listcell();
//			CreateDateCell.setParent(arg0);
//			new Label(staff.getCreateDate().toString()).setParent(CreateDateCell);
//
//			Listcell UpadteDateCell = new Listcell();
//			UpadteDateCell.setParent(arg0);
//			new Label(staff.getUpdateDate().toString()).setParent(UpadteDateCell);
//
//			arg0.setValue(staff);
//		}
//	}
//
//	/**
//	 * 载树
//	 * 
//	 * @param store
//	 * @throws DataNotFoundException
//	 * @throws DAOException
//	 */
//	public void loadingParent(Store store) throws DataNotFoundException, DAOException {
//		deptDTOList.clear();
//		if (store != null) {
//			// listParentAll = departmentService.getDepartmentByStore(store);
//			deptDTOList = departmentService.getDepartmentDTOByStore(store);
//		} else {
//			deptDTOList = departmentService.getDepartmentDTOAll();
//		}
//		loadingTree();
//	}
//
//	private void loadingTree() throws DataNotFoundException {
//		departmentTree.clear();
//		tempDTOList.clear();
//		List<DepartmentDTO> rootDeptDTOList = new ArrayList<DepartmentDTO>();
//		for (DepartmentDTO department : deptDTOList) {
//			if (department.getParentDeptDTO() == null) {
//				rootDeptDTOList.add(department);
//			} else {
//				tempDTOList.add(department);
//			}
//		}
//
//		if (departmentTree.getTreechildren() == null) {
//			new org.zkoss.zul.Treechildren().setParent(departmentTree);
//		}
//		for (DepartmentDTO department : rootDeptDTOList) {
//			createTreeitem(department, departmentTree.getTreechildren());
//		}
//		departmentTree.clearSelection();
//	}
//
//	private void createTreeitem(DepartmentDTO department, Treechildren tc) throws DataNotFoundException {
//		Treeitem treeitem = new Treeitem();
//		showTreeItem(treeitem, department);
//		Treechildren treechildren = treeitem.getTreechildren();
//		for (DepartmentDTO temp : deptDTOList) {
//			if (temp.getParentDeptDTO() != null) {
//				if (temp.getParentDeptDTO().intValue() == department.getId()) {
//					if (treechildren == null) {
//						treechildren = new org.zkoss.zul.Treechildren();
//						treechildren.setParent(treeitem);
//					}
//					createTreeitem(temp, treechildren);
//				}
//			}
//		}
//		treeitem.setParent(tc);
//	}
//
//	private void createSearchTree() throws DataNotFoundException {
//		if (departmentTree.getTreechildren() == null) {
//			new org.zkoss.zul.Treechildren().setParent(departmentTree);
//		}
//		for (DepartmentDTO dept : searchDTOList) {
//			Treeitem treeitem = new Treeitem();
//			showTreeItem(treeitem, dept);
//			treeitem.setParent(departmentTree.getTreechildren());
//		}
//	}
//
//	private void showTreeItem(Treeitem item, DepartmentDTO department) throws DataNotFoundException {
//		Treerow tr = item.getTreerow();
//		if (tr == null) {
//			tr = new Treerow();
//			tr.setParent(item);
//		}
//		new Treecell(department.getDeptName()).setParent(tr);
//		new Treecell(department.getSupervisor()).setParent(tr);
//		new Treecell(String.valueOf(department.getActualNums())).setParent(tr);
//		new Treecell(String.valueOf(department.getTotalActualNums())).setParent(tr);
//		new Treecell(String.valueOf(department.getStaffNums())).setParent(tr);
//		new Treecell(String.valueOf(department.getTotalStaffNums())).setParent(tr);
//		new Treecell(department.getStoreName()).setParent(tr);
//		new Treecell(department.getRemark()).setParent(tr);
//		item.setValue(department);
//	}
//
//	public void enableStaffButton(String type) {
//		if (type.equals("add")) {
//			addStaffButton.setVisible(false);
//			editStaffButton.setVisible(false);
//			saveStaffButton.setVisible(true);
//			cancelStaffButton.setVisible(true);
//			delStaffButton.setVisible(false);
//		}
//		if (type.equals("update")) {
//			addStaffButton.setVisible(false);
//			editStaffButton.setVisible(false);
//			saveStaffButton.setVisible(true);
//			cancelStaffButton.setVisible(true);
//			delStaffButton.setVisible(true);
//		}
//		if (type.equals("cancel")) {
//			addStaffButton.setVisible(true);
//			editStaffButton.setVisible(false);
//			saveStaffButton.setVisible(false);
//			cancelStaffButton.setVisible(false);
//			delStaffButton.setVisible(false);
//		}
//		if (type.equals("save")) {
//			addStaffButton.setVisible(true);
//			editStaffButton.setVisible(false);
//			saveStaffButton.setVisible(false);
//			cancelStaffButton.setVisible(false);
//			delStaffButton.setVisible(false);
//		}
//		if (type.equals("select")) {
//			addStaffButton.setVisible(true);
//			editStaffButton.setVisible(true);
//			saveStaffButton.setVisible(false);
//			cancelStaffButton.setVisible(false);
//			delStaffButton.setVisible(true);
//		}
//
//	}
//
//	public void enableButton(String type) {
//		if (type.equals("add")) {
//			addButton.setVisible(false);
//			editButton.setVisible(false);
//			delButton.setVisible(false);
//			saveButton.setVisible(true);
//			cancerButton.setVisible(true);
//		}
//		if (type.equals("update")) {
//			addButton.setVisible(false);
//			editButton.setVisible(false);
//			delButton.setVisible(false);
//			saveButton.setVisible(true);
//			cancerButton.setVisible(true);
//		}
//		if (type.equals("cancel")) {
//			addButton.setVisible(true);
//			delButton.setVisible(false);
//			editButton.setVisible(false);
//			saveButton.setVisible(false);
//			cancerButton.setVisible(false);
//		}
//		if (type.equals("save")) {
//			addButton.setVisible(true);
//			editButton.setVisible(false);
//			saveButton.setVisible(false);
//			delButton.setVisible(false);
//			cancerButton.setVisible(false);
//		}
//		if (type.equals("select")) {
//			addButton.setVisible(true);
//			editButton.setVisible(true);
//			delButton.setVisible(true);
//			saveButton.setVisible(false);
//			cancerButton.setVisible(false);
//		}
//	}
//
//	public void enableDepartmentTextbox(boolean flag) {
//		storeLabel.setVisible(!flag);
//		storeListbox.setVisible(flag);
//		departmentNameLabel.setVisible(!flag);
//		departmentIdLabel.setVisible(!flag);
//		departmentParentIdLabel.setVisible(!flag);
//		departmentSuperVisorLabel.setVisible(!flag);
//		departmentRemrakLabel.setVisible(!flag);
//		departmentNameTextbox.setVisible(flag);
//		employeeChoose.setVisible(flag);
//		departmentRemarkTextbox.setVisible(flag);
//		departmentChoose.setVisible(flag);
//	}
//
//	public void enableStaffTextbox(boolean flag) {
//		staffRemarkTextbox.setVisible(flag);
//		staffNumsIntbox.setVisible(flag);
//		positionListbox.setVisible(flag);
//		staffDepartPositionNameLabel.setVisible(!flag);
//		staffRemarkLabel.setVisible(!flag);
//		staffNumsLabel.setVisible(!flag);
//	}
//
//	public void clearDepartmentData() {
//		storeLabel.setValue("");
//		departmentNameTextbox.setValue("");
//		departmentIdTextbox.setValue("");
//		employeeChoose.setValue("");
//		departmentRemarkTextbox.setValue("");
//		departmentChoose.setValue("");
//		departmentNameLabel.setValue("");
//		departmentIdLabel.setValue("");
//		departmentParentIdLabel.setValue("");
//		departmentSuperVisorLabel.setValue("");
//		departmentRemrakLabel.setValue("");
//	}
//
//	public void clearStaffData() {
//		staffRemarkTextbox.setValue("");
//		staffNumsIntbox.setValue(0);
//		positionListbox.setSelectedIndex(0);
//		staffDepartPositionNameLabel.setValue("");
//		staffNumsLabel.setValue("");
//		staffRemarkLabel.setValue("");
//	}
//
//	public void setDepartmentData(Department department) throws DAOException, DataNotFoundException {
//		departmentNameLabel.setValue(department.getDepartmentName());
//		departmentNameTextbox.setValue(department.getDepartmentName());
//
//		Store store = department.getStore();
//		if (store != null) {
//			store = storeService.getById(store.getId());
//		}
//		storeLabel.setValue(store == null ? "" : store.getShortName());
//		storeListbox.setStore(department.getStore());
//
//		departmentIdLabel.setValue(String.valueOf(department.getId()));
//		Department deptParent = department.getParentDepartment();
//		if (deptParent != null) {
//			deptParent = departmentService.getById(deptParent.getId());
//		}
//		departmentParentIdLabel.setValue(deptParent == null ? "" : deptParent.getDepartmentName());
//		departmentChoose.setDepartment(deptParent);
//
//		Employee emp = department.getDeparmentSupervisor();
//		if (emp != null) {
//			emp = employeeService.getById(emp.getId());
//		}
//		employeeChoose.setEmployee(emp);
//		departmentSuperVisorLabel.setValue(emp == null ? "" : emp.getName());
//
//		departmentRemrakLabel.setValue(department.getRemark());
//		departmentRemarkTextbox.setValue(department.getRemark());
//
//	}
//
//	public void setStaffData(Staff staff) throws DataNotFoundException {
//		PositionTypeService positionTypeService = (PositionTypeService) SpringUtil.getBean("PositionTypeService");
//		PositionType positionType = positionTypeService.getById(staff.getPosition().getId());
//		staffDepartPositionNameLabel.setValue(positionType.getPositionType());
//		positionListbox.setPositionType(positionType);
//
//		staffRemarkLabel.setValue(staff.getRemark());
//		staffRemarkTextbox.setValue(staff.getRemark());
//
//		staffNumsLabel.setValue(String.valueOf(staff.getNums()));
//		staffNumsIntbox.setValue(staff.getNums());
//	}
//
//	public void onSelect$positionTypeListbox() throws SuspendNotAllowedException, InterruptedException {
//		positionType = (PositionType) positionListbox.getSelectedValue();
//	}
//
//	public void onSelect$staffListbox() throws SuspendNotAllowedException, InterruptedException, DataNotFoundException {
//		if (departmentId != -1) {
//			staffListbox.clearSelection();
//			return;
//		}
//		staff = staffListbox.getSelectedItem().getValue();
//		setStaffData(staff);
//		enableStaffTextbox(false);
//		enableStaffButton("select");
//	}
//
//	public void onSelect$departmentTree() throws SuspendNotAllowedException, InterruptedException {
//		try {
//			employeeChoose.setEmployee(null);
//			DepartmentDTO deptDTO = (DepartmentDTO) departmentTree.getSelectedItem().getValue();
//			department = departmentService.getById(deptDTO.getId());
//			setDepartmentData(department);
//			enableDepartmentTextbox(false);
//			enableStaffTextbox(false);
//			enableButton("select");
//			departmentId = 0;
//			enableStaffButton("save");
//			addStaffButton.setVisible(false);
//			staffSet.clear();
//
//			departmentService.refresh(department);
//			staffSet = department.getStaffs();
//			staffListbox.setModel(new ListModelSet<Staff>(staffSet, true));
//			deleteStaffSet.clear();
//			clearStaffData();
//		} catch (Exception e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("获取部门表数据出错了!");
//		}
//	}
//
//	/**
//	 * 部门增加
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$addButton() throws SuspendNotAllowedException, InterruptedException {
//		departmentId = 0;
//		employeeChoose.setEmployee(null);
//		enableDepartmentTextbox(true);
//		enableButton("add");
//		clearDepartmentData();
//		clearStaffData();
//		staffSet.clear();
//		staffListbox.setModel(new ListModelList<Staff>());
//		departmentNameTextbox.focus();
//		addStaffButton.setVisible(true);
//	}
//
//	/**
//	 * 部门编辑
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$editButton() throws SuspendNotAllowedException, InterruptedException {
//		departmentId = -1;
//		enableDepartmentTextbox(true);
//		enableButton("update");
//		departmentNameTextbox.focus();
//		addStaffButton.setVisible(true);
//	}
//
//	public boolean checkDeptActNum() {
//		if (department.getActualNums() <= department.getStaffNums()
//				&& department.getTotalActualNums() <= department.getTotalStaffNums()) {
//			return true;
//		}
//		return false;
//	}
//
//	/**
//	 * 部门保存
//	 * 
//	 * @throws Exception
//	 */
//	public void onClick$saveButton() throws Exception {
//		User user = UserSession.getUser();
//		if (user == null) {
//			Messagebox.show("长时间未操作，出于安全考虑，请重新登陆！");
//			return;
//		}
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		String userName = user.getLoginName();
//		try {
//			if (departmentNameTextbox.getValue().equals("")) {
//				Messagebox.error("部门名称不能为空）：");
//				return;
//			}
//			if (departmentId == 0) {
//				department = new Department();
//				department.setStore(storeListbox.getStore());
//				department.setDepartmentName(departmentNameTextbox.getValue());
//				department.setParentDepartment(departmentChoose.getDepartment());
//				department.setDeparmentSupervisor(employeeChoose.getEmployee());
//				department.setRemark(departmentRemarkTextbox.getValue());
//				department.setCreateUser(userName);
//				department.setCreateDate(date);
//				department.setUpdateUser(userName);
//				department.setUpdateDate(date);
//				Iterator<Staff> it = staffSet.iterator();
//				while (it.hasNext()) {
//					Staff value = it.next();
//					value.setDepartment(department);
//					value.setCreateDate(date);
//					value.setCreateUser(userName);
//					value.setUpdateDate(date);
//					value.setUpdateUser(userName);
//				}
//				department.setStaffs(staffSet);
//				if (!checkDeptActNum()) {
//					Messagebox.show("实际人数不能大于编制人数！");
//					return;
//				}
//				departmentService.addDept(department);
//			} else {
//				department = departmentService.getById(department.getId());
//				Iterator<Staff> deleteIt = deleteStaffSet.iterator();
//				while (deleteIt.hasNext()) {
//					Staff value = deleteIt.next();
//					Staff s = staffService.getById(value.getId());
//					staffService.delete(s);
//				}
//				department.setStore(storeListbox.getStore());
//				department.setDepartmentName(departmentNameTextbox.getValue());
//				department.setDeparmentSupervisor(employeeChoose.getEmployee());
//				department.setRemark(departmentRemarkTextbox.getValue());
//				department.setParentDepartment(departmentChoose.getDepartment());
//				department.setUpdateUser(user.getUserName());
//				department.setUpdateDate(date);
//
//				Iterator<Staff> it = staffSet.iterator();
//				while (it.hasNext()) {
//					Staff value = it.next();
//					if (value.getId() == 0) {
//						value.setDepartment(department);
//						value.setCreateDate(date);
//						value.setCreateUser(userName);
//					}
//					value.setUpdateDate(date);
//					value.setUpdateUser(userName);
//				}
//				department.setStaffs(staffSet);
//				if (!departmentChoose.checkCirculate(department)) {
//					Messagebox.show("数据异常）：自己不能选择自己哦！");
//					return;
//				}
//				if (!checkDeptActNum()) {
//					Messagebox.show("实际人数不能大于编制人数！");
//				}
//				departmentService.update(department);
//				departmentService.updateNumberDepartments();
//			}
//		} catch (CreateException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("保存出错了");
//		} catch (UpdateException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("修改出错了");
//		} catch (WrongValueException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("查询出错了");
//		} catch (DataNotFoundException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("数据错误了");
//		} catch (DeleteException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("删除错误了");
//		}
//		departmentChoose.initComponent();
//		loadingParent(store);
//		enableButton("save");
//		addStaffButton.setVisible(false);
//		init();
//	}
//
//	/**
//	 * 部门删除，只删除为空的部门．和
//	 * 
//	 * @throws DataNotFoundException
//	 * @throws DAOException
//	 */
//	public void onClick$delButton() throws DAOException, DataNotFoundException {
//		if (department.getActualNums() != 0 || department.getTotalActualNums() != 0) {
//			Messagebox.show("当前部门存在员工不能删除！或者请将员工转于其他部门再操作.");
//			return;
//		}
//		try {
//			departmentService.deleteDept(department);
//			departmentChoose.initComponent();
//			loadingParent(store);
//			enableButton("save");
//			addStaffButton.setVisible(false);
//			init();
//		} catch (DeleteException | UpdateException e) {
//			Messagebox.error("删除部门出错！");
//			log.error("DepartmentWindow", e);
//		}
//	}
//
//	/**
//	 * 部门取消
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$cancerButton() throws SuspendNotAllowedException, InterruptedException {
//		departmentId = 0;
//		staffListbox.clearSelection();
//		enableButton("cancel");
//		enableDepartmentTextbox(false);
//		clearDepartmentData();
//		enableStaffButton("cancel");
//		addStaffButton.setVisible(false);
//	}
//
//	/**
//	 * 编制增加
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$addStaffButton() throws SuspendNotAllowedException, InterruptedException {
//		staffId = 0;
//		enableStaffTextbox(true);
//		enableStaffButton("add");
//		clearStaffData();
//		staffListbox.focus();
//	}
//
//	/**
//	 * 编制编辑
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$editStaffButton() throws SuspendNotAllowedException, InterruptedException {
//		staffId = -1;
//		enableStaffTextbox(true);
//		enableStaffButton("update");
//		staffListbox.focus();
//	}
//
//	/**
//	 * 编制删除
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 * @throws DAOException
//	 * @throws UpdateException
//	 */
//	public void onClick$delStaffButton()
//			throws SuspendNotAllowedException, InterruptedException, DAOException, UpdateException {
//		if (staff.getId() != 0) {
//			deleteStaffSet.add(staff);
//		}
//		staffSet.remove(staff);
//		staffListbox.setModel(new ListModelSet<Staff>(staffSet, true));
//		enableStaffButton("save");
//		clearStaffData();
//	}
//
//	public boolean checkById() {
//		boolean flag = false;
//		Iterator<Staff> staffIt = staffSet.iterator();
//		while (staffIt.hasNext()) {
//			Staff value = staffIt.next();
//			if (value.getPosition().getId() == staff.getPosition().getId()) {
//				flag = true;
//			}
//		}
//		return flag;
//	}
//
//	/**
//	 * 编制保存
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 * @throws IntrospectionException
//	 * @throws InvocationTargetException
//	 * @throws IllegalArgumentException
//	 * @throws IllegalAccessException
//	 */
//	public void onClick$saveStaffButton() throws SuspendNotAllowedException, InterruptedException,
//			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		User user = UserSession.getUser();
//		String userName = user.getLoginName();
//		if (staffId == 0) {
//			staff = new Staff();
//			staff.setPosition(positionListbox.getPositionType());
//			staff.setDepartment(department);
//			staff.setNums(staffNumsIntbox.getValue() == null ? 0 : staffNumsIntbox.getValue());
//			staff.setRemark(staffRemarkTextbox.getValue());
//			staff.setCreateDate(date);
//			staff.setUpdateDate(date);
//			staff.setCreateUser(userName);
//			staff.setUpdateUser(userName);
//			if (checkById()) {
//				Messagebox.show("当前职位已经存在");
//				return;
//			}
//			staffSet.add(staff);
//		} else {
//			staff.setPosition(positionListbox.getPositionType());
//			staff.setDepartment(department);
//			staff.setNums(staffNumsIntbox.getValue() == null ? 0 : staffNumsIntbox.getValue());
//			staff.setRemark(staffRemarkTextbox.getValue());
//			staff.setUpdateDate(date);
//			staff.setUpdateUser(userName);
//		}
//		staffListbox.setModel(new ListModelSet<Staff>(staffSet, true));
//		clearStaffData();
//		enableStaffButton("save");
//		enableStaffTextbox(false);
//	}
//
//	/**
//	 * 编制取消
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$cancelStaffButton() throws SuspendNotAllowedException, InterruptedException {
//		enableStaffButton("cancel");
//		staffListbox.clearSelection();
//		enableStaffTextbox(false);
//		clearStaffData();
//	}
//
//	/**
//	 * 搜索
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 * @throws DataNotFoundException
//	 * @throws DAOException
//	 */
//	public void onClick$searchButton()
//			throws SuspendNotAllowedException, InterruptedException, DataNotFoundException, DAOException {
//		String condition = conditionTextbox.getValue();
//		searchDTOList.clear();
//		departmentTree.clear();
//		if (condition.equals("")) {
//			loadingParent(store);
//			return;
//		}
//		for (DepartmentDTO department : deptDTOList) {
//			if (department.getDeptName().toUpperCase().indexOf(condition.toUpperCase()) > -1
//					|| (department.getSupervisor() == null ? "" : department.getSupervisor()).toUpperCase()
//							.indexOf(condition.toUpperCase()) > -1) {
//				searchDTOList.add(department);
//			}
//		}
//		if (searchDTOList.size() == 1) {
//			conditionTextbox.setValue(searchDTOList.get(0).getDeptName());
//			conditionTextbox.setFocus(true);
//			createSearchTree();
//		} else if (searchDTOList.size() == 0) {
//			conditionTextbox.setFocus(true);
//			Messagebox.show("" + condition + "不存在");
//			return;
//		} else {
//			conditionTextbox.setFocus(true);
//			createSearchTree();
//		}
//	}
//
//	/**
//	 * 清屏
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$clsButton() throws SuspendNotAllowedException, InterruptedException {
//		init();
//	}
//
//	/**
//	 * 导出Excel
//	 * 
//	 * @throws DataNotFoundException
//	 */
//	public void onClick$excelButton() throws DataNotFoundException {
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//		String dateString = formatter.format(date);
//		boolean tag = false;
//		List<Employee> list = new ArrayList<Employee>();
//		List<EmployeeDTO> tempList = new ArrayList<EmployeeDTO>();
//		try {
//			if (department == null) {
//				Messagebox.error("请选择需要导出的部门！");
//				return;
//			}
//
//			if (departmentTree.getSelectedItem().getTreechildren() != null) {
//				if (Messagebox.show("是否导出下级部门?", "系统提示", Messagebox.OK | Messagebox.CANCEL,
//						Messagebox.QUESTION) == Messagebox.OK) {
//					tag = true;
//				}
//			}
//			list = departmentService.getEmpByDept(department, tag);
//
//			// 从数据库查询
//			for (Employee emp : list) {
//				Department dept = departmentService.getById(emp.getDepartment().getId());
//				Store store = storeService.getById(dept.getStore().getId());
//				PositionType positionType = positiontypeService.getById(emp.getPosition().getId());
//				EducationType educationType = educationTypeService.getById(emp.getEducationBackground().getId());
//				EmployeeDTO empDTO = new EmployeeDTO(emp.getName(), dept.getDepartmentName(), store.getShortName(),
//						emp.getSexString(), emp.getIDNo(), positionType.getPositionType(), emp.getBirthday(),
//						emp.getEntryDate(), emp.getObtainmentDate(), emp.getFertilityString(), emp.getPoliticalStatus(),
//						educationType.getEducationType(), emp.getGraduateSchool(), emp.getMajor(),
//						emp.getGraduationDate(), emp.getWorkDate(), emp.getNativePlace(),
//						emp.getHouseholdRegistionPlace(), emp.getCurrentAdress(), emp.getContactInformation(),
//						emp.getEmail(), emp.getBankCardNo(), emp.getReferees(), emp.getRemark());
//				tempList.add(empDTO);
//			}
//
//		} catch (DAOException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("获取部门表数据出错了!");
//		}
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		String templateFile, outputFile;
//		templateFile = getTemplates() + "/employee.xls";
//		outputFile = getOutput() + dateString + department.getDepartmentName().replace("/", "_").replace("\\", "_")
//				+ ".xls";
//		File filet = new File(getOutput());
//		if (!filet.exists()) {
//			filet.mkdirs();
//		}
//		map.put("list", tempList);
//		try {
//			JxlsUtil.exportExcel(templateFile, outputFile, map);
//		} catch (FileNotFoundException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("获取文件失败！当前不存在实际人数数据．故不能导出报表！");
//		} catch (IOException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("IO流异常！");
//		}
//		File file = new File(outputFile);
//		try {
//			Filedownload.save(file, DownloadUtil.MIME_TYPES.getContentType(outputFile));
//		} catch (FileNotFoundException e) {
//			log.error("DepartmentWindow", e);
//			Messagebox.error("获取文件失败！");
//		}
//	}
//
//	public void init() {
//		staffSet.clear();
//		deleteStaffSet.clear();
//		enableButton("save");
//		addStaffButton.setVisible(false);
//		departmentTree.clearSelection();
//		staffListbox.setModel(new ListModelSet<Staff>());
//		departmentChoose.setDepartment(null);
//		clearStaffData();
//		clearDepartmentData();
//		enableStaffTextbox(false);
//		enableDepartmentTextbox(false);
//	}
//
//	/**
//	 * 查看部门员工
//	 */
//	public void onClick$testButton() {
//		if (department == null) {
//			Messagebox.error("请选择要查看的部门！");
//			return;
//		}
//		int id = department.getId();
//		String dptName = department.getDepartmentName();
//		Menu menu = new Menu();
//		menu.setMenuUrl("/hr/departmentEmployee.zul?dptId=" + id + "");
//		menu.setName("查看" + dptName + "部门员工");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript("ShowUrl()");
//	}
//
//}