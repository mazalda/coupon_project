package com.CouponProject.CouponProject.CLR;

import com.CouponProject.CouponProject.Beans.Company;
import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Beans.Customer;
import com.CouponProject.CouponProject.Enum.Category;
import com.CouponProject.CouponProject.Enum.ClientType;
import com.CouponProject.CouponProject.Exceptions.AdminException;
import com.CouponProject.CouponProject.Exceptions.CompanyException;
import com.CouponProject.CouponProject.Exceptions.CustomerException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import com.CouponProject.CouponProject.Service.AdminService;
import com.CouponProject.CouponProject.Service.CompanyService;
import com.CouponProject.CouponProject.Service.CustomerService;
import com.CouponProject.CouponProject.Service.LoginManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * Class for the testing
 */
@Service
@Order(1)
@RequiredArgsConstructor
public class Test implements CommandLineRunner {
    /**
     * field with a ready object of LoginManager
     */
    @Autowired
    LoginManager loginManager;

    /**
     * method for all the required testing
     * @param args default parameter in the main method
     */
    @Override
    public void run(String... args) {
        try {
            //Login with admin
            System.out.println("------------------------------------------------");
            System.out.println("------------Login with AdminService-------------");
            System.out.println("" +
                    " █████╗ ██████╗ ███╗   ███╗██╗███╗   ██╗\n" +
                    "██╔══██╗██╔══██╗████╗ ████║██║████╗  ██║\n" +
                    "███████║██║  ██║██╔████╔██║██║██╔██╗ ██║\n" +
                    "██╔══██║██║  ██║██║╚██╔╝██║██║██║╚██╗██║\n" +
                    "██║  ██║██████╔╝██║ ╚═╝ ██║██║██║ ╚████║\n" +
                    "╚═╝  ╚═╝╚═════╝ ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝");
            System.out.println();

            //Wrong login as admin
            //Expecting to get an exception
            System.out.println("Attempt to login as admin with incorrect email and password:");
            try {
                AdminService wrongAdminService = (AdminService) loginManager.login("admin@admi.com", "dmin", ClientType.ADMINISTRATOR);
            } catch (LoginException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Correct login as admin
            System.out.println("Correct login as admin:");
            AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            System.out.println("=================================================");
            System.out.println();

            //Creating several companies
            Company company1 = Company.builder()
                    .name("Company1")
                    .email("company1@gmail.com")
                    .password("11234")
                    .build();

            Company company2 = Company.builder()
                    .name("Company2")
                    .email("company2@gmail.com")
                    .password("21234")
                    .build();

            Company company3 = Company.builder()
                    .name("Company3")
                    .email("company3@gmail.com")
                    .password("31234")
                    .build();

            Company company4 = Company.builder()
                    .name("Company4")
                    .email("company4@gmail.com")
                    .password("41234")
                    .build();

            Company company5 = Company.builder()
                    .name("Company5")
                    .email("company5@gmail.com")
                    .password("51234")
                    .build();

            Company company6 = Company.builder()
                    .name("Company6")
                    .email("company6@gmail.com")
                    .password("61234")
                    .build();

            //Adding the companies by the admin
            System.out.println("Addition of companies:");
            adminService.addCompany(company1);
            adminService.addCompany(company2);
            adminService.addCompany(company3);
            adminService.addCompany(company4);
            adminService.addCompany(company5);
            adminService.addCompany(company6);
            System.out.println("=================================================");
            System.out.println();

            //Addition of a company with the same name or email
            System.out.println("Attempt to add a company with an existing name:");
            Company company7 = Company.builder()
                    .name("Company1")
                    .email("company7@gmail.com")
                    .password("71234")
                    .build();
            try {
                adminService.addCompany(company7);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            System.out.println("Attempt to add a company with an existing email:");
            Company company8 = Company.builder()
                    .name("Company8")
                    .email("company1@gmail.com")
                    .password("11234")
                    .build();
            try {
                adminService.addCompany(company8);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Updating one of the company details
            company2.setEmail("company9@gmail.com");
            company2.setPassword("91234");

            //Updating the new company details in the database by the admin
            System.out.println("Updating a company:");
            adminService.updateCompany(company2);
            System.out.println("=================================================");
            System.out.println();

            //Updating a company with an exist email
            company3.setEmail("company4@gmail.com");
            company3.setPassword("31234");

            //Updating the new company details in the database by the admin
            System.out.println("Attempt to update a company with an existing email:");
            try {
                adminService.updateCompany(company3);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Updating a company while changing it's name
            company3.setName("mazal");

            //Updating the new company details in the database by the admin
            System.out.println("Attempt to update a company while changing it's name:");
            try {
                adminService.updateCompany(company3);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Updating a company while changing it's id
            company4.setId(15);

            //Updating the new company details in the database by the admin
            System.out.println("Attempt to update a company while changing it's Id:");
            try {
                adminService.updateCompany(company4);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Deleting one company by the admin
            System.out.println("Deleting a company (which has no coupons yet):");
            adminService.deleteCompany(6);
            System.out.println("=================================================");
            System.out.println();

            //Deleting none existing company
            System.out.println("Attempt to delete a none existing company:");
            try {
                adminService.deleteCompany(10);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }

            //Getting all companies by the admin
            System.out.println("Getting all existing companies (still without coupons):");
            adminService.getAllCompanies().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting one company by the admin
            System.out.println("Getting one company (still without coupons):");
            System.out.println(adminService.getOneCompany(1));
            System.out.println("=================================================");
            System.out.println();

            //Getting none existing company
            System.out.println("Attempt to get a none existing company:");
            try {
                adminService.getOneCompany(10);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }

            //Creating several customers
            Customer customer1 = Customer.builder()
                    .lastName("Cus_LN_1")
                    .firstName("Cus_FN_1")
                    .email("customer1@gmail.com")
                    .password("11234")
                    .build();

            Customer customer2 = Customer.builder()
                    .firstName("Cus_FN_2")
                    .lastName("Cus_LS_2")
                    .email("customer2@gmail.com")
                    .password("21234")
                    .build();

            Customer customer3 = Customer.builder()
                    .lastName("Cus_LN_3")
                    .firstName("Cus_FN_3")
                    .email("customer3@gmail.com")
                    .password("31234")
                    .build();

            Customer customer4 = Customer.builder()
                    .firstName("Cus_FN_4")
                    .lastName("Cus_LS_4")
                    .email("customer4@gmail.com")
                    .password("41234")
                    .build();

            Customer customer5 = Customer.builder()
                    .firstName("Cus_FN_5")
                    .lastName("Cus_LS_5")
                    .email("customer5@gmail.com")
                    .password("51234")
                    .build();

            //Adding the customers by the admin
            System.out.println("Addition of customers:");
            adminService.addCustomer(customer1);
            adminService.addCustomer(customer2);
            adminService.addCustomer(customer3);
            adminService.addCustomer(customer4);
            adminService.addCustomer(customer5);
            System.out.println("=================================================");
            System.out.println();

            //Adding a customer with an exist email
            System.out.println("Attempt to add a customer with an existing details:");
            Customer customer6 = Customer.builder()
                    .firstName("Cus_FN_4")
                    .lastName("Cus_LS_4")
                    .email("customer4@gmail.com")
                    .password("41234")
                    .build();
            try {
                adminService.addCustomer(customer6);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Updating one of the customer details
            customer2.setFirstName("Mazal");
            customer2.setLastName("Daniel");
            customer2.setEmail("mazal.d6@gmail.com");
            customer2.setPassword("51234");

            //Updating the new company details in the database by the admin
            System.out.println("Updating customer details:");
            adminService.updateCustomer(customer2);
            System.out.println("=================================================");
            System.out.println();

            //Updating a customer with an exist email
            //Expecting to get an exception
            System.out.println("Attempt to update a customer with an existing email:");
            customer3.setEmail("customer1@gmail.com");
            try {
                adminService.updateCustomer(customer3);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Deleting one customer by the admin
            System.out.println("Deleting a customer (which has no coupons yet):");
            adminService.deleteCustomer(5);
            System.out.println("=================================================");
            System.out.println();

            //Deleting none existing customer
            System.out.println("Attempt to delete a none existing customer:");
            try {
                adminService.deleteCustomer(10);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }

            //Getting all customers by the admin
            System.out.println("Getting all customers (without coupons yet):");
            adminService.getAllCustomers().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting one customer by the admin
            System.out.println("Getting one customer (without coupons yet):");
            System.out.println(adminService.getOneCustomer(1));
            System.out.println("=================================================");
            System.out.println();

            //Getting none existing customer
            System.out.println("Attempt to get a none existing customer:");
            try {
                adminService.getOneCustomer(10);
            } catch (AdminException err) {
                System.out.println(err.getMessage());
            }

            //Login with companyFacade as Company1
            System.out.println("------------------------------------------------");
            System.out.println("-----------Login with CompanyService------------");
            System.out.println("" +
                    " ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ███╗   ██╗██╗   ██╗     ██╗\n" +
                    "██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗████╗  ██║╚██╗ ██╔╝    ███║\n" +
                    "██║     ██║   ██║██╔████╔██║██████╔╝███████║██╔██╗ ██║ ╚████╔╝     ╚██║\n" +
                    "██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║╚██╗██║  ╚██╔╝       ██║\n" +
                    "╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║  ██║██║ ╚████║   ██║        ██║\n" +
                    " ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝        ╚═╝");
            System.out.println();

            //Wrong login as company
            //Expecting to get an exception
            System.out.println("Attempt to login with incorrect email or password:");
            try {
                CompanyService wrongCompanyService = (CompanyService) loginManager.login("company8@gmail.com", "11234", ClientType.COMPANY);
            } catch (LoginException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Correct login as Company1
            System.out.println("Correct login as Company1:");
            CompanyService companyService = (CompanyService) loginManager.login("company1@gmail.com", "11234", ClientType.COMPANY);
            System.out.println("=================================================");
            System.out.println();

            //Creating coupons
            Coupon coupon1 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.ELECTRICITY)
                    .amount(10)
                    .title("Room")
                    .description("TV")
                    .image("https://images.pexels.com/photos/6976103/pexels-photo-6976103.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(1000.00)
                    .build();

            Coupon coupon2 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.ELECTRICITY)
                    .amount(10)
                    .title("LivingRoom")
                    .description("TV")
                    .image("https://images.pexels.com/photos/1201996/pexels-photo-1201996.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(3000.00)
                    .build();

            Coupon coupon3 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FOOD)
                    .amount(10)
                    .title("Italian")
                    .description("Pizza")
                    .image("https://images.pexels.com/photos/208537/pexels-photo-208537.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(50.00)
                    .build();

            Coupon coupon4 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(2)
                    .title("ZARA")
                    .description("Dresses")
                    .image("https://images.pexels.com/photos/5637259/pexels-photo-5637259.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(300.00)
                    .build();

            Coupon coupon5 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.VACATION)
                    .amount(2)
                    .title("New-York")
                    .description("2 weeks")
                    .image("https://images.pexels.com/photos/2190283/pexels-photo-2190283.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(3500.00)
                    .build();

            Coupon coupon6 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.VACATION)
                    .amount(10)
                    .title("Toronto")
                    .description("3 weeks")
                    .image("https://images.pexels.com/photos/1750754/pexels-photo-1750754.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(4000.00)
                    .build();

            Coupon coupon7 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FOOD)
                    .amount(10)
                    .title("Asian")
                    .description("Sushi")
                    .image("https://images.pexels.com/photos/5900876/pexels-photo-5900876.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(45.00)
                    .build();

            Coupon coupon8 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(10)
                    .title("Nike")
                    .description("Jordan shoes")
                    .image("https://images.pexels.com/photos/2385477/pexels-photo-2385477.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(60.00)
                    .build();

            //Adding the coupons by the Company1
            System.out.println("Adding coupons to Company 1:");
            companyService.addCoupon(coupon1);
            companyService.addCoupon(coupon2);
            companyService.addCoupon(coupon3);
            companyService.addCoupon(coupon4);
            companyService.addCoupon(coupon5);
            companyService.addCoupon(coupon6);
            companyService.addCoupon(coupon7);
            companyService.addCoupon(coupon8);
            System.out.println("=================================================");
            System.out.println();

            //Attempt to add coupon with short endDate
            System.out.println("Attempt to add a coupon with short expiration date:");
            Coupon couponWithShortEndDate = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(10)
                    .title("Addict")
                    .description("shirts")
                    .image("image")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2021-08-17"))
                    .price(60.00)
                    .build();
            try {
                companyService.addCoupon(couponWithShortEndDate);
            } catch (CompanyException err) {
                System.out.println(err.getMessage());
            }

            //Adding a coupon with the same companyId and title
            //Expecting to get an exception
            System.out.println("Attempt to add a coupon with the same title to Company1:");
            Coupon improperCouponToAdd = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(10)
                    .title("ZARA")
                    .description("dresses")
                    .image("image")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(90.00)
                    .build();
            try {
                companyService.addCoupon(improperCouponToAdd);
            } catch (CompanyException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Updating one of the coupons details
            coupon2.setTitle("Kitchen");
            coupon2.setDescription("Oven");
            coupon2.setStartDate(Date.valueOf("2029-07-15"));
            coupon2.setEndDate(Date.valueOf("2029-09-30"));
            coupon2.setAmount(40);
            coupon2.setPrice(2000.00);
            coupon2.setImage("https://images.pexels.com/photos/7525215/pexels-photo-7525215.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

            //Updating the new coupon details in the database by the Company1
            System.out.println("Updating coupon details:");
            companyService.updateCoupon(coupon2);
            System.out.println("=================================================");
            System.out.println();

            //Updating one of the coupons details with an exist title
            //Expecting an exception
            System.out.println("Attempt to update a coupon with an existing title:");
            coupon3.setTitle("Asian");
            try {
                companyService.updateCoupon(coupon3);
            } catch (CompanyException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Deleting coupon by Company1
            System.out.println("Deleting Coupon1:");
            companyService.deleteCoupon(coupon1.getId());
            System.out.println("=================================================");
            System.out.println();

            //Deleting none existing coupon
            System.out.println("Attempt to delete a none existing coupon:");
            try {
                companyService.deleteCoupon(20);
            } catch (CompanyException err) {
                System.out.println(err.getMessage());
            }

            //Getting all Company1 coupons:
            System.out.println("Getting all Company1 existing coupons:");
            companyService.getCompanyCoupons().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting all Company1 coupons by Category:
            System.out.println("Getting all Company1 coupons by the Category Food:");
            companyService.getCompanyCouponsByCategory(Category.FOOD).forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting all Company1 coupons by Price:
            System.out.println("Getting all Company1 coupons up to a price of 70.0:");
            companyService.getCompanyCouponsMaxPrice(70.0).forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting one company by the companyFacade
            System.out.println("Getting Company1 details:");
            System.out.println(companyService.getCompanyDetails());
            System.out.println("=================================================");
            System.out.println();

            //Login as Company4
            System.out.println("" +
                    " ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ███╗   ██╗██╗   ██╗    ██╗  ██╗\n" +
                    "██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗████╗  ██║╚██╗ ██╔╝    ██║  ██║\n" +
                    "██║     ██║   ██║██╔████╔██║██████╔╝███████║██╔██╗ ██║ ╚████╔╝     ███████║\n" +
                    "██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║╚██╗██║  ╚██╔╝      ╚════██║\n" +
                    "╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║  ██║██║ ╚████║   ██║            ██║\n" +
                    " ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝            ╚═╝");
            System.out.println();

            System.out.println("Correct login as Company4:");
            companyService = (CompanyService) loginManager.login("company4@gmail.com", "41234", ClientType.COMPANY);
            System.out.println("=================================================");
            System.out.println();

            //Adding coupons for Company4
            Coupon coupon9 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(10)
                    .title("Levi's")
                    .description("Jeans")
                    .image("https://images.pexels.com/photos/1598507/pexels-photo-1598507.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(90.00)
                    .build();

            Coupon coupon10 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FOOD)
                    .amount(10)
                    .title("Burger")
                    .description("Burger & Fries")
                    .image("https://images.pexels.com/photos/3219547/pexels-photo-3219547.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(20.00)
                    .build();

            Coupon coupon11 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.VACATION)
                    .amount(10)
                    .title("Thailand")
                    .description("one month")
                    .image("https://images.pexels.com/photos/1007657/pexels-photo-1007657.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(25000.00)
                    .build();

            System.out.println("Adding coupons to Company4:");
            companyService.addCoupon(coupon9);
            companyService.addCoupon(coupon10);
            companyService.addCoupon(coupon11);
            System.out.println("=================================================");
            System.out.println();

            //Login as Company5
            System.out.println("" +
                    " ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ███╗   ██╗██╗   ██╗    ███████╗\n" +
                    "██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗████╗  ██║╚██╗ ██╔╝    ██╔════╝\n" +
                    "██║     ██║   ██║██╔████╔██║██████╔╝███████║██╔██╗ ██║ ╚████╔╝     ███████╗\n" +
                    "██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║╚██╗██║  ╚██╔╝      ╚════██║\n" +
                    "╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║  ██║██║ ╚████║   ██║       ███████║\n" +
                    " ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝       ╚══════╝");
            System.out.println();

            System.out.println("Correct login as Company5:");
            companyService = (CompanyService) loginManager.login("company5@gmail.com", "51234", ClientType.COMPANY);
            System.out.println("=================================================");
            System.out.println();

            //Adding coupons for Company5
            Coupon coupon12 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FASHION)
                    .amount(10)
                    .title("Bershka")
                    .description("Skirt")
                    .image("https://images.pexels.com/photos/9769853/pexels-photo-9769853.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(90.00)
                    .build();

            Coupon coupon13 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.FOOD)
                    .amount(10)
                    .title("Sandwiches")
                    .description("hotdog")
                    .image("https://images.pexels.com/photos/4518656/pexels-photo-4518656.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(20.00)
                    .build();

            Coupon coupon14 = Coupon.builder()
                    .companyId(companyService.getCompanyId())
                    .category(Category.VACATION)
                    .amount(10)
                    .title("Mexico")
                    .description("one month")
                    .image("https://images.pexels.com/photos/3290068/pexels-photo-3290068.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260")
                    .startDate(new Date(System.currentTimeMillis()))
                    .endDate(Date.valueOf("2029-01-30"))
                    .price(30000.00)
                    .build();

            System.out.println("Adding coupons to Company4:");
            companyService.addCoupon(coupon12);
            companyService.addCoupon(coupon13);
            companyService.addCoupon(coupon14);
            System.out.println("=================================================");
            System.out.println();

            //Login with CustomerFacade
            System.out.println("------------------------------------------------");
            System.out.println("-----------Login with CustomerService-----------");
            System.out.println("" +
                    " ██████╗██╗   ██╗███████╗████████╗ ██████╗ ███╗   ███╗███████╗██████╗      ██╗\n" +
                    "██╔════╝██║   ██║██╔════╝╚══██╔══╝██╔═══██╗████╗ ████║██╔════╝██╔══██╗    ███║\n" +
                    "██║     ██║   ██║███████╗   ██║   ██║   ██║██╔████╔██║█████╗  ██████╔╝    ╚██║\n" +
                    "██║     ██║   ██║╚════██║   ██║   ██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗     ██║\n" +
                    "╚██████╗╚██████╔╝███████║   ██║   ╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║     ██║\n" +
                    " ╚═════╝ ╚═════╝ ╚══════╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝     ╚═╝");
            System.out.println();

            //Wrong login as customer
            //Expecting to get an exception
            System.out.println("Attempt to login with incorrect email or password:");
            try {
                CustomerService wrongCustomerService = (CustomerService) loginManager.login("customer9@gmail.com", "11234", ClientType.CUSTOMER);
            } catch (LoginException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Correct login as Customer1
            System.out.println("Correct login as Customer1:");
            CustomerService customerService = (CustomerService) loginManager.login("customer1@gmail.com", "11234", ClientType.CUSTOMER);
            System.out.println("=================================================");
            System.out.println();

            //Purchasing coupons for Customer1
            System.out.println("Purchasing coupons as Customer1:");
            customerService.purchaseCoupon(coupon2.getId());
            customerService.purchaseCoupon(coupon4.getId());
            customerService.purchaseCoupon(coupon5.getId());
            customerService.purchaseCoupon(coupon6.getId());
            System.out.println("=================================================");
            System.out.println();

            //Purchasing a coupon more than one time
            System.out.println("Attempt to buy twice the same coupon as Customer1:");
            try {
                customerService.purchaseCoupon(coupon2.getId());
            } catch (CustomerException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            //Getting Customer1's coupons
            System.out.println("Getting Customer1 all coupons:");
            customerService.getCustomerCoupons().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting Customer1's coupons by Category
            System.out.println("Getting Customer1 all coupons by the Category Vacation:");
            customerService.getCustomerCouponsByCategory(Category.VACATION).forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting Customer1's coupons by max price
            System.out.println("Getting Customer1 all coupons up to the price 900.00:");
            customerService.getCustomerCouponsByMaxPrice(900.00).forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting Customer1's details
            System.out.println("Getting Customer1 details:");
            System.out.println(customerService.getCustomerDetails());
            System.out.println("=================================================");
            System.out.println();

            //Login as Customer3
            System.out.println("" +
                    " ██████╗██╗   ██╗███████╗████████╗ ██████╗ ███╗   ███╗███████╗██████╗     ██████╗ \n" +
                    "██╔════╝██║   ██║██╔════╝╚══██╔══╝██╔═══██╗████╗ ████║██╔════╝██╔══██╗    ╚════██╗\n" +
                    "██║     ██║   ██║███████╗   ██║   ██║   ██║██╔████╔██║█████╗  ██████╔╝     █████╔╝\n" +
                    "██║     ██║   ██║╚════██║   ██║   ██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗     ╚═══██╗\n" +
                    "╚██████╗╚██████╔╝███████║   ██║   ╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║    ██████╔╝\n" +
                    " ╚═════╝ ╚═════╝ ╚══════╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝    ╚═════╝ ");
            System.out.println();

            System.out.println("Correct login as Customer3:");
            customerService = (CustomerService) loginManager.login("customer3@gmail.com", "31234", ClientType.CUSTOMER);
            System.out.println("=================================================");
            System.out.println();

            //Purchasing coupons for Company3
            System.out.println("Purchasing coupons as Customer3:");
            customerService.purchaseCoupon(coupon4.getId());
            customerService.purchaseCoupon(coupon8.getId());
            customerService.purchaseCoupon(coupon9.getId());
            customerService.purchaseCoupon(coupon10.getId());
            customerService.purchaseCoupon(coupon11.getId());
            customerService.purchaseCoupon(coupon12.getId());
            customerService.purchaseCoupon(coupon13.getId());
            customerService.purchaseCoupon(coupon14.getId());
            System.out.println("=================================================");
            System.out.println();

            //Login as Customer4
            System.out.println("" +
                    " ██████╗██╗   ██╗███████╗████████╗ ██████╗ ███╗   ███╗███████╗██████╗     ██╗  ██╗\n" +
                    "██╔════╝██║   ██║██╔════╝╚══██╔══╝██╔═══██╗████╗ ████║██╔════╝██╔══██╗    ██║  ██║\n" +
                    "██║     ██║   ██║███████╗   ██║   ██║   ██║██╔████╔██║█████╗  ██████╔╝    ███████║\n" +
                    "██║     ██║   ██║╚════██║   ██║   ██║   ██║██║╚██╔╝██║██╔══╝  ██╔══██╗    ╚════██║\n" +
                    "╚██████╗╚██████╔╝███████║   ██║   ╚██████╔╝██║ ╚═╝ ██║███████╗██║  ██║         ██║\n" +
                    " ╚═════╝ ╚═════╝ ╚══════╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝         ╚═╝");
            System.out.println();

            System.out.println("Correct login as Customer4:");
            customerService = (CustomerService) loginManager.login("customer4@gmail.com", "41234", ClientType.CUSTOMER);
            System.out.println("=================================================");
            System.out.println();

            //Purchasing coupons for Company4
            System.out.println("Purchasing coupons as Customer4:");
            customerService.purchaseCoupon(coupon8.getId());
            customerService.purchaseCoupon(coupon9.getId());
            customerService.purchaseCoupon(coupon11.getId());
            customerService.purchaseCoupon(coupon12.getId());
            customerService.purchaseCoupon(coupon13.getId());
            System.out.println("=================================================");
            System.out.println();

            //Purchasing coupon for Company4 in which the amount=0
            System.out.println("An attempt to purchase a coupon with amount 0:");
            try {
                customerService.purchaseCoupon(coupon4.getId());
            } catch (CustomerException err) {
                System.out.println(err.getMessage());
            }
            System.out.println("=================================================");
            System.out.println();

            System.out.println("------------------------------------------------");
            System.out.println("------------Login with AdminService-------------");
            System.out.println("" +
                    " █████╗ ██████╗ ███╗   ███╗██╗███╗   ██╗\n" +
                    "██╔══██╗██╔══██╗████╗ ████║██║████╗  ██║\n" +
                    "███████║██║  ██║██╔████╔██║██║██╔██╗ ██║\n" +
                    "██╔══██║██║  ██║██║╚██╔╝██║██║██║╚██╗██║\n" +
                    "██║  ██║██████╔╝██║ ╚═╝ ██║██║██║ ╚████║\n" +
                    "╚═╝  ╚═╝╚═════╝ ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝");
            System.out.println();

            adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
            System.out.println("=================================================");
            System.out.println();

            //Deleting one company by the admin
            System.out.println("Deleting a company (which has coupon- in order to check if the cascade is correct):");
            adminService.deleteCompany(5);
            System.out.println("=================================================");
            System.out.println();

            //Getting all companies by the admin
            System.out.println("Getting all existing companies (with all coupons):");
            adminService.getAllCompanies().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting one company by the admin
            System.out.println("Getting one company (with all coupons):");
            System.out.println(adminService.getOneCompany(1));
            System.out.println("=================================================");
            System.out.println();

            //Deleting one customer by the admin
            System.out.println("Deleting a customer (which has coupon- in order to check if the cascade is correct):");
            adminService.deleteCustomer(4);
            System.out.println("=================================================");
            System.out.println();

            //Getting all customers by the admin
            System.out.println("Getting all customers (with all coupons):");
            adminService.getAllCustomers().forEach(System.out::println);
            System.out.println("=================================================");
            System.out.println();

            //Getting one customer by the admin
            System.out.println("Getting one customer (with all coupons):");
            System.out.println(adminService.getOneCustomer(1));
            System.out.println("=================================================");
            System.out.println();

            System.out.println("" +
                    "███████╗███╗   ██╗██████╗     ██████╗ ██████╗  ██████╗  ██████╗  █████╗ ███╗   ███╗\n" +
                    "██╔════╝████╗  ██║██╔══██╗    ██╔══██╗██╔══██╗██╔═══██╗██╔════╝ ██╔══██╗████╗ ████║\n" +
                    "█████╗  ██╔██╗ ██║██║  ██║    ██████╔╝██████╔╝██║   ██║██║  ███╗███████║██╔████╔██║\n" +
                    "██╔══╝  ██║╚██╗██║██║  ██║    ██╔═══╝ ██╔══██╗██║   ██║██║   ██║██╔══██║██║╚██╔╝██║\n" +
                    "███████╗██║ ╚████║██████╔╝    ██║     ██║  ██║╚██████╔╝╚██████╔╝██║  ██║██║ ╚═╝ ██║\n" +
                    "╚══════╝╚═╝  ╚═══╝╚═════╝     ╚═╝     ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝");
            System.out.println();

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }
}
