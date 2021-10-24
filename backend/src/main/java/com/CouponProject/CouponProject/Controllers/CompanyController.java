package com.CouponProject.CouponProject.Controllers;

import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Beans.UserDetails;
import com.CouponProject.CouponProject.Enum.Category;
import com.CouponProject.CouponProject.Enum.ClientType;
import com.CouponProject.CouponProject.Exceptions.CompanyException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import com.CouponProject.CouponProject.Repositories.CompanyRepo;
import com.CouponProject.CouponProject.Repositories.CouponRepo;
import com.CouponProject.CouponProject.Service.CompanyService;
import com.CouponProject.CouponProject.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * class for the company controller
 */
@RestController
@RequestMapping("Company")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CompanyController {
    /**
     * field for the jwt creator
     */
    private final JWTutil jwtUtil;

    /**
     * field for the companyService
     */
    private final CompanyService companyService;

    /**
     * field for the coupon repository
     */
    private final CouponRepo couponRepo;

    /**
     * field for the company repository- will be used only for getting the companyId by email
     */
    private final CompanyRepo companyRepo;

    /**
     * Post method performing the login
     * @param userDetails which include email, clientType and password
     * @return httpStatus + new JWT
     */
    @PostMapping("login")
    private ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails) {
        try {
            if (companyService.login(userDetails.getEmail(), userDetails.getPassword()) &&
                    userDetails.getClientType().equals(ClientType.COMPANY.toString())
            ) {
                userDetails.setUserId(companyRepo.findByEmail(userDetails.getEmail()).getId());
                return ResponseEntity.ok()
                        .headers(getHeaders(jwtUtil.generateToken(userDetails)))
                        .body("login was successful");
            }
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for adding new coupon
     * @param token JWT for authorization
     * @param coupon to add
     * @return httpStatus + new JWT
     */
    @PostMapping("addCoupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name="Authorization") String token, @RequestBody Coupon coupon) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                try {
                    companyService.addCoupon(coupon);
                } catch (CompanyException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The coupon was added");
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for updating coupon
     * @param token JWT for authorization
     * @param coupon to update
     * @return httpStatus + new JWT
     */
    @PostMapping("updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name="Authorization") String token, @RequestBody Coupon coupon) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                try {
                    companyService.updateCoupon(coupon);
                } catch (CompanyException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The coupon was updated");
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete method foe deleting coupon
     * @param token JWT for authorization
     * @param couponId to find the required coupon
     * @return httpStatus + new JWT
     */
    @DeleteMapping("deleteCoupon/{couponId}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name="Authorization") String token, @PathVariable int couponId) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                try {
                    companyService.deleteCoupon(couponId);
                } catch (CompanyException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The coupon was deleted");
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all company coupons
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCompanyCoupons")
    public ResponseEntity<?> getAllCompanyCoupons(@RequestHeader(name="Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(companyService.getCompanyCoupons());
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all company coupons by category
     * @param token JWT for authorization
     * @param category to find the required coupons
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCompanyCouponsByCategory/{category}")
    public ResponseEntity<?> getAllCompanyCouponsByCategory(@RequestHeader(name="Authorization") String token, @PathVariable Category category) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(companyService.getCompanyCouponsByCategory(category));
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all company coupons by price
     * @param token JWT for authorization
     * @param maxPrice to find the required coupons
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCompanyCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getAllCompanyCouponsByMaxPrice(@RequestHeader(name="Authorization") String token, @PathVariable double maxPrice) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(companyService.getCompanyCouponsMaxPrice(maxPrice));
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting company details
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getCompanyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name="Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(companyService.getCompanyDetails());
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting coupon details
     * @param token JWT for authorization
     * @param couponId of the required coupon
     * @return httpStatus + new JWT
     */
    @PostMapping("getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name="Authorization") String token, @PathVariable int couponId) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("COMPANY")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(couponRepo.findById(couponId));
            }
            return new ResponseEntity<>("You are not logged in as Company",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * method for getting a new token based on the details of the previous token
     * @param token which was received and from which the new token will be created
     * @return Authorization + new token
     */
    private HttpHeaders getHeaders(String token){
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setClientType((String)jwtUtil.extractAllClaims(token).get("clientType"));
        userDetails.setUserId((int)jwtUtil.extractAllClaims(token).get("userId"));
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }
}
