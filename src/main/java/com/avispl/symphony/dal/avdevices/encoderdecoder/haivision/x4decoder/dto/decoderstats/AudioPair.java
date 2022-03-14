/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.NormalizeData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.AudioPairMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;

/**
 * Decoder audio pairs
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioPair {

	@JsonAlias("mode")
	private String audioPairMode;

	@JsonAlias("compression")
	private String compression;

	@JsonAlias("bitrate")
	private String bitrate;

	@JsonAlias("avSyncMs")
	private String avSyncMs;

	@JsonAlias("discontinuities")
	private Integer discontinuities;

	@JsonAlias("decodeErrors")
	private String decodeErrors;

	@JsonAlias("outputErrors")
	private String outputErrors;

	@JsonAlias("sampeRateIn")
	private Integer sampeRateIn;

	@JsonAlias("sampeRateOut")
	private Integer sampeRateOut;

	private NormalizeData normalizeData = new NormalizeData();
	/**
	 * Retrieves {@code {@link #audioPairMode}}
	 *
	 * @return value of {@link #audioPairMode}
	 */
	public String getAudioPairMode() {
		return audioPairMode;
	}

	/**
	 * Sets {@code audioPairMode}
	 *
	 * @param audioPairMode the {@code java.lang.String} field
	 */
	public void setAudioPairMode(String audioPairMode) {
		this.audioPairMode = audioPairMode;
	}

	/**
	 * Retrieves {@code {@link #compression}}
	 *
	 * @return value of {@link #compression}
	 */
	public String getCompression() {
		return compression;
	}

	/**
	 * Sets {@code compression}
	 *
	 * @param compression the {@code java.lang.String} field
	 */
	public void setCompression(String compression) {
		this.compression = compression;
	}

	/**
	 * Retrieves {@code {@link #bitrate}}
	 *
	 * @return value of {@link #bitrate}
	 */
	public String getBitrate() {
		return bitrate;
	}

	/**
	 * Sets {@code bitrate}
	 *
	 * @param bitrate the {@code java.lang.String} field
	 */
	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * Retrieves {@code {@link #avSyncMs}}
	 *
	 * @return value of {@link #avSyncMs}
	 */
	public String getAvSyncMs() {
		return avSyncMs;
	}

	/**
	 * Sets {@code avSyncMs}
	 *
	 * @param avSyncMs the {@code java.lang.String} field
	 */
	public void setAvSyncMs(String avSyncMs) {
		this.avSyncMs = avSyncMs;
	}

	/**
	 * Retrieves {@code {@link #discontinuities}}
	 *
	 * @return value of {@link #discontinuities}
	 */
	public Integer getDiscontinuities() {
		return discontinuities;
	}

	/**
	 * Sets {@code discontinuities}
	 *
	 * @param discontinuities the {@code java.lang.Integer} field
	 */
	public void setDiscontinuities(Integer discontinuities) {
		this.discontinuities = discontinuities;
	}

	/**
	 * Retrieves {@code {@link #decodeErrors}}
	 *
	 * @return value of {@link #decodeErrors}
	 */
	public String getDecodeErrors() {
		return decodeErrors;
	}

	/**
	 * Sets {@code decodeErrors}
	 *
	 * @param decodeErrors the {@code java.lang.String} field
	 */
	public void setDecodeErrors(String decodeErrors) {
		this.decodeErrors = decodeErrors;
	}

	/**
	 * Retrieves {@code {@link #outputErrors}}
	 *
	 * @return value of {@link #outputErrors}
	 */
	public String getOutputErrors() {
		return outputErrors;
	}

	/**
	 * Sets {@code outputErrors}
	 *
	 * @param outputErrors the {@code java.lang.String} field
	 */
	public void setOutputErrors(String outputErrors) {
		this.outputErrors = outputErrors;
	}

	/**
	 * Retrieves {@code {@link #sampeRateIn}}
	 *
	 * @return value of {@link #sampeRateIn}
	 */
	public Integer getSampeRateIn() {
		return sampeRateIn;
	}

	/**
	 * Sets {@code sampeRateIn}
	 *
	 * @param sampeRateIn the {@code java.lang.Integer} field
	 */
	public void setSampeRateIn(Integer sampeRateIn) {
		this.sampeRateIn = sampeRateIn;
	}

	/**
	 * Retrieves {@code {@link #sampeRateOut}}
	 *
	 * @return value of {@link #sampeRateOut}
	 */
	public Integer getSampeRateOut() {
		return sampeRateOut;
	}

	/**
	 * Sets {@code sampeRateOut}
	 *
	 * @param sampeRateOut the {@code java.lang.Integer} field
	 */
	public void setSampeRateOut(Integer sampeRateOut) {
		this.sampeRateOut = sampeRateOut;
	}

	/**
	 * @param audioPairMetric
	 *
	 * @return String value of audio pair monitoring properties by metric
	 */
	public String getValueByAudioPairMetric(AudioPairMetric audioPairMetric) {

		switch (audioPairMetric) {
			case AUDIO_PAIR_MODE:
				return getAudioPairMode();
			case COMPRESSION:
				return getCompression();
			case BITRATE:
				return normalizeData.getDataValue(getBitrate());
			case AV_SYNC_MS:
				return getAvSyncMs();
			case DISCONTINUITIES:
				return getDiscontinuities().toString();
			case DECODE_ERRORS:
				return normalizeData.getDataValue(getDecodeErrors());
			case LAST_DECODE_ERRORS:
				return normalizeData.getDataExtraInfo(getDecodeErrors());
			case OUTPUT_ERRORS:
				return normalizeData.getDataValue(getOutputErrors());
			case LAST_OUTPUT_ERRORS:
				return normalizeData.getDataExtraInfo(getOutputErrors());
			case SAMPE_RATE_IN:
				return getSampeRateIn().toString();
			case SAMPE_RATE_OUT:
				return getSampeRateOut().toString();
			default:
				return DecoderConstant.NONE;
		}
	}
}
