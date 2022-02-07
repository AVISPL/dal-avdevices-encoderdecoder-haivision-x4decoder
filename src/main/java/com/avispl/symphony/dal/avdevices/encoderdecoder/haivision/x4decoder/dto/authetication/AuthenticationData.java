/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Authentication data
 *
 * @author Harry
 * @since 1.0
 */
public class AuthenticationData {

	@JsonAlias("data")
	AuthenticationCookie authenticationCookie;

	/**
	 * Retrieves {@code {@link #authenticationCookie}}
	 *
	 * @return value of {@link #authenticationCookie}
	 */
	public AuthenticationCookie getAuthenticationCookie() {
		return authenticationCookie;
	}

	/**
	 * Sets {@code authenticationCookie}
	 *
	 * @param authenticationCookie the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie} field
	 */
	public void setAuthenticationCookie(AuthenticationCookie authenticationCookie) {
		this.authenticationCookie = authenticationCookie;
	}
}
