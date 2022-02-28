/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;

/**
 * Stream
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {

	@JsonAlias("info")
	private StreamInfo streamInfo;

	@JsonAlias("stats")
	private StreamStats streamStats;

	/**
	 * Retrieves {@code {@link #streamInfo}}
	 *
	 * @return value of {@link #streamInfo}
	 */
	public StreamInfo getStreamInfo() {
		return streamInfo;
	}

	/**
	 * Sets {@code streamInfo}
	 *
	 * @param streamInfo the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.StreamInfo} field
	 */
	public void setStreamInfo(StreamInfo streamInfo) {
		this.streamInfo = streamInfo;
	}

	/**
	 * Retrieves {@code {@link #streamStats}}
	 *
	 * @return value of {@link #streamStats}
	 */
	public StreamStats getStreamStats() {
		return streamStats;
	}

	/**
	 * Sets {@code streamStats}
	 *
	 * @param streamStats the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.StreamStats} field
	 */
	public void setStreamStats(StreamStats streamStats) {
		this.streamStats = streamStats;
	}

	/**
	 * @return String value of Stream monitoring properties by metric
	 */
	public String getValueByStreamMonitoringMetric(StreamMonitoringMetric streamMonitoringMetric) {

		switch (streamMonitoringMetric) {
			case ID:
				return streamInfo.getId().toString();
			case NAME:
				return streamInfo.getName();
			case DECODER_ID:
				return streamInfo.getDecoderId();
			case ENCAPSULATION:
				return streamInfo.getEncapsulation().getName();
			case STATE:
				return streamStats.getState();
			case SOURCE_ADDRESS:
				return streamStats.getSourceAddress();
			case BIT_RATE:
				return streamStats.getBitRate();
			case CONNECTIONS:
				return streamStats.getConnections();
			case RECEIVED_PACKET:
				return streamStats.getReceivedPacket();
			case RECEIVED_BYTES:
				return streamStats.getReceivedBytes();
			case OUTPUT_PACKETS:
				return streamStats.getOutputPackets();
			case PROGRAM_NUMBER:
				return streamStats.getProgramNumber();
			case PCR_PID:
				return streamStats.getPcrPid();
			case RECEIVED_ERRO:
				return streamStats.getReceivedErrors();
			case STREAM_LATENCY:
				return streamInfo.getLatency();
			case DROPPED_PACKETS:
				return streamStats.getDroppedPackets();
			case CORRUPTED_FRAMES:
				return streamStats.getCorruptedFrames();
			case RESTARTS:
				return streamStats.getRestarts();
			case LOCAL_PORT:
				return streamStats.getLocalPort();
			case REMOTE_PORT:
				return streamStats.getRemotePort();
			default:
				return DecoderConstant.NONE;
		}
	}
}
