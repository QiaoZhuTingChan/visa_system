package com.jyd.bms.window.business;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.jyd.bms.bean.Menu;
import com.jyd.bms.bean.Product;
import com.jyd.bms.tool.exception.DAOException;
import com.jyd.bms.tool.zk.BaseWindow;

public class ProductListWindow extends BaseWindow {
	private Product product;
	private int editType;

	@Override
	public void initUI() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() throws DAOException {
		// TODO Auto-generated method stub

	}

	class ProductRenderer implements ListitemRenderer {
		public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
			Product product = (Product) arg1;
			Listcell productNameCell = new Listcell();
			productNameCell.setParent(arg0);
			new Label(product.getProductName()).setParent(productNameCell);
			Listcell lineCustomerPriceCell = new Listcell();
			lineCustomerPriceCell.setParent(arg0);
			new Label(String.valueOf(product.getLineCustomerPrice())).setParent(lineCustomerPriceCell);
			Listcell peerPriceCell = new Listcell();
			peerPriceCell.setParent(arg0);
			new Label(String.valueOf(product.getPeerPrice())).setParent(peerPriceCell);
			Listcell marketPriceCell = new Listcell();
			marketPriceCell.setParent(arg0);
			new Label(String.valueOf(product.getMarketPrice())).setParent(marketPriceCell);
			Listcell imgCell = new Listcell();
			imgCell.setParent(arg0);
			new Label(product.getImg()).setParent(imgCell);
			Listcell baVisaTypeIdCell = new Listcell();
			baVisaTypeIdCell.setParent(arg0);
			new Label(String.valueOf(product.getBaVisaTypeId())).setParent(baVisaTypeIdCell);
			Listcell baAdmissibleAreaIdCell = new Listcell();
			baAdmissibleAreaIdCell.setParent(arg0);
			new Label(String.valueOf(product.getBaAdmissibleAreaId())).setParent(baAdmissibleAreaIdCell);
			Listcell admissibleAreaTypeCell = new Listcell();
			admissibleAreaTypeCell.setParent(arg0);
			new Label(product.getAdmissibleAreaType()).setParent(admissibleAreaTypeCell);
			Listcell outCycleCell = new Listcell();
			outCycleCell.setParent(arg0);
			new Label(product.getOutCycle()).setParent(outCycleCell);
			Listcell residenceTimeCell = new Listcell();
			residenceTimeCell.setParent(arg0);
			new Label(product.getResidenceTime()).setParent(residenceTimeCell);
			Listcell effectiveTimeCell = new Listcell();
			effectiveTimeCell.setParent(arg0);
			new Label(product.getEffectiveTime()).setParent(effectiveTimeCell);
			Listcell entryTimesCell = new Listcell();
			entryTimesCell.setParent(arg0);
			new Label(String.valueOf(product.getEntryTimes())).setParent(entryTimesCell);
			Listcell faceSignCell = new Listcell();
			faceSignCell.setParent(arg0);
			new Label(product.isFaceSign() ? "面签" : "不面签").setParent(faceSignCell);
			Listcell remarkCell = new Listcell();
			remarkCell.setParent(arg0);
			new Label(product.getRemark()).setParent(remarkCell);
			arg0.setValue(product);
		}
	}

	public void onClick$addButton() {
		Menu menu = new Menu();
		menu.setMenuUrl("/business/productEdit.zul?id=" + product.getId() + "&type=" + editType + "");
		menu.setName(product == null ? "" : product.getProductName() + "-的产品添加");
		Session session = Sessions.getCurrent();
		session.setAttribute("OpenMenu", menu);
		Clients.evalJavaScript(
				"ShowUrl('" + "/business/productEdit.zul?id=" + product.getId() + "&type=" + editType + "')");
	}
}
