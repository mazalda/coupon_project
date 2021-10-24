package com.CouponProject.CouponProject.Controllers;

import com.CouponProject.CouponProject.Repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * class for the guest controller
 */
@RestController
@RequestMapping("Guest")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestController {
    /**
     * field for the coupon repository
     */
    private final CouponRepo couponRepo;

    /**
     * Post method foe getting all existing coupons
     * @return httpStatus + all coupons
     */
    @PostMapping("getAllCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(couponRepo.findAll(),HttpStatus.ACCEPTED);
    }

    /**
     * Post method foe getting one coupon
     * @param couponId of the required coupon
     * @return httpStatus + one coupon
     */
    @PostMapping("getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupons(@PathVariable int couponId) {
        return new ResponseEntity<>(couponRepo.findById(couponId),HttpStatus.ACCEPTED);
    }
}
