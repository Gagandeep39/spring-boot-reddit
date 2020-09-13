package com.gagan.redditclone.mapper;

import java.util.List;

import com.gagan.redditclone.dto.SubredditDto;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.Subreddit;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

  @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
  SubredditDto mapSubredditToDto(Subreddit subreddit);

  default Integer mapPosts(List<Post> posts) {
    return posts.size();
  }

  /**
   * Implies inverse mapping of another method in this file
   */
  @InheritInverseConfiguration
  @Mapping(target = "posts", ignore = true)
  Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
