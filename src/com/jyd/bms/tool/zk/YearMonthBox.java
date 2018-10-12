package com.jyd.bms.tool.zk;

import java.util.Calendar;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Intbox;

import com.jyd.bms.tool.DateUtils;

public class YearMonthBox extends Intbox
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yearMonth;
    private String yearMonthNoSeparator;
    private boolean error;
    
    public YearMonthBox() {
        this.setMaxlength(6);
        this.setConstraint("no negative,no zero");
        this.addForward("onBlur", (Component)this, "onYearMonthBoxBlur");
    }
    
    public void onYearMonthBoxBlur() {
        final Integer val = this.getValue();
        this.error = true;
        this.yearMonth = null;
        this.yearMonthNoSeparator = null;
        if (val != null) {
            final String vaz = val.toString();
            if (vaz.length() == 6 && Integer.valueOf(vaz.substring(4)) < 13 && Integer.valueOf(vaz.substring(4)) > 0) {
                this.processYearMonth(val);
            }
        }
    }
    
    private void processYearMonth(final Integer val) {
        this.error = false;
        final Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtils.getFirstOrEndDayOfMonth(true));
        cal.set(1, Integer.valueOf(val.toString().substring(0, 4)));
        cal.set(2, val - Integer.valueOf(val.toString().substring(0, 4)) * 100 - 1);
        this.yearMonth = DateUtils.formatYearMonth(cal.getTime());
        this.yearMonthNoSeparator = DateUtils.formatYearMonthNoSeparator(cal.getTime());
    }
    
    public String getYearMonth() {
        return this.yearMonth;
    }
    
    public void setNow() {
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(1);
        final int month = cal.get(2) + 1;
        final int yearMonthValue = year * 100 + month;
        this.setValue(yearMonthValue);
        this.onYearMonthBoxBlur();
    }
    
    public void setDate(final Date date) {
        if (date != null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            final int year = cal.get(1);
            final int month = cal.get(2) + 1;
            final int yearMonthValue = year * 100 + month;
            this.setValue(yearMonthValue);
            this.onYearMonthBoxBlur();
        }
    }
    
    public String getYearMonthNoSeparator() {
        return this.yearMonthNoSeparator;
    }
    
    public boolean isError() {
        return this.error;
    }
}

