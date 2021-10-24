package com.CouponProject.CouponProject.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Class for creating the Company entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    /**
     * field for the company_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * field for company_name
     */
    @Column(nullable = false)
    private String name;

    /**
     * field for company email
     */
    @Column(nullable = false)
    private String email;

    /**
     * field for company password
     */
    @Column(nullable = false)
    private String password;

    /**
     * field for making the connection between the company and the coupons
     */
    @Singular
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coupon> coupons;

    /**
     * toString for the companies
     * @return company details
     */
    @Override
    public String toString() {
        return "Company " +
                "id=" + id + '\n' +
                "name=" + name + '\n' +
                "email=" + email + '\n' +
                "password=" + password + '\n' +
                "coupons=" + coupons + '\n' + '\n';
    }
}
