package com.CouponProject.CouponProject.Exceptions;

/**
 * class for LoginException
 */
public class LoginException extends Exception{
    /**
     * template for login exceptions
     * @param message that will be sent
     */
    public LoginException(String message){
        super(message);
    }
}
