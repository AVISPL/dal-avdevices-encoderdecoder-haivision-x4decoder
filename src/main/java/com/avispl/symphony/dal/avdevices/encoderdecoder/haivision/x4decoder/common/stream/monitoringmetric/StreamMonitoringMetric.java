/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric;

/**
 * Set of stream monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum StreamMonitoringMetric {

	// Static metric
	ID("Content ID"),
	NAME("Content Name"),
	DECODER_ID("Content DecoderId"),
	ENCAPSULATION("General Encapsulation"),
	STATE("General Stream Status"),
	SOURCE_ADDRESS("General SourceAddress"),
	BIT_RATE("General BitRate"),
	CONNECTIONS("General Connections"),
	RECEIVED_PACKET("General ReceivedPacket"),
	RECEIVED_BYTES("General ReceivedBytes"),
	LAST_RECEIVED("General LastReceived"),
	OUTPUT_PACKETS("General OutputPackets"),
	OUTPUT_BYTES("General OutputBytes"),
	PROGRAM_NUMBER("General ProgramNumber"),
	PCR_PID("General PcrPid"),
	RECEIVED_ERRO("Stream Error ReceivedError"),
	STREAM_LATENCY("Stream Latency"),
	STREAM_SUMMARY("SRT StreamSummary"),
	DROPPED_PACKETS("SRT DroppedPackets"),
	CORRUPTED_FRAMES("SRT CorruptedFrames"),
	RESTARTS("SRT Restarts"),
	LOCAL_PORT("SRT LocalPort"),
	REMOTE_PORT("SRT RemotePort");

	private final String name;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	StreamMonitoringMetric(String name) {
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
