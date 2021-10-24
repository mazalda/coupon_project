package com.CouponProject.CouponProject.Service;

import com.CouponProject.CouponProject.Enum.ClientType;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class for loginManager
 */
@Service
@NoArgsConstructor
public class LoginManager {
    /**
     * field with a ready object of AdminService
     */
    @Autowired
    AdminService adminService;

    /**
     * field with a ready object of CompanyService
     */
    @Autowired
    CompanyService companyService;

    /**
     * field with a ready object of CompanyService
     */
    @Autowired
    CustomerService customerService;

    /**
     * method for performing the login to the system
     * @param email of the object
     * @param password of the object
     * @param clientType of the object
     * @return the correct clientService which made the right login
     * @throws LoginException if the email/password are wrong
     */
    public ClientService login(String email, String password, ClientType clientType) throws LoginException {
        switch (clientType) {
            case ADMINISTRATOR:
                if (adminService.login(email, password)) {
                    return adminService;
                } else {
                    System.out.println("Email/Password are incorrect\n");
                    return null;
                }
            case COMPANY:
                if (companyService.login(email, password)) {
                    return companyService;
                } else {
                    System.out.println("Email/Password are incorrect\n");
                    return null;
                }
            case CUSTOMER:
                if (customerService.login(email, password)) {
                    return customerService;
                } else {
                    System.out.println("Email/Password are incorrect\n");
                    return null;
                }
            default:
                return null;
        }
    }
}
