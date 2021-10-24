package com.CouponProject.CouponProject.Repositories;

import com.CouponProject.CouponProject.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * interface for company methods
 */
@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    /**
     * method for checking if the company exists by email and password
     * @param email of the company
     * @param password of the company
     * @return boolean isCompanyExists
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * method for checking if the company name exists
     * @param name of the company
     * @return boolean isNameExists
     */
    boolean existsByName(String name);

    /**
     * method for checking if the company exists
     * @param email of the company
     * @return boolean isEmailExists
     */
    boolean existsByEmail(String email);

    /**
     * method for getting the company by the company name
     * @param name of the company
     * @return object of company
     */
    Company findByName(String name);

    /**
     * method for getting the company by the company email
     * @param email of the company
     * @return object of company
     */
    Company findByEmail(String email);

    /**
     * method for getting the company by the company id
     * @param companyId of the company
     * @return object of company
     */
    Company findById(int companyId);

    /**
     * query for deleting the coupon from the company_coupons table
     * @param couponId of the required coupon to delete
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `coupondb`.`company_coupons` WHERE coupons_id=:couponId", nativeQuery = true)
    void deleteCouponFromCompany(int couponId);
}
