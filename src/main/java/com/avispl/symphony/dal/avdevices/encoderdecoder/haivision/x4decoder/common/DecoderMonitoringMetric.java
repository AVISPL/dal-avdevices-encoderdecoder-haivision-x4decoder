/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of monitoring Metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum DecoderMonitoringMetric {

	DECODER_ID("General: Decoder ID"),
	STATE("General: State"),
	UPTIME("General: Uptime"),
	OVERSUBSCRIBED_FRAMES("General: Oversubscribed Frames"),
	BUFFERING_STATE("Buffering: Buffering State"),
	BUFFERING_MODE("Buffering: Buffering Mode"),
	BUFFERING_ADJUSTMENTS("Buffering: Buffering Adjustments"),
	VIDEO_LATENCY("Video: Latency"),
	STC_TO_PCR_LEAD_TIME("Video: Stc To Pcr Lead Time"),
	VIDEO_ALGORITHM("Video: Algorithm"),
	VIDEO_PROFILE("Video: Profile"),
	VIDEO_LEVEL("Video: Level"),
	VIDEO_OUTPUT_FORMAT("Video: Output Format"),
	VIDEO_FRAMING("Video: Framing"),
	VIDEO_SLICES_PER_FRAME("Video: Slices Per Frame"),
	VIDEO_INPUT_FRAME_RATE("Video: Input Frame Rate"),
	STILL_IMAGE("Video: Still Image"),
	VIDEO_DECODER_STATE("Video: Decoder State"),
	VIDEO_DISPLAY_FORMAT("Video: Display Format"),
	LOAD_PERCENTAGE("Video: Load Percentage"),
	PREPROCESSOR_STATE("Video: Preprocessor State"),
	TROUBLE_CODE("Video: Trouble Code"),
	DISPLAYED_OUTPUT_FRAMES("Video: Displayed Output Frames"),
	SKIPPED_OUTPUT_FRAMES("Video: Skipped Output Frames"),
	REPLAYED_OUTPUT_FRAMES("Video: Replayed Output Frames"),
	AUDIO_STATE("Audio: State"),
	AUDIO_SAMPLE_RATE("Audio: Sample Rate"),
	AUDIO_PAIRS_AMOUNT("Audio: Pairs Amount"),
	AUDIO_DECODED_FRAMES("Audio: Decoded Frames"),
	AUDIO_PLAYED_FRAMES("Audio: Played Frames"),
	AUDIO_SKIPPED_FRAMES("Audio: Skipped Frames"),
	AUDIO_PAIR_MODE("Audio: Pair Mode"),
	COMPRESSION("Audio: Compression"),
	BITRATE("Audio: Bitrate"),
	AV_SYNC_MS("Audio: Av Sync Ms"),
	DISCONTINUITIES("Audio: Discontinuities"),
	DECODE_ERRORS("Audio: Decode Errors"),
	OUTPUT_ERRORS("Audio: Output Errors"),
	SAMPE_RATE_IN("Audio: Sampe Rate In"),
	SAMPE_RATE_OUT("Audio: Sampe Rate Out"),
	CLOCK_TRACKING_MODE("Clock: Tracking Mode"),
	CLOCK_STATUS("Clock: Status"),
	CLOCK_RE_SYNC_COUNT("Clock: Re Sync Count"),
	CLOCK_CURRENT_STC("Clock: Current Stc"),
	CLOCK_STC_AVG("Clock: Stc Avg"),
	HDR_TYPE_IN("Hdr: Type In"),
	HDR_TYPE("Hdr: Type"),
	HDR_COLOUR_PRIMARIES("Hdr: Colour Primaries"),
	HDR_TRANSFER_CHARACTERISTICS("Hdr: Transfer Characteristics"),
	HDR_MATRIX_COEFFICIENTS("Hdr: Matrix Coefficients"),
	TC_RECEIVED_PACKETS("Time Code: Tc Received Packets"),
	TC_OUTPUT_PACKETS("Time Code: Tc Output Packets"),
	TC_FREED_PACKETS("Time Code: Tc Freed Packets");

	private final String name;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	DecoderMonitoringMetric(String name) {
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

