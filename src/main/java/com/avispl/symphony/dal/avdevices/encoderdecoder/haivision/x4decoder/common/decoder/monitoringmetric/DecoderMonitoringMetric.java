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
	BUFFERING_STATE("DecoderBufferingState"),
	BUFFERING_MODE("DecoderBufferingMode"),
	BUFFERING_ADJUSTMENTS("DecoderBufferingAdjustments"),
	STC_TO_PCR_LEAD_TIME("DecoderBufferingStcToPcrLeadTime"),
	VIDEO_LATENCY("DecoderBufferingVideoLatency"),
	VIDEO_ALGORITHM("VideoAlgorithm"),
	VIDEO_PROFILE("VideoProfile"),
	VIDEO_LEVEL("VideoLevel"),
	VIDEO_FRAMING("VideoFraming"),
	VIDEO_SLICES_PER_FRAME("VideoSlicesPerFrame"),
	STILL_IMAGE("VideoStillImage"),
	VIDEO_DECODER_STATE("VideoDecoderState"),
	VIDEO_DISPLAY_FORMAT("VideoDisplayFormat"),
	LOAD_PERCENTAGE("VideoLoadPercentage"),
	PREPROCESSOR_STATE("VideoPreprocessorState"),
	VIDEO_INPUT_PACKET("VideoInputPackets"),
	DISPLAYED_OUTPUT_FRAMES("VideoDisplayedOutputFrames"),
	VIDEO_ENCODER_FORMAT("VideoEncoderFormat"),
	SKIPPED_OUTPUT_FRAMES("VideoSkippedOutputFrames"),
	REPLAYED_OUTPUT_FRAMES("VideoReplayedOutputFrames"),
	AUDIO_STATE("DecoderAudioState"),
	AUDIO_SAMPLE_RATE("DecoderAudioSampleRate"),
	AUDIO_PAIRS_AMOUNT("DecoderAudioPairsAmount"),
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
	TC_FREED_PACKETS("TimeCodeTcFreedPackets"),
	MULTI_SYNC_DELAY_RANGE_MIN_MS("MultisyncDelayRangeMinMs"),
	MULTI_SYNC_DELAY_RANGE_MAX_MS("MultisyncDelayRangeMaxMs"),
	MULTI_SYNC_STATUS("MultisyncStatus"),
	MULTI_SYNC_STATUS_CODE("MultisyncStatusCode"),
	MULTI_SYNC_DELAY_ACTUAL("MultisyncDelayActual"),
	MULTI_SYNC_DELAY_RANGE("MultisyncDelayRange"),
	MULTI_SYNC_DELAY_SET("MultisyncDelaySet"),
	MULTI_SYNC_SYSTEM_TIME("MultisyncSystemTime"),
	MULTI_SYNC_TIME_CODE("MultisyncTimecode"),
	MULTI_SYNC_TIME_CODE_PACKETS("MultisyncTimecodePackets"),
	MULTI_SYNC_TIME_DIFF("MultisyncTimeDiff"),
	MULTI_SYNC_TRANSMISSION_TIME("MultisyncTransmissionTime");

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

