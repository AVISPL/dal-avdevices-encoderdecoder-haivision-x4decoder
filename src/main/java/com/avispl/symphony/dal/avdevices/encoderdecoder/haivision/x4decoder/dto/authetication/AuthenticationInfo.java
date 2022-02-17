/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Authentication information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationInfo {

	@JsonAlias("info")
	private AuthenticationRole authenticationRole;

	/**
	 * Retrieves {@code {@link #authenticationRole }}
	 *
	 * @return value of {@link #authenticationRole}
	 */
	public AuthenticationRole getAuthenticationRole() {
		return authenticationRole;
	}

	/**
	 * Sets {@code authenticationCookie}
	 *
	 * @param authenticationRole the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationRole} field
	 */
	public void setAuthenticationRole(AuthenticationRole authenticationRole) {
		this.authenticationRole = authenticationRole;
	}
}
