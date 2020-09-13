package com.gagan.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;

import com.gagan.redditclone.dto.SubredditDto;
import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.mapper.SubredditMapper;
import com.gagan.redditclone.model.Subreddit;
import com.gagan.redditclone.repository.SubredditRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

  private final SubredditRepository subredditRepository;
  private final SubredditMapper subredditMapper;

  @Transactional
  public SubredditDto saveSubreddit(SubredditDto subredditDto) {
    Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
    subredditDto.setId(subreddit.getId());
    return subredditDto;
  }

  @Transactional(readOnly = true)
  public List<SubredditDto> getAll() {
    return subredditRepository
    .findAll()
    .stream()
    .map(subredditMapper::mapSubredditToDto)
    .collect(Collectors.toList());
  }

  public SubredditDto getSubredditById(Long id) {
    Subreddit subreddit = subredditRepository.findById(id)
      .orElseThrow(()-> new SpringRedditException("Subreddit not found with ID: " + id));
    return subredditMapper.mapSubredditToDto(subreddit);
  }

}
