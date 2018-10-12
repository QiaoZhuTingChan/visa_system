package com.jyd.bms.tool.exception;

public class DAOException extends Exception
{
    public DAOException(final String msg) {
        super(msg);
    }
    
    public DAOException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
