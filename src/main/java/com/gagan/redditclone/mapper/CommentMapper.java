package com.gagan.redditclone.mapper;

import com.gagan.redditclone.dto.CommentsDto;
import com.gagan.redditclone.model.Comment;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "commentsDto.text", target = "text")
  @Mapping(source = "post", target = "post")
  @Mapping(source = "user", target = "user")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
  Comment map(CommentsDto commentsDto, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
  @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
  CommentsDto mapToDto(Comment comment);
}
