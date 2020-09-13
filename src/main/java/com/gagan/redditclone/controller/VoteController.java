package com.gagan.redditclone.controller;

import com.gagan.redditclone.dto.VoteDto;
import com.gagan.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gagandeep
 * @date 13-09-2020
 * @time 19:51
 */

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<String> vote(@RequestBody VoteDto voteDto){
        voteService.vote(voteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Succesuly updated Vote");
    };
}
