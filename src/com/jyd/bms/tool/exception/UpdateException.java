package com.jyd.bms.tool.exception;

public class UpdateException extends Exception
{
    public UpdateException(final String msg) {
        super(msg);
    }
    
    public UpdateException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
