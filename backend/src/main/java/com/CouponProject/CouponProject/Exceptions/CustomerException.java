package com.CouponProject.CouponProject.Exceptions;

/**
 * class for CustomerException
 */
public class CustomerException extends Exception{
    /**
     * template for different exceptions thrown by the methods of CustomerService
     * @param message will be changed according to the relevant method
     */
    public CustomerException(String message){
        super(message);
    }
}
