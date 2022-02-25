/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.NomalizeData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;

/**
 * Decoder Data
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderData {

	@JsonAlias ("stats")
	private DecoderStats decoderStats;

	@JsonAlias("info")
	private DecoderInfo decoderInfo;

	private NomalizeData nomalizeData = new NomalizeData();

	/**
	 * Retrieves {@code {@link #decoderStats }}
	 *
	 * @return value of {@link #decoderStats}
	 */
	public DecoderStats getDecoderStats() {
		return decoderStats;
	}

	/**
	 * Sets {@code decoderStatistics}
	 *
	 * @param decoderStats the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderStatistics} field
	 */
	public void setDecoderStats(DecoderStats decoderStats) {
		this.decoderStats = decoderStats;
	}

	/**
	 * Retrieves {@code {@link #decoderInfo}}
	 *
	 * @return value of {@link #decoderInfo}
	 */
	public DecoderInfo getDecoderInfo() {
		return decoderInfo;
	}

	/**
	 * Sets {@code decoderInfo}
	 *
	 * @param decoderInfo the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderInfo} field
	 */
	public void setDecoderInfo(DecoderInfo decoderInfo) {
		this.decoderInfo = decoderInfo;
	}

	/**
	 * @param decoderMonitoringMetric
	 *
	 * @return String value of decoder monitoring properties by metric
	 */
	public String getValueByDecoderMonitoringMetric(DecoderMonitoringMetric decoderMonitoringMetric) {
		switch (decoderMonitoringMetric) {
			case DECODER_ID:
				return decoderStats.getDecoderID().toString();
			case STATE:
				return decoderStats.getState();
			case UPTIME:
				return nomalizeData.formatTimeData(decoderStats.getUptime());
			case OVERSUBSCRIBED_FRAMES:
				return decoderStats.getOversubscribedFrames();
			case BUFFERING_STATE:
				return decoderStats.getBufferingState();
			case BUFFERING_MODE:
				return decoderStats.getBufferingMode();
			case BUFFERING_ADJUSTMENTS:
				return decoderStats.getBufferingAdjustments();
			case BUFFERING_DELAY:
				return decoderInfo.getBufferingDelay().toString();
			case LATENCY:
				return decoderInfo.getLatency();
			case VIDEO_LATENCY:
				return decoderStats.getVideoLatency();
			case STC_TO_PCR_LEAD_TIME:
				return decoderStats.getStcToPcrLeadTime();
			case VIDEO_ALGORITHM:
				return decoderStats.getVideoAlgorithm();
			case VIDEO_PROFILE:
				return decoderStats.getVideoProfile();
			case VIDEO_LEVEL:
				return decoderStats.getVideoLevel();
			case VIDEO_OUTPUT_FORMAT:
				return decoderStats.getVideoOutputFormat();
			case VIDEO_FRAMING:
				return decoderStats.getVideoFraming();
			case VIDEO_SLICES_PER_FRAME:
				return decoderStats.getVideoSlicesPerFrame();
			case VIDEO_INPUT_FRAME_RATE:
				return decoderStats.getVideoInputFrameRate();
			case STILL_IMAGE:
				return decoderStats.getStillImage();
			case VIDEO_DECODER_STATE:
				return decoderStats.getVideoDecoderState();
			case VIDEO_DISPLAY_FORMAT:
				return decoderStats.getVideoDisplayFormat();
			case LOAD_PERCENTAGE:
				return decoderStats.getLoadPercentage().toString();
			case PREPROCESSOR_STATE:
				return decoderStats.getPreprocessorState();
			case TROUBLE_CODE:
				return decoderStats.getTroubleCode().toString();
			case DISPLAYED_OUTPUT_FRAMES:
				return decoderStats.getDisplayedOutputFrames();
			case SKIPPED_OUTPUT_FRAMES:
				return decoderStats.getSkippedOutputFrames();
			case REPLAYED_OUTPUT_FRAMES:
				return decoderStats.getReplayedOutputFrames();
			case AUDIO_STATE:
				return decoderStats.getAudioState();
			case AUDIO_SAMPLE_RATE:
				return decoderStats.getAudioSampleRate();
			case AUDIO_PAIRS_AMOUNT:
				return decoderStats.getAudioPairsAmount().toString();
			case AUDIO_DECODED_FRAMES:
				return decoderStats.getAudioDecodedFrames();
			case AUDIO_PLAYED_FRAMES:
				return decoderStats.getAudioPlayedFrames();
			case AUDIO_SKIPPED_FRAMES:
				return decoderStats.getAudioSkippedFrames();
			case CLOCK_TRACKING_MODE:
				return decoderStats.getClockTrackingMode();
			case CLOCK_STATUS:
				return decoderStats.getClockStatus();
			case CLOCK_RE_SYNC_COUNT:
				return decoderStats.getClockReSyncCount().toString();
			case CLOCK_CURRENT_STC:
				return decoderStats.getClockCurrentStc();
			case CLOCK_STC_AVG:
				return decoderStats.getClockStcAvg();
			case HDR_TYPE_IN:
				return decoderStats.getHdrTypeIn();
			case HDR_TYPE:
				return decoderStats.getHdrType();
			case HDR_COLOUR_PRIMARIES:
				return decoderStats.getHdrColourPrimaries();
			case HDR_TRANSFER_CHARACTERISTICS:
				return decoderStats.getHdrTransferCharacteristics();
			case HDR_MATRIX_COEFFICIENTS:
				return decoderStats.getHdrMatrixCoefficients();
			case TC_RECEIVED_PACKETS:
				return decoderStats.getTcReceivedPackets();
			case TC_OUTPUT_PACKETS:
				return decoderStats.getTcOutputPackets();
			case TC_FREED_PACKETS:
				return decoderStats.getTcFreedPackets();
			default:
				return DecoderConstant.NONE;
		}
	}
}
