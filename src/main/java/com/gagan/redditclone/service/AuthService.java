package com.gagan.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import com.gagan.redditclone.dto.RegisterRequest;
import com.gagan.redditclone.model.NotificationEmail;
import com.gagan.redditclone.model.User;
import com.gagan.redditclone.model.VerificationToken;
import com.gagan.redditclone.repository.UserRepository;
import com.gagan.redditclone.repository.VerificationTokenRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

  /**
   * 1. Autowiring in inndividual field is not recommended 2. We must always use
   * constructor for autowiring 3. Here @AllArgsConstructor creates a constructor
   * for us 4. Make sure Fields a re final
   */
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailService mailService;

  @Transactional
  public void signUp(RegisterRequest registerRequest) {
    User user = new User();
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setUsername(registerRequest.getUsername());
    user.setCreated(Instant.now());
    user.setEnabled(false);
    userRepository.save(user);
    String token = generateVerificationToken(user);
    mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(),
        "Click on the link for activation http://localhost:8080/api/auth/accountVerification/" + token));
  }

  private String generateVerificationToken(User user) {
    String verificationToken = UUID.randomUUID().toString();
    VerificationToken token = new VerificationToken();
    token.setToken(verificationToken);
    token.setUser(user);
    verificationTokenRepository.save(token);
    return verificationToken;
  }

}
