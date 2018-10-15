package com.jyd.bms.window.basedata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.South;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.jyd.bms.bean.BaDataTemplate;
import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.bean.User;
import com.jyd.bms.common.Environment;
import com.jyd.bms.components.BaModelTypeListbox;
import com.jyd.bms.service.BaDataTemplateService;
import com.jyd.bms.service.BaModelTypeService;
import com.jyd.bms.tool.exception.CreateException;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.exception.DataNotFoundException;
import com.jyd.bms.tool.exception.UpdateException;
import com.jyd.bms.tool.zk.BaseWindow;
import com.jyd.bms.tool.zk.GridPaging;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.Messagebox;
import com.jyd.bms.tool.zk.PagingControlComponentModelList;
import com.jyd.bms.tool.zk.UserSession;

/**
 * @category Generated 2018-10-14 20:12:29 by GeneratedTool
 * @author mjy
 */
public class BaDataTemplateWindow extends BaseWindow {
	private Window uploadWindow;
	private Button addButton;
	private Button editButton;
	private Button cancelButton;
	private Button saveButton;
	private GridPaging gridPaging;
	private Textbox conditionTextbox;
	private String condition = "";
	private South southPaging;
	private Label srcLabel;
	private Button uploadButton;
	// 进度条
	private Progressmeter userprogress;
	private Label baDataTemplateLabel;
	private Textbox baDataTemplateTextbox;
	private Label remarkLabel;
	private Textbox remarkTextbox;
	private Listbox baDataTemplateListbox;
	private BaDataTemplate baDataTemplate;
	private BaDataTemplateService baDataTemplateService;
	private List<BaDataTemplate> baDataTemplatelist = new ArrayList<BaDataTemplate>();
	private static final Logger log = LoggerFactory.getLogger(BaDataTemplateWindow.class);
	private int edit = 0;
	private BaModelTypeListbox baModelTypeListbox;
	private BaModelTypeService baModelTypeService;
	private Label baModelTypeLabel;

	public BaDataTemplateWindow() {
		this.menuId = "ba_data_template";
	}

	public Listitem getSelectItem() {
		return baDataTemplateListbox.getSelectedItem();
	}

	public void initUI() {
		baModelTypeService = getBean("BaModelTypeService");
		baDataTemplateService = getBean("BaDataTemplateService");
		baDataTemplateListbox.setItemRenderer(new BaDataTemplateRenderer());
	}

	@Override
	public void initData() {
		try {
			baModelTypeListbox.setBaModelTypeService(baModelTypeService);
			baModelTypeListbox.initComponent();
			PagingControlComponentModelList pagingModelList = new PagingControlComponentModelList(baDataTemplateService,
					"getPagingBaDataTemplate", new Object[] { condition });
			if (gridPaging == null) {
				gridPaging = new GridPaging(baDataTemplateListbox, pagingModelList, 20);
			} else {
				gridPaging.setPagingControlComponentModel(pagingModelList, 20);
			}
			gridPaging.setTotalSize(baDataTemplateService.getBaDataTemplateCount(condition));
			gridPaging.setDetailed(true);
			gridPaging.setParent(southPaging);
		} catch (DAOException e) {
			log.error("BaDataTemplateWindow", e);
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
		srcLabel.setVisible(!flag);
		uploadButton.setVisible(flag);
		baDataTemplateLabel.setVisible(!flag);
		baDataTemplateTextbox.setVisible(flag);
		remarkLabel.setVisible(!flag);
		remarkTextbox.setVisible(flag);
		baModelTypeListbox.setVisible(flag);
		baModelTypeLabel.setVisible(!flag);
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
		srcLabel.setValue("");
		baDataTemplateLabel.setValue("");
		baDataTemplateTextbox.setValue("");
		remarkLabel.setValue("");
		remarkTextbox.setValue("");
		baModelTypeLabel.setValue("");
	}

	public void setBaDataTemplateValue(BaDataTemplate baDataTemplate) {
		baDataTemplate.setSrc(srcLabel.getValue());
		baDataTemplate.setBaDataTemplate(baDataTemplateTextbox.getValue());
		baDataTemplate.setRemark(remarkTextbox.getValue());
		baDataTemplate.setBaModelType(baModelTypeListbox.getBaModelType());
	}

	public void setBaDataTemplateData(BaDataTemplate baDataTemplate) throws DataNotFoundException {
		srcLabel.setValue(baDataTemplate.getSrc());
		baDataTemplateLabel.setValue(baDataTemplate.getBaDataTemplate());
		baDataTemplateTextbox.setValue(baDataTemplate.getBaDataTemplate());
		remarkLabel.setValue(baDataTemplate.getRemark());
		remarkTextbox.setValue(baDataTemplate.getRemark());
		baModelTypeListbox.setBaModelType(baDataTemplate.getBaModelType());
		BaModelTypeService baModelTypeService = getBean("BaModelTypeService");
		BaModelType baModelType = baModelTypeService.getById(baDataTemplate.getBaModelType().getId());
		baModelTypeLabel.setValue(baModelType == null ? "" : baModelType.getBaModelType());
	}

	public void onSelect$baDataTemplateListbox()
			throws SuspendNotAllowedException, InterruptedException, DataNotFoundException {
		edit = -1;
		baDataTemplate = getSelectItem().getValue();
		clearTextbox();
		setBaDataTemplateData(baDataTemplate);
		enableTextbox(false);
		enableButton("select");
	}

	public void onClick$cancelButton() {
		userprogress.setVisible(false);
		enableButton("cancel");
		enableTextbox(false);
		clearTextbox();
	}

	public void onClick$addButton() {
		userprogress.setVisible(false);
		edit = 0;
		enableTextbox(true);
		enableButton("add");
		clearTextbox();
	}

	public void onClick$editButton() {
		userprogress.setVisible(false);
		edit = -1;
		enableTextbox(true);
		enableButton("update");
	}

	public boolean checkInput() {
		boolean flag = true;
		if (baDataTemplateTextbox.getValue().equals("")) {
			baDataTemplateTextbox.focus();
			Messagebox.show("名称不能为空！");
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
				baDataTemplate = new BaDataTemplate();
				setBaDataTemplateValue(baDataTemplate);
				baDataTemplate.setCreateDate(date);
				baDataTemplate.setCreateUser(user);
				baDataTemplate.setUpdateDate(date);
				baDataTemplate.setUpdateUser(user);
				baDataTemplateService.add(baDataTemplate);
			} else {
				setBaDataTemplateValue(baDataTemplate);
				baDataTemplate.setUpdateDate(date);
				baDataTemplate.setUpdateUser(user);
				baDataTemplateService.update(baDataTemplate);
			}
			onClick$cancelButton();
			initData();
		} catch (CreateException e) {
			log.error("baDataTemplateWindow", e);
		} catch (UpdateException e) {
			log.error("baDataTemplateWindow", e);
		}
	}

	class BaDataTemplateRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			BaDataTemplate baDataTemplate = (BaDataTemplate) arg1;
			Listcell srcCell = new Listcell();
			srcCell.setParent(arg0);
			new Label(baDataTemplate.getSrc()).setParent(srcCell);
			Listcell baDataTemplateCell = new Listcell();
			baDataTemplateCell.setParent(arg0);
			new Label(baDataTemplate.getBaDataTemplate()).setParent(baDataTemplateCell);

			Listcell modelCell = new Listcell();
			modelCell.setParent(arg0);
			BaModelTypeService baModelTypeService = getBean("BaModelTypeService");
			BaModelType baModelType = baModelTypeService.getById(baDataTemplate.getBaModelType().getId());
			new Label(baModelType.getBaModelType()).setParent(modelCell);

			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(baDataTemplate.getRemark()).setParent(remarkCell);
			arg0.setValue(baDataTemplate);
		}
	}

	public void onUpload(Object fileMedia) throws InterruptedException {
		try {
			userprogress.setVisible(true);
			String PATH = Environment.getFilePath() + "file/";
			Media media = ((UploadEvent) fileMedia).getMedia();
			if (media != null) {
				File fileContractPath = new File(PATH);
				if (!fileContractPath.exists()) {
					fileContractPath.mkdirs();
				}
				int fileMaxSize = 20 * 1024 * 1024;
				int fileRealSize = media.getByteData().length;
				if (fileRealSize < fileMaxSize) {
					String fileType = getFileType(media.getName());
					String newFileName = java.util.UUID.randomUUID() + fileType;
					newFileName = newFileName.replace("-", "");
					File fileNew = new File(PATH + newFileName);
					if (!fileNew.exists()) {
						fileNew.createNewFile();
					}
					srcLabel.setValue(newFileName);
					uploadWindow.getDesktop().enableServerPush(true);
					UserHandleThread thred = new UserHandleThread(userprogress, fileNew, media);
					thred.start();
				}
			}
		} catch (IOException e) {
			Messagebox.show("上传文件出错了，请联系系统管理员！");
			log.error(this.getClass().getSimpleName(), e);
		} catch (SuspendNotAllowedException e) {
			Messagebox.show("保存数据库失败，请联系系统管理员！");
			log.error(this.getClass().getSimpleName(), e);
		}
	}

	class UserHandleThread extends Thread {
		// 文件
		private File file;
		// 文件流
		private Media media;
		// 进度条
		private Progressmeter userprogress;

		public UserHandleThread(Progressmeter userprogress, File file, Media media) {
			this.userprogress = userprogress;
			this.file = file;
			this.media = media;
		}

		@Override
		public void run() {
			try {
				/************** 原图 ********************/
				InputStream in = media.getStreamData();
				FileOutputStream out = new FileOutputStream(file);
				int totalSize = media.getByteData().length;// 总字节
				int readNumber = 0;// 读取到的字节
				int havaSzie = 0;// 已有字节
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits(2);
				byte[] buffer = new byte[totalSize / 10];
				/************** 进度条 ********************/
				int[] arr = new int[11];
				Executions.activate(userprogress.getDesktop());
				userprogress.setValue(0);
				Executions.deactivate(userprogress.getDesktop());
				while ((readNumber = in.read(buffer)) != -1) {
					// 获取当前获取字节数
					havaSzie += readNumber;
					// 格式化小数位
					String result = numberFormat.format((float) havaSzie / (float) totalSize * 100);
					Double value = Double.parseDouble(result);
					// 写文件
					out.write(buffer);
					for (int j = 10; j <= 100; j += 10) {
						if (value.intValue() == j) {
							if (arr[j / 10] == 0) {
								Executions.activate(userprogress.getDesktop());
								userprogress.setValue(value.intValue());
								Executions.deactivate(userprogress.getDesktop());
								arr[j / 10] = 1;
							}
						}
					}
				}
				out.close();
				in.close();
			} catch (Exception e) {
				log.error("UserHandleThread", e);
			}
		}
	}

	private String getFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}
}
