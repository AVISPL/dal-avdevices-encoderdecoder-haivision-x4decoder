/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Decoder information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderInfo {

	@JsonAlias("id")
	private String id;

	@JsonAlias("streamId")
	private String streamId;

	@JsonAlias("altStreamId")
	private String altStreamId;

	@JsonAlias("state")
	private String state;

	@JsonAlias("latency")
	private String latency;

	@JsonAlias("stillImage")
	private String stillImage;

	@JsonAlias("stillImageDelay")
	private String stillImageDelay;

	@JsonAlias("szStillImageFileName")
	private String szStillImageFileName;

	@JsonAlias("enableBuffering")
	private String enableBuffering;

	@JsonAlias("bufferingMode")
	private String bufferingMode;

	@JsonAlias("bufferingDelay")
	private String bufferingDelay;

	@JsonAlias("multisyncBufferingDelay")
	private String multisyncBufferingDelay;

	@JsonAlias("hdrDynamicRange")
	private String hdrDynamicRange;

	@JsonAlias("nNumOfOutputs")
	private String nNumOfOutputs;

	@JsonAlias("output1")
	private String output1;

	@JsonAlias("output2")
	private String output2;

	@JsonAlias("output3")
	private String output3;

	@JsonAlias("output4")
	private String output4;

	@JsonAlias("outputFrameRate")
	private String outputFrameRate;

	@JsonAlias("previewEnabled")
	private String previewEnabled;

	@JsonAlias("previewIntervalSec")
	private String previewIntervalSec;

	@JsonAlias("quadMode")
	private String quadMode;
}
