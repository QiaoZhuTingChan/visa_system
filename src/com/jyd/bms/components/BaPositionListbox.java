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

import com.jyd.bms.bean.BaPosition;
import com.jyd.bms.service.BaPositionService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;
import com.jyd.bms.window.admin.component.ComponentInterface;

/**
 * @category 职位
 * @author mjy
 *
 */
public class BaPositionListbox extends Listbox implements ComponentInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(BaPositionListbox.class);
	private BaPositionService BaPositionService;
	private Component handleComponent;

	public BaPositionService getBaPositionService() {
		return BaPositionService;
	}

	private BaPosition BaPosition;

	public void setBaPositionService(BaPositionService BaPositionService) {
		this.BaPositionService = BaPositionService;
	}

	public BaPositionListbox() {
		this.setMold("select");
		this.setItemRenderer(new BaPositionRenderer());
		this.addForward("onSelect", (Component) this, "onSelectBaPosition", null);
	}

	public void onSelectBaPosition(Event event) throws SuspendNotAllowedException, InterruptedException {
		BaPosition = this.getSelectedItem().getValue();
		if (handleComponent == null) {
			Events.postEvent("onSelectBaPosition", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onSelectBaPosition", handleComponent, null);
		}
	}

	public BaPositionListbox(BaPosition BaPosition) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new BaPositionRenderer(BaPosition));
		}
	}

	public boolean setBaPosition(BaPosition BaPosition) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			BaPosition BaPosition1 = (BaPosition) this.getItemAtIndex(iAdd).getValue();
			if (BaPosition == null) {
				break;
			}
			if (BaPosition1.getId() == BaPosition.getId()) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public BaPosition getBaPosition() {
		if (this.getSelectedItem() != null) {
			return (BaPosition) this.getSelectedValue();
		} else {
			return null;
		}
	}

	class BaPositionRenderer extends ListitemRenderer {
		public BaPositionRenderer() {
		}

		public BaPositionRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object productTypeObj, int arg2) throws Exception {
			BaPosition productType = (BaPosition) productTypeObj;
			item.setLabel(productType.getBaPosition());
			item.setValue(productType);
			super.render(item, productTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getBaPosition();
	}

	@Override
	public void initComponent() {
		try {
			List<BaPosition> lstProductType = BaPositionService.getAllBaPosition();
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
			log.error("BaPositionListbox", e);
			Messagebox.show("获取数据出错");
		}
	}

}
