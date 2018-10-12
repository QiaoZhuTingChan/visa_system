package com.jyd.bms.tool.zk;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

class PropertyListitemRenderer extends BaseItemRenderer
{
    private static final Logger log;
    protected Map<String, Component>[] labelValues;
    
    static {
        log = LoggerFactory.getLogger((Class)PropertyListitemRenderer.class);
    }
    
    public void render(final Listitem item, final Object data) throws Exception {
        if (this.labelProperties != null) {
            if (this.dataTypeProperties != null && this.labelProperties.length != this.dataTypeProperties.length) {
                final IllegalArgumentException ex = new IllegalArgumentException("labelProperties(" + this.labelProperties + ").length != dataTypeProperties(" + this.dataTypeProperties + ").length!");
                PropertyListitemRenderer.log.error(ex.toString(), (Throwable)ex);
                throw ex;
            }
            if (this.labelAlignProperties != null && this.labelProperties.length != this.labelAlignProperties.length) {
                final IllegalArgumentException ex = new IllegalArgumentException("labelProperties(" + this.labelProperties + ").length != labelAlignProperties(" + this.labelAlignProperties + ").length!");
                PropertyListitemRenderer.log.error(ex.toString(), (Throwable)ex);
                throw ex;
            }
            if (this.dataFormatProperties != null && this.labelProperties.length != this.dataFormatProperties.length) {
                final IllegalArgumentException ex = new IllegalArgumentException("labelProperties(" + this.labelProperties + ").length != dataFormatProperties(" + this.dataFormatProperties + ").length!");
                PropertyListitemRenderer.log.error(ex.toString(), (Throwable)ex);
                throw ex;
            }
            for (int i = 0; i < this.labelProperties.length; ++i) {
                final String label = this.getPropertyValue(data, this.labelProperties[i]);
                final Listcell listcell = new Listcell();
                listcell.setParent((Component)item);
                if (this.labelValues != null && i < this.labelValues.length && this.labelValues[i] != null) {
                    final Component comp = this.labelValues[i].get(label);
                    if (comp != null) {
                        ((Component)comp.clone()).setParent((Component)listcell);
                    }
                    else {
                        listcell.setLabel("");
                    }
                }
                else {
                    if (this.dataTypeProperties == null) {
                        if (this.labelAlignProperties == null) {
                            listcell.setLabel(label);
                            continue;
                        }
                    }
                    try {
                        final Component comp = this.getFormattedPropertyValue(this.getPropertyObject(data, this.labelProperties[i]), i, this.dataTypeProperties, this.dataFormatProperties, this.labelAlignProperties);
                        comp.setParent((Component)listcell);
                    }
                    catch (Exception e) {
                        PropertyListitemRenderer.log.error(e.toString(), (Throwable)e);
                    }
                }
            }
        }
        if (this.selectedObjects != null && this.selectedObjects.contains(data)) {
            item.setSelected(true);
        }
    }
}
