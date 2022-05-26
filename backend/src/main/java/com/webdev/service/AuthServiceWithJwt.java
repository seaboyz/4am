package com.webdev.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.webdev.model.Customer;
import com.webdev.utils.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceWithJwt {
  private CustomerSerivice customerService;

  @Autowired
  public AuthServiceWithJwt(CustomerSerivice customerService) {
    this.customerService = customerService;
  }

  public Customer login(String email, String password) {

    Customer customer = customerService.getCustomerByEmail(email);

    if (customer == null) {
      throw new IllegalArgumentException("Customer not found");
    }

    String encriptedPassword = customer.getPassword();

    if (BCrypt.checkpw(password, encriptedPassword)) {
      // String token = Jwt.getToken(customer);
      // customer.setToken(token);
      // customerService.updateCustomer(customer);
      return customer;
    } else {
      throw new IllegalArgumentException("Email and password not match");
    }

  }

  public Customer register(Customer userToBeRegistered) {

    Customer customer = customerService.getCustomerByEmail(userToBeRegistered.getEmail());

    if (customer != null) {
      throw new IllegalArgumentException("Email already exists");
    }

    // encrypt password
    String encryptedPassword = Jwt.encriptPassword(userToBeRegistered.getPassword());

    // update user with encriptedpassword
    userToBeRegistered.setPassword(encryptedPassword);

    // generate token
    String token = Jwt.getToken(userToBeRegistered);
    userToBeRegistered.setToken(token);

    // save user
    return customerService.createCustomer(userToBeRegistered);
  }

  private DecodedJWT getVerifier(String token) {
    String secret = Jwt.getSecret();
    if (secret == null) {
      return null;
    }
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
      DecodedJWT jwt = verifier.verify(token);
      return jwt;
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  public Customer getCustomer(String token) {
    DecodedJWT jwt = getVerifier(token);
    if (jwt == null) {
      return null;
    }

    String email = jwt.getClaim("email").asString();
    return customerService.getCustomerByEmail(email);

  }

  public int getCustomerId(String token) {
    DecodedJWT verifier = getVerifier(token);
    if (verifier != null) {
      int verifiedId = verifier.getClaim("id").asInt();
      return verifiedId;
    }
    return -1;
  }

  public boolean isTokenValid(String token) {
    try {
      String secret = Jwt.getSecret();
      if (secret == null) {
        return false;
      }
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
      DecodedJWT jwt = verifier.verify(token);
      if (jwt == null) {
        return false;
      }
      return true;

    } catch (JWTVerificationException e) {
      return false;
    }
  }

  public boolean isSelf(int userId, String token) {
    DecodedJWT verifier = getVerifier(token);
    if (verifier != null) {
      int verifiedId = verifier.getClaim("id").asInt();
      return verifiedId == userId;
    }
    return false;
  }

}
