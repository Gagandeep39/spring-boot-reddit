/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-13 18:38:07
 * @modify date 2020-09-13 18:38:07
 * @desc [description]
 */
package com.gagan.redditclone.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
  private Long id;
  private Long postId;
  private Instant createdDate;
  private String text;
  private String userName;
}
