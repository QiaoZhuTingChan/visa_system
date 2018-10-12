package com.jyd.bms.components;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.jyd.bms.bean.BaVisaType;
import com.jyd.bms.service.BaVisaTypeService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;

/**
 * @category 签证类型控件
 * @author mjy
 *
 */
public class BaVisaTypeListbox extends Listbox implements ComponentInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(BaVisaTypeListbox.class);
	private BaVisaTypeService BaVisaTypeService;
	private Component handleComponent;

	public BaVisaTypeService getBaVisaTypeService() {
		return BaVisaTypeService;
	}

	private BaVisaType BaVisaType;

	public void setBaVisaTypeService(BaVisaTypeService BaVisaTypeService) {
		this.BaVisaTypeService = BaVisaTypeService;
	}

	public BaVisaTypeListbox() {
		this.setMold("select");
		this.setItemRenderer(new BaVisaTypeRenderer());
		this.addForward("onSelect", (Component) this, "onSelectBaVisaType", null);
	}

	public void onSelectBaVisaType(Event event) throws SuspendNotAllowedException, InterruptedException {
		BaVisaType = this.getSelectedItem().getValue();
		if (handleComponent == null) {
			Events.postEvent("onSelectBaVisaType", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onSelectBaVisaType", handleComponent, null);
		}
	}

	public BaVisaTypeListbox(BaVisaType BaVisaType) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new BaVisaTypeRenderer(BaVisaType));
		}
	}

	public boolean setBaVisaType(BaVisaType BaVisaType) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			BaVisaType BaVisaType1 = (BaVisaType) this.getItemAtIndex(iAdd).getValue();
			if (BaVisaType == null) {
				break;
			}
			if (BaVisaType1.getId() == BaVisaType.getId()) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public BaVisaType getBaVisaType() {
		if (this.getSelectedItem() != null) {
			return (BaVisaType) this.getSelectedValue();
		} else {
			return null;
		}
	}

	class BaVisaTypeRenderer extends ListitemRenderer {
		public BaVisaTypeRenderer() {
		}

		public BaVisaTypeRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object productTypeObj, int arg2) throws Exception {
			BaVisaType productType = (BaVisaType) productTypeObj;
			item.setLabel(productType.getBaVisaType());
			item.setValue(productType);
			super.render(item, productTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getBaVisaType();
	}

	@Override
	public void initComponent() {
		try {
			List<BaVisaType> lstProductType = BaVisaTypeService.getAllBaVisaType();
			if (lstProductType.size() > 0) {
				this.setModel(new ListModelList(lstProductType, true));
				this.renderAll();
				this.invalidate();
				this.setSelectedIndex(0);
			} else {
				this.setModel(new ListModelList(lstProductType, true));
				this.invalidate();
			}
		} catch (DAOException e) {
			log.error("BaVisaTypeListbox", e);
			Messagebox.show("获取数据出错");
		}
	}

}
