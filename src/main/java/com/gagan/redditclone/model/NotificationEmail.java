/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2020-09-12 22:24:47
 * @modify date 2020-09-12 22:24:47
 * @desc [description]
 */
package com.gagan.redditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
  private String subject;
  private String recipient;
  private String body;
}
