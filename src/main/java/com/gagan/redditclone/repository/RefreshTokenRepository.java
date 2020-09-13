package com.gagan.redditclone.repository;

import com.gagan.redditclone.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Gagandeep
 * @date 13-09-2020
 * @time 21:40
 */

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
