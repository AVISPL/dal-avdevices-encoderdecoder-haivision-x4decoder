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
public enum SRTMetric {

	// Static metric
	ENCRYPTION("SRT Encryption"),
	KEY_LENGTH("SRT KeyLength"),
	DECRYPT_STATE("SRT DecryptState"),
	LOST_PACKETS("SRT LostPackets"),
	SENT_ACKS("SRT SentAcks"),
	SENT_NAKS("SRT SentNaks"),
	PATH_MAX_BANDWIDTH("SRT PathMaxBandwidth"),
	RTT("SRT Rtt"),

	// To Do: Can be Historical metric in next version
	BUFFER("SRT Buffer"),
	SRT_LATENCY("SRT Latency");

	private final String name;

	/**
	 *Parameterized constructor
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

