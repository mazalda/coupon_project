package com.CouponProject.CouponProject.Util;

import com.CouponProject.CouponProject.Beans.UserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * class for the token creator
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWTutil {
    /**
     * Signature algorithm field - type of encryption
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * Encoded secret key field - our private key
     */
    private String encodedSecretKey = "this+is+my+key+and+it+must+be+at+least+256+bits+long";
    /**
     * Decoded secret key field - creates our private key
     */
    private Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey), signatureAlgorithm);

    /**
     * Generate token
     * this method generates our key
     * @param userDetails- the user's details
     * @return Token in String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getUserId());
        claims.put("clientType", userDetails.getClientType());
        String myToken = createToken(claims, userDetails.getEmail());
        System.out.println("New token was created: " + myToken);
        return myToken;
    }

    /**
     * Create token
     * this method creates our token
     * @param claims - contains the fields which we are basing the token on
     * @param email - email of the user
     * @return Token in String
     */
    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * Extract all claims
     * this method extracts all the claims in json
     * @param token - the user's token
     * @return Claims
     * @throws ExpiredJwtException throws error if something went wrong
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        return jwtParser.parseClaimsJws(getToken(token)).getBody();
    }

    /**
     * Extract email
     * this method extracts the user's email
     * @param token- the user's token
     * @return String - the user's email
     */
    public String extractClientType(String token) {
        return (String) extractAllClaims(getToken(token)).get("clientType");
    }

    /**
     * Extract email
     * this method extracts the user's email
     * @param token- the user's token
     * @return String - the user's email
     */
    public String extractEmail(String token) {
        return extractAllClaims(getToken(token)).getSubject();
    }

    /**
     * Extract expiration date
     * this method extracts the expiration date of the token
     * @param token -the user's token
     * @return the token's expiration date
     */
    public Date extractExpirationDate(String token) {
        return extractAllClaims(getToken(token)).getExpiration();
    }

    /**
     * Is token expired
     * this method checks if the token is expired
     * @param token - the user's token
     * @return boolean- if it's expired
     */
    private boolean isTokenExpired(String token) {
        try {
            extractAllClaims(getToken(token));
            return false;
        } catch (ExpiredJwtException | SignatureException err) {
            return true;
        }
    }

    /**
     * Validate token
     * this method checks the validation of the user's details with the token
     * @param token - the user's token
     * @return boolean - if the token is valid
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(getToken(token));
    }

    /**
     * method for separating the token to "Bearer" and the token itself
     * @param token full token (Bearer + token)
     * @return only the token itself
     */
    public String getToken(String token){
        if (token.contains("Bearer")) {
            return token.split(" ")[1];
        }
        return token;
    }
}
