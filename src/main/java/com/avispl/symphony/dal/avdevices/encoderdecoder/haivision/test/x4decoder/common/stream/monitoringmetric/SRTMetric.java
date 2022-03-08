/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.monitoringmetric;

/**
 * Set of SRT monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum SRTMetric {

	// Static metric
	RECONNECTIONS("SRTReconnections"),
	LOST_PACKETS("SRTLostPackets"),
	SENT_ACKS("SRTSentAcks"),
	SENT_NAKS("SRTSentNaks"),
	DROPPED_PACKET("SRTSkippedPackets"),
	PATH_MAX_BANDWIDTH("SRTPathMaxBandwidth"),
	RTT("SRTRtt"),

	// TODO: Can be Historical metric in next version
	BUFFER("SRT Buffer"),
	SRT_LATENCY("SRT Latency");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	SRTMetric(String name) {
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

