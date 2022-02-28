/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.SourceMetric;

/**
 * Stream sources
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {

	@JsonAlias("name")
	private String name;

	@JsonAlias("compression")
	private String compression;

	@JsonAlias("bitrate")
	private String bitrate;

	@JsonAlias("programId")
	private String programId;

	@JsonAlias("PTS")
	private String PTS;

	@JsonAlias("DTS")
	private String DTS;

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets {@code name}
	 *
	 * @param name the {@code java.lang.String} field
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Retrieves {@code {@link #programId}}
	 *
	 * @return value of {@link #programId}
	 */
	public String getProgramId() {
		return programId;
	}

	/**
	 * Sets {@code programId}
	 *
	 * @param programId the {@code java.lang.String} field
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}

	/**
	 * Retrieves {@code {@link #PTS}}
	 *
	 * @return value of {@link #PTS}
	 */
	public String getPTS() {
		return PTS;
	}

	/**
	 * Sets {@code PTS}
	 *
	 * @param PTS the {@code java.lang.String} field
	 */
	public void setPTS(String PTS) {
		this.PTS = PTS;
	}

	/**
	 * Retrieves {@code {@link #DTS}}
	 *
	 * @return value of {@link #DTS}
	 */
	public String getDTS() {
		return DTS;
	}

	/**
	 * Sets {@code DTS}
	 *
	 * @param DTS the {@code java.lang.String} field
	 */
	public void setDTS(String DTS) {
		this.DTS = DTS;
	}

	/**
	 * @param source
	 *
	 * @return String value of source monitoring properties by metric
	 */
	public String getValueBySourceMetric(SourceMetric source) {
		switch (source) {
			case COMPRESSION:
				return getCompression();
			case BITRATE:
				return getBitrate();
			case PROGRAM_ID:
				return getProgramId();
			case PTS:
				return getPTS();
			case DTS:
				return DTS;
			default:
				return DecoderConstant.NONE;
		}
	}
}
