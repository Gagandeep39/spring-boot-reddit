/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 12:48:12
 * @modify date 2020-09-13 12:48:12
 * @desc [description]
 */
package com.gagan.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
  private Long id;
  private String postName;
  private String url;
  private String description;
  private String userName;
  private String subredditName;
  private Integer voteCount;
  private Integer commentCount;
  private String duration;
//  private boolean upVote;
//  private boolean downVote;
}
