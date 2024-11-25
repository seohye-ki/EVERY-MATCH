package com.everymatch.mvc.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dao.FavoriteTeamDao;
import com.everymatch.mvc.model.dao.MatchScheduleDao;
import com.everymatch.mvc.model.dao.SportTeamDao;
import com.everymatch.mvc.model.dao.UserDao;
import com.everymatch.mvc.model.dto.MatchSchedule;
import com.everymatch.mvc.model.dto.User;
import com.everymatch.mvc.util.EmailUtil;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {

	private final MatchScheduleDao matchScheduleDao;

	private final FavoriteTeamDao favoriteTeamDao;

	private final SportTeamDao sportTeamDao;

	private final UserDao userDao;

	private final EmailUtil emailUtil;

	public MatchScheduleServiceImpl(MatchScheduleDao matchScheduleDao, FavoriteTeamDao favoriteTeamDao,
			SportTeamDao sportTeamDao, UserDao userDao, EmailUtil emailUtil) {
		this.matchScheduleDao = matchScheduleDao;
		this.favoriteTeamDao = favoriteTeamDao;
		this.sportTeamDao = sportTeamDao;
		this.userDao = userDao;
		this.emailUtil = emailUtil;
	}

	@Override
	public List<MatchSchedule> getMatchScheduleByUserId(String userId) {
		return matchScheduleDao.getMatchScheduleByUserFavoriteTeamId(userId);
	}

	// 경기 1시간 전 메일 보내기
	@Scheduled(cron = "0 */5 * * * *")
	public void scheduleEmailCron() {
		LocalDateTime time = LocalDateTime.now().plusHours(1); // 찾고자하는 경기시간 지금으로부터 1시간
		LocalDateTime start = time.minusMinutes(5);
		LocalDateTime end = time.plusMinutes(5);

		List<MatchSchedule> upcomingMatches = matchScheduleDao.getMatchesByTime(start, end);
		;

		for (MatchSchedule match : upcomingMatches) {
			List<String> likeUserIds = favoriteTeamDao.getUserIdsByTeamIds(
					sportTeamDao.getSportTeamIdByTeamName(match.getHomeTeamName()),
					sportTeamDao.getSportTeamIdByTeamName(match.getAwayTeamName()));

			for (String userId : likeUserIds) {
				User user = userDao.getUserById(userId);
				String content = "	<div style='text-align: center;'>"
						+ "<h1>Today's Match</h1>"
						+ "<h3 안녕하세요, " + user.getNickname() + "님! 경기시작 1시간 전입니다.</h3>"
						+ "<br>"
						+ "<h4>경기 정보 : " + match.getHomeTeamName() + " VS " + match.getAwayTeamName() + "</h4>"
						+ "<h4>경기 장소 및 시간 : " + match.getTime() + " " + match.getLocation() + "</h4>"
						+ "</div>";
				emailUtil.sendEmail(user.getEmail(), "오늘의 경기", content);
			}
		}
	}
}
