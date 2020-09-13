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
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.Subreddit;
import com.gagan.redditclone.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  /**
   * Mapping order must be same s parameter order
   */
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  @Mapping(target = "description", source = "postRequest.description")
  @Mapping(target = "subreddit", source = "subreddit")
  @Mapping(target = "voteCount", constant = "0")
  @Mapping(target = "user", source = "user")
  Post map(PostRequest postRequest, Subreddit subreddit, User user);

  @Mapping(source = "postId", target = "id")
  @Mapping(source = "user.username", target = "userName")
  @Mapping(source = "subreddit.name", target = "subredditName")
  PostResponse mapPostToPostResponse(Post post);

}
