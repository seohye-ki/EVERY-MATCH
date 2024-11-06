package com.ssafy.mvc.util;

public class EmailNotification {
    @Autowired
    private JavaMailSender mailSender;

    // 알림 이메일 전송
    public void sendMatchNotification(String email, MatchSchedule matchDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("경기 알림: " + matchDetails.getSportName() + " - " + matchDetails.getHomeTeamId() + " vs " + matchDetails.getAwayTeamId());
        message.setText("경기 시간: " + matchDetails.getDate() + " " + matchDetails.getTime() + "\n장소: " + matchDetails.getLocation());
        mailSender.send(message);
    }
}
