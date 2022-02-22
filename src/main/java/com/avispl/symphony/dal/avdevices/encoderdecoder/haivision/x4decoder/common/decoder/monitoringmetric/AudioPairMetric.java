/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric;

/**
 * Set of audio pair metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum AudioPairMetric {

	// Static metric
	AUDIO_PAIR_MODE("Pair Mode"),
	COMPRESSION("Compression"),
	BITRATE("Bitrate"),
	AV_SYNC_MS("Av Sync Ms"),
	DISCONTINUITIES("Discontinuities"),
	DECODE_ERRORS("Decode Errors"),
	OUTPUT_ERRORS("Output Errors"),
	SAMPE_RATE_IN("Sampe Rate In"),
	SAMPE_RATE_OUT(" Sampe Rate Out");

	private final String name;

	/**
	 *Parameterized constructor
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

