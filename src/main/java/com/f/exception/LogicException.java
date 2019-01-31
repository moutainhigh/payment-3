package com.f.exception;/*
 *@ClassName LogicExcetion
 *@Author 建国
 *@Date 1/22/19 4:23 PM
 */

public class LogicException extends Exception{
    private String msg;
    private int code;
    public LogicException(String msg, int code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

}
