package com.jyd.bms.tool.exception;

public class CommitException extends Exception
{
    public CommitException(final String msg) {
        super(msg);
    }
    
    public CommitException(final String msg, final Throwable e) {
        super(msg, e);
    }
}
