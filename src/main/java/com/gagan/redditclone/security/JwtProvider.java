/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 01:23:26
 * @modify date 2020-09-13 01:23:26
 * @desc [description]
 */
package com.gagan.redditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtProvider {
  private KeyStore keyStore;
  @Value("${jwt.expiration.time}")
  private Long jwtExpirationInMillis;

  public String generateToken(Authentication authentication) {
    log.info(authentication.getPrincipal().getClass().toString());
    UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
  }

  @PostConstruct
  public void init() {
    try {
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
      keyStore.load(resourceAsStream, "secret".toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      throw new SpringRedditException("Exception occurred while loading keystore");
    }

  }

  private PrivateKey getPrivateKey() {
    try {
      return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      throw new SpringRedditException("Exception occured while retrieving public key from keystore");
    }
  }

  public boolean validateToken(String jwt) {
    Jwts.parserBuilder().setSigningKey(getPublickey()).build().parseClaimsJws(jwt);
    return true;
  }

  private PublicKey getPublickey() {
    try {
      return keyStore.getCertificate("springblog").getPublicKey();
    } catch (KeyStoreException e) {
      throw new SpringRedditException("Exception occured while " + "retrieving public key from keystore");
    }
  }

  public String getUsernameFromJwt(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(getPublickey()).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

}
