package com.CouponProject.CouponProject.Advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class for setting error details
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    /**
     * field for the error title
     */
    private String error;

    /**
     * field for the error description
     */
    private String description;
}
