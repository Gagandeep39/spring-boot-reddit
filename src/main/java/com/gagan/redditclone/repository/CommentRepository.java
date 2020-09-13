package com.gagan.redditclone.repository;

import java.util.List;
import java.util.Optional;

import com.gagan.redditclone.model.Comment;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 18:02
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByUser(User user);
  List<Comment> findByPost(Post post);
}
