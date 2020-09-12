package com.gagan.redditclone.repository;

import com.gagan.redditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:02
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
