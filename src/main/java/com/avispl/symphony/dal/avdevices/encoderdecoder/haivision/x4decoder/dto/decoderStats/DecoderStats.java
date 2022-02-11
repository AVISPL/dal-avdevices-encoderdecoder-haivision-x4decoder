/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Decoder statistics
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderStats {

	@JsonAlias("id")
	private Integer decoderID;

	@JsonAlias("state")
	private Integer state;

	@JsonAlias("uptime")
	private String uptime;

	@JsonAlias("oversubscribedFrames")
	private String oversubscribedFrames;

	@JsonAlias("bufferingState")
	private String bufferingState;

	@JsonAlias("bufferingMode")
	private String bufferingMode;

	@JsonAlias("bufferingAdjustments")
	private String bufferingAdjustments;

	@JsonAlias("videoLatency")
	private String videoLatency;

	@JsonAlias("stcToPcrLeadTime")
	private String stcToPcrLeadTime;

	@JsonAlias("videoAlgorithm")
	private String videoAlgorithm;

	@JsonAlias("videoProfile")
	private String videoProfile;

	@JsonAlias("videoLevel")
	private String videoLevel;

	@JsonAlias("videoOutputFormat")
	private String videoOutputFormat;

	@JsonAlias("videoFraming")
	private String videoFraming;

	@JsonAlias("videoSlicesPerFrame")
	private String videoSlicesPerFrame;

	@JsonAlias("videoInputFrameRate")
	private String videoInputFrameRate;

	@JsonAlias("stillImage")
	private String stillImage;

	@JsonAlias("videoDecoderState")
	private String videoDecoderState;

	@JsonAlias("videoDisplayFormat")
	private String videoDisplayFormat;

	@JsonAlias("loadPercentage")
	private Integer loadPercentage;

	@JsonAlias("preprocessorState")
	private String preprocessorState;

	@JsonAlias("troubleCode")
	private Integer troubleCode;

	@JsonAlias("displayedOutputFrames")
	private String displayedOutputFrames;

	@JsonAlias("skippedOutputFrames")
	private String skippedOutputFrames;

	@JsonAlias("replayedOutputFrames")
	private String replayedOutputFrames;

	@JsonAlias("audioState")
	private String audioState;

	@JsonAlias("audioSampleRate")
	private String audioSampleRate;

	@JsonAlias("audioPairsAmount")
	private Integer audioPairsAmount ;

	@JsonAlias("audioDecodedFrames")
	private String audioDecodedFrames;

	@JsonAlias("audioPlayedFrames")
	private String audioPlayedFrames;

	@JsonAlias("audioSkippedFrames")
	private String audioSkippedFrames;

	@JsonAlias("audioPairs")
	private List<AudioPair> audioPairs;

	@JsonAlias("clockTrackingMode")
	private String clockTrackingMode;

	@JsonAlias("clockStatus")
	private String clockStatus;

	@JsonAlias("clockReSyncCount")
	private Integer clockReSyncCount;

	@JsonAlias("clockCurrentStc")
	private String clockCurrentStc;

	@JsonAlias("clockStcAvg")
	private String clockStcAvg;

	@JsonAlias("hdrTypeIn")
	private String hdrTypeIn;

	@JsonAlias("hdrType")
	private String hdrType;

	@JsonAlias("hdrColourPrimaries")
	private String hdrColourPrimaries;

	@JsonAlias("hdrTransferCharacteristics")
	private String hdrTransferCharacteristics;

	@JsonAlias("hdrMatrixCoefficients")
	private String hdrMatrixCoefficients;

	@JsonAlias("tcReceivedPackets")
	private String tcReceivedPackets;

	@JsonAlias("tcOutputPackets")
	private String tcOutputPackets;

	@JsonAlias("tcFreedPackets")
	private String tcFreedPackets;

	/**
	 * Retrieves {@code {@link #decoderID}}
	 *
	 * @return value of {@link #decoderID}
	 */
	public Integer getDecoderID() {
		return decoderID;
	}

	/**
	 * Sets {@code decoderID}
	 *
	 * @param decoderID the {@code java.lang.Integer} field
	 */
	public void setDecoderID(Integer decoderID) {
		this.decoderID = decoderID;
	}

	/**
	 * Retrieves {@code {@link #state}}
	 *
	 * @return value of {@link #state}
	 */
	public String getState() {
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
				return "";
		}
	}

	/**
	 * Sets {@code state}
	 *
	 * @param state the {@code java.lang.Integer} field
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * Retrieves {@code {@link #uptime}}
	 *
	 * @return value of {@link #uptime}
	 */
	public String getUptime() {
		return uptime;
	}

	/**
	 * Sets {@code uptime}
	 *
	 * @param uptime the {@code java.lang.String} field
	 */
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	/**
	 * Retrieves {@code {@link #oversubscribedFrames}}
	 *
	 * @return value of {@link #oversubscribedFrames}
	 */
	public String getOversubscribedFrames() {
		return oversubscribedFrames;
	}

	/**
	 * Sets {@code oversubscribedFrames}
	 *
	 * @param oversubscribedFrames the {@code java.lang.String} field
	 */
	public void setOversubscribedFrames(String oversubscribedFrames) {
		this.oversubscribedFrames = oversubscribedFrames;
	}

	/**
	 * Retrieves {@code {@link #bufferingState}}
	 *
	 * @return value of {@link #bufferingState}
	 */
	public String getBufferingState() {
		return bufferingState;
	}

	/**
	 * Sets {@code bufferingState}
	 *
	 * @param bufferingState the {@code java.lang.String} field
	 */
	public void setBufferingState(String bufferingState) {
		this.bufferingState = bufferingState;
	}

	/**
	 * Retrieves {@code {@link #bufferingMode}}
	 *
	 * @return value of {@link #bufferingMode}
	 */
	public String getBufferingMode() {
		return bufferingMode;
	}

	/**
	 * Sets {@code bufferingMode}
	 *
	 * @param bufferingMode the {@code java.lang.String} field
	 */
	public void setBufferingMode(String bufferingMode) {
		this.bufferingMode = bufferingMode;
	}

	/**
	 * Retrieves {@code {@link #bufferingAdjustments}}
	 *
	 * @return value of {@link #bufferingAdjustments}
	 */
	public String getBufferingAdjustments() {
		return bufferingAdjustments;
	}

	/**
	 * Sets {@code bufferingAdjustments}
	 *
	 * @param bufferingAdjustments the {@code java.lang.String} field
	 */
	public void setBufferingAdjustments(String bufferingAdjustments) {
		this.bufferingAdjustments = bufferingAdjustments;
	}

	/**
	 * Retrieves {@code {@link #videoLatency}}
	 *
	 * @return value of {@link #videoLatency}
	 */
	public String getVideoLatency() {
		return videoLatency;
	}

	/**
	 * Sets {@code videoLatency}
	 *
	 * @param videoLatency the {@code java.lang.String} field
	 */
	public void setVideoLatency(String videoLatency) {
		this.videoLatency = videoLatency;
	}

	/**
	 * Retrieves {@code {@link #stcToPcrLeadTime}}
	 *
	 * @return value of {@link #stcToPcrLeadTime}
	 */
	public String getStcToPcrLeadTime() {
		return stcToPcrLeadTime;
	}

	/**
	 * Sets {@code stcToPcrLeadTime}
	 *
	 * @param stcToPcrLeadTime the {@code java.lang.String} field
	 */
	public void setStcToPcrLeadTime(String stcToPcrLeadTime) {
		this.stcToPcrLeadTime = stcToPcrLeadTime;
	}

	/**
	 * Retrieves {@code {@link #videoAlgorithm}}
	 *
	 * @return value of {@link #videoAlgorithm}
	 */
	public String getVideoAlgorithm() {
		return videoAlgorithm;
	}

	/**
	 * Sets {@code videoAlgorithm}
	 *
	 * @param videoAlgorithm the {@code java.lang.String} field
	 */
	public void setVideoAlgorithm(String videoAlgorithm) {
		this.videoAlgorithm = videoAlgorithm;
	}

	/**
	 * Retrieves {@code {@link #videoProfile}}
	 *
	 * @return value of {@link #videoProfile}
	 */
	public String getVideoProfile() {
		return videoProfile;
	}

	/**
	 * Sets {@code videoProfile}
	 *
	 * @param videoProfile the {@code java.lang.String} field
	 */
	public void setVideoProfile(String videoProfile) {
		this.videoProfile = videoProfile;
	}

	/**
	 * Retrieves {@code {@link #videoLevel}}
	 *
	 * @return value of {@link #videoLevel}
	 */
	public String getVideoLevel() {
		return videoLevel;
	}

	/**
	 * Sets {@code videoLevel}
	 *
	 * @param videoLevel the {@code java.lang.String} field
	 */
	public void setVideoLevel(String videoLevel) {
		this.videoLevel = videoLevel;
	}

	/**
	 * Retrieves {@code {@link #videoOutputFormat}}
	 *
	 * @return value of {@link #videoOutputFormat}
	 */
	public String getVideoOutputFormat() {
		return videoOutputFormat;
	}

	/**
	 * Sets {@code videoOutputFormat}
	 *
	 * @param videoOutputFormat the {@code java.lang.String} field
	 */
	public void setVideoOutputFormat(String videoOutputFormat) {
		this.videoOutputFormat = videoOutputFormat;
	}

	/**
	 * Retrieves {@code {@link #videoFraming}}
	 *
	 * @return value of {@link #videoFraming}
	 */
	public String getVideoFraming() {
		return videoFraming;
	}

	/**
	 * Sets {@code videoFraming}
	 *
	 * @param videoFraming the {@code java.lang.String} field
	 */
	public void setVideoFraming(String videoFraming) {
		this.videoFraming = videoFraming;
	}

	/**
	 * Retrieves {@code {@link #videoSlicesPerFrame}}
	 *
	 * @return value of {@link #videoSlicesPerFrame}
	 */
	public String getVideoSlicesPerFrame() {
		return videoSlicesPerFrame;
	}

	/**
	 * Sets {@code videoSlicesPerFrame}
	 *
	 * @param videoSlicesPerFrame the {@code java.lang.String} field
	 */
	public void setVideoSlicesPerFrame(String videoSlicesPerFrame) {
		this.videoSlicesPerFrame = videoSlicesPerFrame;
	}

	/**
	 * Retrieves {@code {@link #videoInputFrameRate}}
	 *
	 * @return value of {@link #videoInputFrameRate}
	 */
	public String getVideoInputFrameRate() {
		return videoInputFrameRate;
	}

	/**
	 * Sets {@code videoInputFrameRate}
	 *
	 * @param videoInputFrameRate the {@code java.lang.String} field
	 */
	public void setVideoInputFrameRate(String videoInputFrameRate) {
		this.videoInputFrameRate = videoInputFrameRate;
	}

	/**
	 * Retrieves {@code {@link #stillImage}}
	 *
	 * @return value of {@link #stillImage}
	 */
	public String getStillImage() {
		return stillImage;
	}

	/**
	 * Sets {@code stillImage}
	 *
	 * @param stillImage the {@code java.lang.String} field
	 */
	public void setStillImage(String stillImage) {
		this.stillImage = stillImage;
	}

	/**
	 * Retrieves {@code {@link #videoDecoderState}}
	 *
	 * @return value of {@link #videoDecoderState}
	 */
	public String getVideoDecoderState() {
		return videoDecoderState;
	}

	/**
	 * Sets {@code videoDecoderState}
	 *
	 * @param videoDecoderState the {@code java.lang.String} field
	 */
	public void setVideoDecoderState(String videoDecoderState) {
		this.videoDecoderState = videoDecoderState;
	}

	/**
	 * Retrieves {@code {@link #videoDisplayFormat}}
	 *
	 * @return value of {@link #videoDisplayFormat}
	 */
	public String getVideoDisplayFormat() {
		return videoDisplayFormat;
	}

	/**
	 * Sets {@code videoDisplayFormat}
	 *
	 * @param videoDisplayFormat the {@code java.lang.String} field
	 */
	public void setVideoDisplayFormat(String videoDisplayFormat) {
		this.videoDisplayFormat = videoDisplayFormat;
	}

	/**
	 * Retrieves {@code {@link #loadPercentage}}
	 *
	 * @return value of {@link #loadPercentage}
	 */
	public Integer getLoadPercentage() {
		return loadPercentage;
	}

	/**
	 * Sets {@code loadPercentage}
	 *
	 * @param loadPercentage the {@code java.lang.Integer} field
	 */
	public void setLoadPercentage(Integer loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	/**
	 * Retrieves {@code {@link #preprocessorState}}
	 *
	 * @return value of {@link #preprocessorState}
	 */
	public String getPreprocessorState() {
		return preprocessorState;
	}

	/**
	 * Sets {@code preprocessorState}
	 *
	 * @param preprocessorState the {@code java.lang.String} field
	 */
	public void setPreprocessorState(String preprocessorState) {
		this.preprocessorState = preprocessorState;
	}

	/**
	 * Retrieves {@code {@link #troubleCode}}
	 *
	 * @return value of {@link #troubleCode}
	 */
	public Integer getTroubleCode() {
		return troubleCode;
	}

	/**
	 * Sets {@code troubleCode}
	 *
	 * @param troubleCode the {@code java.lang.Integer} field
	 */
	public void setTroubleCode(Integer troubleCode) {
		this.troubleCode = troubleCode;
	}

	/**
	 * Retrieves {@code {@link #displayedOutputFrames}}
	 *
	 * @return value of {@link #displayedOutputFrames}
	 */
	public String getDisplayedOutputFrames() {
		return displayedOutputFrames;
	}

	/**
	 * Sets {@code displayedOutputFrames}
	 *
	 * @param displayedOutputFrames the {@code java.lang.String} field
	 */
	public void setDisplayedOutputFrames(String displayedOutputFrames) {
		this.displayedOutputFrames = displayedOutputFrames;
	}

	/**
	 * Retrieves {@code {@link #skippedOutputFrames}}
	 *
	 * @return value of {@link #skippedOutputFrames}
	 */
	public String getSkippedOutputFrames() {
		return skippedOutputFrames;
	}

	/**
	 * Sets {@code skippedOutputFrames}
	 *
	 * @param skippedOutputFrames the {@code java.lang.String} field
	 */
	public void setSkippedOutputFrames(String skippedOutputFrames) {
		this.skippedOutputFrames = skippedOutputFrames;
	}

	/**
	 * Retrieves {@code {@link #replayedOutputFrames}}
	 *
	 * @return value of {@link #replayedOutputFrames}
	 */
	public String getReplayedOutputFrames() {
		return replayedOutputFrames;
	}

	/**
	 * Sets {@code replayedOutputFrames}
	 *
	 * @param replayedOutputFrames the {@code java.lang.String} field
	 */
	public void setReplayedOutputFrames(String replayedOutputFrames) {
		this.replayedOutputFrames = replayedOutputFrames;
	}

	/**
	 * Retrieves {@code {@link #audioState}}
	 *
	 * @return value of {@link #audioState}
	 */
	public String getAudioState() {
		return audioState;
	}

	/**
	 * Sets {@code audioState}
	 *
	 * @param audioState the {@code java.lang.String} field
	 */
	public void setAudioState(String audioState) {
		this.audioState = audioState;
	}

	/**
	 * Retrieves {@code {@link #audioSampleRate}}
	 *
	 * @return value of {@link #audioSampleRate}
	 */
	public String getAudioSampleRate() {
		return audioSampleRate;
	}

	/**
	 * Sets {@code audioSampleRate}
	 *
	 * @param audioSampleRate the {@code java.lang.String} field
	 */
	public void setAudioSampleRate(String audioSampleRate) {
		this.audioSampleRate = audioSampleRate;
	}

	/**
	 * Retrieves {@code {@link #audioPairsAmount}}
	 *
	 * @return value of {@link #audioPairsAmount}
	 */
	public Integer getAudioPairsAmount() {
		return audioPairsAmount;
	}

	/**
	 * Sets {@code audioPairsAmount}
	 *
	 * @param audioPairsAmount the {@code java.lang.String} field
	 */
	public void setAudioPairsAmount(Integer audioPairsAmount) {
		this.audioPairsAmount = audioPairsAmount;
	}

	/**
	 * Retrieves {@code {@link #audioDecodedFrames}}
	 *
	 * @return value of {@link #audioDecodedFrames}
	 */
	public String getAudioDecodedFrames() {
		return audioDecodedFrames;
	}

	/**
	 * Sets {@code audioDecodedFrames}
	 *
	 * @param audioDecodedFrames the {@code java.lang.String} field
	 */
	public void setAudioDecodedFrames(String audioDecodedFrames) {
		this.audioDecodedFrames = audioDecodedFrames;
	}

	/**
	 * Retrieves {@code {@link #audioPlayedFrames}}
	 *
	 * @return value of {@link #audioPlayedFrames}
	 */
	public String getAudioPlayedFrames() {
		return audioPlayedFrames;
	}

	/**
	 * Sets {@code audioPlayedFrames}
	 *
	 * @param audioPlayedFrames the {@code java.lang.String} field
	 */
	public void setAudioPlayedFrames(String audioPlayedFrames) {
		this.audioPlayedFrames = audioPlayedFrames;
	}

	/**
	 * Retrieves {@code {@link #audioSkippedFrames}}
	 *
	 * @return value of {@link #audioSkippedFrames}
	 */
	public String getAudioSkippedFrames() {
		return audioSkippedFrames;
	}

	/**
	 * Sets {@code audioSkippedFrames}
	 *
	 * @param audioSkippedFrames the {@code java.lang.String} field
	 */
	public void setAudioSkippedFrames(String audioSkippedFrames) {
		this.audioSkippedFrames = audioSkippedFrames;
	}

	/**
	 * Retrieves {@code {@link #audioPairs}}
	 *
	 * @return value of {@link #audioPairs}
	 */
	public List<AudioPair> getAudioPairs() {
		return audioPairs;
	}

	/**
	 * Sets {@code audioPairs}
	 *
	 * @param audioPairs the {@code java.util.List<com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.AudioPairs>} field
	 */
	public void setAudioPairs(List<AudioPair> audioPairs) {
		this.audioPairs = audioPairs;
	}

	/**
	 * Retrieves {@code {@link #clockTrackingMode}}
	 *
	 * @return value of {@link #clockTrackingMode}
	 */
	public String getClockTrackingMode() {
		return clockTrackingMode;
	}

	/**
	 * Sets {@code clockTrackingMode}
	 *
	 * @param clockTrackingMode the {@code java.lang.String} field
	 */
	public void setClockTrackingMode(String clockTrackingMode) {
		this.clockTrackingMode = clockTrackingMode;
	}

	/**
	 * Retrieves {@code {@link #clockStatus}}
	 *
	 * @return value of {@link #clockStatus}
	 */
	public String getClockStatus() {
		return clockStatus;
	}

	/**
	 * Sets {@code clockStatus}
	 *
	 * @param clockStatus the {@code java.lang.String} field
	 */
	public void setClockStatus(String clockStatus) {
		this.clockStatus = clockStatus;
	}

	/**
	 * Retrieves {@code {@link #clockReSyncCount}}
	 *
	 * @return value of {@link #clockReSyncCount}
	 */
	public Integer getClockReSyncCount() {
		return clockReSyncCount;
	}

	/**
	 * Sets {@code clockReSyncCount}
	 *
	 * @param clockReSyncCount the {@code java.lang.Integer} field
	 */
	public void setClockReSyncCount(Integer clockReSyncCount) {
		this.clockReSyncCount = clockReSyncCount;
	}

	/**
	 * Retrieves {@code {@link #clockCurrentStc}}
	 *
	 * @return value of {@link #clockCurrentStc}
	 */
	public String getClockCurrentStc() {
		return clockCurrentStc;
	}

	/**
	 * Sets {@code clockCurrentStc}
	 *
	 * @param clockCurrentStc the {@code java.lang.String} field
	 */
	public void setClockCurrentStc(String clockCurrentStc) {
		this.clockCurrentStc = clockCurrentStc;
	}

	/**
	 * Retrieves {@code {@link #clockStcAvg}}
	 *
	 * @return value of {@link #clockStcAvg}
	 */
	public String getClockStcAvg() {
		return clockStcAvg;
	}

	/**
	 * Sets {@code clockStcAvg}
	 *
	 * @param clockStcAvg the {@code java.lang.String} field
	 */
	public void setClockStcAvg(String clockStcAvg) {
		this.clockStcAvg = clockStcAvg;
	}

	/**
	 * Retrieves {@code {@link #hdrTypeIn}}
	 *
	 * @return value of {@link #hdrTypeIn}
	 */
	public String getHdrTypeIn() {
		return hdrTypeIn;
	}

	/**
	 * Sets {@code hdrTypeIn}
	 *
	 * @param hdrTypeIn the {@code java.lang.String} field
	 */
	public void setHdrTypeIn(String hdrTypeIn) {
		this.hdrTypeIn = hdrTypeIn;
	}

	/**
	 * Retrieves {@code {@link #hdrType}}
	 *
	 * @return value of {@link #hdrType}
	 */
	public String getHdrType() {
		return hdrType;
	}

	/**
	 * Sets {@code hdrType}
	 *
	 * @param hdrType the {@code java.lang.String} field
	 */
	public void setHdrType(String hdrType) {
		this.hdrType = hdrType;
	}

	/**
	 * Retrieves {@code {@link #hdrColourPrimaries}}
	 *
	 * @return value of {@link #hdrColourPrimaries}
	 */
	public String getHdrColourPrimaries() {
		return hdrColourPrimaries;
	}

	/**
	 * Sets {@code hdrColourPrimaries}
	 *
	 * @param hdrColourPrimaries the {@code java.lang.String} field
	 */
	public void setHdrColourPrimaries(String hdrColourPrimaries) {
		this.hdrColourPrimaries = hdrColourPrimaries;
	}

	/**
	 * Retrieves {@code {@link #hdrTransferCharacteristics}}
	 *
	 * @return value of {@link #hdrTransferCharacteristics}
	 */
	public String getHdrTransferCharacteristics() {
		return hdrTransferCharacteristics;
	}

	/**
	 * Sets {@code hdrTransferCharacteristics}
	 *
	 * @param hdrTransferCharacteristics the {@code java.lang.String} field
	 */
	public void setHdrTransferCharacteristics(String hdrTransferCharacteristics) {
		this.hdrTransferCharacteristics = hdrTransferCharacteristics;
	}

	/**
	 * Retrieves {@code {@link #hdrMatrixCoefficients}}
	 *
	 * @return value of {@link #hdrMatrixCoefficients}
	 */
	public String getHdrMatrixCoefficients() {
		return hdrMatrixCoefficients;
	}

	/**
	 * Sets {@code hdrMatrixCoefficients}
	 *
	 * @param hdrMatrixCoefficients the {@code java.lang.String} field
	 */
	public void setHdrMatrixCoefficients(String hdrMatrixCoefficients) {
		this.hdrMatrixCoefficients = hdrMatrixCoefficients;
	}

	/**
	 * Retrieves {@code {@link #tcReceivedPackets}}
	 *
	 * @return value of {@link #tcReceivedPackets}
	 */
	public String getTcReceivedPackets() {
		return tcReceivedPackets;
	}

	/**
	 * Sets {@code tcReceivedPackets}
	 *
	 * @param tcReceivedPackets the {@code java.lang.String} field
	 */
	public void setTcReceivedPackets(String tcReceivedPackets) {
		this.tcReceivedPackets = tcReceivedPackets;
	}

	/**
	 * Retrieves {@code {@link #tcOutputPackets}}
	 *
	 * @return value of {@link #tcOutputPackets}
	 */
	public String getTcOutputPackets() {
		return tcOutputPackets;
	}

	/**
	 * Sets {@code tcOutputPackets}
	 *
	 * @param tcOutputPackets the {@code java.lang.String} field
	 */
	public void setTcOutputPackets(String tcOutputPackets) {
		this.tcOutputPackets = tcOutputPackets;
	}

	/**
	 * Retrieves {@code {@link #tcFreedPackets}}
	 *
	 * @return value of {@link #tcFreedPackets}
	 */
	public String getTcFreedPackets() {
		return tcFreedPackets;
	}

	/**
	 * Sets {@code tcFreedPackets}
	 *
	 * @param tcFreedPackets the {@code java.lang.String} field
	 */
	public void setTcFreedPackets(String tcFreedPackets) {
		this.tcFreedPackets = tcFreedPackets;
	}
}
