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
	ID("ContentID"),
	NAME("ContentName"),
	DECODER_ID("ContentDecoderId"),
	ENCAPSULATION("GeneralEncapsulation"),
	STATE("GeneralStreamStatus"),
	SOURCE_ADDRESS("GeneralSourceAddress"),
	BIT_RATE("GeneralBitRate"),
	CONNECTIONS("GeneralConnections"),
	RECEIVED_PACKET("GeneralReceivedPacket"),
	RECEIVED_BYTES("GeneralReceivedBytes"),
	LAST_RECEIVED("GeneralLastReceived"),
	OUTPUT_PACKETS("GeneralOutputPackets"),
	OUTPUT_BYTES("GeneralOutputBytes"),
	PROGRAM_NUMBER("GeneralProgramNumber"),
	PCR_PID("GeneralPcrPid"),
	RECEIVED_ERRO("StreamErrorReceivedError"),
	STREAM_LATENCY("StreamLatency"),
	STREAM_SUMMARY("SRTStreamSummary"),
	DROPPED_PACKETS("SRTDroppedPackets"),
	CORRUPTED_FRAMES("SRTCorruptedFrames"),
	RESTARTS("SRTRestarts"),
	LOCAL_PORT("SRTLocalPort"),
	REMOTE_PORT("SRTRemotePort");

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

