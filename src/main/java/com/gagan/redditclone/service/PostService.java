package com.gagan.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gagan.redditclone.dto.PostRequest;
import com.gagan.redditclone.dto.PostResponse;
import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.mapper.PostMapper;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.Subreddit;
import com.gagan.redditclone.model.User;
import com.gagan.redditclone.repository.PostRepository;
import com.gagan.redditclone.repository.SubredditRepository;
import com.gagan.redditclone.repository.UserRepository;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
  private final PostRepository postRepository;
  private final AuthService authService;
  private final SubredditRepository subredditRepository;
  private final UserRepository userRepository;
  private final PostMapper postMapper;

  public void createPost(PostRequest postRequest) {
    Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
        .orElseThrow(() -> new SpringRedditException("Subreddit not found"));
    User user = authService.getCurrentUser();
    Post post = postMapper.map(postRequest, subreddit, user);
    postRepository.save(post);
  }

  @Transactional(readOnly = true)
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("Post not found"));
    return postMapper.mapPostToPostResponse(post);
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    return postRepository.findAll().stream().map(postMapper::mapPostToPostResponse).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsBySubreddit(Long subredditId) {
    Subreddit subreddit = subredditRepository.findById(subredditId)
        .orElseThrow(() -> new SpringRedditException(subredditId.toString()));
    List<Post> posts = postRepository.findAllBySubreddit(subreddit);
    return posts.stream().map(post -> {
      return postMapper.mapPostToPostResponse(post);
    }).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user).stream().map(postMapper::mapPostToPostResponse).collect(Collectors.toList());
  }

}
