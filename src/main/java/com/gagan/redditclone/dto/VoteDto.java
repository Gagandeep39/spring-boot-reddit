package com.gagan.redditclone.dto;

import com.gagan.redditclone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gagandeep
 * @date 13-09-2020
 * @time 19:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
