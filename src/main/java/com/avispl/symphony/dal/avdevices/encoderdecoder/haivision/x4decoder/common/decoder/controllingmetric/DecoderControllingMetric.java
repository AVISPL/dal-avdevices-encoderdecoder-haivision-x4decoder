/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

/**
 * Set of decoder controlling metric keys
 *
 * @author Harry
 * @since 1.0
 */
public enum DecoderControllingMetric {

	DECODER("Decoder"),
	STREAM_ID("StreamId"),
	ALT_STREAM_ID("AltStreamId"),
	BUFFERING_DELAY("BufferingDelay"),
	BUFFERING_MODE("BufferingMode"),
	ENABLE_BUFFERING("EnableBuffering"),
	HDR_DYNAMIC_RANGE("HdrDynamicRange"),
	ID("Id"),
	LATENCY("Latency"),
	MULTI_SYNC_BUFFERING_DELAY("MultiSyncBufferingDelay"),
	NUM_OF_OUTPUTS("NumOfOutputs"),
	OUTPUT_1("Output1"),
	OUTPUT_2("Output2"),
	OUTPUT_3("Output3"),
	OUTPUT_4("Output4"),
	OUTPUT_FRAME_RATE("OutputFrameRate"),
	PREVIEW_ENABLED("PreviewEnabled"),
	PREVIEW_INTERVAL_SEC("PreviewIntervalSec"),
	QUAD_MODE("QuadMode"),
	STATE("State"),
	STILL_IMAGE("StillImage"),
	STILL_IMAGE_DELAY("StillImageDelay"),
	APPLY_CHANGE("ApplyChange");

	private final String name;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	DecoderControllingMetric(String name) {
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

