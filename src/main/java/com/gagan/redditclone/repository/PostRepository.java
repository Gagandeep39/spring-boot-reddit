package com.gagan.redditclone.repository;

import java.util.List;

import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.Subreddit;
import com.gagan.redditclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:01
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllBySubreddit(Subreddit subreddit);

  List<Post> findByUser(User user);
}
