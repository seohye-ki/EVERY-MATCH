package com.everymatch.mvc.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ChatbotRequest {
	
	private String model;
	private List<Message> messages;
	private double temperature;
	
	public ChatbotRequest() {
		this.messages = new ArrayList<>();
	}
	
	public ChatbotRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(Message messages) {
		this.messages.add(messages);
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
}
