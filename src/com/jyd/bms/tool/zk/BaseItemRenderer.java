package com.jyd.bms.tool.zk;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

import com.jyd.bms.tool.DateUtils;
import com.jyd.bms.tool.StringUtils;

public abstract class BaseItemRenderer {
	private static final Logger log;
	public static final String DATA_TYPE_STRING = "string";
	public static final String DATA_TYPE_DATE = "date";
	public static final String DATA_TYPE_TIMESTAMP = "timestamp";
	public static final String DATA_TYPE_NUMBER = "number";
	public static final String LABEL_ALIGN_START = "start";
	public static final String LABEL_ALIGN_CENTER = "center";
	public static final String LABEL_ALIGN_END = "end";
	protected Object selected;
	protected List selectedObjects;
	protected String[] labelProperties;
	protected String[] dataTypeProperties;
	protected String[] dataFormatProperties;
	protected String[] labelAlignProperties;

	static {
		log = LoggerFactory.getLogger((Class) BaseItemRenderer.class);
	}

	public String[] getLabelProperties() {
		return this.labelProperties;
	}

	public void setLabelProperties(final String[] labelProperties) {
		this.labelProperties = labelProperties;
	}

	public String[] getDataTypeProperties() {
		return this.dataTypeProperties;
	}

	public void setDataTypeProperties(final String[] dataTypeProperties) {
		this.dataTypeProperties = dataTypeProperties;
	}

	public String[] getDataFormatProperties() {
		return this.dataFormatProperties;
	}

	public void setDataFormatProperties(final String[] dataFormatProperties) {
		this.dataFormatProperties = dataFormatProperties;
	}

	public String[] getLabelAlignProperties() {
		return this.labelAlignProperties;
	}

	public void setLabelAlignProperties(final String[] labelAlignProperties) {
		this.labelAlignProperties = labelAlignProperties;
	}

	public void setSelected(final Object selected) {
		this.selected = selected;
	}

	public Object getSelected() {
		return this.selected;
	}

	public void setSelectedObjects(final List selectedObjects) {
		this.selectedObjects = selectedObjects;
	}

	public List getSelectedObjects() {
		return this.selectedObjects;
	}

	public void addSelected(final Object selected) {
		if (this.selectedObjects == null) {
			this.selectedObjects = new ArrayList();
		}
		this.selectedObjects.add(selected);
	}

	protected String getPropertyValue(final Object data, final String property) {
		String label = "";
		try {
			label = PropertyUtils.getProperty(data, property).toString();
		} catch (Exception ex) {
			BaseItemRenderer.log.warn(ex.toString(), (Throwable) ex);
		}
		return label;
	}

	protected Object getPropertyObject(final Object data, final String property) {
		Object propertyObject = null;
		try {
			propertyObject = PropertyUtils.getProperty(data, property);
		} catch (Exception ex) {
			BaseItemRenderer.log.warn(ex.toString(), (Throwable) ex);
		}
		return propertyObject;
	}

	protected Component getFormattedPropertyValue(final Object propertyObject, final int index,
			final String[] dateTypes, final String[] dataFormats, final String[] labelAligns) {
		return this.getFormattedPropertyValue(propertyObject, index, dateTypes, dataFormats, labelAligns, false);
	}

	protected Component getFormattedPropertyValue(final Object propertyObject, final int index,
			final String[] dateTypes, final String[] dataFormats, final String[] labelAligns,
			final boolean overrideAign) {
		final Label label = new Label();
		if (!overrideAign) {
			final Vbox vbox = new Vbox();
			vbox.setWidth("100%");
			label.setParent((Component) vbox);
			if (labelAligns != null && labelAligns[index] != null && StringUtils.isNotBlank(labelAligns[index])) {
				vbox.setAlign(labelAligns[index]);
			}
			if (dateTypes != null && dateTypes[index] != null && StringUtils.isNotBlank(dateTypes[index])) {
				if (dateTypes[index].equals("string")) {
					label.setValue(propertyObject.toString());
				} else if (dateTypes[index].equals("date")) {
					label.setValue(DateUtils.formatDate((Date) propertyObject));
				} else if (dateTypes[index].equals("timestamp")) {
					label.setValue(DateUtils.formatTimestamp((Timestamp) propertyObject));
				} else if (dateTypes[index].equals("number")) {
					final DecimalFormat df1 = new DecimalFormat(dataFormats[index]);
					label.setValue(df1.format(propertyObject));
				} else {
					label.setValue(propertyObject.toString());
				}
			} else {
				label.setValue(propertyObject.toString());
			}
			return (Component) vbox;
		}
		if (dateTypes != null && dateTypes[index] != null && StringUtils.isNotBlank(dateTypes[index])) {
			if (dateTypes[index].equals("string")) {
				label.setValue(propertyObject.toString());
			} else if (dateTypes[index].equals("date")) {
				label.setValue(DateUtils.formatDate((Date) propertyObject));
			} else if (dateTypes[index].equals("timestamp")) {
				label.setValue(DateUtils.formatTimestamp((Timestamp) propertyObject));
			} else if (dateTypes[index].equals("number")) {
				final DecimalFormat df2 = new DecimalFormat(dataFormats[index]);
				label.setValue(df2.format(propertyObject));
			} else {
				label.setValue(propertyObject.toString());
			}
		} else {
			label.setValue(propertyObject.toString());
		}
		return (Component) label;
	}
}
