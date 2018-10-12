package com.jyd.bms.tool.exception;

public class DeleteException extends Exception
{
    public DeleteException(final String msg) {
        super(msg);
    }
    
    public DeleteException(final String msg, final Throwable ex) {
        super(msg, ex);
    }
}
