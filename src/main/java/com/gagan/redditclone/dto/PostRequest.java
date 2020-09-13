/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 12:47:52
 * @modify date 2020-09-13 12:47:52
 * @desc [description]
 */
package com.gagan.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
  private Long postId;
  private String subredditName;
  private String postName;
  private String url;
  private String description;
}
