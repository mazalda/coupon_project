package com.CouponProject.CouponProject.Service;

import com.CouponProject.CouponProject.Exceptions.LoginException;
import com.CouponProject.CouponProject.Repositories.CompanyRepo;
import com.CouponProject.CouponProject.Repositories.CouponRepo;
import com.CouponProject.CouponProject.Repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * abstract class for ClientService
 */
public abstract class ClientService {
    /**
     * field with a ready object of CompanyRepo
     */
    @Autowired
    protected CompanyRepo companyRepo;

    /**
     * field with a ready object of CustomerRepo
     */
    @Autowired
    protected CustomerRepo customerRepo;

    /**
     * field with a ready object of CouponRepo
     */
    @Autowired
    protected CouponRepo couponRepo;

    /**
     * login method
     * @param email of the clientType
     * @param password of the clientType
     * @return boolean for correct login/wrong login
     * @throws LoginException if the email/password are wrong
     */
    public abstract boolean login(String email, String password) throws LoginException;
}
