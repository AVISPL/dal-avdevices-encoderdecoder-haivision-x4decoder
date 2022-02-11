/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Stream information
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamInfo {

	@JsonAlias("name")
	private String name;

	@JsonAlias("id")
	private String id;

	@JsonAlias("decoderId")
	private String decoderId;

	@JsonAlias("encapsulation")
	private String encapsulation;

	@JsonAlias("userData")
	private String userData;

	@JsonAlias("address")
	private String address;

	@JsonAlias("port")
	private String port;

	@JsonAlias("sourceIp")
	private String sourceIp;

	@JsonAlias("latency")
	private String latency;

	@JsonAlias("srtMode")
	private String srtMode;

	@JsonAlias("sourcePort")
	private String sourcePort;

	@JsonAlias("strictMode")
	private String strictMode;

	@JsonAlias("passphrase")
	private String passphrase;

	@JsonAlias("passphraseSet")
	private String passphraseSet;

	@JsonAlias("srtToUdp")
	private String srtToUdp;

	@JsonAlias("srtToUdp_address")
	private String srtToUdp_address;

	@JsonAlias("srtToUdp_port")
	private String srtToUdp_port;

	@JsonAlias("srtToUdp_tos")
	private String srtToUdp_tos;

	@JsonAlias("srtToUdp_ttl")
	private String srtToUdp_ttl;

	@JsonAlias("fecRtp")
	private String fecRtp;

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
	 * Retrieves {@code {@link #id}}
	 *
	 * @return value of {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets {@code id}
	 *
	 * @param id the {@code java.lang.String} field
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieves {@code {@link #decoderId}}
	 *
	 * @return value of {@link #decoderId}
	 */
	public String getDecoderId() {
		return decoderId;
	}

	/**
	 * Sets {@code decoderId}
	 *
	 * @param decoderId the {@code java.lang.String} field
	 */
	public void setDecoderId(String decoderId) {
		this.decoderId = decoderId;
	}

	/**
	 * Retrieves {@code {@link #encapsulation}}
	 *
	 * @return value of {@link #encapsulation}
	 */
	public String getEncapsulation() {
		return encapsulation;
	}

	/**
	 * Sets {@code encapsulation}
	 *
	 * @param encapsulation the {@code java.lang.String} field
	 */
	public void setEncapsulation(String encapsulation) {
		this.encapsulation = encapsulation;
	}

	/**
	 * Retrieves {@code {@link #userData}}
	 *
	 * @return value of {@link #userData}
	 */
	public String getUserData() {
		return userData;
	}

	/**
	 * Sets {@code userData}
	 *
	 * @param userData the {@code java.lang.String} field
	 */
	public void setUserData(String userData) {
		this.userData = userData;
	}

	/**
	 * Retrieves {@code {@link #address}}
	 *
	 * @return value of {@link #address}
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets {@code address}
	 *
	 * @param address the {@code java.lang.String} field
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Retrieves {@code {@link #port}}
	 *
	 * @return value of {@link #port}
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Sets {@code port}
	 *
	 * @param port the {@code java.lang.String} field
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Retrieves {@code {@link #sourceIp}}
	 *
	 * @return value of {@link #sourceIp}
	 */
	public String getSourceIp() {
		return sourceIp;
	}

	/**
	 * Sets {@code sourceIp}
	 *
	 * @param sourceIp the {@code java.lang.String} field
	 */
	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
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
	 * Retrieves {@code {@link #srtMode}}
	 *
	 * @return value of {@link #srtMode}
	 */
	public String getSrtMode() {
		return srtMode;
	}

	/**
	 * Sets {@code srtMode}
	 *
	 * @param srtMode the {@code java.lang.String} field
	 */
	public void setSrtMode(String srtMode) {
		this.srtMode = srtMode;
	}

	/**
	 * Retrieves {@code {@link #sourcePort}}
	 *
	 * @return value of {@link #sourcePort}
	 */
	public String getSourcePort() {
		return sourcePort;
	}

	/**
	 * Sets {@code sourcePort}
	 *
	 * @param sourcePort the {@code java.lang.String} field
	 */
	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	/**
	 * Retrieves {@code {@link #strictMode}}
	 *
	 * @return value of {@link #strictMode}
	 */
	public String getStrictMode() {
		return strictMode;
	}

	/**
	 * Sets {@code strictMode}
	 *
	 * @param strictMode the {@code java.lang.String} field
	 */
	public void setStrictMode(String strictMode) {
		this.strictMode = strictMode;
	}

	/**
	 * Retrieves {@code {@link #passphrase}}
	 *
	 * @return value of {@link #passphrase}
	 */
	public String getPassphrase() {
		return passphrase;
	}

	/**
	 * Sets {@code passphrase}
	 *
	 * @param passphrase the {@code java.lang.String} field
	 */
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	/**
	 * Retrieves {@code {@link #passphraseSet}}
	 *
	 * @return value of {@link #passphraseSet}
	 */
	public String getPassphraseSet() {
		return passphraseSet;
	}

	/**
	 * Sets {@code passphraseSet}
	 *
	 * @param passphraseSet the {@code java.lang.String} field
	 */
	public void setPassphraseSet(String passphraseSet) {
		this.passphraseSet = passphraseSet;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp}}
	 *
	 * @return value of {@link #srtToUdp}
	 */
	public String getSrtToUdp() {
		return srtToUdp;
	}

	/**
	 * Sets {@code srtToUdp}
	 *
	 * @param srtToUdp the {@code java.lang.String} field
	 */
	public void setSrtToUdp(String srtToUdp) {
		this.srtToUdp = srtToUdp;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp_address}}
	 *
	 * @return value of {@link #srtToUdp_address}
	 */
	public String getSrtToUdp_address() {
		return srtToUdp_address;
	}

	/**
	 * Sets {@code srtToUdp_address}
	 *
	 * @param srtToUdp_address the {@code java.lang.String} field
	 */
	public void setSrtToUdp_address(String srtToUdp_address) {
		this.srtToUdp_address = srtToUdp_address;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp_port}}
	 *
	 * @return value of {@link #srtToUdp_port}
	 */
	public String getSrtToUdp_port() {
		return srtToUdp_port;
	}

	/**
	 * Sets {@code srtToUdp_port}
	 *
	 * @param srtToUdp_port the {@code java.lang.String} field
	 */
	public void setSrtToUdp_port(String srtToUdp_port) {
		this.srtToUdp_port = srtToUdp_port;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp_tos}}
	 *
	 * @return value of {@link #srtToUdp_tos}
	 */
	public String getSrtToUdp_tos() {
		return srtToUdp_tos;
	}

	/**
	 * Sets {@code srtToUdp_tos}
	 *
	 * @param srtToUdp_tos the {@code java.lang.String} field
	 */
	public void setSrtToUdp_tos(String srtToUdp_tos) {
		this.srtToUdp_tos = srtToUdp_tos;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp_ttl}}
	 *
	 * @return value of {@link #srtToUdp_ttl}
	 */
	public String getSrtToUdp_ttl() {
		return srtToUdp_ttl;
	}

	/**
	 * Sets {@code srtToUdp_ttl}
	 *
	 * @param srtToUdp_ttl the {@code java.lang.String} field
	 */
	public void setSrtToUdp_ttl(String srtToUdp_ttl) {
		this.srtToUdp_ttl = srtToUdp_ttl;
	}

	/**
	 * Retrieves {@code {@link #fecRtp}}
	 *
	 * @return value of {@link #fecRtp}
	 */
	public String getFecRtp() {
		return fecRtp;
	}

	/**
	 * Sets {@code fecRtp}
	 *
	 * @param fecRtp the {@code java.lang.String} field
	 */
	public void setFecRtp(String fecRtp) {
		this.fecRtp = fecRtp;
	}
}
