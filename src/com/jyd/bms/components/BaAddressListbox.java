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

import com.jyd.bms.bean.BaAddress;
import com.jyd.bms.service.BaAddressService;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;
import com.jyd.bms.window.admin.component.ComponentInterface;

/**
 * @category地址控件
 * @author mjy
 *
 */
public class BaAddressListbox extends Listbox implements ComponentInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(BaAddressListbox.class);
	private BaAddressService BaAddressService;
	private Component handleComponent;

	public BaAddressService getBaAddressService() {
		return BaAddressService;
	}

	private BaAddress BaAddress;

	public void setBaAddressService(BaAddressService BaAddressService) {
		this.BaAddressService = BaAddressService;
	}

	public BaAddressListbox() {
		this.setMold("select");
		this.setItemRenderer(new BaAddressRenderer());
		this.addForward("onSelect", (Component) this, "onSelectBaAddress", null);
	}

	public void onSelectBaAddress(Event event) throws SuspendNotAllowedException, InterruptedException {
		BaAddress = this.getSelectedItem().getValue();
		if (handleComponent == null) {
			Events.postEvent("onSelectBaAddress", (Component) this.getSpaceOwner(), null);
		} else {
			Events.postEvent("onSelectBaAddress", handleComponent, null);
		}
	}

	public BaAddressListbox(BaAddress BaAddress) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new BaAddressRenderer(BaAddress));
		}
	}

	public boolean setBaAddress(BaAddress BaAddress) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			BaAddress BaAddress1 = (BaAddress) this.getItemAtIndex(iAdd).getValue();
			if (BaAddress == null) {
				break;
			}
			if (BaAddress1.getId() == BaAddress.getId()) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public BaAddress getBaAddress() {
		if (this.getSelectedItem() != null) {
			return (BaAddress) this.getSelectedValue();
		} else {
			return null;
		}
	}

	class BaAddressRenderer extends ListitemRenderer {
		public BaAddressRenderer() {
		}

		public BaAddressRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object productTypeObj, int arg2) throws Exception {
			BaAddress productType = (BaAddress) productTypeObj;
			item.setLabel("联系人:" + productType.getContact() + " 电话：" + productType.getContactPhone() + " 地址:"
					+ productType.getAddress());
			item.setValue(productType);
			super.render(item, productTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getBaAddress();
	}

	@Override
	public void initComponent() {
		try {
			List<BaAddress> lstProductType = BaAddressService.getAllBaAddress();
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
			log.error("BaAddressListbox", e);
			Messagebox.show("获取数据出错");
		}
	}

}
