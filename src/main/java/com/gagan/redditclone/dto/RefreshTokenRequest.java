package com.gagan.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gagandeep
 * @date 13-09-2020
 * @time 21:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    private String username;
    private String refreshToken;
}
