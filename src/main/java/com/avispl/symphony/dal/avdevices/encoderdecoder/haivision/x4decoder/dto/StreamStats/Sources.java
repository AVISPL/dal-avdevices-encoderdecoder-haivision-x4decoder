/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream sources
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sources {

	@JsonAlias("name")
	private String name;

	@JsonAlias("compression")
	private String compression;

	@JsonAlias("bitrate")
	private String bitrate;

	@JsonAlias("programId")
	private String programId;

	@JsonAlias("PTS")
	private String PTS;

	@JsonAlias("DTS")
	private String DTS;

}
