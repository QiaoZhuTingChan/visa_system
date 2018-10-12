package com.jyd.bms.tool.zk;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Listitem;

public class Listbox extends org.zkoss.zul.Listbox
{
    public List getSelectedValues() {
        final List selectedObjects = new ArrayList();
        for (final Object itemObj : this.getSelectedItems()) {
            final Listitem item = (Listitem)itemObj;
            selectedObjects.add(item.getValue());
        }
        return selectedObjects;
    }
    
    public Object getSelectedValue() {
        final Listitem item = this.getSelectedItem();
        if (item != null) {
            return item.getValue();
        }
        return null;
    }
}
