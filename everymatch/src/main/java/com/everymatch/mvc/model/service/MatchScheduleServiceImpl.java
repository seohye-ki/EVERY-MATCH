package com.everymatch.mvc.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.MatchScheduleDao;
import com.everymatch.mvc.model.dto.MatchSchedule;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {

	private final MatchScheduleDao matchScheduleDao;
	
	public MatchScheduleServiceImpl(MatchScheduleDao matchScheduleDao) {
		this.matchScheduleDao = matchScheduleDao;
	}
	
	@Override
	public List<MatchSchedule> getMatchScheduleByUserId(String userId) {
		return matchScheduleDao.getMatchScheduleByUserFavoriteTeamId(userId);
	}

	@Override
	public List<MatchSchedule> getMatchesByTime(LocalDateTime time) {
		return matchScheduleDao.getMatchesByTime(time);
	}
	
//	//경기 1시간 전 메일 보내기 
//    @Scheduled(cron = "0 */5 * * * *")
//    public void scheduleEmailCron(){
//        LocalDateTime time = LocalDateTime.now().plusHours(1); //찾고자하는 경기시간 지금으로부터 1시간 
//        List<MatchSchedule> upcomingMatches = matchScheduleService.getMatchesByTime(time); //1시간 뒤 경기정보 모두 가져오기
//    	
//        //경기리스트를 돌면서 해당 경기의 팀 혹은 어웨이 팀을 관심팀으로 설정한 사용자들에게 메일 전
//        for(MatchSchedule match : upcomingMatches) {
//        	List<String> likeUserIds = favoriteTeamService.getUserIdsByTeamIds(match.getHomeTeamId(), match.getAwayTeamId());
//        	
//        	for(String userId: likeUserIds) {
//        		User user = userService.getUserDetails(userId);
//        		String content = "	<div style='text-align: center;'>"
//        				+ "<h1>Today's Match</h1>"
//        				+ "<h3 안녕하세요, " + user.getNickname() + "님! 경기시작 1시간 전입니다.</h3>"
//        				+ "<br>"
//        				+ "<h4>경기 정보 : " + sportTeamService.getSportTeamNameByTeamId(match.getHomeTeamId()) + " VS " + sportTeamService.getSportTeamNameByTeamId(match.getHomeTeamId()) + "</h4>"
//        				+ "<h4>경기 장소 및 시간 : " + match.getTime() + " "  + match.getLocation() + "</h4>"
//        				+ "</div>";
//        		sendEmail(user.getEmail(), "오늘의 경기", content);
//        	}
//        }
//    }
}
