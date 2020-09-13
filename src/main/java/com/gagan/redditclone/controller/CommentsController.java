/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 18:34:14
 * @modify date 2020-09-13 18:34:14
 * @desc [description]
 */
package com.gagan.redditclone.controller;

import java.util.List;

import com.gagan.redditclone.dto.CommentsDto;
import com.gagan.redditclone.model.Comment;
import com.gagan.redditclone.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {
  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<String> createComments(@RequestBody CommentsDto commentsDto) {
    commentService.saveComment(commentsDto);
    return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created comment");
  }

  @GetMapping("/by-postId/{postId}")
  public ResponseEntity<List<CommentsDto>> getAllCommentsForPosts(@PathVariable Long postId) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
  }

  @GetMapping("/by-user/{username}")
  public ResponseEntity<List<CommentsDto>> getCommentsByUsername(@PathVariable String username) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
  }

}
