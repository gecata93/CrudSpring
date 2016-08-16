/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.markovskisolutions.exceptions.handler;

import com.markovskisolutions.exceptions.ErrorCode;
import com.markovskisolutions.exceptions.ErrorMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author User
 */
@ControllerAdvice
public class ExceptionController {
    
    private static final Logger LOG = Logger.getLogger(ExceptionController.class.getName());

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        LOG.log(Level.WARNING, ex.getMessage(), ex);

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        errMsg.setErrorCode(new ErrorCode("1001", ex.getMessage()));
        
        return errMsg;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NumberFormatException.class})
    @ResponseBody
    public ErrorMessage handleNumberFormatException(NumberFormatException ex) {
        LOG.log(Level.WARNING, ex.getMessage(), ex);

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errMsg.setErrorCode(new ErrorCode("1002", "Please enter a propper id"));
        
        return errMsg;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NullPointerException.class})
    @ResponseBody
    public ErrorMessage handleNullException(NullPointerException ex) {
        LOG.log(Level.WARNING, ex.getMessage(), ex);

        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errMsg.setErrorCode(new ErrorCode("1000", "This person does not exist"));
        
        return errMsg;
    }

}
