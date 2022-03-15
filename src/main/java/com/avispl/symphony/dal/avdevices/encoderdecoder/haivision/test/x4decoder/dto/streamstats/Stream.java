/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.NormalizeData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;

/**
 * Stream
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {

	@JsonAlias("info")
	private StreamInfo streamInfo;

	@JsonAlias("stats")
	private StreamStats streamStats;

	private NormalizeData normalizeData = new NormalizeData();

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
				return normalizeData.getDataValue(streamStats.getBitRate());
			case CONNECTIONS:
				return normalizeData.getDataValue(streamStats.getConnections());
			case LAST_CONNECTIONS:
				return normalizeData.getDataExtraInfo(streamStats.getConnections());
			case RECEIVED_PACKET:
				return normalizeData.getValueOnly(streamStats.getReceivedPackets());
			case RECEIVED_BYTES:
				return normalizeData.getValueOnly(streamStats.getReceivedBytes());
			case OUTPUT_PACKETS:
				return normalizeData.getDataValue(streamStats.getOutputPackets());
			case LAST_OUTPUT_PACKETS:
				return normalizeData.getDataExtraInfo(streamStats.getOutputPackets());
			case PROGRAM_NUMBER:
				return streamStats.getProgramNumber();
			case PCR_PID:
				return streamStats.getPcrPid();
			case ERROR_DROPPED_PACKETS:
				return normalizeData.getDataValue(streamStats.getDroppedPackets());
			case ERROR_LAST_DROPPED_PACKETS:
				return normalizeData.getDataExtraInfo(streamStats.getDroppedPackets());
			case ERROR_CORRUPTED_FRAMES:
				return normalizeData.getDataValue(streamStats.getCorruptedFrames());
			case ERROR_LAST_CORRUPTED_FRAMES:
				return normalizeData.getDataExtraInfo(streamStats.getCorruptedFrames());
			case ERROR_RESTARTS:
				return normalizeData.getDataValue(streamStats.getResumedStreamFlags());
			case ERROR_LAST_RESTARTS:
				return normalizeData.getDataExtraInfo(streamStats.getResumedStreamFlags());
			case OUTPUT_BYTES:
				return normalizeData.getValueOnly(streamStats.getOutputBytes());
			case LAST_RECEIVED:
				return streamStats.getLastReceived();
			case STREAM_SUMMARY:
				return streamStats.getStreamSummary();
			default:
				return DecoderConstant.NONE;
		}
	}
}
