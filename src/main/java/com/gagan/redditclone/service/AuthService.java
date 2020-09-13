package com.gagan.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.gagan.redditclone.dto.AuthenticationResponse;
import com.gagan.redditclone.dto.LoginRequest;
import com.gagan.redditclone.dto.RefreshTokenRequest;
import com.gagan.redditclone.dto.RegisterRequest;
import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.model.NotificationEmail;
import com.gagan.redditclone.model.User;
import com.gagan.redditclone.model.VerificationToken;
import com.gagan.redditclone.repository.UserRepository;
import com.gagan.redditclone.repository.VerificationTokenRepository;
import com.gagan.redditclone.security.JwtProvider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  /**
   * @Transactional performs Database related error handlonngs
   */
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

  public void verifyAccount(String token) {
    Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
    verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
    fetchUserAndEnable(verificationToken.get());
  }

  /**
   * Can be called multiple times to verify account
   */
  @Transactional
  public void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new SpringRedditException("User not found with name " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.generateToken(authentication);
    return AuthenticationResponse.builder()
            .authenticationToken(token)
            .refreshToken(refreshTokenService.generateRefreshToken().getToken())
            .username(loginRequest.getUsername())
            .expiresAt(Instant.now().plusMillis(jwtProvider.getExpirationInMillis()))
            .build();
  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userRepository.findByUsername(principal.getUsername())
        .orElseThrow(() -> new SpringRedditException("User name not found - " + principal.getUsername().toString()));
  }

  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();

  }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
      refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
      String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
      return AuthenticationResponse.builder()
              .authenticationToken(token)
              .refreshToken(refreshTokenRequest.getRefreshToken())
              .expiresAt(Instant.now().plusMillis(jwtProvider.getExpirationInMillis()))
              .username(refreshTokenRequest.getUsername())
              .build();
    }
}
