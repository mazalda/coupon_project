package com.CouponProject.CouponProject.Controllers;

import com.CouponProject.CouponProject.Beans.UserDetails;
import com.CouponProject.CouponProject.Enum.Category;
import com.CouponProject.CouponProject.Enum.ClientType;
import com.CouponProject.CouponProject.Exceptions.CustomerException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import com.CouponProject.CouponProject.Repositories.CouponRepo;
import com.CouponProject.CouponProject.Repositories.CustomerRepo;
import com.CouponProject.CouponProject.Service.CustomerService;
import com.CouponProject.CouponProject.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * class for the customer controller
 */
@RestController
@RequestMapping("Customer")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController {
    /**
     * field for the customerService
     */
    private final CustomerService customerService;

    /**
     * field for the jwt creator
     */
    private final JWTutil jwtUtil;

    /**
     * field for the coupon repository
     */
    private final CouponRepo couponRepo;

    /**
     * field for the customer repository- will be used only for getting the customerId by email
     */
    private final CustomerRepo customerRepo;

    /**
     * Post method performing the login
     * @param userDetails which include email, clientType and password
     * @return httpStatus + new JWT
     */
    @PostMapping("login")
    private ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails) {
        try {
            if (customerService.login(userDetails.getEmail(), userDetails.getPassword()) &&
                    userDetails.getClientType().equals(ClientType.CUSTOMER.toString())
            ) {
                userDetails.setUserId(customerRepo.findByEmail(userDetails.getEmail()).getId());
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
     * Post method for purchasing a coupon
     * @param token JWT for authorization
     * @param couponId of the required coupon
     * @return httpStatus + new JWT
     */
    @PostMapping("purchaseCoupon/{couponId}")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name="Authorization") String token, @PathVariable int couponId) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                try {
                    customerService.purchaseCoupon(couponId);
                } catch (CustomerException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The coupon was purchased");
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all customer coupons
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getCustomerCoupons")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name="Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(customerService.getCustomerCoupons());
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all customer coupons by category
     * @param token JWT for authorization
     * @param category of the required coupons
     * @return httpStatus + new JWT
     */
    @PostMapping("getCustomerCouponsByCategory/{category}")
    public ResponseEntity<?> getCustomerCouponsByCategory(@RequestHeader(name="Authorization") String token, @PathVariable Category category) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(customerService.getCustomerCouponsByCategory(category));
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all customer coupons
     * @param token JWT for authorization
     * @param maxPrice of the required coupons
     * @return httpStatus + new JWT
     */
    @PostMapping("getCustomerCouponsByPrice/{maxPrice}")
    public ResponseEntity<?> getCustomerCouponsByPrice(@RequestHeader(name="Authorization") String token, @PathVariable double maxPrice) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(customerService.getCustomerCouponsByMaxPrice(maxPrice));
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting customer details
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getCustomerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name="Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(customerService.getCustomerDetails());
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all existing coupons
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name="Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(couponRepo.findAll());
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
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
            if(jwtUtil.extractClientType(token).equals("CUSTOMER")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(couponRepo.findById(couponId));
            }
            return new ResponseEntity<>("You are not logged in as Customer",HttpStatus.UNAUTHORIZED);
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
