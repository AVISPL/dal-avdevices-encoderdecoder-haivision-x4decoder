/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.Encapsulation;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.FecRTP;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.NetworkType;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.SRTMode;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * Set of stream configuration properties
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
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
	private Boolean strictMode;

	@JsonAlias("passphrase")
	private String passphrase;

	@JsonAlias("passphraseSet")
	private Boolean passphraseSet;

	@JsonAlias("srtToUdp")
	private Boolean srtToUdp;

	@JsonAlias("srtToUdp_address")
	private String srtToUdpAddress;

	@JsonAlias("srtToUdp_port")
	private Integer srtToUdpPort;

	@JsonAlias("srtToUdp_tos")
	private String srtToUdpTos;

	@JsonAlias("srtToUdp_ttl")
	private Integer srtToUdpTtl;

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
		this.srtToUdpAddress = streamInfo.getSrtToUdpAddress();
		this.srtToUdpPort = streamInfo.getSrtToUdpPort();
		this.srtToUdpTos = streamInfo.getSrtToUdpTos();
		this.srtToUdpTtl = streamInfo.getSrtToUdpTtl();
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
		if (address == null){
			return DecoderConstant.EMPTY;
		}
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
		if (port == null) {
			return DecoderConstant.MIN_PORT;
		}
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
		if (sourceIp == null){
			return DecoderConstant.EMPTY;
		}
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
		if (latency == null) {
			return DecoderConstant.MIN_LATENCY;
		}
		if (latency < DecoderConstant.MIN_LATENCY) {
			return DecoderConstant.MIN_LATENCY;
		} else if (latency > DecoderConstant.MAX_LATENCY) {
			return DecoderConstant.MAX_LATENCY;
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
		if (sourcePort == null) {
			return DecoderConstant.MIN_PORT;
		}
		if (sourcePort < DecoderConstant.MIN_PORT) {
			return DecoderConstant.MIN_PORT;
		} else if (sourcePort > DecoderConstant.MAX_PORT) {
			return DecoderConstant.MAX_PORT;
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
	public Boolean getStrictMode() {
		if (strictMode == null) {
			return false;
		}
		return strictMode;
	}

	/**
	 * Sets {@code strictMode}
	 *
	 * @param strictMode the {@code java.lang.String} field
	 */
	public void setStrictMode(Boolean strictMode) {
		this.strictMode = strictMode;
	}

	/**
	 * Retrieves {@code {@link #passphrase}}
	 *
	 * @return value of {@link #passphrase}
	 */
	public String getPassphrase() {
		if (passphrase == null){
			return DecoderConstant.EMPTY;
		}
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
	public Boolean getPassphraseSet() {
		if (passphraseSet == null) {
			return false;
		}
		return passphraseSet;
	}

	/**
	 * Sets {@code passphraseSet}
	 *
	 * @param passphraseSet the {@code java.lang.String} field
	 */
	public void setPassphraseSet(Boolean passphraseSet) {
		this.passphraseSet = passphraseSet;
	}

	/**
	 * Retrieves {@code {@link #srtToUdp}}
	 *
	 * @return value of {@link #srtToUdp}
	 */
	public Boolean getSrtToUdp() {
		if (srtToUdp == null) {
			return false;
		}
		return srtToUdp;
	}

	/**
	 * Sets {@code srtToUdp}
	 *
	 * @param srtToUdp the {@code java.lang.String} field
	 */
	public void setSrtToUdp(Boolean srtToUdp) {
		this.srtToUdp = srtToUdp;
	}

	/**
	 * Retrieves {@code {@link #srtToUdpAddress }}
	 *
	 * @return value of {@link #srtToUdpAddress}
	 */
	public String getSrtToUdpAddress() {
		if (srtToUdpAddress == null){
			return DecoderConstant.EMPTY;
		}
		return srtToUdpAddress;
	}

	/**
	 * Sets {@code srtToUdp_address}
	 *
	 * @param srtToUdpAddress the {@code java.lang.String} field
	 */
	public void setSrtToUdpAddress(String srtToUdpAddress) {
		this.srtToUdpAddress = srtToUdpAddress;
	}

	/**
	 * Retrieves {@code {@link #srtToUdpPort }}
	 *
	 * @return value of {@link #srtToUdpPort}
	 */
	public Integer getSrtToUdpPort() {
		if (srtToUdpPort == null) {
			return DecoderConstant.MIN_PORT;
		}
		if (srtToUdpPort < DecoderConstant.MIN_PORT) {
			return DecoderConstant.MIN_PORT;
		} else if (srtToUdpPort > DecoderConstant.MAX_PORT) {
			return DecoderConstant.MAX_PORT;
		}
		return srtToUdpPort;
	}

	/**
	 * Sets {@code srtToUdp_port}
	 *
	 * @param srtToUdpPort the {@code java.lang.String} field
	 */
	public void setSrtToUdpPort(Integer srtToUdpPort) {
		this.srtToUdpPort = srtToUdpPort;
	}

	/**
	 * Retrieves {@code {@link #srtToUdpTos }}
	 *
	 * @return value of {@link #srtToUdpTos}
	 */
	public String getSrtToUdpTos() {
		if (StringUtils.isNullOrEmpty(srtToUdpTos)) {
			return DecoderConstant.SRT_TO_UDP_TOS;
		}
		return srtToUdpTos;
	}

	/**
	 * Sets {@code srtToUdp_tos}
	 *
	 * @param srtToUdpTos the {@code java.lang.String} field
	 */
	public void setSrtToUdpTos(String srtToUdpTos) {
		this.srtToUdpTos = srtToUdpTos;
	}

	/**
	 * Retrieves {@code {@link #srtToUdpTtl }}
	 *
	 * @return value of {@link #srtToUdpTtl}
	 */
	public Integer getSrtToUdpTtl() {
		if (srtToUdpTtl == null) {
			return 64;
		}
		return srtToUdpTtl;
	}

	/**
	 * Sets {@code srtToUdp_ttl}
	 *
	 * @param srtToUdpTtl the {@code java.lang.String} field
	 */
	public void setSrtToUdpTtl(Integer srtToUdpTtl) {
		this.srtToUdpTtl = srtToUdpTtl;
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

	/**
	 * This method is used to create request body as Json for stream controlling in case protocol is TS over UDP
	 *
	 * @return String json request body
	 */
	public String jsonRequest() {
		return '{' +
				"\"encapsulation\":" + getEncapsulation().getCode() +
				",\"fecRtp\":" + getFecRtp().getCode() +
				",\"name\":" + '\"' + name + '\"' +
				",\"passphrase\":" + '\"' + getPassphrase() + '\"' +
				",\"address\":" + '\"' + getAddress() + '\"' +
				",\"sourceIp\":" + '\"' + getSourceIp() + '\"' +
				",\"stillImage\":" + '\"' + '\"' +
				",\"port\":" + getPort() +
				",\"sourcePort\":" + getSourcePort() +
				",\"latency\":" + getLatency() +
				",\"srtMode\":" + getSrtMode().getCode() +
				",\"srtToUdp\":" + getSrtToUdp() +
				",\"srtToUdp_address\":" + '\"' + getSrtToUdpAddress() + '\"' +
				",\"srtToUdp_port\":" + getSrtToUdpPort() +
				",\"srtToUdp_tos\":" + '\"' + getSrtToUdpTos() + '\"' +
				",\"srtToUdp_ttl\":" + getSrtToUdpTtl() +
				",\"strictMode\":" + getStrictMode() +
				'}';
	}

	/**
	 * This method is used to compare object in specify protocol
	 */
	public boolean equalsByProtocol(Object o, Encapsulation encapsulation, SRTMode srtMode, Boolean encrypted, Boolean srtToUDP) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StreamInfo that = (StreamInfo) o;
		switch (encapsulation) {
			case TS_OVER_UDP:
				return Objects.equals(name, that.name)
						&& Objects.equals(this.encapsulation, that.encapsulation)
						&& Objects.equals(port, that.port)
						&& Objects.equals(address, that.address)
						&& Objects.equals(sourceIp, that.sourceIp);
			case TS_OVER_RTP:
				return Objects.equals(name, that.name)
						&& Objects.equals(this.encapsulation, that.encapsulation)
						&& Objects.equals(port, that.port)
						&& Objects.equals(address, that.address)
						&& Objects.equals(sourceIp, that.sourceIp)
						&& Objects.equals(fecRtp, that.fecRtp);
			case TS_OVER_SRT:
				return Objects.equals(name, that.name)
						&& Objects.equals(this.encapsulation, that.encapsulation)
						&& Objects.equals(latency, that.latency)
						&& Objects.equals(this.srtMode, that.srtMode)
						&& Objects.equals(passphraseSet, that.passphraseSet)
						&& Objects.equals(srtToUdp, that.srtToUdp)
						&& equalsByStreamConversion(o, srtToUDP)
						&& equalsBySRTMode(o, srtMode)
						&& equalsByEncrypted(o, encrypted);
			default:
				return false;
		}
	}

	/**
	 * This method is used to compare object in specify stream conversion
	 */
	public boolean equalsByStreamConversion(Object o, Boolean srtToUdp) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StreamInfo that = (StreamInfo) o;
		if (srtToUdp) {
			return Objects.equals(srtToUdpAddress, that.srtToUdpAddress)
					&& Objects.equals(srtToUdpPort, that.srtToUdpPort)
					&& Objects.equals(srtToUdpTos, that.srtToUdpTos)
					&& Objects.equals(srtToUdpTtl, that.srtToUdpTtl);
		}
		return true;
	}

	/**
	 * This method is used to compare object in specify encrypted
	 */
	public boolean equalsByEncrypted(Object o, Boolean passphraseSet) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StreamInfo that = (StreamInfo) o;
		if (passphraseSet) {
			return Objects.equals(passphrase, that.passphrase);
		}
		return true;
	}

	/**
	 * This method is used to compare object in specify SRT mode
	 */
	public boolean equalsBySRTMode(Object o, SRTMode srtMode) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		StreamInfo that = (StreamInfo) o;
		switch (srtMode) {
			case LISTENER:
				return Objects.equals(port, that.port)
						&& Objects.equals(strictMode, that.strictMode);
			case CALLER:
			case RENDEZVOUS:
				return Objects.equals(sourcePort, that.sourcePort)
						&& Objects.equals(address, that.address)
						&& Objects.equals(sourcePort, that.sourcePort)
						&& Objects.equals(port, that.port);
			default:
				return false;
		}
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
				&& Objects.equals(srtToUdp, that.srtToUdp) && Objects.equals(srtToUdpAddress, that.srtToUdpAddress) && Objects.equals(srtToUdpPort, that.srtToUdpPort)
				&& Objects.equals(srtToUdpTos, that.srtToUdpTos) && Objects.equals(srtToUdpTtl, that.srtToUdpTtl) && Objects.equals(fecRtp, that.fecRtp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id, decoderId, encapsulation, userData, address, port, sourceIp, latency, srtMode, sourcePort, strictMode, passphrase, passphraseSet, srtToUdp, srtToUdpAddress,
				srtToUdpPort, srtToUdpTos, srtToUdpTtl, fecRtp);
	}
}
