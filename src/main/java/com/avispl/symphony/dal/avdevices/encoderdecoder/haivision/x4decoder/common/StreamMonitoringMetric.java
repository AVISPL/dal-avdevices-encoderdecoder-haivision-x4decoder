/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of stream monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum StreamMonitoringMetric {
	NAME("Name"),
	ENCAPSULATION("Encapsulation"),
	DECODER_ID("DecoderId"),
	STATE("State"),
	SOURCE_ADDRESS("SourceAddress"),
	BIT_RATE("BitRate"),
	CONNECTIONS("Connections"),
	RECEIVED_PACKET("ReceivedPacket"),
	RECEIVED_BYTES("ReceivedBytes"),
	LAST_RECEIVED("LastReceived"),
	OUTPUT_PACKETS("OutputPackets"),
	OUTPUT_BYTES("OutputBytes"),
	PROGRAM_NUMBER("ProgramNumber"),
	PCR_PID("PcrPid"),
	STREAM_SUMMARY("StreamSummary"),
	DROPPED_PACKETS("DroppedPackets"),
	CORRUPTED_FRAMES("CorruptedFrames"),
	RESTARTS("Restarts"),
	LOCAL_PORT("LocalPort"),
	REMOTE_PORT("RemotePort"),
	ENCRYPTION("Encryption"),
	KEY_LENGTH("KeyLength"),
	DECRYPT_STATE("DecryptState"),
	LOST_PACKETS("LostPackets"),
	SRT_DROPPED_PACKETS("DroppedPackets"),
	SENT_ACKS("SentAcks"),
	SENT_NAKS("SentNaks"),
	PATH_MAX_BANDWIDTH("PathMaxBandwidth"),
	RTT("Rtt"),
	BUFFER("Buffer"),
	LATENCY("Latency"),
	COMPRESSION("Compression"),
	BITRATE("Bitrate"),
	PROGRAM_ID("ProgramId"),
	SOURCE_RECEIVED_PACKETS("ReceivedPackets"),
	SOURCE_RECEIVED_BYTES("ReceivedBytes"),
	PTS("PTS"),
	DTS("DTS");


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

