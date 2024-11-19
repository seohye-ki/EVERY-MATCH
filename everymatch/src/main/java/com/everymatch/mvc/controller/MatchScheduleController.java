package com.everymatch.mvc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.dto.MatchSchedule;
import com.everymatch.mvc.model.service.MatchScheduleService;

@RestController
@RequestMapping("/api/match")
public class MatchScheduleController {
	
	private final MatchScheduleService matchScheduleService;

    public MatchScheduleController(MatchScheduleService matchScheduleService) {
        this.matchScheduleService = matchScheduleService;
    }

    @GetMapping("/schedule/{userId}")
    public ResponseEntity<List<MatchSchedule>> getFavoriteTeamSchedule(@PathVariable String userId) {
        List<MatchSchedule> schedule = matchScheduleService.getMatchScheduleByUserId(userId);
        
        if (schedule == null || schedule.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
