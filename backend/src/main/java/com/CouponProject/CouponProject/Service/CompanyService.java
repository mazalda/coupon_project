package com.CouponProject.CouponProject.Service;

import com.CouponProject.CouponProject.Beans.Company;
import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Enum.Category;
import com.CouponProject.CouponProject.Exceptions.CompanyException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class for company service
 */
@Service
@NoArgsConstructor
@Data
public class CompanyService extends ClientService {
    /**
     * field for the companyId which logged in the system with the service
     */
    private int companyId;

    /**
     * login of the company
     * @param email of the company
     * @param password of the company
     * @return boolean for correct login/wrong login
     * @throws LoginException if the email/password are wrong
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            System.out.println("Login was successful :)\n");
            this.companyId = companyRepo.findByEmail(email).getId();
            System.out.printf("Yor are now logged in with Company Id %d\n", this.companyId);
            return true;
        } else {
            throw new LoginException("Email/Password are incorrect\n");
        }
    }

    /**
     * method for adding a coupon to the company
     *
     * @param coupon object of Coupon
     * @throws CompanyException in case the company already has a coupon with the same title
     *                          / the amount of the coupon is 0/the coupon is already expires
     */
    public void addCoupon(Coupon coupon) throws CompanyException {
        Date currentTime = new Date(System.currentTimeMillis());
        if (coupon.getEndDate().after(currentTime)) {
            if (coupon.getAmount() > 0) {
                if (!couponRepo.existsByCompanyIdAndTitle(coupon.getCompanyId(), coupon.getTitle())) {
                    couponRepo.save(coupon);
                    Company companyToUpdate = companyRepo.getOne(this.companyId);
                    companyToUpdate.getCoupons().add(coupon);
                    companyRepo.saveAndFlush(companyToUpdate);
                    System.out.printf("The coupon of Company_Id %d with the title '%s' was added successfully\n", coupon.getCompanyId(), coupon.getTitle());
                } else {
                    throw new CompanyException("This company already has a coupon with the same title\n");
                }
            } else {
                throw new CompanyException("The amount of the coupon must be more than 0!\n");
            }
        } else {
            throw new CompanyException("You cannot enter coupon with this expiry date :(\n");
        }
    }

    /**
     * method for updating a coupon
     *
     * @param coupon object of Coupon
     * @throws CompanyException in case the start date is update to be expired/ the title is updated to an existing title
     */
    public void updateCoupon(Coupon coupon) throws CompanyException {
        Date currentTime = new Date(System.currentTimeMillis());
        if (!couponRepo.existsByCompanyIdAndTitle(coupon.getCompanyId(), coupon.getTitle()) || couponRepo.findById(coupon.getId()).getTitle().equals(coupon.getTitle())) {
            if (coupon.getStartDate().after(currentTime) || (coupon.getStartDate().equals(currentTime)) || coupon.getStartDate().equals(couponRepo.findById(coupon.getId()).getStartDate())) {
                if (coupon.getEndDate().after(currentTime) || coupon.getEndDate().equals(currentTime)) {
                    couponRepo.saveAndFlush(coupon);
                    System.out.printf("Coupon_Id %d was updated successfully\n", coupon.getId());
                } else{
                    throw new CompanyException("You cannot change the end Date to a date before now!:(\n");
                }
            } else {
                throw new CompanyException("You cannot change the start Date to a date before now!:(\n");
            }
        } else {
            throw new CompanyException("This company already has a coupon with the same title :(\n");
        }
    }

    /**
     * method for deleting a coupon
     * @param couponId of the coupon we want to delete
     * @throws CompanyException in case trying to delete none existing couponId
     */
    public void deleteCoupon(int couponId) throws CompanyException {
        if (couponRepo.findById(couponId) != null) {
            companyRepo.deleteCouponFromCompany(couponId);
            couponRepo.deleteById(couponId);
            System.out.printf("Coupon_Id %d was deleted successfully\n", couponId);
        } else {
            throw new CompanyException("There is no coupon with this id\n");
        }
    }

    /**
     * method for getting all company's coupons
     * @return List of coupons
     */
    public List<Coupon> getCompanyCoupons() {
        return couponRepo.findByCompanyId(this.companyId);
    }

    /**
     * method for getting all company's coupons by a specific category
     * @param category Category
     * @return List of coupons
     */
    public List<Coupon> getCompanyCouponsByCategory(Category category) {
        return couponRepo.findByCompanyIdAndCategory(this.companyId, category);
    }

    /**
     * method for getting all company's coupons up to a limited price
     * @param maxPrice the limit price
     * @return List of coupons
     */
    public List<Coupon> getCompanyCouponsMaxPrice(double maxPrice) {
        List<Coupon> couponsByPrice = getCompanyCoupons().stream().filter(item -> item.getPrice() <= maxPrice).collect(Collectors.toList());
        return couponsByPrice;
    }

    /**
     * method for getting the details of the company which logged in
     * @return object of Company
     */
    public Company getCompanyDetails() {
        return companyRepo.findById(this.companyId);
    }
}
