/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import static com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant.HASH;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderURL;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.SRT;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.Stream;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.StreamData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.StreamInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.StreamStats.StreamStats;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.AudioPair;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderStats.DecoderStats;
import com.avispl.symphony.dal.communicator.RestCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

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
	private ObjectMapper objectMapper;

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
		Map<String, String> dynamicStats = new HashMap<>();
		failedMonitor = new HashMap<>();
		authenticationCookie = initAuthenticationCookie();

		populateDecoderMonitoringMetrics(stats, dynamicStats);
		extendedStatistics.setStatistics(stats);
		extendedStatistics.setDynamicStatistics(dynamicStats);

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

		String sessionID = authenticationCookie.getSessionID();
		if (sessionID != null && !sessionID.equals(DecoderConstant.AUTHORIZED)) {
			headers.set("Cookie", authenticationCookie.getSessionID());
		}
		return super.putExtraRequestHeaders(httpMethod, uri, headers);
	}

	/**
	 * Init instance of Authentication Cookie
	 *
	 * @return AuthenticationCookie
	 */
	protected AuthenticationCookie initAuthenticationCookie() {
		return new AuthenticationCookie();
	}

	/**
	 * @param path url of the request
	 * @return String full path of the device
	 */
	private String buildDeviceFullPath(String path) {
		Objects.requireNonNull(path);

		return DecoderConstant.HTTPS
				+ getHost()
				+ path;
	}

	/**
	 * This method is used to retrieve Authentication Cookie (SessionID) by send POST request to http://{IP_Address}/apis/authentication
	 *
	 * When there is no Session ID data or having an Exception, The Session ID of Authentication Cookie is going to set with null value
	 */
	private void retrieveSessionFromDecoder() {
		String login = getLogin();
		String password = getPassword();

		ObjectNode request = JsonNodeFactory.instance.objectNode();
		request.put(DecoderConstant.USERNAME, login);
		request.put(DecoderConstant.PASSWORD, password);

		try {
			if (this.authenticationCookie.getSessionID() == null) {
				HttpClient httpClient = this.obtainHttpClient(true);

				HttpPost httppost = new HttpPost(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.AUTHENTICATION));
				httppost.setHeader("Content-Type", "text/xml");
				httppost.setHeader("Content-Type", "application/json");
				StringEntity entity = new StringEntity(request.toString());
				httppost.setEntity(entity);
				HttpResponse response = null;

				// Get SessionID
				try {
					response = httpClient.execute(httppost);
				} finally {
					if (response instanceof CloseableHttpResponse) {
						((CloseableHttpResponse) response).close();
					}
				}
				Header headerResponse = response.getFirstHeader(DecoderConstant.SESSION_ID);

				if (headerResponse.getValue() != null) {
					authenticationCookie.setSessionID(headerResponse.getValue());
				} else {
					throw new ResourceNotReachableException(DecoderConstant.GETTING_SESSION_ID_ERR);
				}
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.GETTING_SESSION_ID_ERR);
		}
	}

	/**
	 * @param value value of device info
	 * @return String (none/value)
	 */
	private String checkForNullData(String value) {
		return value == null || value.equals("") ? DecoderConstant.NONE : value;
	}

	/**
	 * Update failedMonitor with Getting device info error message
	 *
	 * @param failedMonitor list statistics property
	 */
	private void updateDecoderStatisticsFailedMonitor(Map<String, String> failedMonitor, Integer decoderID) {
		failedMonitor.put(MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID, DecoderConstant.GETTING_DECODER_STATS_ERR + decoderID);
	}
	/**
	 * Format time data
	 *
	 * @param time the time is String
	 * @return String
	 */
	private String formatTimeData(String time) {
		if (DecoderConstant.NONE.equals(time)) {
			return DecoderConstant.NONE;
		}
		int index = time.indexOf("s");
		if (index > -1) {
			time = time.substring(0, index + 1);
		}
		int indexDay = time.indexOf("d");
		int indexHour = time.indexOf("h");
		int indexMinute = time.indexOf("m");
		StringBuilder stringBuilder = new StringBuilder();
		if (indexDay > -1) {
			stringBuilder.append(time, 0, indexDay);
			stringBuilder.append(DecoderConstant.DAY);
			stringBuilder.append(time, indexDay + 1, indexHour);
			stringBuilder.append(DecoderConstant.HOUR);
			stringBuilder.append(time, indexHour + 1, indexMinute);
			stringBuilder.append(DecoderConstant.MINUTE);
			stringBuilder.append(time, indexMinute + 1, index);
			stringBuilder.append(DecoderConstant.SECOND);
		} else if (indexHour > -1) {
			stringBuilder.append(time, 0, indexHour);
			stringBuilder.append(DecoderConstant.HOUR);
			stringBuilder.append(time, indexHour + 1, indexMinute);
			stringBuilder.append(DecoderConstant.MINUTE);
			stringBuilder.append(time, indexMinute + 1, index);
			stringBuilder.append(DecoderConstant.SECOND);
		} else if (indexMinute > -1) {
			stringBuilder.append(time, 0, indexMinute);
			stringBuilder.append(DecoderConstant.MINUTE);
			stringBuilder.append(time, indexMinute + 1, index);
			stringBuilder.append(DecoderConstant.SECOND);
		} else {
			stringBuilder.append(time, 0, index);
			stringBuilder.append(DecoderConstant.SECOND);
		}
		return String.valueOf(stringBuilder);
	}
	/**
	 * This method is used to retrieve decoder statistic by send GET request to http://{IP_Address}/apis/decoders/:id
	 *
	 * @param stats list statistics property
	 *
	 * When sessionID is null, the failedMonitor is going to update
	 * When there is no response data, the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveDecoderStats(Map<String, String> stats, Map<String, String> dynamicStats, Integer decoderID) {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				JsonNode response = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID), JsonNode.class);
				if (!response.isEmpty()) {
					objectMapper = new ObjectMapper();
					DecoderData decoderData = objectMapper.readValue(response.toString(), DecoderData.class);
					DecoderStats decoderStats = decoderData.getDecoderStats();
					DecoderInfo decoderInfo = decoderData.getDecoderInfo();
					List<AudioPair> audioPairs = decoderStats.getAudioPairs();

					String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + DecoderConstant.SPACE + decoderID + HASH;

					// Update static stats
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DECODER_ID.getName(), checkForNullData(decoderStats.getDecoderID().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STATE.getName(), checkForNullData(decoderStats.getState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.UPTIME.getName(), checkForNullData(formatTimeData(decoderStats.getUptime())));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.OVERSUBSCRIBED_FRAMES.getName(), checkForNullData(decoderStats.getOversubscribedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_STATE.getName(), checkForNullData(decoderStats.getBufferingState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_MODE.getName(), checkForNullData(decoderStats.getBufferingMode()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_ADJUSTMENTS.getName(), checkForNullData(decoderStats.getBufferingAdjustments()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LATENCY.getName(), checkForNullData(decoderStats.getVideoLatency()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STC_TO_PCR_LEAD_TIME.getName(), checkForNullData(decoderStats.getStcToPcrLeadTime()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_ALGORITHM.getName(), checkForNullData(decoderStats.getVideoAlgorithm()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_PROFILE.getName(), checkForNullData(decoderStats.getVideoProfile()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_LEVEL.getName(), checkForNullData(decoderStats.getVideoLevel()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_OUTPUT_FORMAT.getName(), checkForNullData(decoderStats.getVideoOutputFormat()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_FRAMING.getName(), checkForNullData(decoderStats.getVideoFraming()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_SLICES_PER_FRAME.getName(), checkForNullData(decoderStats.getVideoSlicesPerFrame()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_INPUT_FRAME_RATE.getName(), checkForNullData(decoderStats.getVideoInputFrameRate()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.STILL_IMAGE.getName(), checkForNullData(decoderStats.getStillImage()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DECODER_STATE.getName(), checkForNullData(decoderStats.getVideoDecoderState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.VIDEO_DISPLAY_FORMAT.getName(), checkForNullData(decoderStats.getVideoDisplayFormat()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.LOAD_PERCENTAGE.getName(), checkForNullData(decoderStats.getLoadPercentage().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.PREPROCESSOR_STATE.getName(), checkForNullData(decoderStats.getPreprocessorState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TROUBLE_CODE.getName(), checkForNullData(decoderStats.getTroubleCode().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.DISPLAYED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStats.getDisplayedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.SKIPPED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStats.getSkippedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.REPLAYED_OUTPUT_FRAMES.getName(), checkForNullData(decoderStats.getReplayedOutputFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_STATE.getName(), checkForNullData(decoderStats.getAudioState()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SAMPLE_RATE.getName(), checkForNullData(decoderStats.getAudioSampleRate()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIRS_AMOUNT.getName(), checkForNullData(decoderStats.getAudioPairsAmount().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_DECODED_FRAMES.getName(), checkForNullData(decoderStats.getAudioDecodedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PLAYED_FRAMES.getName(), checkForNullData(decoderStats.getAudioPlayedFrames()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_SKIPPED_FRAMES.getName(), checkForNullData(decoderStats.getAudioSkippedFrames()));

					if (audioPairs != null) {
						for (int i = 1; i < audioPairs.size(); i++) {
							String audioPair = decoderStatisticGroup + DecoderMonitoringMetric.AUDIO_PAIR + i + DecoderConstant.COLON;
							stats.put(audioPair + DecoderMonitoringMetric.AUDIO_PAIR_MODE.getName(), checkForNullData(audioPairs.get(i).getAudioPairMode()));
							stats.put(audioPair + DecoderMonitoringMetric.COMPRESSION.getName(), checkForNullData(audioPairs.get(i).getCompression()));
							stats.put(audioPair + DecoderMonitoringMetric.BITRATE.getName(), checkForNullData(audioPairs.get(i).getBitrate()));
							stats.put(audioPair + DecoderMonitoringMetric.AV_SYNC_MS.getName(), checkForNullData(audioPairs.get(i).getAvSyncMs()));
							stats.put(audioPair + DecoderMonitoringMetric.DISCONTINUITIES.getName(), checkForNullData(audioPairs.get(i).getDiscontinuities().toString()));
							stats.put(audioPair + DecoderMonitoringMetric.DECODE_ERRORS.getName(), checkForNullData(audioPairs.get(i).getDecodeErrors()));
							stats.put(audioPair + DecoderMonitoringMetric.OUTPUT_ERRORS.getName(), checkForNullData(audioPairs.get(i).getOutputErrors()));
							stats.put(audioPair + DecoderMonitoringMetric.SAMPE_RATE_IN.getName(), checkForNullData(audioPairs.get(i).getSampeRateIn().toString()));
							stats.put(audioPair + DecoderMonitoringMetric.SAMPE_RATE_OUT.getName(), checkForNullData(audioPairs.get(i).getSampeRateOut().toString()));
						}
					}
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_TRACKING_MODE.getName(), checkForNullData(decoderStats.getClockTrackingMode()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STATUS.getName(), checkForNullData(decoderStats.getClockStatus()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_RE_SYNC_COUNT.getName(), checkForNullData(decoderStats.getClockReSyncCount().toString()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_CURRENT_STC.getName(), checkForNullData(decoderStats.getClockCurrentStc()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.CLOCK_STC_AVG.getName(), checkForNullData(decoderStats.getClockStcAvg()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE_IN.getName(), checkForNullData(decoderStats.getHdrTypeIn()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TYPE.getName(), checkForNullData(decoderStats.getHdrType()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_COLOUR_PRIMARIES.getName(), checkForNullData(decoderStats.getHdrColourPrimaries()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_TRANSFER_CHARACTERISTICS.getName(), checkForNullData(decoderStats.getHdrTransferCharacteristics()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.HDR_MATRIX_COEFFICIENTS.getName(), checkForNullData(decoderStats.getHdrMatrixCoefficients()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_RECEIVED_PACKETS.getName(), checkForNullData(decoderStats.getTcReceivedPackets()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_OUTPUT_PACKETS.getName(), checkForNullData(decoderStats.getTcOutputPackets()));
					stats.put(decoderStatisticGroup + DecoderMonitoringMetric.TC_FREED_PACKETS.getName(), checkForNullData(decoderStats.getTcFreedPackets()));
				
//					// Update dynamic stats
					dynamicStats.put(decoderStatisticGroup + DecoderMonitoringMetric.BUFFERING_DELAY.getName(), checkForNullData(decoderInfo.getBufferingDelay()));
					dynamicStats.put(decoderStatisticGroup + DecoderMonitoringMetric.LATENCY.getName(), checkForNullData(decoderInfo.getLatency()));

				} else {
					updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
				}
			} else {
				updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
			}
		} catch (Exception e) {
			updateDecoderStatisticsFailedMonitor(failedMonitor, decoderID);
		}
	}

	/**
	 * Update failedMonitor with Getting stream statistics error message
	 *
	 * @param failedMonitor list statistics property
	 */
	private void updateStreamStatisticsFailedMonitor(Map<String, String> failedMonitor) {
		failedMonitor.put(MonitoringMetricGroup.STREAM_STATISTICS.getName(), DecoderConstant.GETTING_STREAM_STATS_ERR);
	}

	/**
	 * This method is used to retrieve streams statistics by send GET request to http://{IP_Address}apis/streams
	 *
	 * @param stats list statistics property
	 *
	 * When sessionID is null, the failedMonitor is going to update
	 * When there is no response data, the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveStreamStats(Map<String, String> stats, Map<String, String> dynamicStats) {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				JsonNode response = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.STREAMS), JsonNode.class);

				if (!response.isEmpty()) {
					objectMapper = new ObjectMapper();
					StreamData streamData = objectMapper.readValue(response.toString(), StreamData.class);
					List<Stream> streams = streamData.getStreams();

					for (Stream stream : streams) {
						StreamInfo streamInfo = stream.getStreamInfo();
						String streamID = streamInfo.getId();
						StreamStats streamStats = stream.getStreamStats();
						SRT srt = streamStats.getSrt();

						String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + DecoderConstant.SPACE + streamInfo.getName() + HASH;

						// Update static stats
						stats.put(streamStatisticGroup + StreamMonitoringMetric.ID.getName(), checkForNullData(streamID));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.NAME.getName(), checkForNullData(streamInfo.getName()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.ENCAPSULATION.getName(), checkForNullData(streamStats.getEncapsulation()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.DECODER_ID.getName(), checkForNullData(streamStats.getDecoderId()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.STATE.getName(), checkForNullData(streamStats.getState()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.SOURCE_ADDRESS.getName(), checkForNullData(streamStats.getSourceAddress()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.BIT_RATE.getName(), checkForNullData(streamStats.getBitRate()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.CONNECTIONS.getName(), checkForNullData(streamStats.getConnections()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.RECEIVED_PACKET.getName(), checkForNullData(streamStats.getReceivedPacket()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.RECEIVED_BYTES.getName(), checkForNullData(streamStats.getReceivedBytes()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.LAST_RECEIVED.getName(), checkForNullData(streamStats.getLastReceived()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.OUTPUT_PACKETS.getName(), checkForNullData(streamStats.getOutputPackets()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.OUTPUT_BYTES.getName(), checkForNullData(streamStats.getOutputBytes()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.PROGRAM_NUMBER.getName(), checkForNullData(streamStats.getProgramNumber()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.PCR_PID.getName(), checkForNullData(streamStats.getPcrPid()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.STREAM_SUMMARY.getName(), checkForNullData(streamStats.getStreamSummary()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.DROPPED_PACKETS.getName(), checkForNullData(streamStats.getDroppedPackets()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.CORRUPTED_FRAMES.getName(), checkForNullData(streamStats.getCorruptedFrames()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.RESTARTS.getName(), checkForNullData(streamStats.getRestarts()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.LOCAL_PORT.getName(), checkForNullData(streamStats.getLocalPort()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.REMOTE_PORT.getName(), checkForNullData(streamStats.getRemotePort()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.RECEIVED_ERRO.getName(), checkForNullData(streamStats.getReceivedErrors()));
						stats.put(streamStatisticGroup + StreamMonitoringMetric.DROPPED_PACKETS.getName(), checkForNullData(streamStats.getDroppedPackets()));

						if (srt != null) {
							stats.put(streamStatisticGroup + StreamMonitoringMetric.ENCRYPTION.getName(), checkForNullData(srt.getEncryption()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.KEY_LENGTH.getName(), checkForNullData(srt.getKeyLength()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.DECRYPT_STATE.getName(), checkForNullData(srt.getDecryptState()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.LOST_PACKETS.getName(), checkForNullData(srt.getLostPackets()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.SENT_ACKS.getName(), checkForNullData(srt.getSentAcks()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.SENT_NAKS.getName(), checkForNullData(srt.getSentNaks()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.PATH_MAX_BANDWIDTH.getName(), checkForNullData(srt.getPathMaxBandwidth()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.RTT.getName(), checkForNullData(srt.getRtt()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.BUFFER.getName(), checkForNullData(srt.getBuffer()));
							stats.put(streamStatisticGroup + StreamMonitoringMetric.SRT_LATENCY.getName(), checkForNullData(srt.getLatency()));
						}

						// Update dynamic stats
						dynamicStats.put(streamStatisticGroup + StreamMonitoringMetric.STREAM_LATENCY.getName(), checkForNullData(streamInfo.getLatency()));
					}
				} else {
					updateStreamStatisticsFailedMonitor(failedMonitor);
				}
			} else {
				updateStreamStatisticsFailedMonitor(failedMonitor);
			}
		} catch (Exception e) {
			updateStreamStatisticsFailedMonitor(failedMonitor);
		}
	}


	/**
	 * Counting metric group is failed to monitor
	 *
	 * @return number failed monitoring metric group in the metric
	 */
	private int getNoOfFailedMonitorMetricGroup() {
		int noOfFailedMonitorMetric = 3;
		for (MonitoringMetricGroup metric : MonitoringMetricGroup.values()) {
			if (metric.isFailedMonitorCheck()) {
				noOfFailedMonitorMetric++;
			}
		}
		return noOfFailedMonitorMetric;
	}

	/**
	 * This method is used to populate all monitoring properties:
	 * <li>Decoders statistic</li>
	 * <li>Streams statistic</li>
	 *
	 * @param stats list statistic property
	 * @throws ResourceNotReachableException when failedMonitor said all device monitoring data are failed to get
	 */
	private void populateDecoderMonitoringMetrics(Map<String, String> stats, Map<String, String> dynamicStats) {
		Objects.requireNonNull(stats);

		if (!StringUtils.isNullOrEmpty(getPassword()) && !StringUtils.isNullOrEmpty(getLogin())) {
			retrieveSessionFromDecoder();
		} else {
			this.authenticationCookie.setSessionID(DecoderConstant.AUTHORIZED);
		}
		// retrieving all decoders stats
		for (int decoderID = 0; decoderID < 4; decoderID++) {
			retrieveDecoderStats(stats, dynamicStats, decoderID);
		}
		//retrieving all streams stats
		retrieveStreamStats(stats, dynamicStats);

		if (failedMonitor.size() == getNoOfFailedMonitorMetricGroup()) {
			authenticationCookie.setSessionID(null);
			StringBuilder errBuilder = new StringBuilder();
			for (Map.Entry<String, String> failedMetric : failedMonitor.entrySet()) {
				errBuilder.append(failedMetric.getValue());
				errBuilder.append(DecoderConstant.NEXT_LINE);
			}
			throw new ResourceNotReachableException(errBuilder.toString());
		}
	}
}
