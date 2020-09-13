package com.gagan.redditclone.service;

import com.gagan.redditclone.dto.VoteDto;
import com.gagan.redditclone.exceptions.SpringRedditException;
import com.gagan.redditclone.model.Post;
import com.gagan.redditclone.model.Vote;
import com.gagan.redditclone.repository.PostRepository;
import com.gagan.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gagan.redditclone.model.VoteType.UPVOTE;

/**
 * @author Gagandeep
 * @date 13-09-2020
 * @time 19:52
 */

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;

    /**
     * TODO Fix Wrong voting Logic Currently Same user cn perform multiple votes in
     * the manner up, down, up down
     */
    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new SpringRedditException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
    }
}
