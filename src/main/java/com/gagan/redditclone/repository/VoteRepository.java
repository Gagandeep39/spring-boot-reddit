package com.gagan.redditclone.repository;

import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.User;
import com.gagan.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 19:17
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
