package com.everymatch.mvc.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.service.ChatGptService;
import com.everymatch.mvc.util.JwtUtil;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	private final ChatGptService chatGptService;
	private final JwtUtil jwtUtil;

    public ChatController(ChatGptService chatGptService, JwtUtil jwtUtil) {
        this.chatGptService = chatGptService;
        this.jwtUtil = jwtUtil;
    }
    
    private String extractUserIdFromToken(String token) {
		return jwtUtil.extractClaim(token, "userId");
	}

    @PostMapping("/ask")
    public String askChatGpt(@RequestHeader("Authorization") String token, @RequestParam String prompt) {
        try {
            String userId = extractUserIdFromToken(token);
        	System.out.println(prompt);
        	String ques = URLDecoder.decode(prompt, StandardCharsets.UTF_8.name());
        	System.out.println(ques);
        	String answer = chatGptService.getChatGptResponse(ques, userId);
        	System.out.println(answer);
            return answer;
        } catch (Exception e) {
            // Handle any errors or exceptions
            return "Error occurred: " + e.getMessage();
        }
    }
    
    @PostMapping("/team")
    public String askTeam(@RequestParam String prompt) {
        try {
        	System.out.println(prompt);
        	String ques = URLDecoder.decode(prompt, StandardCharsets.UTF_8.name());
        	System.out.println(ques);
        	String answer = chatGptService.recommendTeam(ques);
        	System.out.println(answer);
            return answer;
        } catch (Exception e) {
            // Handle any errors or exceptions
            return "Error occurred: " + e.getMessage();
        }
    }
}
