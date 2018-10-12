package com.jyd.bms.tool;

import java.sql.Clob;
import java.util.StringTokenizer;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Hibernate;

public class StringUtils
{
    public static final String displayHTML(final String string) {
        if (org.apache.commons.lang.StringUtils.isEmpty(string)) {
            return "";
        }
        return string.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
    }
    
    public static final boolean isBlank(final String str) {
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }
    
    public static final boolean isNotBlank(final String str) {
        return org.apache.commons.lang.StringUtils.isNotBlank(str);
    }
    
    public static final boolean isQualifiedString(final String str) {
        return str != null && !str.trim().equals("");
    }
    
    public static final String getLastToken(final String value, final String delimiter) {
        String result = null;
        final StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
        while (tokenizer.hasMoreElements()) {
            result = (String)tokenizer.nextElement();
        }
        return result;
    }
    
    public static final String stringToBase64(final String source) throws Exception {
        return new String(Base64.encodeBase64(source.getBytes("UTF-8")), "UTF-8");
    }
    
    public static final String base64ToString(final String source) throws Exception {
        return new String(Base64.decodeBase64(source.getBytes("UTF-8")), "UTf-8");
    }
}
