/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderURL;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.AudioPairs;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderStats;
import com.avispl.symphony.dal.communicator.RestCommunicator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * An implementation of RestCommunicator to provide communication and interaction with Haivision X4 Decoders
 * Supported features are:
 * <p>
 * Monitoring:
 * <p>
 * Controlling:
 *
 * @author Harry
 * @since 1.0.0
 */
public class HaivisionX4DecoderCommunicator extends RestCommunicator implements Monitorable, Controller {

	private AuthenticationCookie authenticationCookie;
	private HashMap<String, String> failedMonitor;

	/**
	 * This method is called by Symphony to control the properties in the device
	 *
	 * @param controllableProperty ControllableProperty instance
	 * @throws Exception if unexpected the value
	 */
	@Override
	public void controlProperty(ControllableProperty controllableProperty) throws Exception {

	}

	/**
	 * This method is called by Symphony to control the properties in the device
	 *
	 * @param list the list ControllableProperty instance
	 * @throws Exception if ControllableProperty list is empty
	 */
	@Override
	public void controlProperties(List<ControllableProperty> list) throws Exception {

	}

	/**
	 * This method is called by Symphony to get the list of statistics to be displayed
	 *
	 * @return List<Statistics> This return the list of statistics
	 */
	@Override
	public List<Statistics> getMultipleStatistics() throws Exception {
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		Map<String, String> stats = new HashMap<>();
		stats.put("deviceInfo#firstProperties", "value");
		extendedStatistics.setStatistics(stats);

		return Collections.singletonList(extendedStatistics);
	}

	@Override
	protected void authenticate() throws Exception {

	}

	/**
	 * @return HttpHeaders contain cookie for authorization
	 */
	@Override
	protected HttpHeaders putExtraRequestHeaders(HttpMethod httpMethod, String uri, HttpHeaders headers) throws Exception {
		headers.set("Content-Type", "text/xml");
		headers.set("Content-Type", "application/json");

		String uid = authenticationCookie.getUid().toString();
		if (uid != null && !uid.equals(DecoderConstant.AUTHORIZED)) {
			headers.set("Cookie", authenticationCookie.getUid().toString());
		}
		return super.putExtraRequestHeaders(httpMethod, uri, headers);
	}

	/**
	 * @param path url of the request
	 * @return String full path of the device
	 */
	private String buildDeviceFullPath(String path) {
		Objects.requireNonNull(path);

		return DecoderConstant.HTTP
				+ getHost()
				+ path;
	}

	/**
	 * This method is used to retrieve device login information (Token) by send POST request to https://10.8.50.160/api/v0/logon
	 *
	 * When there is no token data or having an Exception, The Token of login information is going to set with null value
	 */
	private void retrieveCookieFromDevice() {
		String login = getLogin();
		String password = getPassword();

		ObjectNode request = JsonNodeFactory.instance.objectNode();
		request.put(DecoderConstant.USERNAME, login);
		request.put(DecoderConstant.PASSWORD, password);

		try {
			if (this.authenticationCookie.isTimeout() || this.authenticationCookie.getUid() == null) {
				JsonNode responseData = doPost(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.AUTHENTICATION), request, JsonNode.class);

				if (responseData != null) {
					ObjectMapper mapper = new ObjectMapper();
					authenticationCookie = mapper.readValue(responseData.toString(), AuthenticationCookie.class);

				} else {
					throw new ResourceNotReachableException(DecoderConstant.GETTING_COOKIE_ERR);
				}
			}
		} catch (Exception e) {
			this.authenticationCookie.setUid(null);
			throw new ResourceNotReachableException(DecoderConstant.GETTING_COOKIE_ERR);
		}
	}

	/**
	 * @param value value of device info
	 * @return String (none/value)
	 */
	private String checkForNullData(String value) {
		return value.equals("") ? DecoderConstant.NONE : value;
	}

	/**
	 * Value of list statistics property of decoder is NONE
	 *
	 * @param stats list statistics property
	 */
	private void contributeNoneValueForDecoderStatistics(Map<String, String> stats, Integer decoderID) {
		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID + DecoderConstant.HASH;

		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DECODER_ID.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.UPTIME.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.OVERSUBSCRIBED_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_STATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_MODE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_ADJUSTMENTS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LATENCY.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STC_TO_PCR_LEAD_TIME.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_ALGORITHM.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_PROFILE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LEVEL.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_OUTPUT_FORMAT.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_FRAMING.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_SLICES_PER_FRAME.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_INPUT_FRAME_RATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STILL_IMAGE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DECODER_STATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DISPLAY_FORMAT.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.LOAD_PERCENTAGE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.PREPROCESSOR_STATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TROUBLE_CODE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DISPLAYED_OUTPUT_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SKIPPED_OUTPUT_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.REPLAYED_OUTPUT_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_STATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SAMPLE_RATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIRS_AMOUNT.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_DECODED_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PLAYED_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SKIPPED_FRAMES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIR_MODE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.COMPRESSION.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BITRATE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AV_SYNC_MS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DISCONTINUITIES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DECODE_ERRORS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.OUTPUT_ERRORS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SAMPE_RATE_IN.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SAMPE_RATE_OUT.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_TRACKING_MODE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STATUS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_RE_SYNC_COUNT.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_CURRENT_STC.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STC_AVG.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE_IN.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_COLOUR_PRIMARIES.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TRANSFER_CHARACTERISTICS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_MATRIX_COEFFICIENTS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_RECEIVED_PACKETS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_OUTPUT_PACKETS.getName(), DecoderConstant.NONE);
		stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_FREED_PACKETS.getName(), DecoderConstant.NONE);
	}

	/**
	 * Update failedMonitor with Getting device info error message
	 *
	 * @param failedMonitor list statistics property
	 */
	private void updateDecoderStatisticsFailedMonitor(Map<String, String> failedMonitor, Integer decoderID) {
		failedMonitor.put(MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID, DecoderConstant.GETTING_DEVICE_INFO_ERR);
	}

	/**
	 * This method is used to retrieve device information by send GET request to http://
	 *
	 * @param stats list statistics property
	 *
	 * When token is null, the stats is going to contribute with NONE value and the failedMonitor is going to update
	 * When there is no response data, the stats is going to contribute with none value and the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveDecoderStats(Map<String, String> stats, Integer decoderID) {
		try {
			if (this.authenticationCookie.getUid() != null) {
				JsonNode response = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID), JsonNode.class);

				if (!response.get(DecoderConstant.STATISTICS).isEmpty()) {
					ObjectMapper objectMapper = new ObjectMapper();
					DecoderInfo decoderInfo = objectMapper.readValue(response.toString(), DecoderInfo.class);

					DecoderStats decoderStatistics = decoderInfo.getDecoderStatistics();
					AudioPairs audioPairs = decoderStatistics.getAudioPairs();
					String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID + DecoderConstant.HASH;

					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DECODER_ID.getName(), checkForNullData(decoderStatistics.getDecoderID().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STATE.getName(), checkForNullData(decoderStatistics.getState().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.UPTIME.getName(), checkForNullData(decoderStatistics.getUptime()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.OVERSUBSCRIBED_FRAMES.getName(), checkForNullData(decoderStatistics.getOversubscribedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_STATE.getName(), checkForNullData(decoderStatistics.getBufferingState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_MODE.getName(), checkForNullData(decoderStatistics.getBufferingMode()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_ADJUSTMENTS.getName(), checkForNullData(decoderStatistics.getBufferingAdjustments()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LATENCY.getName(), checkForNullData(decoderStatistics.getVideoLatency()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STC_TO_PCR_LEAD_TIME.getName(), checkForNullData(decoderStatistics.getStcToPcrLeadTime()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_ALGORITHM.getName(), checkForNullData(decoderStatistics.getVideoAlgorithm()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_PROFILE.getName(), checkForNullData(decoderStatistics.getVideoProfile()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LEVEL.getName(), checkForNullData(decoderStatistics.getVideoLevel()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_OUTPUT_FORMAT.getName(), checkForNullData(decoderStatistics.getVideoOutputFormat()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_FRAMING.getName(), checkForNullData(decoderStatistics.getVideoFraming()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_SLICES_PER_FRAME.getName(), checkForNullData(decoderStatistics.getVideoSlicesPerFrame()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_INPUT_FRAME_RATE.getName(), checkForNullData(decoderStatistics.getVideoInputFrameRate()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STILL_IMAGE.getName(), checkForNullData(decoderStatistics.getStillImage()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DECODER_STATE.getName(), checkForNullData(decoderStatistics.getVideoDecoderState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DISPLAY_FORMAT.getName(), checkForNullData(decoderStatistics.getVideoDisplayFormat()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.LOAD_PERCENTAGE.getName(), checkForNullData(decoderStatistics.getLoadPercentage().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.PREPROCESSOR_STATE.getName(), checkForNullData(decoderStatistics.getPreprocessorState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TROUBLE_CODE.getName(), checkForNullData(decoderStatistics.getTroubleCode().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DISPLAYED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStatistics.getDisplayedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SKIPPED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStatistics.getSkippedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.REPLAYED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStatistics.getReplayedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_STATE.getName(), checkForNullData(decoderStatistics.getAudioState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SAMPLE_RATE.getName(), checkForNullData(decoderStatistics.getAudioSampleRate()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIRS_AMOUNT.getName(), checkForNullData(decoderStatistics.getAudioPairsAmount()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_DECODED_FRAMES.getName(), checkForNullData(decoderStatistics.getAudioDecodedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PLAYED_FRAMES.getName(), checkForNullData(decoderStatistics.getAudioPlayedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SKIPPED_FRAMES.getName(), checkForNullData(decoderStatistics.getAudioSkippedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIR_MODE.getName(), checkForNullData(audioPairs.getAudioPairMode()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.COMPRESSION.getName(), checkForNullData(audioPairs.getCompression()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BITRATE.getName(), checkForNullData(audioPairs.getBitrate()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AV_SYNC_MS.getName(), checkForNullData(audioPairs.getAvSyncMs()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DISCONTINUITIES.getName(), checkForNullData(audioPairs.getDiscontinuities().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DECODE_ERRORS.getName(), checkForNullData(audioPairs.getDecodeErrors()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.OUTPUT_ERRORS.getName(), checkForNullData(audioPairs.getOutputErrors()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SAMPE_RATE_IN.getName(), checkForNullData(audioPairs.getSampeRateIn().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SAMPE_RATE_OUT.getName(), checkForNullData(audioPairs.getSampeRateOut().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_TRACKING_MODE.getName(), checkForNullData(decoderStatistics.getClockTrackingMode()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STATUS.getName(), checkForNullData(decoderStatistics.getClockStatus()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_RE_SYNC_COUNT.getName(), checkForNullData(decoderStatistics.getClockReSyncCount().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_CURRENT_STC.getName(), checkForNullData(decoderStatistics.getClockCurrentStc()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STC_AVG.getName(), checkForNullData(decoderStatistics.getClockStcAvg()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE_IN.getName(), checkForNullData(decoderStatistics.getHdrTypeIn()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE.getName(), checkForNullData(decoderStatistics.getHdrType()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_COLOUR_PRIMARIES.getName(), checkForNullData(decoderStatistics.getHdrColourPrimaries()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TRANSFER_CHARACTERISTICS.getName(), checkForNullData(decoderStatistics.getHdrTransferCharacteristics()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_MATRIX_COEFFICIENTS.getName(), checkForNullData(decoderStatistics.getHdrMatrixCoefficients()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_RECEIVED_PACKETS.getName(), checkForNullData(decoderStatistics.getTcReceivedPackets()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_OUTPUT_PACKETS.getName(), checkForNullData(decoderStatistics.getTcOutputPackets()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_FREED_PACKETS.getName(), checkForNullData(decoderStatistics.getTcFreedPackets()));
				} else {
					contributeNoneValueForDecoderStatistics(stats, decoderID);
					updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
				}
			} else {
				contributeNoneValueForDecoderStatistics(stats, decoderID);
				updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
			}
		} catch (Exception e) {
			contributeNoneValueForDecoderStatistics(stats, decoderID);
			updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
		}
	}

	/**
	 * Value of list statistics property of stream is NONE
	 *
	 * @param stats list statistics property
	 */
	private void contributeNoneValueForStreamStatistics(Map<String, String> stats, Integer streamID) {
	}
}
