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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.dto.SportTeam;
import com.everymatch.mvc.model.service.FavoriteTeamService;
import com.everymatch.mvc.model.service.SportTeamService;

@RestController
@RequestMapping("/api-favorite")
public class FavoriteTeamController {

	private final FavoriteTeamService favoriteTeamService;
	
	private final SportTeamService sportTeamService;
	
	public FavoriteTeamController(FavoriteTeamService favoriteTeamService, SportTeamService sportTeamService) {
		this.favoriteTeamService = favoriteTeamService;
		this.sportTeamService = sportTeamService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<HashMap<String, List<SportTeam>>> getFavoriteTeams(@PathVariable String userId) {
		HashMap<String, List<SportTeam>> result = new HashMap<>();
		//all teams 리스트 만들기
		List<SportTeam> allTeams = sportTeamService.getAllSportTeams();
		result.put("allTeams", allTeams);
		
		//favorite teams 리스트 만들기
		List<SportTeam> favoriteTeams = new ArrayList<>();
		List<Integer> teamIds = favoriteTeamService.getFavoriteTeams(userId);
		if(teamIds == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		for(Integer id : teamIds) {
			favoriteTeams.add(sportTeamService.getSportTeamByTeamId(id));
		}
		result.put("favoriteTeams", favoriteTeams);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<String> saveFavoriteTeams(@PathVariable String userId, @RequestBody List<Integer> teamIds) {
		favoriteTeamService.updateFavoriteTeam(userId, teamIds);
		return new ResponseEntity<>("관심팀 저장 완료", HttpStatus.OK);
	}
}
