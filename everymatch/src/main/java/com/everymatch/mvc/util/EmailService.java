package com.everymatch.mvc.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.everymatch.mvc.model.dto.MatchSchedule;
import com.everymatch.mvc.model.dto.User;
import com.everymatch.mvc.model.service.FavoriteTeamService;
import com.everymatch.mvc.model.service.MatchScheduleService;
import com.everymatch.mvc.model.service.SportTeamService;
import com.everymatch.mvc.model.service.UserService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private MatchScheduleService matchScheduleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SportTeamService sportTeamService;
	
	@Autowired
	private FavoriteTeamService favoriteTeamService;
	
	//메일 보내기
	public void sendEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//경기 1시간 전 메일 보내기 
    @Scheduled(cron = "0 */5 * * * *")
    public void scheduleEmailCron(){
        LocalDateTime time = LocalDateTime.now().plusHours(1); //찾고자하는 경기시간 지금으로부터 1시간 
        List<MatchSchedule> upcomingMatches = matchScheduleService.getMatchesByTime(time); //1시간 뒤 경기정보 모두 가져오기
    	
        //경기리스트를 돌면서 해당 경기의 팀 혹은 어웨이 팀을 관심팀으로 설정한 사용자들에게 메일 전
        for(MatchSchedule match : upcomingMatches) {
        	List<String> likeUserIds = favoriteTeamService.getUserIdsByTeamIds(match.getHomeTeamId(), match.getAwayTeamId());
        	
        	for(String userId: likeUserIds) {
        		User user = userService.detailUser(userId);
        		String content = "	<div style='text-align: center;'>"
        				+ "<h1>Today's Match</h1>"
        				+ "<h3 안녕하세요, " + user.getNickname() + "님! 경기시작 1시간 전입니다.</h3>"
        				+ "<br>"
        				+ "<h4>경기 정보 : " + sportTeamService.getSportTeamNameByTeamId(match.getHomeTeamId()) + " VS " + sportTeamService.getSportTeamNameByTeamId(match.getHomeTeamId()) + "</h4>"
        				+ "<h4>경기 장소 및 시간 : " + match.getTime() + " "  + match.getLocation() + "</h4>"
        				+ "</div>";
        		sendEmail(user.getEmail(), "오늘의 경기", content);
        	}
        }
    }
}