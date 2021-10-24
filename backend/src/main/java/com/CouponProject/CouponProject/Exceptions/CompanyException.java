package com.CouponProject.CouponProject.Exceptions;

/**
 * class for CompanyException
 */
public class CompanyException extends Exception{
    /**
     * template for different exceptions thrown by the methods of CompanyService
     * @param message will be changed according to the relevant method
     */
    public CompanyException(String message){
        super(message);
    }
}
