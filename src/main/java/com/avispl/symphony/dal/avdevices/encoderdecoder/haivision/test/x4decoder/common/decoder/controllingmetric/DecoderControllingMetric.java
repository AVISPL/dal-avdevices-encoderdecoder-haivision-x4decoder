/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of decoder controlling metric keys
 *
 * @author Harry
 * @since 1.0
 */
public enum DecoderControllingMetric {

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
	STATE("Active"),
	STILL_IMAGE("StillImage"),
	STILL_IMAGE_DELAY("StillImageDelay"),
	APPLY_CHANGE("ApplyChange"),
	CANCEL("Cancel");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
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

	/**
	 * This method is used to get decoder controlling metric by name
	 *
	 * @param name is the name of decoder controlling metric that want to get
	 * @return DecoderControllingMetric is the decoder controlling metric that want to get
	 */
	public static DecoderControllingMetric getByName(String name) {
		Optional<DecoderControllingMetric> decoderControllingMetric = Arrays.stream(DecoderControllingMetric.values()).filter(com -> com.getName().equals(name)).findFirst();
		return decoderControllingMetric.orElse(null);
	}
}

