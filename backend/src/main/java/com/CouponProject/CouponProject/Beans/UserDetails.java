package com.CouponProject.CouponProject.Beans;

import lombok.Data;

/**
 * Class for creating the UserDetails for the connection with the client side
 */
@Data
public class UserDetails {
    /**
     * field for the client type
     */
    private String clientType;

    /**
     * field for the email
     */
    private String email;

    /**
     * field for the password
     */
    private String password;

    /**
     * field for the userId
     */
    private int userId;
}
