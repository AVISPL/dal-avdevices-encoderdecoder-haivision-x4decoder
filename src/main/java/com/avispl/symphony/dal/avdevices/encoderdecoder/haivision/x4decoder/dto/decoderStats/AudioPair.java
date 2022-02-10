/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Decoder audio pairs
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioPair {

	@JsonAlias("audioPairMode")
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
}
