package com.jyd.bms.components;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;

import com.jyd.bms.tool.zk.Listbox;
import com.jyd.bms.tool.zk.ListitemRenderer;

public class CarseatListbox extends Listbox implements ComponentInterface {
	private static final Logger log = LoggerFactory.getLogger(CarseatListbox.class);

	public CarseatListbox() {
		this.setMold("select");
		this.setItemRenderer(new CarseatRenderer());
	}

	public CarseatListbox(int value) {
		this();
		if (this.getItemCount() > 0) {
			this.setItemRenderer(new CarseatRenderer(value));
		}
	}

	public boolean setCarseatNum(int value) {
		for (int iAdd = 0; iAdd < this.getItemCount(); iAdd++) {
			int positiontype1 = (int) this.getItemAtIndex(iAdd).getValue();
			if (positiontype1 == value) {
				this.setSelectedIndex(iAdd);
				return true;
			}
		}
		return false;
	}

	public int getCarseatNum() {
		if (this.getSelectedItem() != null) {
			return (int) this.getSelectedValue();
		} else {
			return 0;
		}
	}

	class CarseatRenderer extends ListitemRenderer {
		public CarseatRenderer() {
		}

		public CarseatRenderer(Object selected) {
			addSelected(selected);
		}

		public void render(Listitem item, Object dutytypeTypeObj, int arg2) throws Exception {
			int value = (int) dutytypeTypeObj;
			item.setLabel(value == 0 ? "五座车" : "七座车");
			item.setValue(value);
			super.render(item, dutytypeTypeObj);
		}
	}

	public Object getCurrentValue() {
		return getCarseatNum();
	}

	@Override
	public void initComponent() {
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(0);
		lst.add(1);
		if (lst.size() > 0) {
			this.setModel(new ListModelList<Integer>(lst, true));
			this.renderAll();
			this.invalidate();
			this.setSelectedIndex(0);
		}
	}
}
