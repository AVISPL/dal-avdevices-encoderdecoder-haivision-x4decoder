/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Authentication cookie information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationCookie {

	private String sessionID;

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
