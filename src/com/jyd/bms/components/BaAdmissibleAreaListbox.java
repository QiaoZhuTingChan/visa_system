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

import com.jyd.bms.bean.BaAdmissibleArea;
import com.jyd.bms.service.BaAdmissibleAreaService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;

/**
 * @category 业务来源控件
 * @author mjy
 *
 */
public class BaAdmissibleAreaListbox extends Listbox implements ComponentInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(BaAdmissibleAreaListbox.class);
	private BaAdmissibleAreaService BaAdmissibleAreaService;
	private Component handleComponent;

	public BaAdmissibleAreaService getBaAdmissibleAreaService() {
		return BaAdmissibleAreaService;
	}

	private BaAdmissibleArea BaAdmissibleArea;

	public void setBaAdmissibleAreaService(BaAdmissibleAreaService BaAdmissibleAreaService) {
		this.BaAdmissibleAreaService = BaAdmissibleAreaService;
	}

	public BaAdmissibleAreaListbox() {
		this.setMold("select");
		this.setItemRenderer(new BaAdmissibleAreaRenderer());
		this.addForward("onSelect", (Component) this, "onSelectBaAdmissibleArea", null);
	}
	
	public void onSelectBaAdmissibleArea(Event event) throws SuspendNotAllowedException, InterruptedException {
		BaAdmissibleArea = this.getSelectedItem().getValue();
		if (handleComponent == null) {
			Events.postEvent("onSelectBaAdmissibleArea", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onSelectBaAdmissibleArea", handleComponent, null);
		}
	}

	public BaAdmissibleAreaListbox(BaAdmissibleArea BaAdmissibleArea) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new BaAdmissibleAreaRenderer(BaAdmissibleArea));
		}
	}

	public boolean setBaAdmissibleArea(BaAdmissibleArea BaAdmissibleArea) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			BaAdmissibleArea BaAdmissibleArea1 = (BaAdmissibleArea) this.getItemAtIndex(iAdd).getValue();
			if (BaAdmissibleArea == null) {
				break;
			}
			if (BaAdmissibleArea1.getId() == BaAdmissibleArea.getId()) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public BaAdmissibleArea getBaAdmissibleArea() {
		if (this.getSelectedItem() != null) {
			return (BaAdmissibleArea) this.getSelectedValue();
		} else {
			return null;
		}
	}

	class BaAdmissibleAreaRenderer extends ListitemRenderer {
		public BaAdmissibleAreaRenderer() {
		}

		public BaAdmissibleAreaRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object productTypeObj, int arg2) throws Exception {
			BaAdmissibleArea productType = (BaAdmissibleArea) productTypeObj;
			item.setLabel(productType.getBaAdmissibleArea());
			item.setValue(productType);
			super.render(item, productTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getBaAdmissibleArea();
	}

	@Override
	public void initComponent() {
		try {
			List<BaAdmissibleArea> lstProductType = BaAdmissibleAreaService.getAllBaAdmissibleArea();
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
			log.error("BaAdmissibleAreaListbox", e);
			Messagebox.show("获取数据出错");
		}
	}

	

}
