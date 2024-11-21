package com.everymatch.mvc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.dto.MatchSchedule;
import com.everymatch.mvc.model.service.MatchScheduleService;
import com.everymatch.mvc.util.JwtUtil;

@RestController
@RequestMapping("/api/match")
public class MatchScheduleController {

    private final MatchScheduleService matchScheduleService;
    private final JwtUtil jwtUtil;

    public MatchScheduleController(MatchScheduleService matchScheduleService, JwtUtil jwtUtil) {
        this.matchScheduleService = matchScheduleService;
        this.jwtUtil = jwtUtil;
    }

    private String extractUserIdFromToken(String token) {
        return jwtUtil.extractClaim(token, "userId");
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<MatchSchedule>> getFavoriteTeamSchedule(@RequestHeader("Authorization") String token) {
        String userId = extractUserIdFromToken(token);
        List<MatchSchedule> schedule = matchScheduleService.getMatchScheduleByUserId(userId);

        if (schedule == null || schedule.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
