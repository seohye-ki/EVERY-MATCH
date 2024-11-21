package com.everymatch.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.dto.SportTeam;
import com.everymatch.mvc.model.service.FavoriteTeamService;
import com.everymatch.mvc.model.service.SportTeamService;
import com.everymatch.mvc.util.JwtUtil;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteTeamController {

	private final FavoriteTeamService favoriteTeamService;

	private final SportTeamService sportTeamService;

	private final JwtUtil jwtUtil;

	public FavoriteTeamController(FavoriteTeamService favoriteTeamService, SportTeamService sportTeamService, JwtUtil jwtUtil) {
		this.favoriteTeamService = favoriteTeamService;
		this.sportTeamService = sportTeamService;
		this.jwtUtil = jwtUtil;
	}

	private String extractUserIdFromToken(String token) {
		return jwtUtil.extractClaim(token, "userId");
	}

	@GetMapping
	public ResponseEntity<HashMap<String, List<SportTeam>>> getFavoriteTeams(
			@RequestHeader("Authorization") String token) {
		HashMap<String, List<SportTeam>> result = new HashMap<>();
		// all teams 리스트 만들기
		List<SportTeam> allTeams = sportTeamService.getAllSportTeams();
		result.put("allTeams", allTeams);

		// favorite teams 리스트 만들기
		List<SportTeam> favoriteTeams = new ArrayList<>();
		String userId = extractUserIdFromToken(token);
		List<Integer> teamIds = favoriteTeamService.getFavoriteTeams(userId);
		if (teamIds == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		for (Integer id : teamIds) {
			favoriteTeams.add(sportTeamService.getSportTeamByTeamId(id));
		}
		result.put("favoriteTeams", favoriteTeams);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<String> saveFavoriteTeams(@RequestHeader("Authorization") String token,
			@RequestBody List<Integer> teamIds) {
		String userId = extractUserIdFromToken(token);
		favoriteTeamService.updateFavoriteTeam(userId, teamIds);
		return new ResponseEntity<>("관심팀 저장 완료", HttpStatus.OK);
	}
}
