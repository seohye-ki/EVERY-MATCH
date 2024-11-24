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
	private String userId;
	private ChatbotRequest chatbotRequest;
	
	private final RestTemplate restTemplate;
	
	public ChatGptService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
    public String getChatGptResponse(String prompt, String userId) throws Exception {

    	if (this.userId != userId) {
    		this.userId = userId;
    		chatbotRequest = new ChatbotRequest();
    		chatbotRequest.setModel("gpt-4o-mini");
    	}
    	else if (chatbotRequest.getMessages().size() >= 10) {
    		chatbotRequest = new ChatbotRequest();
    		chatbotRequest.setModel("gpt-4o-mini");
    	}
    	chatbotRequest.setMessages(new Message("user",  prompt));
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
                chatbotRequest.setMessages(new Message("assistant", message.getContent()));
                return message.getContent();  // Return the chatbot's response
            } else {
                throw new Exception("No response from ChatGPT.");
            }
        } else {
            throw new Exception("Error from OpenAI API: " + response.getStatusCode());
        }
    }
    
    public String recommendTeam(String prompt) throws Exception {

    	ChatbotRequest teamRequest = new ChatbotRequest();
    	teamRequest.setModel("gpt-4o-mini");
    	teamRequest.setMessages(new Message("system",
        		"너는 질문, 답변들을 보고 어느 스포츠팀을 응원하는지 좋을지 추천해줘야 해. 답변 형식은 다음과 같아 "
        		+ "** 첫 문장 (재미와 팀 컬러 강조) **"
        		+ "** [팀명]라서 [재미있는 특징/문구]! **"
        		+ "** 본문 (심리적 성향과 팀 추천 이유) **"
        		+ "** 당신은 [성향 설명: 예. 느긋하지만 열정이 숨어있는 사람, 승부욕이 강하지만 실패에도 너그러운 성격 등]이군요. 그런 당신에게 딱 어울리는 [스포츠]팀은 바로 [팀명]입니다! **"
        		+ "** [팀명]은 [팀의 특징: 예. 열정적이지만 다소 기다림이 필요한 팀, 젊고 역동적인 팀 등]으로 당신의 성향과 꼭 맞는 매력을 가진 팀이죠. ** "
        		+ "** 참고로 [팀명]은 [팀의 역사적 성과, 유명한 특징, 현재 지표 등 한 문장: 예. KBO 리그에서 유일하게 9차례 우승한 팀, 가장 긴 리빌딩 역사를 가진 팀 등]을 자랑합니다. **"
        		+"위와 같이 답변해주고, 너가 선택할 수 있는 팀 명단은 다음과 같아"
        		+"{서울 FC, 전북 현대, 울산 현대, FC 안양, 수원 FC, 두산 베어스, LG 트윈스, 삼성 라이온즈, 한화 이글스, 기아 타이거즈, 키움 히어로즈, kt 위즈, 서울 삼성 썬더스, 인천 전자랜드, 창원 LG 세이커스, SK Knights, 우리은행 우리WON, 현대캐피탈 스카이워커스, 대한항공 점보스, 핑크 스파이더즈 }이고, 답변에서 []는 빼줘"
        		));
    	teamRequest.setMessages(new Message("user",  prompt));
    	teamRequest.setTemperature(0.5);  // Example of temperature control

        // Setting headers for the request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create HttpEntity to wrap the request body and headers
        HttpEntity<ChatbotRequest> entity = new HttpEntity<>(teamRequest, headers);

        // Sending POST request to OpenAI API
        ResponseEntity<ChatbotResponse> response = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                ChatbotResponse.class
        );
        System.out.println(response);
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