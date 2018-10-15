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

import com.jyd.bms.bean.BaModelType;
import com.jyd.bms.service.BaModelTypeService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;
import com.jyd.bms.window.admin.component.ComponentInterface;

/**
 * @category 模板类型控件
 * @author mjy
 *
 */
public class BaModelTypeListbox extends Listbox implements ComponentInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(BaModelTypeListbox.class);
	private BaModelTypeService BaModelTypeService;
	private Component handleComponent;

	public BaModelTypeService getBaModelTypeService() {
		return BaModelTypeService;
	}

	private BaModelType BaModelType;

	public void setBaModelTypeService(BaModelTypeService BaModelTypeService) {
		this.BaModelTypeService = BaModelTypeService;
	}

	public BaModelTypeListbox() {
		this.setMold("select");
		this.setItemRenderer(new BaModelTypeRenderer());
		this.addForward("onSelect", (Component) this, "onSelectBaModelType", null);
	}

	public void onSelectBaModelType(Event event) throws SuspendNotAllowedException, InterruptedException {
		BaModelType = this.getSelectedItem().getValue();
		if (handleComponent == null) {
			Events.postEvent("onSelectBaModelType", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onSelectBaModelType", handleComponent, null);
		}
	}

	public BaModelTypeListbox(BaModelType BaModelType) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new BaModelTypeRenderer(BaModelType));
		}
	}

	public boolean setBaModelType(BaModelType BaModelType) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			BaModelType BaModelType1 = (BaModelType) this.getItemAtIndex(iAdd).getValue();
			if (BaModelType == null) {
				break;
			}
			if (BaModelType1.getId() == BaModelType.getId()) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public BaModelType getBaModelType() {
		if (this.getSelectedItem() != null) {
			return (BaModelType) this.getSelectedValue();
		} else {
			return null;
		}
	}

	class BaModelTypeRenderer extends ListitemRenderer {
		public BaModelTypeRenderer() {
		}

		public BaModelTypeRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object productTypeObj, int arg2) throws Exception {
			BaModelType productType = (BaModelType) productTypeObj;
			item.setLabel(productType.getBaModelType());
			item.setValue(productType);
			super.render(item, productTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getBaModelType();
	}

	@Override
	public void initComponent() {
		try {
			List<BaModelType> lstProductType = BaModelTypeService.getAllBaModelType();
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
			log.error("BaModelTypeListbox", e);
			Messagebox.show("获取数据出错");
		}
	}

}
