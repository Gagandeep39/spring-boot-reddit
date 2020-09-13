package com.gagan.redditclone.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 17:03
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    @Nullable
    private String url;
    @Lob
    @Nullable
    private String description;
    private Integer voteCount = 0;
    private Instant createdDate;
    @ManyToOne
    @JoinColumn(name = "subredditId", referencedColumnName = "id")
    private Subreddit subreddit;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

}
