package com.CouponProject.CouponProject.Advice;

import com.CouponProject.CouponProject.Exceptions.AdminException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Exception for Admin
 */
@RestController
@ControllerAdvice
public class AdminRestException {
    /**
     * template for different exceptions thrown by the methods of AdminService
     * @param err will be changed according to the relevant method
     * @return new customized error
     */
    @ExceptionHandler(value = {AdminException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetails handleException(Exception err) {
        return new ErrorDetails("Admin error:", err.getMessage());
    }
}
