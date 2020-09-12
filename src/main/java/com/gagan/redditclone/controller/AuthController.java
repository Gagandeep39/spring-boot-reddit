/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-12 21:58:30
 * @modify date 2020-09-12 21:58:30
 * @desc [description]
 */
package com.gagan.redditclone.controller;

import com.gagan.redditclone.dto.RegisterRequest;
import com.gagan.redditclone.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) {
    authService.signUp(registerRequest);
    return new ResponseEntity<>("User registration successful", HttpStatus.OK);
  }

}
