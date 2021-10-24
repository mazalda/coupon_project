package com.CouponProject.CouponProject.Advice;

import com.CouponProject.CouponProject.Exceptions.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Exception for Customer
 */
@RestController
@ControllerAdvice
public class CustomerRestException {
    /**
     * template for different exceptions thrown by the methods of CustomerService
     * @param err will be changed according to the relevant method
     * @return new customized error
     */
    @ExceptionHandler(value = {CustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleException(Exception err) {
        return new ErrorDetails("Customer error:", err.getMessage());
    }
}
