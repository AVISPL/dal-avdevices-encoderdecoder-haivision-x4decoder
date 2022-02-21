/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
}
