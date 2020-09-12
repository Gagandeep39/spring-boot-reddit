package com.gagan.redditclone.repository;

import com.gagan.redditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:03
 */

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
