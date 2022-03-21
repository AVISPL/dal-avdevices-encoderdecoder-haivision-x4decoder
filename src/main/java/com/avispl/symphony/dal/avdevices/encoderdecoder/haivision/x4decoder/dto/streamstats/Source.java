/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.NormalizeData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.SourceMetric;

/**
 * Stream sources
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
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

	@JsonAlias("receivedPackets")
	private String receivedPackets;

	@JsonAlias("receivedBytes")
	private String receivedBytes;

	@JsonAlias("PTS")
	private String pts;

	@JsonAlias("DTS")
	private String dts;

	private NormalizeData normalizeData = new NormalizeData();

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
	 * Retrieves {@code {@link #receivedPackets}}
	 *
	 * @return value of {@link #receivedPackets}
	 */
	public String getReceivedPackets() {
		return receivedPackets;
	}

	/**
	 * Sets {@code receivedPackets}
	 *
	 * @param receivedPackets the {@code java.lang.String} field
	 */
	public void setReceivedPackets(String receivedPackets) {
		this.receivedPackets = receivedPackets;
	}

	/**
	 * Retrieves {@code {@link #receivedBytes}}
	 *
	 * @return value of {@link #receivedBytes}
	 */
	public String getReceivedBytes() {
		return receivedBytes;
	}

	/**
	 * Sets {@code receivedBytes}
	 *
	 * @param receivedBytes the {@code java.lang.String} field
	 */
	public void setReceivedBytes(String receivedBytes) {
		this.receivedBytes = receivedBytes;
	}

	/**
	 * Retrieves {@code {@link #pts }}
	 *
	 * @return value of {@link #pts}
	 */
	public String getPts() {
		return pts;
	}

	/**
	 * Sets {@code PTS}
	 *
	 * @param pts the {@code java.lang.String} field
	 */
	public void setPts(String pts) {
		this.pts = pts;
	}

	/**
	 * Retrieves {@code {@link #dts }}
	 *
	 * @return value of {@link #dts}
	 */
	public String getDts() {
		return dts;
	}

	/**
	 * Sets {@code DTS}
	 *
	 * @param dts the {@code java.lang.String} field
	 */
	public void setDts(String dts) {
		this.dts = dts;
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
				return normalizeData.getDataValue(getBitrate());
			case PROGRAM_ID:
				return getProgramId();
			case RECEIVED_PACKETS:
				return normalizeData.getDataValue(getReceivedPackets());
			case LAST_RECEIVED_PACKETS:
				return normalizeData.getDataExtraInfoCase1(getReceivedPackets());
			case RECEIVED_BYTES:
				return normalizeData.getValueOnly(getReceivedBytes());
			case PTS:
				return getPts();
			case DTS:
				return getDts();
			default:
				return DecoderConstant.NONE;
		}
	}
}