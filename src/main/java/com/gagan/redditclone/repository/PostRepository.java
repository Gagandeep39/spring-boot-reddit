package com.gagan.redditclone.repository;

import com.gagan.redditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:01
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
