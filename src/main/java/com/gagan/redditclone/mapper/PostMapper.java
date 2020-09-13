/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 13:13:00
 * @modify date 2020-09-13 13:13:00
 * @desc [description]
 */
package com.gagan.redditclone.mapper;

import com.gagan.redditclone.dto.PostRequest;
import com.gagan.redditclone.dto.PostResponse;
import com.gagan.redditclone.model.*;

import com.gagan.redditclone.repository.CommentRepository;
import com.gagan.redditclone.repository.VoteRepository;
import com.gagan.redditclone.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {


  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private VoteRepository voteRepository;
  @Autowired
  private AuthService authService;

  /**
   * Mapping order must be same s parameter order
   */
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "subreddit", source = "subreddit")
  @Mapping(target = "voteCount", constant = "0")
  @Mapping(target = "user", source = "user")
  public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

  @Mapping(source = "postId", target = "id")
  @Mapping(source = "user.username", target = "userName")
  @Mapping(source = "subreddit.name", target = "subredditName")
  @Mapping(target = "commentCount", expression = "java(commentCount(post))")
  @Mapping(target = "duration", expression = "java(getDuration(post))")
  public abstract PostResponse mapPostToPostResponse(Post post);

  Integer commentCount(Post post) {
    return commentRepository.findByPost(post).size();
  }

  String getDuration(Post post) {
    return post.getCreatedDate().toString();
  }

  private boolean checkVoteType(Post post, VoteType voteType) {
    if (authService.isLoggedIn()) {
      Optional<Vote> voteForPostByUser =
              voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                      authService.getCurrentUser());
      return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
              .isPresent();
    }
    return false;
  }

}
