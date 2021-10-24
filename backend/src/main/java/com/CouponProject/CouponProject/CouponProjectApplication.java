package com.CouponProject.CouponProject;

import com.CouponProject.CouponProject.Util.Art;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * class for the main program
 */
@SpringBootApplication
public class CouponProjectApplication {

	/**
	 * the main program
	 * @param args default parameter in the main method
	 */
	public static void main(String[] args) {
		SpringApplication.run(CouponProjectApplication.class, args);
		System.out.println(Art.RUNNING);
	}
}
