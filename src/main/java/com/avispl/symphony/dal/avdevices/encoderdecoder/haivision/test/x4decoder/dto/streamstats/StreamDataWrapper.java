/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream data wrapper
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/11/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamDataWrapper {

	@JsonAlias("data")
	Stream streams;

	/**
	 * Retrieves {@code {@link #streams}}
	 *
	 * @return value of {@link #streams}
	 */
	public Stream getStreams() {
		return streams;
	}

	/**
	 * Sets {@code streams}
	 *
	 * @param streams the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Stream} field
	 */
	public void setStreams(Stream streams) {
		this.streams = streams;
	}
}
