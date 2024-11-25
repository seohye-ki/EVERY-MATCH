package com.everymatch.mvc.model.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.FavoriteTeamDao;
import com.everymatch.mvc.model.dao.MatchDao;
import com.everymatch.mvc.model.dao.MatchScheduleDao;
import com.everymatch.mvc.model.dao.SportTeamDao;
import com.everymatch.mvc.model.dao.UserDao;
import com.everymatch.mvc.model.dto.MatchSchedule;
import com.everymatch.mvc.model.dto.User;
import com.everymatch.mvc.model.dto.Match;
import com.everymatch.mvc.util.EmailUtil;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {

	private final MatchScheduleDao matchScheduleDao;
	
	private final MatchDao matchDao;

	private final FavoriteTeamDao favoriteTeamDao;

	private final SportTeamDao sportTeamDao;

	private final UserDao userDao;

	private final EmailUtil emailUtil;

	public MatchScheduleServiceImpl(MatchScheduleDao matchScheduleDao, MatchDao matchDao, FavoriteTeamDao favoriteTeamDao,
			SportTeamDao sportTeamDao, UserDao userDao, EmailUtil emailUtil) {
		this.matchScheduleDao = matchScheduleDao;
		this.matchDao = matchDao;
		this.favoriteTeamDao = favoriteTeamDao;
		this.sportTeamDao = sportTeamDao;
		this.userDao = userDao;
		this.emailUtil = emailUtil;
	}

	@Override
	public List<MatchSchedule> getMatchScheduleByUserId(String userId) {
		return matchScheduleDao.getMatchScheduleByUserFavoriteTeamId(userId);
	}

	//경기 1시간 전 메일 보내기 
    @Scheduled(cron = "0 */5 * * * *")
    public void scheduleEmailCron(){
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String start = LocalDateTime.now().plusHours(1).minusMinutes(5).format(formatter);
    	String end = LocalDateTime.now().plusHours(1).format(formatter);
        List<Match> upcomingMatches = matchDao.getMatchesByTime(start, end);
        if(upcomingMatches == null || upcomingMatches.isEmpty())
        	return;
        
        for(Match match : upcomingMatches) {
        	List<String> likeUserIds = favoriteTeamDao.getUserIdsByTeamIds(match.getHomeTeamId(), match.getAwayTeamId());
	      	
	      	if(likeUserIds == null || likeUserIds.isEmpty())
	      		return;
	      	
	      	for(String userId: likeUserIds) {
	      		User user = userDao.getUserById(userId);
	      		String content = "	<div style='text-align: center;'>"
	      				+ "<h1>Today's Match</h1>"
	      				+ "<h3 안녕하세요, " + user.getNickname() + "님! 경기시작 1시간 전입니다.</h3>"
	      				+ "<br>"
	      				+ "<h4>경기 정보 : " + sportTeamDao.getSportTeamNameByTeamId(match.getHomeTeamId()) + " VS " + sportTeamDao.getSportTeamNameByTeamId(match.getAwayTeamId()) + "</h4>"
	      				+ "<h4>경기 장소 및 시간 : " + match.getTime() + " "  + match.getLocation() + "</h4>"
	      				+ "</div>";
	      		emailUtil.sendEmail(user.getEmail(), "오늘의 경기", content);
	      	}
      }
  }
}
