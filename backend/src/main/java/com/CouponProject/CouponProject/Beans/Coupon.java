package com.CouponProject.CouponProject.Beans;

import com.CouponProject.CouponProject.Enum.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Class for creating the Coupon entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon {
    /**
     * field for coupon id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * field for coupon's companyId
     */
    @Column(nullable = false)
    private int companyId;

    /**
     * field for coupon category
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    /**
     * field for coupon title
     */
    @Column(nullable = false)
    private String title;

    /**
     * field for coupon description
     */
    @Column(nullable = false)
    private String description;

    /**
     * field for coupon startDate
     */
    @Column(nullable = false)
    private Date startDate;

    /**
     * field for coupon endDate
     */
    @Column(nullable = false)
    private Date endDate;

    /**
     * field for coupon amount
     */
    @Column(nullable = false)
    private int amount;

    /**
     * field for coupon price
     */
    @Column(nullable = false)
    private double price;

    /**
     * field for coupon image
     */
    @Column(nullable = false)
    private String image;

    /**
     * field for making the connection between the company ant it's coupons
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_companyId")
    private Company company;

    /**
     * field for making the connection between the customers and the coupons
     */
    @ManyToMany(mappedBy = "boughtCoupons", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Customer> owners;

    /**
     * method for removing the deleted coupons from the customer_coupons table
     */
    @PreRemove
    public void removeCouponFromCustomer() {
        for (Customer customer : owners) {
            customer.getBoughtCoupons().remove(this);
        }
    }

    /**
     * toString for the companies
     * @return coupon details
     */
    @Override
    public String toString() {
        return "Coupon " +
                "id=" + id + '\n' +
                "companyId=" + companyId + '\n' +
                "category=" + category + '\n' +
                "amount=" + amount + '\n' +
                "title=" + title + '\n' +
                "description=" + description + '\n' +
                "image=" + image + '\n' +
                "startDate=" + startDate + '\n' +
                "endDate=" + endDate + '\n' +
                "price=" + price + '\n' + '\n';
    }
}
