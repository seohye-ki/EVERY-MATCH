package com.everymatch.mvc.model.dto;

public class User {
	private String userId;
    private String nickname;
    private String password;
    private String email;
    private String userImg;
	
    public User() {
	}

	public User(String userId, String nickname, String email, String userImg) {
		this.userId = userId;
		this.nickname = nickname;
		this.email = email;
		this.userImg = userImg;
	}

	public User(String userId, String nickname, String password, String email, String userImg) {
		this.userId = userId;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.userImg = userImg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", nickname=" + nickname + ", email=" + email
				+ ", userImg=" + userImg + "]";
	}    
}
