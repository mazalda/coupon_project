package com.CouponProject.CouponProject.Repositories;

import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * interface for coupon methods
 */
@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    /**
     * method for checking if the coupon exists
     * @param companyId of the coupon
     * @param title of the coupon
     * @return boolean isCouponExists
     */
    boolean existsByCompanyIdAndTitle(int companyId, String title);

    /**
     * method for getting all company coupons
     * @param companyId of the required company
     * @return list of coupons
     */
    List<Coupon> findByCompanyId(int companyId);

    /**
     * method for getting all company coupons by a specific category
     * @param companyId of the required company
     * @param category the required category
     * @return list of coupons
     */
    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    /**
     * method for getting the coupon by the coupon id
     * @param couponId of the required coupon
     * @return object of coupon
     */
    Coupon findById (int couponId);

    /**
     * query for checking if a customer already purchased a specific coupon
     * @param customerId of the customer
     * @param couponId of the required coupon
     * @return 0 in case the customer didn't purchase the coupon
     */
    @Query(value = "SELECT COUNT(*) FROM `coupondb`.`customer_bought_coupons` WHERE customer_id=:customerId AND coupon_id=:couponId", nativeQuery = true)
    int findByCustomerIdAndCouponId(int customerId, int couponId);
}
