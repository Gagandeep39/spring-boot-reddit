/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 12:42:33
 * @modify date 2020-09-13 12:42:33
 * @desc [description]
 */
package com.gagan.redditclone.controller;

import java.util.List;

import com.gagan.redditclone.dto.PostRequest;
import com.gagan.redditclone.dto.PostResponse;
import com.gagan.redditclone.service.PostService;

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
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
    postService.createPost(postRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("Created post");
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.FOUND).body(postService.getPostById(id));
  }

  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
  }

  @GetMapping("/by-subreddit/{id}")
  public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.FOUND).body(postService.getPostsBySubreddit(id));
  }

  @GetMapping("/by-user/{username}")
  public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String username) {
    return ResponseEntity.status(HttpStatus.FOUND).body(postService.getPostsByUsername(username));
  }

}
