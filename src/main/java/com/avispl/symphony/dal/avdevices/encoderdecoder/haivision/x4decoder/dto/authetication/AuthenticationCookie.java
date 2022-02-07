/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Authentication cookie information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationCookie {

	@JsonAlias("username")
	String username;

	@JsonAlias("uid")
	Integer uid;

	@JsonAlias("gid")
	Integer gid;

	@JsonAlias("passwordExp")
	Integer passwordExp;

	/**
	 * Retrieves {@code {@link #username}}
	 *
	 * @return value of {@link #username}
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets {@code username}
	 *
	 * @param username the {@code java.lang.String} field
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retrieves {@code {@link #uid}}
	 *
	 * @return value of {@link #uid}
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * Sets {@code uid}
	 *
	 * @param uid the {@code java.lang.Integer} field
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * Retrieves {@code {@link #gid}}
	 *
	 * @return value of {@link #gid}
	 */
	public Integer getGid() {
		return gid;
	}

	/**
	 * Sets {@code gid}
	 *
	 * @param gid the {@code java.lang.Integer} field
	 */
	public void setGid(Integer gid) {
		this.gid = gid;
	}

	/**
	 * Retrieves {@code {@link #passwordExp}}
	 *
	 * @return value of {@link #passwordExp}
	 */
	public Integer getPasswordExp() {
		return passwordExp;
	}

	/**
	 * Sets {@code passwordExp}
	 *
	 * @param passwordExp the {@code java.lang.Integer} field
	 */
	public void setPasswordExp(Integer passwordExp) {
		this.passwordExp = passwordExp;
	}

	/**
	 * @return boolean
	 */
	public boolean isTimeout() {
		return this.passwordExp == 0;
	}
}
