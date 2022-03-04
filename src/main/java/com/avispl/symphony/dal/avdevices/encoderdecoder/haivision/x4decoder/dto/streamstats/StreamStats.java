/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;

/**
 * Stream statistics
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StreamStats {

	@JsonAlias("name")
	private String name;

	@JsonAlias("encapsulation")
	private String encapsulation;

	@JsonAlias("decoderId")
	private String decoderId;

	@JsonAlias("state")
	private Integer state;

	@JsonAlias("sourceAddress")
	private String sourceAddress;

	@JsonAlias("bitRate")
	private String bitRate;

	@JsonAlias("connections")
	private String connections;

	@JsonAlias("receivedPackets")
	private String receivedPackets;

	@JsonAlias("receivedBytes")
	private String receivedBytes;

	@JsonAlias("lastReceived")
	private String lastReceived;

	@JsonAlias("outputPackets")
	private String outputPackets;

	@JsonAlias("outputBytes")
	private String outputBytes;

	@JsonAlias("programNumber")
	private String programNumber;

	@JsonAlias("pcrPid")
	private String pcrPid;

	@JsonAlias("streamSummary")
	private String streamSummary;

	@JsonAlias("droppedPackets")
	private String droppedPackets;

	@JsonAlias("corruptedFrames")
	private String corruptedFrames;

	@JsonAlias("restarts")
	private String resumedStreamFlags;

	@JsonAlias("localPort")
	private String localPort;

	@JsonAlias("remotePort")
	private String remotePort;

	@JsonAlias("receivedErrors")
	private String receivedErrors;

	@JsonAlias("srt")
	private SRT srt;

	@JsonAlias("sources")
	private List<Source> sources;


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
	 * Retrieves {@code {@link #state}}
	 *
	 * @return value of {@link #state}
	 */
	public String getState() {
		if (state == null) {
			return DecoderConstant.EMPTY;
		}
		switch (state) {
			case 0:
				return "UNKNOWN";
			case 1:
				return "STOPPED";
			case 2:
				return "LISTENING";
			case 3:
				return "ACTIVE";
			case 4:
				return "RESOLVING";
			case 5:
				return "CONNECTING";
			case 6:
				return "SCRAMBLED";
			case 7:
				return "SECURING";
			case -1:
				return "INVALID";
			case -2:
				return "FAILED";
			case -3:
				return "UNLICENSED";
			default:
				return DecoderConstant.NONE;
		}
	}

	/**
	 * Sets {@code state}
	 *
	 * @param state the {@code java.lang.String} field
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * Retrieves {@code {@link #sourceAddress}}
	 *
	 * @return value of {@link #sourceAddress}
	 */
	public String getSourceAddress() {
		return sourceAddress;
	}

	/**
	 * Sets {@code sourceAddress}
	 *
	 * @param sourceAddress the {@code java.lang.String} field
	 */
	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	/**
	 * Retrieves {@code {@link #bitRate}}
	 *
	 * @return value of {@link #bitRate}
	 */
	public String getBitRate() {
		return bitRate;
	}

	/**
	 * Sets {@code bitRate}
	 *
	 * @param bitRate the {@code java.lang.String} field
	 */
	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	/**
	 * Retrieves {@code {@link #connections}}
	 *
	 * @return value of {@link #connections}
	 */
	public String getConnections() {
		return connections;
	}

	/**
	 * Sets {@code connections}
	 *
	 * @param connections the {@code java.lang.String} field
	 */
	public void setConnections(String connections) {
		this.connections = connections;
	}

	/**
	 * Retrieves {@code {@link #receivedPackets }}
	 *
	 * @return value of {@link #receivedPackets}
	 */
	public String getReceivedPackets() {
		return receivedPackets;
	}

	/**
	 * Sets {@code receivedPacket}
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
	 * Retrieves {@code {@link #lastReceived}}
	 *
	 * @return value of {@link #lastReceived}
	 */
	public String getLastReceived() {
		return lastReceived;
	}

	/**
	 * Sets {@code lastReceived}
	 *
	 * @param lastReceived the {@code java.lang.String} field
	 */
	public void setLastReceived(String lastReceived) {
		this.lastReceived = lastReceived;
	}

	/**
	 * Retrieves {@code {@link #outputPackets}}
	 *
	 * @return value of {@link #outputPackets}
	 */
	public String getOutputPackets() {
		return outputPackets;
	}

	/**
	 * Sets {@code outputPackets}
	 *
	 * @param outputPackets the {@code java.lang.String} field
	 */
	public void setOutputPackets(String outputPackets) {
		this.outputPackets = outputPackets;
	}

	/**
	 * Retrieves {@code {@link #outputBytes}}
	 *
	 * @return value of {@link #outputBytes}
	 */
	public String getOutputBytes() {
		return outputBytes;
	}

	/**
	 * Sets {@code outputBytes}
	 *
	 * @param outputBytes the {@code java.lang.String} field
	 */
	public void setOutputBytes(String outputBytes) {
		this.outputBytes = outputBytes;
	}

	/**
	 * Retrieves {@code {@link #programNumber}}
	 *
	 * @return value of {@link #programNumber}
	 */
	public String getProgramNumber() {
		return programNumber;
	}

	/**
	 * Sets {@code programNumber}
	 *
	 * @param programNumber the {@code java.lang.String} field
	 */
	public void setProgramNumber(String programNumber) {
		this.programNumber = programNumber;
	}

	/**
	 * Retrieves {@code {@link #pcrPid}}
	 *
	 * @return value of {@link #pcrPid}
	 */
	public String getPcrPid() {
		return pcrPid;
	}

	/**
	 * Sets {@code pcrPid}
	 *
	 * @param pcrPid the {@code java.lang.String} field
	 */
	public void setPcrPid(String pcrPid) {
		this.pcrPid = pcrPid;
	}

	/**
	 * Retrieves {@code {@link #streamSummary}}
	 *
	 * @return value of {@link #streamSummary}
	 */
	public String getStreamSummary() {
		return streamSummary;
	}

	/**
	 * Sets {@code streamSummary}
	 *
	 * @param streamSummary the {@code java.lang.String} field
	 */
	public void setStreamSummary(String streamSummary) {
		this.streamSummary = streamSummary;
	}

	/**
	 * Retrieves {@code {@link #droppedPackets}}
	 *
	 * @return value of {@link #droppedPackets}
	 */
	public String getDroppedPackets() {
		return droppedPackets;
	}

	/**
	 * Sets {@code droppedPackets}
	 *
	 * @param droppedPackets the {@code java.lang.String} field
	 */
	public void setDroppedPackets(String droppedPackets) {
		this.droppedPackets = droppedPackets;
	}

	/**
	 * Retrieves {@code {@link #corruptedFrames}}
	 *
	 * @return value of {@link #corruptedFrames}
	 */
	public String getCorruptedFrames() {
		return corruptedFrames;
	}

	/**
	 * Sets {@code corruptedFrames}
	 *
	 * @param corruptedFrames the {@code java.lang.String} field
	 */
	public void setCorruptedFrames(String corruptedFrames) {
		this.corruptedFrames = corruptedFrames;
	}

	/**
	 * Retrieves {@code {@link #resumedStreamFlags }}
	 *
	 * @return value of {@link #resumedStreamFlags}
	 */
	public String getResumedStreamFlags() {
		return resumedStreamFlags;
	}

	/**
	 * Sets {@code restarts}
	 *
	 * @param resumedStreamFlags the {@code java.lang.String} field
	 */
	public void setResumedStreamFlags(String resumedStreamFlags) {
		this.resumedStreamFlags = resumedStreamFlags;
	}

	/**
	 * Retrieves {@code {@link #localPort}}
	 *
	 * @return value of {@link #localPort}
	 */
	public String getLocalPort() {
		return localPort;
	}

	/**
	 * Sets {@code localPort}
	 *
	 * @param localPort the {@code java.lang.String} field
	 */
	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}

	/**
	 * Retrieves {@code {@link #remotePort}}
	 *
	 * @return value of {@link #remotePort}
	 */
	public String getRemotePort() {
		return remotePort;
	}

	/**
	 * Sets {@code remotePort}
	 *
	 * @param remotePort the {@code java.lang.String} field
	 */
	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * Retrieves {@code {@link #receivedErrors}}
	 *
	 * @return value of {@link #receivedErrors}
	 */
	public String getReceivedErrors() {
		return receivedErrors;
	}

	/**
	 * Sets {@code receivedErrors}
	 *
	 * @param receivedErrors the {@code java.lang.String} field
	 */
	public void setReceivedErrors(String receivedErrors) {
		this.receivedErrors = receivedErrors;
	}

	/**
	 * Retrieves {@code {@link #srt}}
	 *
	 * @return value of {@link #srt}
	 */
	public SRT getSrt() {
		return srt;
	}

	/**
	 * Sets {@code srt}
	 *
	 * @param srt the {@code com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.SRT} field
	 */
	public void setSrt(SRT srt) {
		this.srt = srt;
	}

	/**
	 * Retrieves {@code {@link #sources}}
	 *
	 * @return value of {@link #sources}
	 */
	public List<Source> getSources() {
		return sources;
	}

	/**
	 * Sets {@code sources}
	 *
	 * @param sources the {@code java.util.List<com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Source>} field
	 */
	public void setSources(List<Source> sources) {
		this.sources = sources;
	}
}
