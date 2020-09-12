package com.gagan.redditclone.repository;

import java.util.Optional;

import com.gagan.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:00
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
