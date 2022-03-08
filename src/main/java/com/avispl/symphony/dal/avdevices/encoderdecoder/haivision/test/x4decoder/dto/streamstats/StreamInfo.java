/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.dto.streamstats;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric.Encapsulation;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric.FecRTP;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric.NetworkType;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric.SRTMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.DecoderConstant;

/**
 * Set of stream configuration properties
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamInfo {

	@JsonAlias("name")
	private String name;

	@JsonAlias("id")
	private Integer id;

	@JsonAlias("decoderId")
	private String decoderId;

	@JsonAlias("encapsulation")
	private Integer encapsulation;

	@JsonAlias("userData")
	private String userData;

	@JsonAlias("address")
	private String address;

	@JsonAlias("port")
	private Integer port;

	@JsonAlias("sourceIp")
	private String sourceIp;

	@JsonAlias("latency")
	private Integer latency;

	@JsonAlias("srtMode")
	private Integer srtMode;

	@JsonAlias("sourcePort")
	private Integer sourcePort;

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
	private Integer srtToUdp_port;

	@JsonAlias("srtToUdp_tos")
	private String srtToUdp_tos;

	@JsonAlias("srtToUdp_ttl")
	private String srtToUdp_ttl;

	@JsonAlias("fecRtp")
	private Integer fecRtp;

	private NetworkType netWorkType;

	public StreamInfo() {
	}

	/**
	 * This constructor is used for deep clone object
	 *
	 * @param streamInfo Stream config info
	 */
	public StreamInfo(StreamInfo streamInfo) {
		this.name = streamInfo.getName();
		this.id = streamInfo.getId();
		this.decoderId = streamInfo.getDecoderId();
		this.encapsulation = streamInfo.getEncapsulation().getCode();
		this.userData = streamInfo.getUserData();
		this.address = streamInfo.getAddress();
		this.port = streamInfo.getPort();
		this.sourceIp = streamInfo.getSourceIp();
		this.latency = streamInfo.getLatency();
		this.srtMode = streamInfo.getSrtMode().getCode();
		this.sourcePort = streamInfo.getSourcePort();
		this.strictMode = streamInfo.getStrictMode();
		this.passphrase = streamInfo.getPassphrase();
		this.passphraseSet = streamInfo.getPassphraseSet();
		this.srtToUdp = streamInfo.getSrtToUdp();
		this.srtToUdp_address = streamInfo.getSrtToUdp_address();
		this.srtToUdp_port = streamInfo.getSrtToUdp_port();
		this.srtToUdp_tos = streamInfo.getSrtToUdp_tos();
		this.srtToUdp_ttl = streamInfo.getSrtToUdp_ttl();
		this.fecRtp = streamInfo.getFecRtp().getCode();
	}

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
	public Integer getId() {
		return id;
	}

	/**
	 * Sets {@code id}
	 *
	 * @param id the {@code java.lang.String} field
	 */
	public void setId(Integer id) {
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
	public Encapsulation getEncapsulation() {
		if (this.encapsulation != null) {
			for (Encapsulation encapsulation : Encapsulation.values()) {
				if (encapsulation.getCode().equals(this.encapsulation)) {
					return encapsulation;
				}
			}
		}
		return Encapsulation.TS_OVER_UDP;
	}

	/**
	 * Sets {@code encapsulation}
	 *
	 * @param encapsulation the {@code java.lang.String} field
	 */
	public void setEncapsulation(Integer encapsulation) {
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
	public Integer getPort() {
		if (port < DecoderConstant.MIN_PORT) {
			return DecoderConstant.MIN_PORT;
		} else if (port > DecoderConstant.MAX_PORT) {
			return DecoderConstant.MAX_PORT;
		}
		return port;
	}

	/**
	 * Sets {@code port}
	 *
	 * @param port the {@code java.lang.String} field
	 */
	public void setPort(Integer port) {
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
	public Integer getLatency() {
		if (latency != null) {
			if (latency < DecoderConstant.MIN_LATENCY) {
				return DecoderConstant.MIN_LATENCY;
			} else if (latency > DecoderConstant.MAX_LATENCY) {
				return DecoderConstant.MAX_LATENCY;
			}
		}
		return latency;
	}

	/**
	 * Sets {@code latency}
	 *
	 * @param latency the {@code java.lang.String} field
	 */
	public void setLatency(Integer latency) {
		this.latency = latency;
	}

	/**
	 * Retrieves {@code {@link #srtMode}}
	 *
	 * @return value of {@link #srtMode}
	 */
	public SRTMode getSrtMode() {
		if (this.srtMode != null) {
			for (SRTMode srtMode : SRTMode.values()) {
				if (srtMode.getCode().equals(this.srtMode)) {
					return srtMode;
				}
			}
		}
		return SRTMode.LISTENER;
	}

	/**
	 * Sets {@code srtMode}
	 *
	 * @param srtMode the {@code java.lang.String} field
	 */
	public void setSrtMode(Integer srtMode) {
		this.srtMode = srtMode;
	}

	/**
	 * Retrieves {@code {@link #sourcePort}}
	 *
	 * @return value of {@link #sourcePort}
	 */
	public Integer getSourcePort() {
		if (sourcePort != null) {
			if (sourcePort < DecoderConstant.MIN_PORT) {
				return DecoderConstant.MIN_PORT;
			} else if (sourcePort > DecoderConstant.MAX_PORT) {
				return DecoderConstant.MAX_PORT;
			}
		}
		return sourcePort;
	}

	/**
	 * Sets {@code sourcePort}
	 *
	 * @param sourcePort the {@code java.lang.String} field
	 */
	public void setSourcePort(Integer sourcePort) {
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
	public Integer getSrtToUdp_port() {
		if (srtToUdp_port != null) {
			if (srtToUdp_port < DecoderConstant.MIN_PORT) {
				return DecoderConstant.MIN_PORT;
			} else if (srtToUdp_port > DecoderConstant.MAX_PORT) {
				return DecoderConstant.MAX_PORT;
			}
		}
		return srtToUdp_port;
	}

	/**
	 * Sets {@code srtToUdp_port}
	 *
	 * @param srtToUdp_port the {@code java.lang.String} field
	 */
	public void setSrtToUdp_port(Integer srtToUdp_port) {
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
	public FecRTP getFecRtp() {
		if (this.fecRtp != null) {
			for (FecRTP fecRTP : FecRTP.values()) {
				if (fecRTP.getCode().equals(this.encapsulation)) {
					return fecRTP;
				}
			}
		}
		return FecRTP.DISABLE;
	}

	/**
	 * Retrieves {@code {@link #netWorkType}}
	 *
	 * @return value of {@link #netWorkType}
	 */
	public NetworkType getNetWorkType() {
		return netWorkType;
	}

	/**
	 * Sets {@code netWorkType}
	 *
	 * @param netWorkType the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.NetWorkType} field
	 */
	public void setNetWorkType(NetworkType netWorkType) {
		this.netWorkType = netWorkType;
	}

	/**
	 * Sets {@code fecRtp}
	 *
	 * @param fecRtp the {@code java.lang.String} field
	 */
	public void setFecRtp(Integer fecRtp) {
		this.fecRtp = fecRtp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StreamInfo that = (StreamInfo) o;
		return Objects.equals(name, that.name) && Objects.equals(id, that.id) && Objects.equals(decoderId, that.decoderId) && Objects.equals(encapsulation,
				that.encapsulation) && Objects.equals(userData, that.userData) && Objects.equals(address, that.address) && Objects.equals(port, that.port)
				&& Objects.equals(sourceIp, that.sourceIp) && Objects.equals(latency, that.latency) && Objects.equals(srtMode, that.srtMode) && Objects.equals(sourcePort,
				that.sourcePort) && Objects.equals(strictMode, that.strictMode) && Objects.equals(passphrase, that.passphrase) && Objects.equals(passphraseSet, that.passphraseSet)
				&& Objects.equals(srtToUdp, that.srtToUdp) && Objects.equals(srtToUdp_address, that.srtToUdp_address) && Objects.equals(srtToUdp_port, that.srtToUdp_port)
				&& Objects.equals(srtToUdp_tos, that.srtToUdp_tos) && Objects.equals(srtToUdp_ttl, that.srtToUdp_ttl) && Objects.equals(fecRtp, that.fecRtp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id, decoderId, encapsulation, userData, address, port, sourceIp, latency, srtMode, sourcePort, strictMode, passphrase, passphraseSet, srtToUdp, srtToUdp_address,
				srtToUdp_port, srtToUdp_tos, srtToUdp_ttl, fecRtp);
	}
}
