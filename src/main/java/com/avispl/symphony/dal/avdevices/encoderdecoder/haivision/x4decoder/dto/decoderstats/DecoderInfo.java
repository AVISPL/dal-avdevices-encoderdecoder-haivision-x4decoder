/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.BufferingMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.HDR;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.OutputFrameRate;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.QuadMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.State;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.StillImage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;

/**
 * Set of decoder configuration properties
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecoderInfo {

	@JsonAlias("id")
	private String id;

	@JsonAlias("streamId")
	private Integer streamId;

	@JsonAlias("altStreamId")
	private Integer altStreamId;

	@JsonAlias("state")
	private Integer state;

	@JsonAlias("latency")
	private String latency;

	@JsonAlias("stillImage")
	private Integer stillImage;

	@JsonAlias("stillImageDelay")
	private Integer stillImageDelay;

	@JsonAlias("szStillImageFileName")
	private String szStillImageFileName;

	@JsonAlias("enableBuffering")
	private String enableBuffering;

	@JsonAlias("bufferingMode")
	private Integer bufferingMode;

	@JsonAlias("bufferingDelay")
	private Integer bufferingDelay;

	@JsonAlias("multisyncBufferingDelay")
	private Integer multisyncBufferingDelay;

	@JsonAlias("hdrDynamicRange")
	private Integer hdrDynamicRange;

	@JsonAlias("nNumOfOutputs")
	private String nNumOfOutputs;

	@JsonAlias("output1")
	private Boolean output1;

	@JsonAlias("output2")
	private Boolean output2;

	@JsonAlias("output3")
	private Boolean output3;

	@JsonAlias("output4")
	private Boolean output4;

	@JsonAlias("outputFrameRate")
	private Integer outputFrameRate;

	@JsonAlias("previewEnabled")
	private String previewEnabled;

	@JsonAlias("previewIntervalSec")
	private String previewIntervalSec;

	@JsonAlias("quadMode")
	private Integer quadMode;

	public DecoderInfo() {
	}

	/**
	 * This constructor is used for deep clone object
	 *
	 * @param decoderInfo Decoder config info
	 */
	public DecoderInfo(DecoderInfo decoderInfo) {
		this.id = decoderInfo.getId();
		this.streamId = decoderInfo.getStreamId();
		this.altStreamId = decoderInfo.getAltStreamId();
		this.state = decoderInfo.getState().getCode();
		this.latency = decoderInfo.getLatency();
		this.stillImage = decoderInfo.getStillImage().getCode();
		this.stillImageDelay = decoderInfo.getStillImageDelay();
		this.szStillImageFileName = decoderInfo.getSzStillImageFileName();
		this.enableBuffering = decoderInfo.getEnableBuffering();
		this.bufferingMode = decoderInfo.getBufferingMode().getCode();
		this.bufferingDelay = decoderInfo.getBufferingDelay();
		this.multisyncBufferingDelay = decoderInfo.getMultisyncBufferingDelay();
		this.hdrDynamicRange = decoderInfo.getHdrDynamicRange().getCode();
		this.nNumOfOutputs = decoderInfo.getnNumOfOutputs();
		this.output1 = decoderInfo.getOutput1();
		this.output2 = decoderInfo.getOutput2();
		this.output3 = decoderInfo.getOutput3();
		this.output4 = decoderInfo.getOutput4();
		this.outputFrameRate = decoderInfo.getOutputFrameRate().getCode();
		this.previewEnabled = decoderInfo.getPreviewEnabled();
		this.previewIntervalSec = decoderInfo.getPreviewIntervalSec();
		this.quadMode = decoderInfo.getQuadMode().getCode();
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
	 * Retrieves {@code {@link #streamId}}
	 *
	 * @return value of {@link #streamId}
	 */
	public Integer getStreamId() {
		return streamId;
	}

	/**
	 * Sets {@code streamId}
	 *
	 * @param streamId the {@code java.lang.Integer} field
	 */
	public void setStreamId(Integer streamId) {
		this.streamId = streamId;
	}

	/**
	 * Retrieves {@code {@link #altStreamId}}
	 *
	 * @return value of {@link #altStreamId}
	 */
	public Integer getAltStreamId() {
		return altStreamId;
	}

	/**
	 * Sets {@code altStreamId}
	 *
	 * @param altStreamId the {@code java.lang.Integer} field
	 */
	public void setAltStreamId(Integer altStreamId) {
		this.altStreamId = altStreamId;
	}

	/**
	 * Retrieves {@code {@link #state}}
	 *
	 * @return value of {@link #state}
	 */
	public State getState() {
		if (this.state != null) {
			for (State state : State.values()) {
				if (state.getCode().equals(this.state)) {
					return state;
				}
			}
		}
		return State.STOPPED;
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
	 * Retrieves {@code {@link #stillImage}}
	 *
	 * @return value of {@link #stillImage}
	 */
	public StillImage getStillImage() {
		if (this.stillImage != null) {
			for (StillImage stillImage : StillImage.values()) {
				if (stillImage.getCode().equals(this.stillImage)) {
					return stillImage;
				}
			}
		}
		return StillImage.FREEZE;
	}

	/**
	 * Sets {@code stillImage}
	 *
	 * @param stillImage the {@code java.lang.Integer} field
	 */
	public void setStillImage(Integer stillImage) {
		this.stillImage = stillImage;
	}

	/**
	 * Retrieves {@code {@link #stillImageDelay}}
	 *
	 * @return value of {@link #stillImageDelay}
	 */
	public Integer getStillImageDelay() {
		if (stillImageDelay == null){
			return DecoderConstant.MIN_STILL_IMAGE_DELAY;
		}
		if (stillImageDelay < DecoderConstant.MIN_STILL_IMAGE_DELAY) {
			return DecoderConstant.MIN_STILL_IMAGE_DELAY;
		} else if (stillImageDelay > DecoderConstant.MAX_STILL_IMAGE_DELAY) {
			return DecoderConstant.MAX_STILL_IMAGE_DELAY;
		}
		return stillImageDelay;
	}

	/**
	 * Sets {@code stillImageDelay}
	 *
	 * @param stillImageDelay the {@code java.lang.Integer} field
	 */
	public void setStillImageDelay(Integer stillImageDelay) {
		this.stillImageDelay = stillImageDelay;
	}

	/**
	 * Retrieves {@code {@link #szStillImageFileName}}
	 *
	 * @return value of {@link #szStillImageFileName}
	 */
	public String getSzStillImageFileName() {
		return szStillImageFileName;
	}

	/**
	 * Sets {@code szStillImageFileName}
	 *
	 * @param szStillImageFileName the {@code java.lang.String} field
	 */
	public void setSzStillImageFileName(String szStillImageFileName) {
		this.szStillImageFileName = szStillImageFileName;
	}

	/**
	 * Retrieves {@code {@link #enableBuffering}}
	 *
	 * @return value of {@link #enableBuffering}
	 */
	public String getEnableBuffering() {
		return enableBuffering;
	}

	/**
	 * Sets {@code enableBuffering}
	 *
	 * @param enableBuffering the {@code java.lang.String} field
	 */
	public void setEnableBuffering(String enableBuffering) {
		this.enableBuffering = enableBuffering;
	}

	/**
	 * Retrieves {@code {@link #bufferingMode}}
	 *
	 * @return value of {@link #bufferingMode}
	 */
	public BufferingMode getBufferingMode() {
		if (this.bufferingMode != null) {
			for (BufferingMode bufferingMode : BufferingMode.values()) {
				if (bufferingMode.getCode().equals(this.bufferingMode)) {
					return bufferingMode;
				}
			}
		}
		return BufferingMode.AUTO;
	}

	/**
	 * Sets {@code bufferingMode}
	 *
	 * @param bufferingMode the {@code java.lang.Integer} field
	 */
	public void setBufferingMode(Integer bufferingMode) {
		this.bufferingMode = bufferingMode;
	}

	/**
	 * Retrieves {@code {@link #bufferingDelay}}
	 *
	 * @return value of {@link #bufferingDelay}
	 */
	public Integer getBufferingDelay() {
		if (bufferingDelay < DecoderConstant.MIN_BUFFERING_DELAY) {
			return DecoderConstant.MIN_BUFFERING_DELAY;
		} else if (bufferingDelay > DecoderConstant.MAX_BUFFERING_DELAY) {
			return DecoderConstant.MAX_BUFFERING_DELAY;
		}
		return bufferingDelay;
	}

	/**
	 * Sets {@code bufferingDelay}
	 *
	 * @param bufferingDelay the {@code java.lang.Integer} field
	 */
	public void setBufferingDelay(Integer bufferingDelay) {
		this.bufferingDelay = bufferingDelay;
	}

	/**
	 * Retrieves {@code {@link #multisyncBufferingDelay}}
	 *
	 * @return value of {@link #multisyncBufferingDelay}
	 */
	public Integer getMultisyncBufferingDelay() {
		if(multisyncBufferingDelay == null){
			return DecoderConstant.MIN_MULTI_SYNC_BUFFERING_DELAY;
		}
		if (multisyncBufferingDelay < DecoderConstant.MIN_MULTI_SYNC_BUFFERING_DELAY) {
			return DecoderConstant.MIN_MULTI_SYNC_BUFFERING_DELAY;
		} else if (multisyncBufferingDelay > DecoderConstant.MAX_MULTI_SYNC_BUFFERING_DELAY) {
			return DecoderConstant.MAX_MULTI_SYNC_BUFFERING_DELAY;
		}
		return multisyncBufferingDelay;
	}

	/**
	 * Sets {@code multisyncBufferingDelay}
	 *
	 * @param multisyncBufferingDelay the {@code java.lang.Integer} field
	 */
	public void setMultisyncBufferingDelay(Integer multisyncBufferingDelay) {
		this.multisyncBufferingDelay = multisyncBufferingDelay;
	}


	/**
	 * Retrieves {@code {@link #hdrDynamicRange}}
	 *
	 * @return value of {@link #hdrDynamicRange}
	 */
	public HDR getHdrDynamicRange() {
		if (this.hdrDynamicRange != null) {
			for (HDR hdr : HDR.values()) {
				if (hdr.getCode().equals(this.hdrDynamicRange)) {
					return hdr;
				}
			}
		}
		return HDR.AUTO;
	}

	/**
	 * Sets {@code hdrDynamicRange}
	 *
	 * @param hdrDynamicRange the {@code java.lang.Integer} field
	 */
	public void setHdrDynamicRange(Integer hdrDynamicRange) {
		this.hdrDynamicRange = hdrDynamicRange;
	}

	/**
	 * Retrieves {@code {@link #nNumOfOutputs}}
	 *
	 * @return value of {@link #nNumOfOutputs}
	 */
	public String getnNumOfOutputs() {
		return nNumOfOutputs;
	}

	/**
	 * Sets {@code nNumOfOutputs}
	 *
	 * @param nNumOfOutputs the {@code java.lang.String} field
	 */
	public void setnNumOfOutputs(String nNumOfOutputs) {
		this.nNumOfOutputs = nNumOfOutputs;
	}

	/**
	 * Retrieves {@code {@link #output1}}
	 *
	 * @return value of {@link #output1}
	 */
	public Boolean getOutput1() {
		return output1;
	}

	/**
	 * Sets {@code output1}
	 *
	 * @param output1 the {@code java.lang.Boolean} field
	 */
	public void setOutput1(Boolean output1) {
		this.output1 = output1;
	}

	/**
	 * Retrieves {@code {@link #output2}}
	 *
	 * @return value of {@link #output2}
	 */
	public Boolean getOutput2() {
		return output2;
	}

	/**
	 * Sets {@code output2}
	 *
	 * @param output2 the {@code java.lang.Boolean} field
	 */
	public void setOutput2(Boolean output2) {
		this.output2 = output2;
	}

	/**
	 * Retrieves {@code {@link #output3}}
	 *
	 * @return value of {@link #output3}
	 */
	public Boolean getOutput3() {
		return output3;
	}

	/**
	 * Sets {@code output3}
	 *
	 * @param output3 the {@code java.lang.Boolean} field
	 */
	public void setOutput3(Boolean output3) {
		this.output3 = output3;
	}

	/**
	 * Retrieves {@code {@link #output4}}
	 *
	 * @return value of {@link #output4}
	 */
	public Boolean getOutput4() {
		return output4;
	}

	/**
	 * Sets {@code output4}
	 *
	 * @param output4 the {@code java.lang.Boolean} field
	 */
	public void setOutput4(Boolean output4) {
		this.output4 = output4;
	}

	/**
	 * Retrieves {@code {@link #outputFrameRate}}
	 *
	 * @return value of {@link #outputFrameRate}
	 */
	public OutputFrameRate getOutputFrameRate() {
		if (this.outputFrameRate != null) {
			for (OutputFrameRate outputFrameRate : OutputFrameRate.values()) {
				if (outputFrameRate.getCode().equals(this.outputFrameRate)) {
					return outputFrameRate;
				}
			}
		}
		return OutputFrameRate.AUTO;
	}

	/**
	 * Sets {@code outputFrameRate}
	 *
	 * @param outputFrameRate the {@code java.lang.Integer} field
	 */
	public void setOutputFrameRate(Integer outputFrameRate) {
		this.outputFrameRate = outputFrameRate;
	}

	/**
	 * Retrieves {@code {@link #previewEnabled}}
	 *
	 * @return value of {@link #previewEnabled}
	 */
	public String getPreviewEnabled() {
		return previewEnabled;
	}

	/**
	 * Sets {@code previewEnabled}
	 *
	 * @param previewEnabled the {@code java.lang.String} field
	 */
	public void setPreviewEnabled(String previewEnabled) {
		this.previewEnabled = previewEnabled;
	}

	/**
	 * Retrieves {@code {@link #previewIntervalSec}}
	 *
	 * @return value of {@link #previewIntervalSec}
	 */
	public String getPreviewIntervalSec() {
		return previewIntervalSec;
	}

	/**
	 * Sets {@code previewIntervalSec}
	 *
	 * @param previewIntervalSec the {@code java.lang.String} field
	 */
	public void setPreviewIntervalSec(String previewIntervalSec) {
		this.previewIntervalSec = previewIntervalSec;
	}

	/**
	 * Retrieves {@code {@link #quadMode}}
	 *
	 * @return value of {@link #quadMode}
	 */
	/**
	 * Retrieves {@code {@link #quadMode}}
	 *
	 * @return value of {@link #quadMode}
	 */
	public QuadMode getQuadMode() {
		if (this.quadMode != null) {
			for (QuadMode quadMode : QuadMode.values()) {
				if (quadMode.getCode().equals(this.quadMode)) {
					return quadMode;
				}
			}
		}
		return QuadMode.NORMAL;
	}

	/**
	 * Sets {@code quadMode}
	 *
	 * @param quadMode the {@code java.lang.Integer} field
	 */
	public void setQuadMode(Integer quadMode) {
		this.quadMode = quadMode;
	}

	/**
	 * This method is used to create request body as Json for decoder controlling start/stop/update
	 * @return String json request body
	 */
	public String jsonRequest() {
		return '{' +
				"\"id\":" + id +
				",\"streamId\":" + streamId +
				",\"state\":" + state +
				",\"latency\":" + latency +
				",\"stillImage\":" + stillImage +
				",\"stillImageDelay\":" + stillImageDelay +
				",\"bufferingMode\":" + bufferingMode +
				",\"bufferingDelay\":" + bufferingDelay +
				",\"multisyncBufferingDelay\":" + multisyncBufferingDelay +
				",\"hdrDynamicRange\":" + hdrDynamicRange +
				",\"output1\":" + output1 +
				",\"output2\":" + output2 +
				",\"output3\":" + output3 +
				",\"output4\":" + output4 +
				",\"outputFrameRate\":" + outputFrameRate +
				",\"quadMode\":" + quadMode +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DecoderInfo that = (DecoderInfo) o;
		return Objects.equals(id, that.id) && Objects.equals(streamId, that.streamId) && Objects.equals(altStreamId, that.altStreamId) && Objects.equals(state,
				that.state) && Objects.equals(latency, that.latency) && Objects.equals(stillImage, that.stillImage) && Objects.equals(stillImageDelay, that.stillImageDelay)
				&& Objects.equals(szStillImageFileName, that.szStillImageFileName) && Objects.equals(enableBuffering, that.enableBuffering) && Objects.equals(bufferingMode,
				that.bufferingMode) && Objects.equals(bufferingDelay, that.bufferingDelay) && Objects.equals(multisyncBufferingDelay, that.multisyncBufferingDelay)
				&& Objects.equals(hdrDynamicRange, that.hdrDynamicRange) && Objects.equals(nNumOfOutputs, that.nNumOfOutputs) && Objects.equals(output1, that.output1)
				&& Objects.equals(output2, that.output2) && Objects.equals(output3, that.output3) && Objects.equals(output4, that.output4) && Objects.equals(
				outputFrameRate, that.outputFrameRate) && Objects.equals(previewEnabled, that.previewEnabled) && Objects.equals(previewIntervalSec, that.previewIntervalSec)
				&& Objects.equals(quadMode, that.quadMode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, streamId, altStreamId, state, latency, stillImage, stillImageDelay, szStillImageFileName, enableBuffering, bufferingMode, bufferingDelay, multisyncBufferingDelay,
				hdrDynamicRange, nNumOfOutputs, output1, output2, output3, output4, outputFrameRate, previewEnabled, previewIntervalSec, quadMode);
	}
}

