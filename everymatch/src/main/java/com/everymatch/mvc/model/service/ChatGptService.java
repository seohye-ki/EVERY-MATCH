package com.everymatch.mvc.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.everymatch.mvc.model.dto.ChatbotRequest;
import com.everymatch.mvc.model.dto.ChatbotResponse;
import com.everymatch.mvc.model.dto.Message;

@Service
public class ChatGptService {

	@Value("${openai.api.key}")
	private String apiKey;
	
	private final RestTemplate restTemplate;
	
	public ChatGptService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	 // Method to get a response from ChatGPT
    public String getChatGptResponse(String prompt) throws Exception {

        // Constructing the request DTO (ChatbotRequest)
        ChatbotRequest chatbotRequest = new ChatbotRequest("gpt-4o-mini", prompt);
        chatbotRequest.setTemperature(0.5);  // Example of temperature control

        // Setting headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create HttpEntity to wrap the request body and headers
        HttpEntity<ChatbotRequest> entity = new HttpEntity<>(chatbotRequest, headers);

        // Sending POST request to OpenAI API
        ResponseEntity<ChatbotResponse> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                ChatbotResponse.class
        );
        // Handling response and extracting message content
        if (response.getStatusCode() == HttpStatus.OK) {
            ChatbotResponse chatbotResponse = response.getBody();
            if (chatbotResponse != null && !chatbotResponse.getChoices().isEmpty()) {
                // Extract the first choice message
                Message message = chatbotResponse.getChoices().get(0).getMessage();
                return message.getContent();  // Return the chatbot's response
            } else {
                throw new Exception("No response from ChatGPT.");
            }
        } else {
            throw new Exception("Error from OpenAI API: " + response.getStatusCode());
        }
    }
}