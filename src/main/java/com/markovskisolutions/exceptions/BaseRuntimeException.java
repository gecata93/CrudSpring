/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.markovskisolutions.exceptions;

/**
 *
 * @author User
 */
public class BaseRuntimeException extends RuntimeException{
    
   private ErrorCode errorCode = new ErrorCode("1000");

    public BaseRuntimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(Exception exception) {
        super(exception);
    }
    
    public BaseRuntimeException() {
        
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }  
}
