package com.CouponProject.CouponProject.Advice;

import com.CouponProject.CouponProject.Exceptions.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Exception for login
 */
@RestController
@ControllerAdvice
public class LoginRestException {
    /**
     * template for different exceptions thrown by the login methods
     * @param err will be changed according to the relevant method
     * @return new customized error
     */
    @ExceptionHandler(value = {LoginException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleException(Exception err) {
        return new ErrorDetails("Login error:", err.getMessage());
    }
}
