package com.CouponProject.CouponProject.Advice;

import com.CouponProject.CouponProject.Exceptions.CompanyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Exception for Company
 */
@RestController
@ControllerAdvice
public class CompanyRestException {
    /**
     * template for different exceptions thrown by the methods of CompanyService
     * @param err will be changed according to the relevant method
     * @return new customized error
     */
    @ExceptionHandler(value = {CompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleException(Exception err) {
        return new ErrorDetails("Company error:", err.getMessage());
    }
}
