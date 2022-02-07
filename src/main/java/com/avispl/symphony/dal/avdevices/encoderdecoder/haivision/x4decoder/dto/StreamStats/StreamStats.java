/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream statistics
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamStats {
	@JsonAlias("name")
	private String name;

	@JsonAlias("encapsulation")
	private String encapsulation;

	@JsonAlias("decoderId")
	private String decoderId;

	@JsonAlias("state")
	private String state;

	@JsonAlias("sourceAddress")
	private String sourceAddress;

	@JsonAlias("bitRate")
	private String bitRate;

	@JsonAlias("connections")
	private String connections;

	@JsonAlias("receivedPacket")
	private String receivedPacket;

	@JsonAlias("receivedBytes")
	private String receivedBytes;

	@JsonAlias("lastReceived")
	private String lastReceived;

	@JsonAlias("outputPackets")
	private String outputPackets;

	@JsonAlias("outputBytes")
	private String outputBytes;

	@JsonAlias("programNumber")
	private String programNumber;

	@JsonAlias("pcrPid")
	private String pcrPid;

	@JsonAlias("streamSummary")
	private String streamSummary;

	@JsonAlias("droppedPackets")
	private String droppedPackets;

	@JsonAlias("corruptedFrames")
	private String corruptedFrames;

	@JsonAlias("restarts")
	private String restarts;

	@JsonAlias("localPort")
	private String localPort;

	@JsonAlias("remotePort")
	private String remotePort;

}
