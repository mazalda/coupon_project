package com.CouponProject.CouponProject.Repositories;

import com.CouponProject.CouponProject.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * interface for customer methods
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    /**
     * method for checking if the customer exists by email and password
     * @param email of the customer
     * @param password of the customer
     * @return boolean isCustomerExists
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * method for checking if the customer exists
     * @param email of the customer
     * @return boolean isEmailExists
     */
    boolean existsByEmail(String email);

    /**
     * method for getting the customer by the customer id
     * @param customerId of the customer
     * @return object of customer
     */
    Customer findById(int customerId);

    /**
     * method for getting the customer by the customer email
     * @param email of the customer
     * @return object of customer
     */
    Customer findByEmail(String email);
}
