package com.gagan.redditclone.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gagan.redditclone.dto.CommentsDto;
import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.mapper.CommentMapper;
import com.gagan.redditclone.model.Comment;
import com.gagan.redditclone.model.NotificationEmail;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.User;
import com.gagan.redditclone.repository.CommentRepository;
import com.gagan.redditclone.repository.PostRepository;
import com.gagan.redditclone.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final CommentMapper commentMapper;
  private final AuthService authService;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;
  private final UserRepository userRepository;

  public Comment saveComment(CommentsDto commentsDto) {
    Post post = postRepository.findById(commentsDto.getPostId())
        .orElseThrow(() -> new SpringRedditException("Post not found"));
    Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
//    log.info(comment.toString());
    commentRepository.save(comment);
    String message = mailContentBuilder.build(post.getUser().getUsername() + " post a comment on post ");
    sendCommentNotification(message, post.getUser());
    return comment;
  }

  private void sendCommentNotification(String message, User user) {
    mailService
        .sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
  }

  public List<CommentsDto> getAllCommentsForPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new SpringRedditException("Post not found"));
    return commentRepository.findAllByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
  }

  public List<CommentsDto> getAllCommentsForUser(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found"));
    return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
  }
}
