package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.MatchSchedule;
import com.ssafy.mvc.model.dto.SportTeam;
import com.ssafy.mvc.model.service.FavoriteTeamService;
import com.ssafy.mvc.model.service.MatchScheduleService;
import com.ssafy.mvc.model.service.SportTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class MatchScheduleController {

    @Autowired
    private SportTeamService sportTeamService;

    @Autowired
    private MatchScheduleService matchScheduleService;

    @Autowired
    private FavoriteTeamService favoriteTeamService;

    // Team related endpoints
    @GetMapping("/teams")
    public ResponseEntity<List<SportTeam>> getAllTeams() {
        List<SportTeam> teams = sportTeamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/teams/{sportName}")
    public ResponseEntity<List<SportTeam>> getTeamsBySport(@PathVariable String sportName) {
        List<SportTeam> teams = sportTeamService.getTeamsBySport(sportName);
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/teams/favorites/{userId}")
    public ResponseEntity<?> updateFavoriteTeams(@PathVariable String userId, @RequestBody List<Integer> teamIds) {
        favoriteTeamService.updateFavoriteTeams(userId, teamIds);
        return ResponseEntity.ok().body("Favorite teams updated successfully!");
    }

    @GetMapping("/teams/favorites/{userId}")
    public ResponseEntity<List<SportTeam>> getFavoriteTeams(@PathVariable String userId) {
        List<SportTeam> favorites = favoriteTeamService.getFavoriteTeams(userId);
        return ResponseEntity.ok(favorites);
    }

    // Match related endpoints
    @GetMapping("/matches/daily")
    public ResponseEntity<List<MatchSchedule>> getDailyMatches(@RequestParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<MatchSchedule> matches = matchScheduleService.getMatchesByDate(parsedDate);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/matches/team/{teamId}")
    public ResponseEntity<List<MatchSchedule>> getMatchesByTeam(@PathVariable int teamId) {
        List<MatchSchedule> matches = matchScheduleService.getMatchesByTeamId(teamId);
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/matches/{matchId}/notify")
    public ResponseEntity<?> notifyOneHourBeforeMatch(@PathVariable int matchId) {
        try {
            matchScheduleService.notifyUsersOne HourBeforeMatch(matchId);
            return ResponseEntity.ok("Notifications sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending notifications: " + e.getMessage());
        }
    }

}
