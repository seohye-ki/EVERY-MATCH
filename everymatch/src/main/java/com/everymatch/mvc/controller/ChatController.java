package com.everymatch.mvc.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everymatch.mvc.model.service.ChatGptService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	private final ChatGptService chatGptService;

    public ChatController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    // Endpoint to accept user input and return ChatGPT response
    @PostMapping("/ask")
    public String askChatGpt(@RequestBody String prompt) {
        try {
            // Calling the service to get the ChatGPT response
        	System.out.println(prompt);
        	String ques = URLDecoder.decode(prompt, StandardCharsets.UTF_8.name());
        	System.out.println(ques);
        	String answer = chatGptService.getChatGptResponse(ques);
        	System.out.println(answer);
            return answer;
        } catch (Exception e) {
            // Handle any errors or exceptions
            return "Error occurred: " + e.getMessage();
        }
    }
}
