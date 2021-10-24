package com.CouponProject.CouponProject.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Class for creating the Customer entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    /**
     * field for customer id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * field for customer first name
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * field for customer last name
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * field for customer email
     */
    @Column(nullable = false)
    private String email;

    /**
     * field for customer password
     */
    @Column(nullable = false)
    private String password;

    /**
     * field for making the connection between the customers and the coupons
     */
    @Singular
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "customer_boughtCoupons",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "id"))
    private List<Coupon> boughtCoupons;

    /**
     * toString for the companies
     * @return customer details
     */
    @Override
    public String toString() {
        return "Customer " +
                "id=" + id + '\n' +
                "firstName=" + firstName + '\n' +
                "lastName=" + lastName + '\n' +
                "email=" + email + '\n' +
                "password=" + password + '\n' +
                "coupons=" + boughtCoupons + '\n' + '\n';
    }


}
