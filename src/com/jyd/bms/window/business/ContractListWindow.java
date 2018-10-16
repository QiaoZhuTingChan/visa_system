//package com.jyd.bms.window.contract;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Session;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.SuspendNotAllowedException;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.util.Clients;
//import org.zkoss.zkplus.spring.SpringUtil;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.ListModelList;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;
//import org.zkoss.zul.South;
//import org.zkoss.zul.Textbox;
//
//import com.jyd.bms.bean.BaProductClassify;
//import com.jyd.bms.bean.ContractVersion;
//import com.jyd.bms.bean.CustomerContract;
//import com.jyd.bms.bean.Menu;
//import com.jyd.bms.bean.Product;
//import com.jyd.bms.bean.RepaymentStage;
//import com.jyd.bms.bean.RepaymentType;
//import com.jyd.bms.bean.Store;
//import com.jyd.bms.common.Environment;
//import com.jyd.bms.common.StaticVariable;
//import com.jyd.bms.components.BaProductClassifyListbox;
//import com.jyd.bms.contract.print.ContractInterface;
//import com.jyd.bms.service.BaProductClassifyService;
//import com.jyd.bms.service.ContractVersionService;
//import com.jyd.bms.service.CustomerContractService;
//import com.jyd.bms.service.ProductService;
//import com.jyd.bms.service.RepaymentStageService;
//import com.jyd.bms.service.RepaymentTypeService;
//import com.jyd.bms.service.StoreService;
//import com.jyd.bms.tool.exception.DAOException;
//import com.jyd.bms.tool.exception.DataNotFoundException;
//import com.jyd.bms.tool.exception.UpdateException;
//import com.jyd.bms.tool.zk.BaseWindow;
//import com.jyd.bms.tool.zk.ContinueProcessAfterComponentCreated;
//import com.jyd.bms.tool.zk.Executions;
//import com.jyd.bms.tool.zk.GridPaging;
//import com.jyd.bms.tool.zk.Messagebox;
//import com.jyd.bms.tool.zk.PagingControlComponentModelList;
//import com.jyd.bms.window.admin.GroupMenuWindow;
//
///**
// * @category 合同列表
// * @author mjy
// */
//public class ContractListWindow extends BaseWindow implements ContinueProcessAfterComponentCreated {
//	private BaProductClassifyListbox productClassifyListbox;
//	private static final long serialVersionUID = -623337974957672295L;
//	private static final Logger log = LoggerFactory.getLogger(ContractListWindow.class);
//	private Textbox conditionTextbox;
//	private Listbox contractListbox;
//	private Listbox stateListbox;
//	private South southPaging;
//
//	private int editType;
//	private String condition = "";
//	private CustomerContract contract;
//	private CustomerContractService contractService;
//	private BaProductClassifyService productClassifyService;
//	private GridPaging gridPaging;
//	
//	private BaProductClassify productClassify = null;
//	private Integer state = -2;
//
//	private static final int stageNum = Environment.getStageNum();
//
//	public ContractListWindow() {
//		this.menuId = "bu_contract";
//	}
//
//	@Override
//	public void processAfterComponentCreated(Component component) {
//		// if (component instanceof ContractEditWindow) {
//		// ContractEditWindow editWindow = (ContractEditWindow) component;
//		// editWindow.setContract(contract);
//		// editWindow.setMenuId(this.menuId);
//		// editWindow.setEditType(editType);
//		// }
//		// if (component instanceof FileUploadWindow) {
//		// FileUploadWindow editWindow = (FileUploadWindow) component;
//		// editWindow.setContract(contract);
//		// }
//		// if (component instanceof FileListWindow) {
//		// FileListWindow editWindow = (FileListWindow) component;
//		// editWindow.setContractId(contract.getId());
//		// }
//		// if (component instanceof ContractOverEditWindow) {
//		// ContractOverEditWindow overWindow = (ContractOverEditWindow) component;
//		// overWindow.setCustomerContract(contract);
//		// overWindow.setMenuId(this.menuId);
//		// }
//		
//		if (component instanceof ContractDetailsWindow) {
//			ContractDetailsWindow contractDetailsWindow = (ContractDetailsWindow) component;
//			contractDetailsWindow.setCont(contract);
//		}
//	}
//
//	@Override
//	public void initUI() {
//		contractService = getBean("CustomerContractService");
//		productClassifyService = getBean("BaProductClassifyService");
//		contractListbox.setItemRenderer(new contractListRenderer());
//		contractListbox.addForward("onSelect", (Component) null, "onSelectContract", null);
//
//		stateListbox.setItemRenderer(new StateListRenderer());
//		List<Integer> listState = new ArrayList<Integer>();
//		listState.add(0);
//		listState.add(1);
//		listState.add(-1);
//		listState.add(2);
//		listState.add(3);
//		listState.add(4);
//		stateListbox.setModel(new ListModelList(listState, true));
//		stateListbox.setSelectedItem(null);;
//		
//		productClassifyListbox.setBaProductClassifyService(productClassifyService);
//		productClassifyListbox.initComponent();
//		productClassify = productClassifyListbox.getBaProductClassify();
//	}
//
//	@Override
//	public void initData() {
//		try {
//			if(stateListbox.getSelectedItem()!=null) {
//				state = stateListbox.getSelectedItem().getValue();
//			}
//			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(contractService,
//					"getPagingCustomerContract", new Object[] { productClassify,condition,state });
//			if (gridPaging == null) {
//				gridPaging = new GridPaging(contractListbox, pagingModelList, 20);
//			} else {
//				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
//			}
//			gridPaging.setTotalSize(contractService.getCustomerContractByParaCount(productClassify,condition,state));
//			gridPaging.setDetailed(true);
//			gridPaging.setParent(southPaging);
//		} catch (DAOException e) {
//			log.error("ContractListWindow", e);
//			Messagebox.error("获取数据出错了!");
//		} catch (Exception e) {
//			log.error("ContractListWindow", e);
//			Messagebox.error("未知错误！");
//		}
//		contractListbox.invalidate();
//	}
//
//	/**
//	 * @throws InterruptedException
//	 * @throws SuspendNotAllowedException
//	 * @category 录入催收
//	 */
//	public void onClick$overButton() throws SuspendNotAllowedException, InterruptedException {
//		if (contract == null) {
//			Messagebox.show("没有选择合同!");
//			return;
//		}
//		// Executions.doModal("/contract/contractOverEdit.zul", null, null, false,
//		// this);
//	}
//
//	/**
//	 * @category 上传归档
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$contractFileButton() throws SuspendNotAllowedException, InterruptedException {
//		if (contract == null) {
//			Messagebox.show("没有选择合同!");
//			return;
//		}
//		Menu menu = new Menu();
//		menu.setMenuUrl("/contract/fileUpload.zul?id=" + contract.getId());
//		menu.setName(contract == null ? "" : contract.getName() + "-的上传归档");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript("ShowUrl('" + "/contract/fileUpload.zul?id=" + contract.getId() + "')");
//		// Executions.doModal("/contract/fileUpload.zul", null, null, false, this);
//	}
//
//	/**
//	 * @category 提前结清
//	 */
//	public void onClick$earlySettlementButton() {
//		if (contract == null) {
//			Messagebox.show("请先选择合同!");
//			return;
//		}
//		Menu menu = new Menu();
//		menu.setMenuUrl("/contract/contractEarlySettlement.zul?id=" + contract.getId());
//		menu.setName(contract == null ? "" : contract.getName() + "的提前结清");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript("ShowUrl('" + "/contract/contractEarlySettlement.zul?id=" + contract.getId() + "')");
//		// Executions.doModal("/contract/contractEarlySettlement.zul", null, null,
//		// false, this);
//	}
//
//	public void onClick$invalidButton() {
//
//		try {
//			if (contract == null) {
//				Messagebox.show("请先选择合同!");
//				return;
//			}
//			if (Messagebox.show("确认是否作废?", "系统提示", Messagebox.YES | Messagebox.NO,
//					Messagebox.QUESTION) == Messagebox.YES) {
//				if (contract.getState() == 1 || contract.getState() == 4) {
//					Messagebox.show("当前合同已结清不能作废！");
//					return;
//				}
//				if (contract.getState() == 5) {
//					Messagebox.show("当前合同已作废不能再次操作！");
//					return;
//				}
//				contract.setState(5);
//				contractService.update(contract);
//				initData();
//			}
//		} catch (UpdateException e) {
//			log.error("ContractListWindow", e);
//			Messagebox.error(" 修改数据出错了！");
//		}
//
//	}
//
//	/**
//	 * @category 查看归档
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onClick$contractViewFileButton() throws SuspendNotAllowedException, InterruptedException {
//		if (contract == null) {
//			Messagebox.show("没有选择合同!");
//			return;
//		}
//		Menu menu = new Menu();
//		menu.setMenuUrl("/contract/fileList.zul?id=" + contract.getId());
//		menu.setName(contract == null ? "" : contract.getName() + "-的归档图片");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript("ShowUrl('" + "/contract/fileList.zul?id=" + contract.getId() + "')");
//		// Executions.doModal("/contract/fileList.zul", null, null, false, this);
//	}
//
//	/**
//	 * 搜索
//	 */
//	public void onClick$searchButton() throws SuspendNotAllowedException, InterruptedException {
//		condition = conditionTextbox.getValue();
//		productClassify = productClassifyListbox.getBaProductClassify();
//		initData();
//	}
//
//	public void onOKsearchButton() throws SuspendNotAllowedException, InterruptedException {
//		onClick$searchButton();
//	}
//
//	/**
//	 * 选定
//	 */
//	public void onSelectContract() throws SuspendNotAllowedException, InterruptedException {
//		contract = (CustomerContract) contractListbox.getSelectedItem().getValue();
//	}
//
//	/**
//	 * 添加产品
//	 */
//	public void onClick$addContractButton() throws SuspendNotAllowedException, InterruptedException {
//		contract = null;
//		editType = 0;
//		Menu menu = new Menu();
//		menu.setMenuUrl("/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "");
//		menu.setName(contract == null ? "" : contract.getName() + "-的合同添加");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript(
//				"ShowUrl('" + "/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "')");
//		// Executions.doModal("/contract/contractEdit.zul", null, null, false, this);
//		// initData();
//	}
//
//	/**
//	 * 修改产品
//	 */
//	public void onClick$editContractButton() throws SuspendNotAllowedException, InterruptedException {
//		if (contract != null) {
//			editType = 1;
//			Menu menu = new Menu();
////			menu.setMenuUrl("/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "");
//			menu.setName(contract == null ? "" : contract.getName() + "-的合同修改");
//			Session session = Sessions.getCurrent();
//			session.setAttribute("OpenMenu", menu);
//			if(contract.getBaProductClassify().getId()==1 || contract.getBaProductClassify().getId()==3) {//车贷 || 优车贷
//				menu.setMenuUrl("/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "");
//				Clients.evalJavaScript(
//						"ShowUrl('" + "/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "')");
//			}
//			if(contract.getBaProductClassify().getId()==2) {//优信贷
//				menu.setMenuUrl("/contract/contractEditYXD.zul?id=" + contract.getId() + "&type=" + editType + "");
//				Clients.evalJavaScript(
//						"ShowUrl('" + "/contract/contractEditYXD.zul?id=" + contract.getId() + "&type=" + editType + "')");
//			}
//			// Executions.doModal("/contract/contractEdit.zul", null, null, false, this);
//			// initData();
//		} else {
//			Messagebox.info("请选择合同！");
//		}
//	}
//
//	/**
//	 * 产品详情
//	 */
//	public void onClick$viewContractButton() throws SuspendNotAllowedException, InterruptedException {
//		if (contract != null) {
//			editType = 2;
//			Menu menu = new Menu();
////			menu.setMenuUrl("/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "");
//			menu.setName(contract == null ? "" : contract.getName() + "-的合同查看");
//			Session session = Sessions.getCurrent();
//			session.setAttribute("OpenMenu", menu);
//			if(contract.getBaProductClassify().getId()==1 || contract.getBaProductClassify().getId()==3) {//车贷
//				menu.setMenuUrl("/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "");
//				Clients.evalJavaScript(
//						"ShowUrl('" + "/contract/contractEdit.zul?id=" + contract.getId() + "&type=" + editType + "')");
//			}
//			if(contract.getBaProductClassify().getId()==2){//优信贷
//				menu.setMenuUrl("/contract/contractEditYXD.zul?id=" + contract.getId() + "&type=" + editType + "");
//				Clients.evalJavaScript(
//						"ShowUrl('" + "/contract/contractEditYXD.zul?id=" + contract.getId() + "&type=" + editType + "')");
//			}
//			
//			
//			// Executions.doModal("/contract/contractEdit.zul", null, null, false, this);
//		} else {
//			Messagebox.info("请选择合同！");
//		}
//	}
//
//	/**
//	 * 双击
//	 * 
//	 * @throws SuspendNotAllowedException
//	 * @throws InterruptedException
//	 */
//	public void onDoubleClick$contractListbox() throws SuspendNotAllowedException, InterruptedException {
//		onClick$viewContractButton();
//	}
//	public void onDetails(Event event) throws SuspendNotAllowedException, InterruptedException {
//		
//		contract=(CustomerContract)event.getData();
//		Executions.doModal("/contract/contractDetails.zul", null, null,
//				false, this);
//	}
//	
//	
//	/**
//	 * 打印
//	 */
//	public void onClick$printContractButton() {
//		if (contract == null) {
//			Messagebox.info("请选择合同！");
//			return;
//		}
//
//		try {
//			// ProductService productService = getBean("ProductService");
//			// Product product = productService.getById(contract.getProduct().getId());
//			ContractVersionService contractVersionService = getBean("ContractVersionService");
//			ContractVersion contractVersion = contractVersionService.getById(contract.getVersion().getId());
//			Class<?> c = Class.forName(contractVersion.getProcess());
//			ContractInterface contractInterface = (ContractInterface) c.newInstance();
//			contractInterface.print(contract, false);
//		} catch (ClassNotFoundException e) {
//			log.error("ContractListWindow", e);
//			Messagebox.show("找不到处理类，请联系系统管理员处理！");
//		} catch (Exception e) {
//			log.error("ContractListWindow", e);
//			Messagebox.show("转换出错，请联系系统管理员处理！");
//		}
//	}
//
//	/**
//	 * 重打印
//	 */
//	public void onClick$rePrintContractButton() {
//		if (contract == null) {
//			Messagebox.info("请选择合同！");
//			return;
//		}
//		try {
//			// ProductService productService = getBean("ProductService");
//			ContractVersionService contractVersionService = getBean("ContractVersionService");
//			// Product product = productService.getById(contract.getProduct().getId());
//			ContractVersion contractVersion = contractVersionService.getById(contract.getVersion().getId());
//			Class<?> c = Class.forName(contractVersion.getProcess());
//			ContractInterface contractInterface = (ContractInterface) c.newInstance();
//			contractInterface.print(contract, true);
//		} catch (ClassNotFoundException e) {
//			log.error("ContractListWindow", e);
//			Messagebox.show("找不到处理类，请联系系统管理员处理！");
//		} catch (Exception e) {
//			log.error("ContractListWindow", e);
//			Messagebox.show("转换出错，请联系系统管理员处理！");
//		}
//	}
//
//	/**
//	 * 录入付款申请书
//	 */
//	public void onClick$inputContractPayApplyButton() {
//		if (contract == null) {
//			Messagebox.info("请选择合同！");
//			return;
//		}
//		Menu menu = new Menu();
//		menu.setMenuUrl("/contract/contractPayApplyInput.zul?id=" + contract.getId() +"");
//		menu.setName(contract == null ? "" : contract.getName() + "的付款申请书信息录入");
//		Session session = Sessions.getCurrent();
//		session.setAttribute("OpenMenu", menu);
//		Clients.evalJavaScript(
//				"ShowUrl('" + "/contract/contractPayApplyInput.zul?id=" + contract.getId() + "')");
//	}
//
//	/**
//	 * 续期
//	 */
//	public void onClick$renewalButton() {
//		try {
//			if (contract == null) {
//				Messagebox.info("请选择产品分期类型为“先息后本”的合同续期!!");
//				return;
//			}
//			if (contract.getState() == 1) {
//				Messagebox.info("此合同已结束，不能续期!!");
//				return;
//			}
//			if (contract.getState() == 5) {
//				Messagebox.info("此合同已作废，不能续期!!");
//				return;
//			}
//
//			Product product = contract.getProduct();
//			if (product != null) {
//				ProductService productService = (ProductService) SpringUtil.getBean("ProductService");
//				product = productService.getById(product.getId());
//			}
//			RepaymentType repaymentType = product.getRepaymentType();
//			if (repaymentType != null) {
//				RepaymentTypeService repaymentTypeService = (RepaymentTypeService) SpringUtil
//						.getBean("RepaymentTypeService");
//				repaymentType = repaymentTypeService.getById(repaymentType.getId());
//			}
//
//			// 1.判断是否是先息后本
//			if (repaymentType.getId() != 7) {
//				Messagebox.info("请选择产品分期类型为“先息后本”的合同续期!!");
//				return;
//			}
//
//			// 2.判断分期期数是否小于6期
//			RepaymentStage stage = contract.getStage();
//			if (stage != null) {
//				RepaymentStageService repaymentStageService = (RepaymentStageService) SpringUtil
//						.getBean("RepaymentStageService");
//				stage = repaymentStageService.getById(stage.getId());
//			}
//
//			if (stage.getRepaymentStage() >= stageNum) {// 到时6给抽出来
//				Messagebox.info("当前只支持还款期数小于" + stageNum + "期的合同续期！！");
//				return;
//			}
//
//			// 3.符合要求，跳转
//			Menu menu = new Menu();
//			menu.setMenuUrl("/contract/contractRenewal.zul?id=" + contract.getId() + "&stageNum=" + stageNum);
//			menu.setName("合同续期");
//			Session session = Sessions.getCurrent();
//			session.setAttribute("OpenMenu", menu);
//			Clients.evalJavaScript("ShowUrl('" + "/contract/contractRenewal.zul?id=" + contract.getId() + "&stageNum="
//					+ stageNum + "')");
//
//			initData();
//		} catch (DataNotFoundException e) {
//			log.error("ContractListWindow", e);
//		} catch (SuspendNotAllowedException e) {
//			log.error("ContractListWindow", e);
//		}
//	}
//
//	public void setListitemColor(Listitem arg0, String color, CustomerContract contract) throws DataNotFoundException {
//
//		Listcell contractidCell = new Listcell();
//		contractidCell.setParent(arg0);
//		contractidCell.setStyle("color: " + color + ";");
//		Label contractNum=new Label(contract.getContractNum());
//		contractNum.setParent(contractidCell);
//		contractNum.addForward("onClick", (Component) null, "onDetails", contract);
//		Listcell store = new Listcell();
//		store.setParent(arg0);
//		store.setStyle("color: " + color + ";");
//		StoreService storeService = (StoreService) SpringUtil.getBean("StoreService");
//		if (contract.getStore() != null) {
//			Store tempStore = storeService.getById(contract.getStore().getId());
//			new Label(tempStore.getShortName()).setParent(store);
//		} else {
//			new Label("");
//		}
//		Listcell nameCell = new Listcell();
//		nameCell.setParent(arg0);
//		nameCell.setStyle("color: " + color + ";");
//		new Label(contract.getName()).setParent(nameCell);
//
//		Listcell idcarCell = new Listcell();
//		idcarCell.setParent(arg0);
//		idcarCell.setStyle("color: " + color + ";");
//		new Label(contract.getIDNo()).setParent(idcarCell);
//
//		Listcell addressCell = new Listcell();
//		addressCell.setParent(arg0);
//		addressCell.setStyle("color: " + color + ";");
//		new Label(contract.getAddress()).setParent(addressCell);
//
//		Listcell phoneCell = new Listcell();
//		phoneCell.setParent(arg0);
//		phoneCell.setStyle("color: " + color + ";");
//		new Label(contract.getPhone()).setParent(phoneCell);
//
//		Listcell stateCell = new Listcell();
//		stateCell.setParent(arg0);
//		stateCell.setStyle("color: " + color + ";");
//		switch (contract.getState()) {
//
//		case StaticVariable.BLUE:
//			new Label("结束").setParent(stateCell);
//			break;
//		case StaticVariable.GREEN:
//			new Label("正常").setParent(stateCell);
//			break;
//		case StaticVariable.RED:
//			new Label("转逾期").setParent(stateCell);
//			break;
//		case StaticVariable.YELLOW:
//			new Label("审批中").setParent(stateCell);
//			break;
//		case StaticVariable.WHTER:
//			new Label("转账外资产").setParent(stateCell);
//			break;
//		case StaticVariable.GRAY:
//			new Label("结清").setParent(stateCell);
//			break;
//		case StaticVariable.DANHUI:
//			new Label("作废").setParent(stateCell);
//			break;
//
//		}
//
//		Listcell lateFeeCell = new Listcell();
//		lateFeeCell.setParent(arg0);
//		lateFeeCell.setStyle("color: " + color + ";");
//		new Label(String.valueOf(contract.getLateFee())).setParent(lateFeeCell);
//
//		Listcell ProductTypeCell = new Listcell();
//		ProductTypeCell.setParent(arg0);
//		ProductTypeCell.setStyle("color: " + color + ";");
//		if (contract.getProduct() == null) {
//			new Label("").setParent(ProductTypeCell);
//		} else {
//			ProductService productService = (ProductService) SpringUtil.getBean("ProductService");
//			Product product = productService.getById(contract.getProduct().getId());
//			new Label(product.getName()).setParent(ProductTypeCell);
//		}
//
//		Listcell StageCell = new Listcell();
//		StageCell.setParent(arg0);
//		StageCell.setStyle("color: " + color + ";");
//		if (contract.getStage() == null) {
//			new Label("").setParent(StageCell);
//		} else {
//			RepaymentStageService repaymentStageService = (RepaymentStageService) SpringUtil
//					.getBean("RepaymentStageService");
//			RepaymentStage repaymentStage = repaymentStageService.getById(contract.getStage().getId());
//			new Label(String.valueOf(repaymentStage.getRepaymentStage())).setParent(StageCell);
//		}
//
//		Listcell amountCell = new Listcell();
//		amountCell.setParent(arg0);
//		amountCell.setStyle("color: " + color + ";");
//		new Label(String.valueOf(contract.getAmount())).setParent(amountCell);
//
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		Listcell startDate = new Listcell();
//		startDate.setParent(arg0);
//
//		//放款开始日期
//		if (contract.getRealLoanDate() == null) {
//			new Label("").setParent(startDate);
//		} else {
//			startDate.setStyle("color: " + color + ";");
//			new Label(sdf.format(contract.getRealLoanDate())).setParent(startDate);
//		}
//		//放款结束日期
//		Listcell endDate = new Listcell();
//		endDate.setParent(arg0);
//		if (contract.getRealLoanEndDate() == null) {
//			new Label("").setParent(endDate);
//		} else {
//			endDate.setStyle("color: " + color + ";");
//			new Label(sdf.format(contract.getRealLoanEndDate())).setParent(endDate);
//		}
//
//		arg0.setValue(contract);
//	}
//	
//	class StateListRenderer implements ListitemRenderer {
//		public void render(Listitem item, Object stateObject, int arg2) throws Exception {
//			int stateValue = (int) stateObject;
//			item.setValue(stateValue);
//
//			switch (stateValue) {
//			case 0:
//				item.setLabel("正常");
//				break;
//			case 1:
//				item.setLabel("结束");
//				break;
//			case -1:
//				item.setLabel("审批中");
//				break;
//			case 2:
//				item.setLabel("转逾期");
//				break;
//			case 3:
//				item.setLabel("转账外资产");
//				break;
//			case 4:
//				item.setLabel("结清");
//				break;
////			case 5:
////				item.setLabel("作废");
////				break;
//			}
//		}
//	}
//
//	/**
//	 * 处理数据绑定
//	 */
//	@SuppressWarnings("rawtypes")
//	class contractListRenderer implements ListitemRenderer {
//		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
//			CustomerContract contract = (CustomerContract) arg1;
//			switch (contract.getState()) {
//			case StaticVariable.RED:
//				setListitemColor(arg0, StaticVariable.RED_COLOR, contract);
//				break;
//			case StaticVariable.BLUE:
//				setListitemColor(arg0, StaticVariable.BLUE_COLOR, contract);
//				break;
//			case StaticVariable.GREEN:
//				setListitemColor(arg0, StaticVariable.GREEN_COLOR, contract);
//				break;
//			case StaticVariable.WHTER:
//				setListitemColor(arg0, StaticVariable.WHTER_COLOR, contract);
//				break;
//			case StaticVariable.YELLOW:
//				setListitemColor(arg0, StaticVariable.YELLOW_COLOR, contract);
//				break;
//			case StaticVariable.GRAY:
//				setListitemColor(arg0, StaticVariable.GRAY_COLOR, contract);
//				break;
//			case StaticVariable.DANHUI:
//				setListitemColor(arg0, StaticVariable.DANHUI_COLOR, contract);
//				break;
//			}
//		}
//	}
//}
