/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream SRT
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SRT {

	@JsonAlias("lostPackets")
	private String lostPackets;

	@JsonAlias("sentAcks")
	private String sentAcks;

	@JsonAlias("sentNaks")
	private String sentNaks;

	@JsonAlias("pathMaxBandwidth")
	private String pathMaxBandwidth;

	@JsonAlias("rtt")
	private String rtt;

	@JsonAlias("buffer")
	private String buffer;

	@JsonAlias("latency")
	private String latency;

	@JsonAlias("encryption")
	private String encryption;

	@JsonAlias("keyLength")
	private String keyLength;

	@JsonAlias("decryptState")
	private String decryptState;

	/**
	 * Retrieves {@code {@link #lostPackets}}
	 *
	 * @return value of {@link #lostPackets}
	 */
	public String getLostPackets() {
		return lostPackets;
	}

	/**
	 * Sets {@code lostPackets}
	 *
	 * @param lostPackets the {@code java.lang.String} field
	 */
	public void setLostPackets(String lostPackets) {
		this.lostPackets = lostPackets;
	}

	/**
	 * Retrieves {@code {@link #sentAcks}}
	 *
	 * @return value of {@link #sentAcks}
	 */
	public String getSentAcks() {
		return sentAcks;
	}

	/**
	 * Sets {@code sentAcks}
	 *
	 * @param sentAcks the {@code java.lang.String} field
	 */
	public void setSentAcks(String sentAcks) {
		this.sentAcks = sentAcks;
	}

	/**
	 * Retrieves {@code {@link #sentNaks}}
	 *
	 * @return value of {@link #sentNaks}
	 */
	public String getSentNaks() {
		return sentNaks;
	}

	/**
	 * Sets {@code sentNaks}
	 *
	 * @param sentNaks the {@code java.lang.String} field
	 */
	public void setSentNaks(String sentNaks) {
		this.sentNaks = sentNaks;
	}

	/**
	 * Retrieves {@code {@link #pathMaxBandwidth}}
	 *
	 * @return value of {@link #pathMaxBandwidth}
	 */
	public String getPathMaxBandwidth() {
		return pathMaxBandwidth;
	}

	/**
	 * Sets {@code pathMaxBandwidth}
	 *
	 * @param pathMaxBandwidth the {@code java.lang.String} field
	 */
	public void setPathMaxBandwidth(String pathMaxBandwidth) {
		this.pathMaxBandwidth = pathMaxBandwidth;
	}

	/**
	 * Retrieves {@code {@link #rtt}}
	 *
	 * @return value of {@link #rtt}
	 */
	public String getRtt() {
		return rtt;
	}

	/**
	 * Sets {@code rtt}
	 *
	 * @param rtt the {@code java.lang.String} field
	 */
	public void setRtt(String rtt) {
		this.rtt = rtt;
	}

	/**
	 * Retrieves {@code {@link #buffer}}
	 *
	 * @return value of {@link #buffer}
	 */
	public String getBuffer() {
		return buffer;
	}

	/**
	 * Sets {@code buffer}
	 *
	 * @param buffer the {@code java.lang.String} field
	 */
	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	/**
	 * Retrieves {@code {@link #latency}}
	 *
	 * @return value of {@link #latency}
	 */
	public String getLatency() {
		return latency;
	}

	/**
	 * Sets {@code latency}
	 *
	 * @param latency the {@code java.lang.String} field
	 */
	public void setLatency(String latency) {
		this.latency = latency;
	}

	/**
	 * Retrieves {@code {@link #encryption}}
	 *
	 * @return value of {@link #encryption}
	 */
	public String getEncryption() {
		return encryption;
	}

	/**
	 * Sets {@code encryption}
	 *
	 * @param encryption the {@code java.lang.String} field
	 */
	public void setEncryption(String encryption) {
		this.encryption = encryption;
	}

	/**
	 * Retrieves {@code {@link #keyLength}}
	 *
	 * @return value of {@link #keyLength}
	 */
	public String getKeyLength() {
		return keyLength;
	}

	/**
	 * Sets {@code keyLength}
	 *
	 * @param keyLength the {@code java.lang.String} field
	 */
	public void setKeyLength(String keyLength) {
		this.keyLength = keyLength;
	}

	/**
	 * Retrieves {@code {@link #decryptState}}
	 *
	 * @return value of {@link #decryptState}
	 */
	public String getDecryptState() {
		return decryptState;
	}

	/**
	 * Sets {@code decryptState}
	 *
	 * @param decryptState the {@code java.lang.String} field
	 */
	public void setDecryptState(String decryptState) {
		this.decryptState = decryptState;
	}
}
