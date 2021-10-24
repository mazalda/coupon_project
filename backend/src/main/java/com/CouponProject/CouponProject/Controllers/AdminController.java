package com.CouponProject.CouponProject.Controllers;

import com.CouponProject.CouponProject.Beans.Company;
import com.CouponProject.CouponProject.Beans.Customer;
import com.CouponProject.CouponProject.Beans.UserDetails;
import com.CouponProject.CouponProject.Enum.ClientType;
import com.CouponProject.CouponProject.Exceptions.AdminException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import com.CouponProject.CouponProject.Repositories.CouponRepo;
import com.CouponProject.CouponProject.Service.AdminService;
import com.CouponProject.CouponProject.Util.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * class for the admin controller
 */
@RestController
@RequestMapping("Admin")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminController {
    /**
     * field for the adminService
     */
    private final AdminService adminService;

    /**
     * field for the jwt creator
     */
    private final JWTutil jwtUtil;

    /**
     * field for the coupon repository
     */
    private final CouponRepo couponRepo;

    /**
     * Post method performing the login
     * @param userDetails which include email, clientType and password
     * @return httpStatus + new JWT
     */
    @PostMapping("login")
    private ResponseEntity<?> userLogin(@RequestBody UserDetails userDetails) {
        try {
            if (adminService.login(userDetails.getEmail(), userDetails.getPassword()) &&
                    userDetails.getClientType().equals(ClientType.ADMINISTRATOR.toString())) {
                userDetails.setUserId(0);
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
     * Post method for adding new company
     * @param token JWT for authorization
     * @param company to add
     * @return httpStatus + new JWT
     */
    @PostMapping("addCompany")
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.addCompany(company);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The company was added");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for updating company
     * @param token JWT for authorization
     * @param company to update
     * @return httpStatus + new JWT
     */
    @PostMapping("updateCompany")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.updateCompany(company);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The company was updated");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete method foe deleting company
     * @param token JWT for authorization
     * @param companyId to find the required company
     * @return httpStatus + new JWT
     */
    @DeleteMapping("deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.deleteCompany(companyId);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The company was deleted");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all companies
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(adminService.getAllCompanies());
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method getting one company
     * @param token JWT for authorization
     * @param companyId of the required company
     * @return httpStatus + new JWT
     */
    @PostMapping("getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int companyId) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                Company companyToReturn = null;
                try {
                    companyToReturn = adminService.getOneCompany(companyId);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(companyToReturn);
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for adding customer
     * @param token JWT for authorization
     * @param customer to add
     * @return httpStatus + new JWT
     */
    @PostMapping("addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.addCustomer(customer);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The customer was added");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for updating customer
     * @param token JWT for authorization
     * @param customer to update
     * @return httpStatus + new JWT
     */
    @PostMapping("updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.updateCustomer(customer);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The customer was updated");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Delete method for deleting company
     * @param token JWT for authorization
     * @param customerId to find the required customer
     * @return httpStatus + new JWT
     */
    @DeleteMapping("deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                try {
                    adminService.deleteCustomer(customerId);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body("The customer was deleted");
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting all customers
     * @param token JWT for authorization
     * @return httpStatus + new JWT
     */
    @PostMapping("getAllCustomers")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(adminService.getAllCustomers());
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Psost method for getting one customer
     * @param token JWT for authorization
     * @param customerId of the required customer
     * @return httpStatus + new JWT
     */
    @PostMapping("getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int customerId) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                Customer customerToGet = null;
                try {
                    customerToGet = adminService.getOneCustomer(customerId);
                } catch (AdminException e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(customerToGet);
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post method for getting coupon details
     * @param token JWT for authorization
     * @param couponId of the required coupon
     * @return httpStatus + new JWT
     */
    @PostMapping("getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int couponId) {
        if (jwtUtil.validateToken(token)) {
            if (jwtUtil.extractClientType(token).equals("ADMINISTRATOR")) {
                return ResponseEntity.ok()
                        .headers(getHeaders(token))
                        .body(couponRepo.findById(couponId));
            }
            return new ResponseEntity<>("You are not logged in as Admin", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Invalid Token", HttpStatus.UNAUTHORIZED);
    }

    /**
     * method for getting a new token based on the details of the previous token
     * @param token which was received and from which the new token will be created
     * @return Authorization + new token
     */
    private HttpHeaders getHeaders(String token) {
        //create new userDetail and DI
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(jwtUtil.extractEmail(token));
        userDetails.setClientType((String) jwtUtil.extractAllClaims(token).get("clientType"));
        //send ok with header of new token
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", jwtUtil.generateToken(userDetails));
        return httpHeaders;
    }
}
