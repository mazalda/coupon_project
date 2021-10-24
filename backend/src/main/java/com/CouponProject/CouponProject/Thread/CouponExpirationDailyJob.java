package com.CouponProject.CouponProject.Thread;

import com.CouponProject.CouponProject.Beans.Coupon;
import com.CouponProject.CouponProject.Repositories.CouponRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

/**
 * class for the daily eraser of expired coupons
 */
@Component
@EnableAsync
@EnableScheduling
@RequiredArgsConstructor
public class CouponExpirationDailyJob {
    /**
     * field with a ready object of CouponRepo
     */
    private final CouponRepo couponRepo;

    /**
     * method for performing a daily job for erasing expired coupons
     */
    @Async
    @Scheduled(cron = "0 5 0 * * ?", zone = "Asia/Jerusalem")
    public void deleteExpiredCoupons() {
        for (Coupon item : couponRepo.findAll()) {
            if (item.getEndDate().before(Date.valueOf(LocalDate.now()))) {
                couponRepo.delete(item);
            }
        }
    }
}


