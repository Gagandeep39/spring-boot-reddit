package com.gagan.redditclone.repository;

import java.util.Optional;

import com.gagan.redditclone.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 19:17
 */

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
  Optional<VerificationToken> findByToken(String token);
}
