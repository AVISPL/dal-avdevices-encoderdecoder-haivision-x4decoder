/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric;

/**
 * Set of SRT monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum SourceMetric {

	// Static metric
	COMPRESSION("Compression"),
	BITRATE("Bitrate"),
	PROGRAM_ID("ProgramID"),
	RECEIVED_PACKETS("ReceivedPackets"),
	RECEIVED_BYTES("ReceivedBytes"),
	PTS("PTS"),
	DTS("DTS");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	SourceMetric(String name) {
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

