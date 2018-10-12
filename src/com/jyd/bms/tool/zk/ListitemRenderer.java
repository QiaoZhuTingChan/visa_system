package com.jyd.bms.tool.zk;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;

public abstract class ListitemRenderer extends PropertyListitemRenderer implements org.zkoss.zul.ListitemRenderer
{
    public ListitemRenderer() {
    }
    
    public ListitemRenderer(final String[] labelProperties) {
        this.labelProperties = labelProperties;
    }
    
    public ListitemRenderer(final String[] labelProperties, final String[] labelAlignProperties) {
        this.labelProperties = labelProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final String[] labelProperties, final String[] dataTypeProperties, final String[] dataFormatProperties, final String[] labelAlignProperties) {
        this.labelProperties = labelProperties;
        this.dataTypeProperties = dataTypeProperties;
        this.dataFormatProperties = dataFormatProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final String[] labelProperties, final Map<String, Component>[] labelValues) {
        this.labelProperties = labelProperties;
        this.labelValues = labelValues;
    }
    
    public ListitemRenderer(final List selectedObjects) {
        this.selectedObjects = selectedObjects;
    }
    
    public ListitemRenderer(final List selectedObjects, final String[] labelProperties) {
        this(selectedObjects);
        this.labelProperties = labelProperties;
    }
    
    public ListitemRenderer(final List selectedObjects, final String[] labelProperties, final String[] labelAlignProperties) {
        this(selectedObjects);
        this.labelProperties = labelProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final List selectedObjects, final String[] labelProperties, final String[] dataTypeProperties, final String[] dataFormatProperties, final String[] labelAlignProperties) {
        this(selectedObjects);
        this.labelProperties = labelProperties;
        this.dataTypeProperties = dataTypeProperties;
        this.dataFormatProperties = dataFormatProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final List selectedObjects, final String[] labelProperties, final Map<String, Component>[] labelValues) {
        this(selectedObjects);
        this.labelProperties = labelProperties;
        this.labelValues = labelValues;
    }
    
    public ListitemRenderer(final Object selected) {
        this.addSelected(selected);
    }
    
    public ListitemRenderer(final Object selected, final String[] labelProperties) {
        this(selected);
        this.labelProperties = labelProperties;
    }
    
    public ListitemRenderer(final Object selected, final String[] labelProperties, final String[] labelAlignProperties) {
        this(selected);
        this.labelProperties = labelProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final Object selected, final String[] labelProperties, final String[] dataTypeProperties, final String[] dataFormatProperties, final String[] labelAlignProperties) {
        this(selected);
        this.labelProperties = labelProperties;
        this.dataTypeProperties = dataTypeProperties;
        this.dataFormatProperties = dataFormatProperties;
        this.labelAlignProperties = labelAlignProperties;
    }
    
    public ListitemRenderer(final Object selected, final String[] labelProperties, final Map<String, Component>[] labelValues) {
        this(selected);
        this.labelProperties = labelProperties;
        this.labelValues = labelValues;
    }
    
    public void setSelected(final Object selected) {
        if (this.selectedObjects != null && !this.selectedObjects.isEmpty()) {
            this.selectedObjects.clear();
        }
        this.addSelected(selected);
    }
}
