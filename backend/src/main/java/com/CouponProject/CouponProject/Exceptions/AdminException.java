package com.CouponProject.CouponProject.Exceptions;

/**
 * class for AdminException
 */
public class AdminException extends Exception{
    /**
     * template for different exceptions thrown by the methods of AdminService
     * @param message will be changed according to the relevant method
     */
    public AdminException(String message){
        super(message);
    }
}
