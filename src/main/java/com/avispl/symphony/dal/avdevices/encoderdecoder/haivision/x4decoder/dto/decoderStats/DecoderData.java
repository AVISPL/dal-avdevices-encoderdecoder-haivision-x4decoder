/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Decoder Data
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderData {

	@JsonAlias ("stats")
	private DecoderStats decoderStats;

	@JsonAlias("info")
	private DecoderInfo decoderInfo;

	/**
	 * Retrieves {@code {@link #decoderStats }}
	 *
	 * @return value of {@link #decoderStats}
	 */
	public DecoderStats getDecoderStats() {
		return decoderStats;
	}

	/**
	 * Sets {@code decoderStatistics}
	 *
	 * @param decoderStats the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderStatistics} field
	 */
	public void setDecoderStats(DecoderStats decoderStats) {
		this.decoderStats = decoderStats;
	}

	/**
	 * Retrieves {@code {@link #decoderInfo}}
	 *
	 * @return value of {@link #decoderInfo}
	 */
	public DecoderInfo getDecoderInfo() {
		return decoderInfo;
	}

	/**
	 * Sets {@code decoderInfo}
	 *
	 * @param decoderInfo the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderInfo} field
	 */
	public void setDecoderInfo(DecoderInfo decoderInfo) {
		this.decoderInfo = decoderInfo;
	}
}
