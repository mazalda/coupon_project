package com.CouponProject.CouponProject.Service;

import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Beans.Customer;
import com.CouponProject.CouponProject.Enum.Category;
import com.CouponProject.CouponProject.Exceptions.CustomerException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for customer service
 */
@Service
@NoArgsConstructor
@Data
public class CustomerService extends ClientService {
    /**
     * field for the customerId which logged in the system with the service
     */
    private int customerId;

    /**
     * login method
     * @param email of the customer
     * @param password of the customer
     * @return boolean for correct login/wrong login
     * @throws LoginException if the email/password are wrong
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            System.out.println("Login was successful :)\n");
            this.customerId = customerRepo.findByEmail(email).getId();
            System.out.printf("You are now logged in with Customer Id %d\n", this.customerId);
            return true;
        } else {
            throw new LoginException("Email/Password are incorrect\n");
        }
    }

    /**
     * method foe adding a purchase to the customer_vs_coupons table
     * only if the adding process was successful:
     * End_Date- not expired, Amount more than 0, and if the customer didn't buy the same coupon already
     * @param couponId of the required Coupon
     * @throws CustomerException in case one of the above is in receives false
     */
    public void purchaseCoupon(int couponId) throws CustomerException {
        Date currentTime = new Date(System.currentTimeMillis());
        Coupon coupon = couponRepo.getOne(couponId);
        if (couponRepo.findByCustomerIdAndCouponId(this.customerId, coupon.getId()) == 0) {
            if (coupon.getAmount() > 0) {
                if (coupon.getEndDate().after(currentTime)){
                    Customer customerToUpdate = customerRepo.getOne(this.customerId);
                    customerToUpdate.getBoughtCoupons().add(coupon);
                    customerRepo.saveAndFlush(customerToUpdate);
                    coupon.setAmount(coupon.getAmount()-1);
                    couponRepo.saveAndFlush(coupon);
                    System.out.printf("Coupon %d was successfully purchased\n", coupon.getId());
                } else {
                    throw new CustomerException("This coupon was expired and should be erased from the database..\n");
                }
            } else {
                throw new CustomerException("The amount of Coupon is 0... find another coupon :)\n");
            }
        } else {
            throw new CustomerException("This coupon was already purchased by the customer\n");
        }
    }

    /**
     * method for getting all customer coupons
     * @return List of coupons
     */
    public List<Coupon> getCustomerCoupons() {
        return customerRepo.getOne(this.customerId).getBoughtCoupons();
    }

    /**
     * method for getting all customer's coupon by a specific category
     * @param category Category
     * @return List of coupons
     */
    public List<Coupon> getCustomerCouponsByCategory(Category category) {
        List<Coupon> allCoupons = customerRepo.getOne(this.customerId).getBoughtCoupons();
        List<Coupon> couponsByCategory = allCoupons.stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());
        return couponsByCategory;
    }

    /**
     * method for getting all customer's coupon up to a limited price
     * @param maxPrice the limit price required
     * @return List of coupons
     */
    public List<Coupon> getCustomerCouponsByMaxPrice(double maxPrice) {
        List<Coupon> allCoupons = customerRepo.getOne(this.customerId).getBoughtCoupons();
        List<Coupon> couponsByPrice = allCoupons.stream().filter(item -> item.getPrice() <= maxPrice).collect(Collectors.toList());
        return couponsByPrice;
    }

    /**
     * method for getting the details of the customer that made the login
     * @return object of Customer
     */
    public Customer getCustomerDetails() {
        return customerRepo.findById(this.customerId);
    }
}
