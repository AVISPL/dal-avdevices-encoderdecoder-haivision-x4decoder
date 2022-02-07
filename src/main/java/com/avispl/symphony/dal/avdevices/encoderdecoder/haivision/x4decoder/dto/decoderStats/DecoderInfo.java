/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Decoder information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderInfo {

	@JsonAlias ("stats")
	private DecoderStats decoderStatistics;

	/**
	 * Retrieves {@code {@link #decoderStatistics}}
	 *
	 * @return value of {@link #decoderStatistics}
	 */
	public DecoderStats getDecoderStatistics() {
		return decoderStatistics;
	}

	/**
	 * Sets {@code decoderStatistics}
	 *
	 * @param decoderStatistics the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderStatistics} field
	 */
	public void setDecoderStatistics(DecoderStats decoderStatistics) {
		this.decoderStatistics = decoderStatistics;
	}
}
