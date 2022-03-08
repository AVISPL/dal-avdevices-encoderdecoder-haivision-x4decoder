/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.deviceinfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * System info
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemInfo {

	@JsonAlias("hasHDR")
	private boolean hasHDR;

	/**
	 * Retrieves {@code {@link #hasHDR}}
	 *
	 * @return value of {@link #hasHDR}
	 */
	public boolean isHasHDR() {
		return hasHDR;
	}

	/**
	 * Sets {@code hasHDR}
	 *
	 * @param hasHDR the {@code boolean} field
	 */
	public void setHasHDR(boolean hasHDR) {
		this.hasHDR = hasHDR;
	}
}
