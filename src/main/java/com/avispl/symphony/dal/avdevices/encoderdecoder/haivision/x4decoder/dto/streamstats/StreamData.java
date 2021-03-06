/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream Data
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamData {

	@JsonAlias("data")
	List<Stream> streams = new LinkedList<>();

	/**
	 * Retrieves {@code {@link #streams }}
	 *
	 * @return value of {@link #streams}
	 */
	public List<Stream> getStreams() {
		return streams;
	}

	/**
	 * Sets {@code Streams}
	 *
	 * @param streams the {@code java.util.List<com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.Stream>} field
	 */
	public void setStreams(List<Stream> streams) {
		this.streams = streams;
	}

	/**
	 * This method is used to get streamInfo by name
	 *
	 * @param streamName name of stream
	 */
	public Stream getStreamByStreamName(String streamName) {
		Optional<Stream> stream = streams.stream().filter(st-> streamName.equals(st.getStreamInfo().getName())).findFirst();
		return stream.orElse(null);
	}

}
