package com.gagan.redditclone.model;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 19:09
 */
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    VoteType(int direction) {
    }
}
