package com.gagan.redditclone.model;

import com.gagan.redditclone.exceptions.SpringRedditException;

import java.util.Arrays;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 19:09
 */
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;
    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
