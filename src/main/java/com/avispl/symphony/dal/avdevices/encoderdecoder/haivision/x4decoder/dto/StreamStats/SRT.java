/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream SRT
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SRT {

	@JsonAlias("lostPackets")
	private String lostPackets;

	@JsonAlias("sentAcks")
	private String sentAcks;

	@JsonAlias("sentNaks")
	private String sentNaks;

	@JsonAlias("pathMaxBandwidth")
	private String pathMaxBandwidth;

	@JsonAlias("rtt")
	private String rtt;

	@JsonAlias("buffer")
	private String buffer;

	@JsonAlias("latency")
	private String latency;

	@JsonAlias("encryption")
	private String encryption;

	@JsonAlias("keyLength")
	private String keyLength;

	@JsonAlias("decryptState")
	private String decryptState;
}
