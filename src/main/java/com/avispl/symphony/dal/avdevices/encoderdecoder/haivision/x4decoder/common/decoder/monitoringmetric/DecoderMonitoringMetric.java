/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric;

/**
 * Set of decoder monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum DecoderMonitoringMetric {

	// Static metric
	DECODER_ID("ContentDecoderID"),
	STATE("ContentState"),
	UPTIME("ContentUptime"),
	OVERSUBSCRIBED_FRAMES("ContentOversubscribedFrames"),
	BUFFERING_STATE("DecoderBufferingState"),
	BUFFERING_MODE("DecoderBufferingMode"),
	BUFFERING_ADJUSTMENTS("DecoderBufferingAdjustments"),
	BUFFERING_DELAY("DecoderBufferingDelay"),
	LATENCY("DecoderLatency"),
	VIDEO_LATENCY("VideoLatency"),
	STC_TO_PCR_LEAD_TIME("VideoStcToPcrLeadTime"),
	VIDEO_ALGORITHM("VideoAlgorithm"),
	VIDEO_PROFILE("VideoProfile"),
	VIDEO_LEVEL("VideoLevel"),
	VIDEO_OUTPUT_FORMAT("VideoOutputFormat"),
	VIDEO_FRAMING("VideoFraming"),
	VIDEO_SLICES_PER_FRAME("VideoSlicesPerFrame"),
	VIDEO_INPUT_FRAME_RATE("VideoInputFrameRate"),
	STILL_IMAGE("VideoStillImage"),
	VIDEO_DECODER_STATE("VideoDecoderState"),
	VIDEO_DISPLAY_FORMAT("VideoDisplayFormat"),
	LOAD_PERCENTAGE("VideoLoadPercentage"),
	PREPROCESSOR_STATE("VideoPreprocessorState"),
	TROUBLE_CODE("VideoTroubleCode"),
	DISPLAYED_OUTPUT_FRAMES("VideoDisplayedOutputFrames"),
	SKIPPED_OUTPUT_FRAMES("VideoSkippedOutputFrames"),
	REPLAYED_OUTPUT_FRAMES("VideoReplayedOutputFrames"),
	AUDIO_STATE("DecoderAudioState"),
	AUDIO_SAMPLE_RATE("DecoderAudioSampleRate"),
	AUDIO_PAIRS_AMOUNT("DecoderDecoderAudioPairsAmount"),
	AUDIO_DECODED_FRAMES("DecoderAudioDecodedFrames"),
	AUDIO_PLAYED_FRAMES("DecoderAudioPlayedFrames"),
	AUDIO_SKIPPED_FRAMES("DecoderAudioSkippedFrames"),
	CLOCK_TRACKING_MODE("DecoderClockTrackingMode"),
	CLOCK_STATUS("DecoderClockStatus"),
	CLOCK_RE_SYNC_COUNT("DecoderClockReSyncCount"),
	CLOCK_CURRENT_STC("DecoderClockCurrentStc"),
	CLOCK_STC_AVG("DecoderClockStcAvg"),
	HDR_TYPE_IN("HdrTypeIn"),
	HDR_TYPE("HdrType"),
	HDR_COLOUR_PRIMARIES("HdrColourPrimaries"),
	HDR_TRANSFER_CHARACTERISTICS("HdrTransferCharacteristics"),
	HDR_MATRIX_COEFFICIENTS("HdrMatrixCoefficients"),
	TC_RECEIVED_PACKETS("TimeCodeTcReceivedPackets"),
	TC_OUTPUT_PACKETS("TimeCodeTcOutputPackets"),
	TC_FREED_PACKETS("TimeCodeTcFreedPackets");

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

