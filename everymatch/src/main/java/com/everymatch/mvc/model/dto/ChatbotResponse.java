package com.everymatch.mvc.model.dto;

import java.awt.Choice;
import java.util.List;

public class ChatbotResponse {
	private List<Choice> choices;

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public ChatbotResponse(List<Choice> choices) {
		super();
		this.choices = choices;
	}
	
	public ChatbotResponse() {
		
	}
	
	public static class Choice{
		private int index;
		private Message message;
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public Message getMessage() {
			return message;
		}
		public void setMessage(Message message) {
			this.message = message;
		}
		public Choice(int index, Message message) {
			this.index = index;
			this.message = message;
		}
		public Choice() {}
	}
}
