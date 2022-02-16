/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication;

/**
 * Authentication cookie information
 *
 * @author Harry
 * @since 1.0
 */
public class AuthenticationCookie {

	String sessionID;

	/**
	 * Retrieves {@code {@link #sessionID }}
	 *
	 * @return value of {@link #sessionID}
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Sets {@code sessionID}
	 *
	 * @param sessionID the {@code java.lang.Integer} field
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

}
