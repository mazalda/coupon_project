package com.CouponProject.CouponProject.Service;

import com.CouponProject.CouponProject.Beans.Company;
import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Beans.Customer;
import com.CouponProject.CouponProject.Exceptions.AdminException;
import com.CouponProject.CouponProject.Exceptions.LoginException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * class for admin service
 */
@Service
@NoArgsConstructor
public class AdminService extends ClientService {
    private final String EMAIL = "admin@admin.com";
    private final String PASSWORD = "admin";

    /**
     * login method
     * @param email    of the admin
     * @param password of the admin
     * @return boolean for correct login/wrong login
     * @throws LoginException if the email/password are wrong
     */
    @Override
    public boolean login(String email, String password) throws LoginException {
        if (email.equals(this.EMAIL) && password.equals(this.PASSWORD)) {
            System.out.printf("Login was successful!\n");
            return true;
        } else {
            throw new LoginException("Email/Password are incorrect\n");
        }
    }

    /**
     * method for adding companies
     * @param company object of Company
     * @throws AdminException if the name/email already exists
     */
    public void addCompany(Company company) throws AdminException {
        if (!companyRepo.existsByEmailAndPassword(company.getEmail(), company.getPassword())) {
            if (!companyRepo.existsByName(company.getName())) {
                companyRepo.save(company);
                System.out.printf("%s was added successfully\n", company.getName());
            } else {
                throw new AdminException("There is already a company with the same name :(\n");
            }
        } else {
            throw new AdminException("There is already a company with the same email :(\n");
        }
    }

    /**
     * method for updating company
     *
     * @param company object of Company
     * @throws AdminException if the name already exists/ trying to update the name/id of the company
     */
    public void updateCompany(Company company) throws AdminException {
        if (companyRepo.findByName(company.getName()) != null) {
            if (companyRepo.findByName(company.getName()).getId() == company.getId()) {
                Company companyToUpdate = new Company(company.getId(), company.getName(), company.getEmail(), company.getPassword(), company.getCoupons());
                if (!companyRepo.existsByEmail(companyToUpdate.getEmail()) || companyToUpdate.getEmail().equals(companyRepo.findByName(company.getName()).getEmail())) {
                    companyRepo.saveAndFlush(companyToUpdate);
                    System.out.printf("%s was updated successfully\n", company.getName());
                } else {
                    throw new AdminException("Another company with the same email already exists\n");
                }
            } else {
                throw new AdminException("You cannot change the company Id\n");
            }
        } else {
            throw new AdminException("You cannot change the company name\n");
        }
    }

    /**
     * method for deleting company
     *
     * @param companyId of the company we want to delete
     * @throws AdminException if trying to delete none existing company id
     */
    public void deleteCompany(int companyId) throws AdminException {
        if (companyRepo.findById(companyId) != null) {
            List<Coupon> companyCoupons = couponRepo.findByCompanyId(companyId);
            companyRepo.deleteById(companyId);
            for (Coupon coupons : companyCoupons) {
                coupons.removeCouponFromCustomer();
                System.out.printf("CouponId %d was removed from the customer_bought_coupons table\n", coupons.getId());
            }
            System.out.printf("Company%d was deleted successfully\n", companyId);
        } else {
            throw new AdminException("There is no company with this id\n");
        }
    }

    /**
     * method for getting all existing companies
     *
     * @return List of companies
     */
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepo.findAll();
        return companies;
    }

    /**
     * method for getting one company
     *
     * @param companyId of the required company
     * @return object of Company
     * @throws AdminException in case trying to get none existing company id
     */
    public Company getOneCompany(int companyId) throws AdminException {
        if (companyRepo.findById(companyId) != null) {
            return companyRepo.getOne(companyId);
        } else {
            throw new AdminException("There is no company with this id\n");
        }
    }

    /**
     * method for adding customers
     *
     * @param customer object of Customer
     * @throws AdminException if the email already exists
     */
    public void addCustomer(Customer customer) throws AdminException {
        if (!customerRepo.existsByEmail(customer.getEmail())) {
            customerRepo.save(customer);
            System.out.printf("%s %s was added successfully\n", customer.getFirstName(), customer.getLastName());
        } else {
            throw new AdminException("There is already a customer with the same email :(\n");
        }
    }

    /**
     * method for updating customer
     *
     * @param customer object of Customer
     * @throws AdminException if the email already exists
     */
    public void updateCustomer(Customer customer) throws AdminException {
        System.out.println(customerRepo.findById(customer.getId()).getEmail());
        System.out.println(customer.getEmail());
        if ((!customerRepo.existsByEmail(customer.getEmail())) || (customerRepo.findById(customer.getId()).getEmail().equals(customer.getEmail()))) {
            customerRepo.saveAndFlush(customer);
            System.out.printf("Customer_id %d was updated successfully\n", customer.getId());
        } else {
            throw new AdminException("Another customer with the same email already exists\n");
        }
    }

    /**
     * method for deleting customer
     *
     * @param customerId of the customer we want to delete
     * @throws AdminException in case trying to delete none existing customer id
     */
    public void deleteCustomer(int customerId) throws AdminException {
        if (customerRepo.findById(customerId) != null) {
            customerRepo.deleteById(customerId);
            System.out.printf("Customer_id %d was deleted successfully\n", customerId);
        } else {
            throw new AdminException("There is no customer with this id\n");
        }
    }

    /**
     * method for getting all existing customers
     *
     * @return List of customers
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }

    /**
     * method for getting one customer
     *
     * @param customerId of the required customer
     * @return object of Customer
     * @throws AdminException in case trying to get none existing customer id
     */
    public Customer getOneCustomer(int customerId) throws AdminException {
        if (customerRepo.findById(customerId) != null) {
            return customerRepo.getOne(customerId);
        } else {
            throw new AdminException("There is no customer with this id\n");
        }
    }
}
