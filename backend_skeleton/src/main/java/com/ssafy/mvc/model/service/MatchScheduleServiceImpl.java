package com.ssafy.mvc.model.service;

import com.ssafy.mvc.model.dao.FavoriteTeamDao;
import com.ssafy.mvc.model.dao.MatchScheduleDao;
import com.ssafy.mvc.model.dto.MatchSchedule;
import com.ssafy.mvc.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {

    private MatchScheduleDao matchScheduleDao;
    private UserService userService;  // Assuming UserService includes method to get users interested in a team

    @Override
    public List<MatchSchedule> getDailyMatches(LocalDate date) {
        return matchScheduleDao.getMatchesByDate(date);
    }

    @Override
    public void notifyUsersOneHourBeforeMatch(int matchId) {
        MatchSchedule match = matchScheduleDao.getMatchById(matchId);
        // Assume a method to get all users who are interested in either the home or away team
        List<User> interestedUsers = userService.getUsersForTeam(match.getHomeTeamId());
        interestedUsers.addAll(userService.getUsersForTeam(match.getAwayTeamId()));

        for (User user : interestedUsers) {
            // Assume a method to send email
            sendNotificationEmail(user.getEmail(), match);
        }
    }

    private void sendNotificationEmail(String email, MatchSchedule match) {
        // Email sending logic
    }
}
