/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric;

/**
 * Set of decoder monitoring metrics keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum DecoderMonitoringMetric {

	// Static metric
	DECODER_ID("ContentDecoderID"),
	STATE("ContentState"),
	UPTIME("ContentUptime"),
	BUFFERING_STATE("DecoderBufferingState"),
	BUFFERING_MODE("DecoderBufferingMode"),
	BUFFERING_ADJUSTMENTS("DecoderBufferingAdjustments"),
	LAST_BUFFERING_ADJUSTMENTS("DecoderLastBufferingAdjustments"),
	STC_TO_PCR_LEAD_TIME("DecoderBufferingStcToPcrLeadTime(ms)"),
	VIDEO_LATENCY("DecoderBufferingVideoLatency(ms)"),
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
	DISPLAYED_OUTPUT_FRAMES_PERCENT("VideoDisplayedOutputFramesPercent(%)"),
	LAST_DISPLAYED_OUTPUT_FRAMES("VideoLastDisplayedOutputFrames"),
	VIDEO_ENCODER_FORMAT("VideoEncoderFormat"),
	SKIPPED_OUTPUT_FRAMES("VideoSkippedOutputFrames"),
	SKIPPED_OUTPUT_FRAMES_PERCENT("VideoSkippedOutputFramesPercent(%)"),
	LAST_SKIPPED_OUTPUT_FRAMES("VideoLastSkippedOutputFrames"),
	REPLAYED_OUTPUT_FRAMES("VideoReplayedOutputFrames"),
	REPLAYED_OUTPUT_FRAMES_PERCENT("VideoReplayedOutputFramesPercent(%)"),
	LAST_REPLAYED_OUTPUT_FRAMES("VideoLastReplayedOutputFrames"),
	AUDIO_STATE("DecoderAudioState"),
	AUDIO_SAMPLE_RATE("DecoderAudioSampleRate(kHz)"),
	AUDIO_PAIRS_AMOUNT("DecoderAudioPairsAmount"),
	AUDIO_DECODED_FRAMES("DecoderAudioDecodedFrames"),
	AUDIO_DECODED_FRAMES_PERCENT("DecoderAudioDecodedFramesPercent(%)"),
	LAST_AUDIO_DECODED_FRAMES("DecoderLastAudioDecodedFrames"),
	AUDIO_PLAYED_FRAMES("DecoderAudioPlayedFrames"),
	AUDIO_PLAYED_FRAMES_PERCENT("DecoderAudioPlayedFramesPercent(%)"),
	LAST_AUDIO_PLAYED_FRAMES("DecoderLastAudioPlayedFrames"),
	AUDIO_SKIPPED_FRAMES("DecoderAudioSkippedFrames"),
	AUDIO_SKIPPED_FRAMES_PERCENT("DecoderAudioSkippedFramesPercent(%)"),
	LAST_AUDIO_SKIPPED_FRAMES("DecoderLastAudioSkippedFrames"),
	CLOCK_TRACKING_MODE("DecoderClockTrackingMode"),
	CLOCK_STATUS("DecoderClockStatus"),
	CLOCK_RE_SYNC_COUNT("DecoderClockReSyncCount"),
	CLOCK_CURRENT_STC("DecoderClockCurrentStc(Hz)"),
	CLOCK_STC_AVG("DecoderClockStcAvg(Hz)"),
	HDR_TYPE_IN("HdrTypeIn"),
	HDR_TYPE("HdrType"),
	HDR_COLOUR_PRIMARIES("HdrColourPrimaries"),
	HDR_COLOUR_PRIMARIES_REC("HdrColourPrimariesRec"),
	HDR_TRANSFER_CHARACTERISTICS("HdrTransferCharacteristics"),
	HDR_TRANSFER_CHARACTERISTICS_REC("HdrTransferCharacteristicsRec"),
	HDR_MATRIX_COEFFICIENTS("HdrMatrixCoefficients"),
	HDR_MATRIX_COEFFICIENTS_REC("HdrMatrixCoefficientsRec"),
	TC_RECEIVED_PACKETS("TimeCodeTcReceivedPackets"),
	LAST_TC_RECEIVED_PACKETS("LastTimeCodeTcReceivedPackets"),
	TC_OUTPUT_PACKETS("TimeCodeTcOutputPackets"),
	LAST_TC_OUTPUT_PACKETS("LastTimeCodeTcOutputPackets"),
	TC_FREED_PACKETS("TimeCodeTcFreedPackets"),
	LAST_TC_FREED_PACKETS("LastTimeCodeTcFreedPackets"),
	MULTI_SYNC_STATUS("MultisyncStatus"),
	MULTI_SYNC_DELAY_ACTUAL("MultisyncDelayActual(ms)"),
	MULTI_SYNC_DELAY_RANGE("MultisyncDelayRange"),
	MULTI_SYNC_DELAY_SET("MultisyncDelaySet(ms)"),
	MULTI_SYNC_DELAY_SET_ALERT("MultisyncDelaySetAlert"),
	MULTI_SYNC_SYSTEM_TIME("MultisyncSystemTime(ms)"),
	MULTI_SYNC_TIME_CODE("MultisyncTimecode(ms)"),
	MULTI_SYNC_TIME_CODE_PACKETS("MultisyncTimecodePackets"),
	MULTI_SYNC_TIME_DIFF("MultisyncTimeDiff(ms)"),
	MULTI_SYNC_TIME_DIFF_COORDINATE_TIME("MultisyncTimeDiffCoordinateTime"),
	MULTI_SYNC_TRANSMISSION_TIME("MultisyncTransmissionTime(ms)");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
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

