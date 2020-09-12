package com.gagan.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author Gagandeep
 * @date 12-09-2020
 * @time 17:42
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Instant createdDate;
    /**
     * Many to One creates a column in this Table
     * Join column specifies the column for which this table must create a foreign key
     * For bidirectional relation, we must specify mappedBy=user in other Table
     */
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    @OneToMany(mappedBy = "subreddit")
    private List<Post> posts;
}
