/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.decoder.monitoringmetric;

/**
 * Set of audio pair metrics keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum AudioPairMetric {

	// Static metric
	AUDIO_PAIR_MODE("PairMode"),
	COMPRESSION("Compression"),
	BITRATE("Bitrate(kbps)"),
	AV_SYNC_MS("AvSyncMs"),
	DISCONTINUITIES("Discontinuities"),
	DECODE_ERRORS("DecodeErrors"),
	LAST_DECODE_ERRORS("LastDecodeErrors"),
	OUTPUT_ERRORS("OutputErrors"),
	LAST_OUTPUT_ERRORS("LastOutputErrors"),
	SAMPE_RATE_IN("SampeRateIn"),
	SAMPE_RATE_OUT(" SampeRateOut");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	AudioPairMetric(String name) {
		this.name = name;
	}

	/**
	 * retrieve {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return this.name;
	}

}

