/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric;

/**
 * Set of source monitoring metrics keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum SourceMetric {

	// Static metric
	COMPRESSION("Compression"),
	BITRATE("Bitrate(Mbps)"),
	PROGRAM_ID("ProgramID"),
	RECEIVED_PACKETS("ReceivedPackets"),
	LAST_RECEIVED_PACKETS("LastReceivedPackets"),
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

