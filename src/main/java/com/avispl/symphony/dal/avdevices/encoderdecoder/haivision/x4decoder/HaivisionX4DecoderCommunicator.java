/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.ControllingMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderURL;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.BufferingMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.DecoderControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.HDR;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.OutputFrameRate;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.QuadMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.State;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.StillImage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.AudioPairMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.SRTMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.SourceMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.AudioPair;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderStats;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.deviceinfo.DeviceInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.SRT;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Source;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Stream;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamStats;
import com.avispl.symphony.dal.communicator.RestCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * An implementation of RestCommunicator to provide communication and interaction with Haivision X4 Decoders
 * Supported features are:
 * <p>
 * Monitoring:
 * <li>DeviceInfo</li>
 * <li>DecoderStats</li>
 * <li>StreamStats</li>
 * <p>
 * Controlling:
 * <li>Start/Stop /Edit Decoder</li>
 * <li>Create/ Edit/ Delete Stream</li>
 *
 * @author Harry
 * @since 1.0.0
 */
public class HaivisionX4DecoderCommunicator extends RestCommunicator implements Monitorable, Controller {

	private AuthenticationCookie authenticationCookie;
	private AuthenticationInfo authenticationInfo;
	private HashMap<String, String> failedMonitor;
	private Set<String> streamNameSet;
	private Set<String> streamStatusSet;
	private Set<String> portNumberSet;
	private boolean isEmergencyCall = false;

	private boolean isEmergencyDelivery = false;
	private ExtendedStatistics localExtendedStatistics;

	// Decoder and stream DTO
	private Map<String, DecoderData> decoderDataDTOList;
	private Map<String, Stream> streamDataDTOList;
	private Map<String, DecoderData> localDecoderDataList;
	private Map<String, Stream> localStreamDataList;

	//Adapter Properties
	private String streamName;
	private String portNumber;
	private String streamStatus;

	/**
	 * Retrieves {@code {@link #streamName }}
	 *
	 * @return value of {@link #streamName}
	 */
	public String getStreamName() {
		return streamName;
	}

	/**
	 * Sets {@code streamsName}
	 *
	 * @param streamsName the {@code java.lang.String} field
	 */
	public void setStreamName(String streamsName) {
		this.streamName = streamsName;
	}

	/**
	 * Retrieves {@code {@link #portNumber}}
	 *
	 * @return value of {@link #portNumber}
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * Sets {@code portNumber}
	 *
	 * @param portNumber the {@code java.lang.String} field
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Retrieves {@code {@link #streamStatus}}
	 *
	 * @return value of {@link #streamStatus}
	 */
	public String getStreamStatus() {
		return streamStatus;
	}

	/**
	 * Sets {@code streamStatus}
	 *
	 * @param streamStatus the {@code java.lang.String} field
	 */
	public void setStreamStatus(String streamStatus) {
		this.streamStatus = streamStatus;
	}

	/**
	 * This method is called by Symphony to control the properties in the device
	 *
	 * @param controllableProperty ControllableProperty instance
	 */
	@Override
	public void controlProperty(ControllableProperty controllableProperty) {
		String property = controllableProperty.getProperty();
		String value = String.valueOf(controllableProperty.getValue());
		Map<String, String> stats = this.localExtendedStatistics.getStatistics();
		List<AdvancedControllableProperty> advancedControllableProperties = this.localExtendedStatistics.getControllableProperties();

		if (this.logger.isDebugEnabled()) {
			this.logger.debug("controlProperty property " + property);
			this.logger.debug("controlProperty value " + value);
		}
		// Decoder control
		String[] splitProperty = property.split(String.valueOf(DecoderConstant.HASH));
		ControllingMetricGroup controllingGroup = ControllingMetricGroup.getByName(splitProperty[0]);

		switch (controllingGroup) {
			case DECODER:
				String name = splitProperty[0].substring(8);
				decoderControl(stats, advancedControllableProperties, name, splitProperty[1], value);
				break;
			case STREAM:
//				handleStreamingControl(property, value, splitProperty[1], splitProperty[0]);
				break;
			case CREATE_STREAM:
//				if (localCreateStreamExtendedStatistics != null) {
//					Map<String, String> localCreateStreamStats = localCreateStreamExtendedStatistics.getStatistics();
//					List<AdvancedControllableProperty> localCreateStreamControls = localCreateStreamExtendedStatistics.getControllableProperties();
//					handleStreamingControl(property, value, splitProperty[1],localCreateStreamStats, localCreateStreamControls, controllingGroup.getName());
//					localCreateStreamExtendedStatistics.setStatistics(localCreateStreamStats);
//					localCreateStreamExtendedStatistics.setControllableProperties(localCreateStreamControls);
//				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + controllingGroup);
		}
	}

	/**
	 * This method is called by Symphony to control the properties in the device
	 *
	 * @param list the list ControllableProperty instance
	 */
	@Override
	public void controlProperties(List<ControllableProperty> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new IllegalArgumentException("NetGearCommunicator: Controllable properties cannot be null or empty");
		}
		for (ControllableProperty controllableProperty : list) {
			controlProperty(controllableProperty);
		}
	}

	/**
	 * This method is called by Symphony to get the list of statistics to be displayed
	 *
	 * @return List<Statistics> This return the list of statistics
	 */
	@Override
	public List<Statistics> getMultipleStatistics() {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Getting statistics from the device X4 decoder at host %s with port %s", this.host, this.getPort()));
		}
		final ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		final Map<String, String> stats = new HashMap<>();
		final List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();
		failedMonitor = new HashMap<>();

		if (localExtendedStatistics == null) {
			localExtendedStatistics = new ExtendedStatistics();
		}
		if (authenticationCookie == null) {
			authenticationCookie = initAuthenticationCookie();
		}
		if (decoderDataDTOList == null) {
			decoderDataDTOList = new HashMap<>();
		}
		if (streamDataDTOList == null) {
			streamDataDTOList = new HashMap<>();
		}

		if (!isEmergencyDelivery) {
			populateDecoderMonitoringMetrics(stats);
			if (isEmergencyCall || localDecoderDataList == null) {
				localDecoderDataList = new HashMap<>();
				localDecoderDataList.putAll(decoderDataDTOList);

				if (isEmergencyCall || localStreamDataList == null) {
					localStreamDataList = new HashMap<>();
					localStreamDataList.putAll(streamDataDTOList);
				}
				isEmergencyCall = false;
			}
			// check Role is Admin or Operator
			String role = authenticationInfo.getAuthenticationRole().getRole();
			if (role.equals(DecoderConstant.OPERATOR_ROLE) || role.equals(DecoderConstant.ADMIN_ROLE)) {
				populateControllingMetrics(stats, advancedControllableProperties);
				extendedStatistics.setControllableProperties(advancedControllableProperties);
			}
			extendedStatistics.setStatistics(stats);
			localExtendedStatistics = extendedStatistics;
			isEmergencyDelivery = false;
		}
		return Collections.singletonList(localExtendedStatistics);
	}

	@Override
	protected void authenticate() {
		// The device has its own authentication behavior, do not use the common one
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
			headers.set(DecoderConstant.COOKIE, authenticationCookie.getSessionID());
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
		ObjectNode request = JsonNodeFactory.instance.objectNode();
		request.put(DecoderConstant.USERNAME, getLogin());
		request.put(DecoderConstant.PASSWORD, getPassword());

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
	 * This method is used to retrieve User Role by send GET request to http://{IP_Address}/apis/accounts/{username}
	 *
	 * @throws ResourceNotReachableException When there is no valid User Role data or having an Exception
	 */
	private void retrieveUserRoleBasedFromDecoder() {
		String login = getLogin();
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				 authenticationInfo = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.ROLE_BASED + DecoderConstant.SLASH + login), AuthenticationInfo.class);
				if (authenticationInfo != null) {
					if (this.authenticationInfo.getAuthenticationRole() != null) {
						if (this.authenticationInfo.getAuthenticationRole().getRole() == null) {
							throw new ResourceNotReachableException(DecoderConstant.ROLE_BASED_ERR);
						}
					} else {
						throw new ResourceNotReachableException(DecoderConstant.ROLE_BASED_ERR);
					}
				} else {
					throw new ResourceNotReachableException(DecoderConstant.ROLE_BASED_ERR);
				}
			} else {
				throw new ResourceNotReachableException(DecoderConstant.ROLE_BASED_ERR);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.ROLE_BASED_ERR,e);
		}
	}

	/**
	 * check for null data
	 *
	 * @param value value of monitoring properties
	 * @return String (none/value)
	 */
	private String checkForNullData(String value) {
		return value == null || value.equals(DecoderConstant.EMPTY) ? DecoderConstant.NONE : value;
	}

	/**
	 * Update failedMonitor with getting device info error message
	 *
	 * @param failedMonitor list statistics property
	 */
	private void updateDeviceInfoFailedMonitor(Map<String, String> failedMonitor) {
		failedMonitor.put(MonitoringMetricGroup.DEVICE_INFO.getName(), DecoderConstant.GETTING_DEVICE_INFO_ERR);
	}

	/**
	 * This method is used to retrieve device info by send GET request to http://{IP_Address}/apis/status
	 *
	 * @param stats list statistics property
	 *
	 * When sessionID is null, the failedMonitor is going to update
	 * When there is no response data, the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveDeviceInfo(Map<String, String> stats) {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				DeviceInfo deviceInfo = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DEVICE_INFO), DeviceInfo.class);
				if (deviceInfo != null) {
					String deviceInfoGroup = MonitoringMetricGroup.DEVICE_INFO.getName() + DecoderConstant.HASH;

					stats.put(deviceInfoGroup + DeviceInfoMetric.SERIAL_NUMBER.getName(), checkForNullData(deviceInfo.getSerialNumber()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.CARD_STATUS.getName(), checkForNullData(deviceInfo.getCardStatus()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.HARDWARE_COMPATIBILITY.getName(), checkForNullData(deviceInfo.getHardwareCompatibility()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.MEZZANINE_PRESENT.getName(), checkForNullData(deviceInfo.getMezzaninePresent()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.HARDWARE_REVISION.getName(), checkForNullData(deviceInfo.getHardwareRevision()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.CPLD_REVISION.getName(), checkForNullData(deviceInfo.getCpldRevision()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.BOOT_VERSION.getName(), checkForNullData(deviceInfo.getBootVersion()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.CARD_TYPE.getName(), checkForNullData(deviceInfo.getCardType()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_DATE.getName(), checkForNullData(deviceInfo.getFirmwareDate()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_VERSION.getName(), checkForNullData(deviceInfo.getFirmwareVersion()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_OPTIONS.getName(), checkForNullData(deviceInfo.getFirmwareOptions()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.UPTIME.getName(), checkForNullData(deviceInfo.getUptime()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.PART_NUMBER.getName(), checkForNullData(deviceInfo.getPartNumber()));
					stats.put(deviceInfoGroup + DeviceInfoMetric.TEMPERATURE.getName(), checkForNullData(deviceInfo.getTemperature()));

				} else {
					updateDeviceInfoFailedMonitor(failedMonitor);
				}
			} else {
				updateDeviceInfoFailedMonitor(failedMonitor);
			}
		} catch (Exception e) {
			updateDeviceInfoFailedMonitor(failedMonitor);
		}
	}

	/**
	 * Update failedMonitor with Getting decoder stats error message
	 *
	 * @param failedMonitor list statistics property
	 * @param decoderID ID of the decoder
	 */
	private void updateDecoderStatisticsFailedMonitor(Map<String, String> failedMonitor, Integer decoderID) {
		failedMonitor.put(MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID, DecoderConstant.GETTING_DECODER_STATS_ERR + decoderID);
	}

	/**
	 * This method is used update decoder statistic from DTO
	 *
	 * @param stats list statistics property
	 * @param decoderData pair of decoder config and stats
	 * @param decoderID ID of decoder
	 */
	private void populateDecoderStats(Map<String, String> stats, DecoderData decoderData, Integer decoderID) {
		DecoderStats decoderStats = decoderData.getDecoderStats();
		List<AudioPair> audioPairs = decoderStats.getAudioPairs();

		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID + DecoderConstant.HASH;

		for (DecoderMonitoringMetric item : DecoderMonitoringMetric.values()) {
			stats.put(decoderStatisticGroup + item.getName(), checkForNullData(decoderData.getValueByDecoderMonitoringMetric(item)));
		}
		if (audioPairs != null) {
			for (int i = 0; i < audioPairs.size(); i++) {
				String audioPair = decoderStatisticGroup + DecoderConstant.AUDIO_PAIR + i + DecoderConstant.COLON;
				for (AudioPairMetric item : AudioPairMetric.values()) {
					stats.put(audioPair + item.getName(), checkForNullData(audioPairs.get(i).getValueByAudioPairMetric(item)));
				}
			}
		}
		if (!isEmergencyCall) {
			this.decoderDataDTOList.put(decoderID.toString(), decoderData);
		}
		if (!this.decoderDataDTOList.get(decoderID.toString()).equals(decoderData)) {
			this.decoderDataDTOList.put(decoderID.toString(), decoderData);
			this.isEmergencyCall = true;
		}
	}

	/**
	 * This method is used to retrieve decoder statistic by send GET request to http://{IP_Address}/apis/decoders/:id
	 *
	 * @param stats list statistics property
	 * @param decoderID ID of the decoder
	 *
	 * When sessionID is null, the failedMonitor is going to update
	 * When there is no response data, the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveDecoderStats(Map<String, String> stats, Integer decoderID) {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				DecoderData decoderData = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID), DecoderData.class);
				if (decoderData != null) {
					populateDecoderStats(stats, decoderData, decoderID);
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
	 * This method is used update stream statistic from DTO
	 *
	 * @param stats list statistics property
	 * @param stream pair of stream config and stats
	 */
	private void populateStreamStats(Map<String, String> stats, Stream stream) {
		SRT srt = stream.getStreamStats().getSrt();
		List<Source> sources = stream.getStreamStats().getSources();
		Integer streamId = stream.getStreamInfo().getId();
		String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + stream.getStreamInfo().getName() + DecoderConstant.HASH;

		for (StreamMonitoringMetric item : StreamMonitoringMetric.values()) {
			stats.put(streamStatisticGroup + item.getName(), checkForNullData(stream.getValueByStreamMonitoringMetric(item)));
		}

		if (stream.getStreamStats().getSrt() != null) {
			for (SRTMetric item : SRTMetric.values()) {
				stats.put(streamStatisticGroup + item.getName(), checkForNullData(srt.getValueBySRTMonitoringMetric(item)));
			}
		}

		if (sources != null) {
			for (Source source : sources) {
				for (SourceMetric item : SourceMetric.values()) {
					stats.put(streamStatisticGroup + source.getName() + item.getName(), checkForNullData(source.getValueBySourceMetric(item)));
				}
			}
		}

		if (!isEmergencyCall) {
			this.streamDataDTOList.put(streamId.toString(), stream);
		}
		if (!stream.equals(this.streamDataDTOList.get(streamId.toString()))) {
			this.streamDataDTOList.put(streamId.toString(), stream);
			this.isEmergencyCall = true;
		}
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
	private void retrieveStreamStats(Map<String, String> stats) {

		// Retrieve Adapter Properties
		if (this.streamName != null && streamNameSet == null) {
			streamNameSet = handleAdapterPropertiesInputFromUser(this.streamName);
		}
		if (this.streamStatus != null && streamStatusSet == null) {
			streamStatusSet = handleAdapterPropertiesInputFromUser(this.streamStatus);
		}
		if (this.portNumber != null && portNumberSet == null) {
			portNumberSet = handleAdapterPropertiesInputFromUser(this.portNumber);
		}

		try {
			if (this.authenticationCookie.getSessionID() != null) {
				StreamData streamData = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.STREAMS), StreamData.class);

				if (streamData != null) {
					List<Stream> streams = streamData.getStreams();

					for (Stream stream : streams) {
						StreamInfo streamInfo = stream.getStreamInfo();
						StreamStats streamStats = stream.getStreamStats();

						// Stream name filtering
						if (this.streamName != null && streamNameSet.contains(streamInfo.getName())) {
							populateStreamStats(stats, stream);
							continue;
						}

						// Stream status filtering
						if (this.streamStatus != null && !streamStatusSet.contains(streamStats.getState())) {
							continue;
						}

						// Port number filtering
						if (this.portNumber != null) {
							Integer port = streamInfo.getPort();
							boolean isValidPort = handleAdapterPortRangeFromUser(port);
							if (!isValidPort) {
								continue;
							}
						}
						populateStreamStats(stats, stream);
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
		noOfFailedMonitorMetric += EnumSet.allOf(MonitoringMetricGroup.class).stream().count();
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
	private void populateDecoderMonitoringMetrics(Map<String, String> stats) {
		Objects.requireNonNull(stats);

		if (!StringUtils.isNullOrEmpty(getPassword()) && !StringUtils.isNullOrEmpty(getLogin())) {
			retrieveSessionFromDecoder();
			retrieveUserRoleBasedFromDecoder();
		} else {
			this.authenticationCookie.setSessionID(DecoderConstant.AUTHORIZED);
		}
		// Retrieving all device info
		retrieveDeviceInfo(stats);

		// Retrieving all decoders stats
		for (int decoderID = DecoderConstant.MIN_DECODER_ID; decoderID < DecoderConstant.MAX_DECODER_ID; decoderID++) {
			retrieveDecoderStats(stats, decoderID);
		}

		// Retrieving all streams stats
		retrieveStreamStats(stats);

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

	/**
	 * This method is used for populate all controlling properties:
	 * <li>Decoder controlling</li>
	 * <li>Stream controlling</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 */
	private void populateControllingMetrics(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {

			// Decoder control
			for (int decoderID = DecoderConstant.MIN_DECODER_ID; decoderID < DecoderConstant.MAX_DECODER_ID; decoderID++) {
				populateDecoderControl(stats, advancedControllableProperties, decoderID);
			}

			// Stream control
	}

	/**
	 * This method is used to handle  input from adapter properties and convert it to Set of String  for control
	 *
	 * @return Set<String> is the Set of String of filter element
	 */
	public Set<String> handleAdapterPropertiesInputFromUser(String input) {
		String[] listAdapterPropertyElement = input.split(DecoderConstant.COMMA);

		// Remove start and end spaces of each gain
		Set<String> setAdapterPropertiesElement = new HashSet<>();
		for (String adapterPropertyElement : listAdapterPropertyElement) {
			setAdapterPropertiesElement.add(adapterPropertyElement.trim());
		}
		return setAdapterPropertiesElement;
	}

	/**
	 * This method is used to handle  input from adapter properties (port, port range)
	 * When the input is an Integer value this method will check whether it is match with port from stream stats or not
	 * When the input is a range value this method will convert the range to min/ max port value and check whether it covers port from stream stats or not
	 *
	 * @return boolean the
	 */
	public boolean handleAdapterPortRangeFromUser(Integer portNumber) {
		int minPortNumber = 0;
		int maxPortNumber = 0;
		try {
			for (String portNumberFromAdapterProperties : portNumberSet) {

				// Port range filtering
				if (portNumberFromAdapterProperties.contains(DecoderConstant.DASH)) {
					String[] rangeList = portNumberFromAdapterProperties.split(DecoderConstant.DASH);
					minPortNumber = Integer.parseInt(rangeList[0]);
					maxPortNumber = Integer.parseInt(rangeList[1]);

					// Swapping if min value > max value
					if (minPortNumber > maxPortNumber) {
						int temp = minPortNumber;
						minPortNumber = maxPortNumber;
						maxPortNumber = temp;
					}
					if (portNumber >= minPortNumber && portNumber <= maxPortNumber) {
						portNumberSet.contains(portNumberFromAdapterProperties);
						return true;
					}

					// Port filtering
				} else if (portNumberFromAdapterProperties.equals(portNumber.toString())) {
					portNumberSet.contains(portNumberFromAdapterProperties);
					return true;
				}
			}
		} catch (NumberFormatException f) {
			throw new ResourceNotReachableException(DecoderConstant.PORT_NUMBER_ERROR);
		}

		return false;
	}

	//region Populate decoder control properties
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method is used for populate all Decoder control properties:
	 * Buffering Mode: Auto
	 * <li>Stream ID</li>
	 * <li>Still Image</li>
	 * <li>Still Image Delay</li>
	 * <li>Buffering Mode</li>
	 * <li>Hdr Dynamic Range</li>
	 * <li>Output 1</li>
	 * <li>Output 2</li>
	 * <li>Output 3</li>
	 * <li>Output 4</li>
	 * <li>Output Frame Rate</li>
	 * <li>Quad Mode</li>
	 *
	 * Buffering Mode: Fixed
	 * <li>Stream ID</li>
	 * <li>Still Image</li>
	 * <li>Still Image Delay</li>
	 * <li>Buffering Mode</li>
	 * <li>Buffering Delay</li>
	 * <li>Hdr Dynamic Range</li>
	 * <li>Output 1</li>
	 * <li>Output 2</li>
	 * <li>Output 3</li>
	 * <li>Output 4</li>
	 * <li>Output Frame Rate</li>
	 * <li>Quad Mode</li>
	 *
	 * Buffering Mode: MultiSync
	 * <li>Stream ID</li>
	 * <li>Still Image</li>
	 * <li>Still Image Delay</li>
	 * <li>Buffering Mode</li>
	 * <li>Multi Sync Buffering Delay</li>
	 * <li>Hdr Dynamic Range</li>
	 * <li>Output 1</li>
	 * <li>Output 2</li>
	 * <li>Output 3</li>
	 * <li>Output 4</li>
	 * <li>Output Frame Rate</li>
	 * <li>Quad Mode</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param decoderID ID of decoder
	 */
	private void populateDecoderControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, Integer decoderID) {
		// Get controllable property current value
		DecoderInfo decoderInfo = this.localDecoderDataList.get(decoderID.toString()).getDecoderInfo();
		HDR hdr = decoderInfo.getHdrDynamicRange();
		OutputFrameRate outputFrameRate = decoderInfo.getOutputFrameRate();
		QuadMode quadMode = decoderInfo.getQuadMode();
		StillImage stillImage = decoderInfo.getStillImage();
		String streamID = decoderInfo.getStreamId().toString();
		if (StringUtils.isNullOrEmpty(streamID)) {
			streamID = DecoderConstant.DEFAULT_STREAM_ID;
		}
		// Get list values of controllable property (dropdown)
		List<String> outputFrameRateList = OutputFrameRate.getOutputFrameRateList();
		List<String> quadModeList = QuadMode.getQuadModeList();
		List<String> stillImageList = StillImage.getStillImageList();
		List<String> hdrList = HDR.getHDRList();
		List<String> streamIDList = new LinkedList<>();

		if (this.streamDataDTOList != null) {
			streamIDList.addAll(this.streamDataDTOList.keySet());
		} else {
			streamIDList.add(DecoderConstant.DEFAULT_STREAM_ID);
		}

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;

		// Populate stream id dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STREAM_ID.getName(), streamIDList, streamID));

		// Populate HDR dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName(), hdrList, hdr.getName()));

		// Populate output frame rate dropdown list control
		advancedControllableProperties.add(
				createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_FRAME_RATE.getName(), outputFrameRateList, outputFrameRate.getName()));

		// Populate quad mode dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.QUAD_MODE.getName(), quadModeList, quadMode.getName()));

		// Populate still image dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE.getName(), stillImageList, stillImage.getName()));

		// Populate still image delay text control
		advancedControllableProperties.add(createText(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE_DELAY.getName(), decoderInfo.getStillImageDelay().toString()));

		// Populate output switch control
		advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_1.getName(), decoderInfo.getOutput1(),
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));
		advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_2.getName(), decoderInfo.getOutput2(),
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));
		advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_3.getName(), decoderInfo.getOutput3(),
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));
		advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_4.getName(), decoderInfo.getOutput4(),
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));

		// Populate Start/Stop switch control
		advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.STATE.getName(), decoderInfo.getState().isRunning(),
				DecoderConstant.OFF, DecoderConstant.ON));

		populateDecoderControlBufferingMode(stats, advancedControllableProperties, decoderInfo);
		populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
	}

	/**
	 * This method is used for populate all buffering mode of decoder control:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param decoderInfo set of decoder configuration
	 */
	private void populateDecoderControlBufferingMode(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, DecoderInfo decoderInfo) {
		// Get controllable property current value
		BufferingMode bufferingMode = decoderInfo.getBufferingMode();
		String decoderID = decoderInfo.getId();

		// Get list values of controllable property (dropdown)
		List<String> bufferingModeList = BufferingMode.getBufferingList();
		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;

		// remove unused keys
		stats.remove(decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName());
		stats.remove(decoderControllingGroup + DecoderControllingMetric.BUFFERING_DELAY.getName());
		stats.remove(decoderControllingGroup + DecoderControllingMetric.MULTI_SYNC_BUFFERING_DELAY.getName());
		switch (bufferingMode) {
			case AUTO:
				// Populate buffering mode dropdown list control
				advancedControllableProperties.add(
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));
				break;
			case FIXED:
				// Populate buffering mode dropdown list control
				advancedControllableProperties.add(
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));

				// Populate fixed delay text control
				advancedControllableProperties.add(createText(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_DELAY.getName(), decoderInfo.getBufferingDelay().toString()));
				break;
			case MULTI_SYNC:
				// Populate buffering mode dropdown list control
				advancedControllableProperties.add(
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));

				// Populate multi sync delay text control
				advancedControllableProperties.add(
						createText(stats, decoderControllingGroup + DecoderControllingMetric.MULTI_SYNC_BUFFERING_DELAY.getName(), decoderInfo.getMultisyncBufferingDelay().toString()));
				break;
		}
	}

	/**
	 * This method is used for populate apply change button and cancel button of decoder control:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param decoderID ID of decoder
	 */
	private void populateApplyChangeAndCancelButtonForDecoder(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, Integer decoderID) {
		DecoderInfo decoderInfo = this.localDecoderDataList.get(decoderID.toString()).getDecoderInfo();

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;
		Set<String> keyToBeRemove = new HashSet<>();
		keyToBeRemove.add(decoderControllingGroup + DecoderControllingMetric.APPLY_CHANGE.getName());
		keyToBeRemove.add(decoderControllingGroup + DecoderControllingMetric.CANCEL.getName());
		if (!decoderInfo.getState().isRunning() && !this.localDecoderDataList.equals(this.decoderDataDTOList)) {
			advancedControllableProperties.add(createButton(stats, decoderControllingGroup + DecoderControllingMetric.APPLY_CHANGE.getName(), DecoderConstant.APPLY));
			advancedControllableProperties.add(createButton(stats, decoderControllingGroup + DecoderControllingMetric.CANCEL.getName(), DecoderConstant.CANCEL));
		} else {
			stats.remove(keyToBeRemove);
		}
	}

	/**
	 * Instantiate Text controllable property
	 *
	 * @param name name of the property
	 * @param label default button label
	 * @return instance of AdvancedControllableProperty with AdvancedControllableProperty.Button as type
	 */
	private AdvancedControllableProperty createButton(Map<String, String> stats, String name, String label) {
		stats.put(name, DecoderConstant.EMPTY);
		AdvancedControllableProperty.Button button = new AdvancedControllableProperty.Button();
		button.setLabel(label);
		return new AdvancedControllableProperty(name, new Date(), button, "");
	}

	/**
	 * Create a switch controllable property
	 *
	 * @param name name of the switch
	 * @param status initial switch state (0|1)
	 * @return AdvancedControllableProperty button instance
	 */
	private AdvancedControllableProperty createSwitch(Map<String, String> stats, String name, boolean status, String labelOff, String labelOn) {
		AdvancedControllableProperty.Switch toggle = new AdvancedControllableProperty.Switch();
		toggle.setLabelOff(labelOff);
		toggle.setLabelOn(labelOn);
		int statusValue = 0;
		if (status) {
			statusValue = 1;
		}
		stats.put(name, String.valueOf(statusValue));
		return new AdvancedControllableProperty(name, new Date(), toggle, statusValue);
	}

	/***
	 * Create AdvancedControllableProperty preset instance
	 * @param name name of the control
	 * @param initialValue initial value of the control
	 * @return AdvancedControllableProperty preset instance
	 */
	private AdvancedControllableProperty createDropdown(Map<String, String> stats, String name, List<String> values, String initialValue) {
		stats.put(name, initialValue);
		AdvancedControllableProperty.DropDown dropDown = new AdvancedControllableProperty.DropDown();
		dropDown.setOptions(values.toArray(new String[0]));
		dropDown.setLabels(values.toArray(new String[0]));

		return new AdvancedControllableProperty(name, new Date(), dropDown, initialValue);
	}

	/**
	 * Create a controllable property Text
	 *
	 * @param name the name of property
	 * @param stringValue character string
	 * @return AdvancedControllableProperty Text instance
	 */
	private AdvancedControllableProperty createText(Map<String, String> stats, String name, String stringValue) {
		stats.put(name, stringValue);
		AdvancedControllableProperty.Text text = new AdvancedControllableProperty.Text();
		return new AdvancedControllableProperty(name, new Date(), text, stringValue);
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	//region Perform decoder control
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method is used for calling control all Decoder control properties in case:
	 * <li>Stream ID</li>
	 * <li>Still Image</li>
	 * <li>Still Image Delay</li>
	 * <li>Buffering Mode</li>
	 * <li>Buffering Delay</li>
	 * <li>Multi Sync Buffering Delay</li>
	 * <li>Hdr Dynamic Range</li>
	 * <li>Output 1</li>
	 * <li>Output 2</li>
	 * <li>Output 3</li>
	 * <li>Output 4</li>
	 * <li>Output Frame Rate</li>
	 * <li>Quad Mode</li>
	 *
	 * @param decoderID ID of decoder
	 * @param controllableProperty name of controllable property
	 * @param decoderID ID of decoder
	 * @param controllableProperty controllable property
	 * @param value value of controllable property
	 */
	private void decoderControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String decoderID, String controllableProperty, String value) {
		DecoderControllingMetric decoderControllingMetric = DecoderControllingMetric.getByName(controllableProperty);
		DecoderData decoderData = this.localDecoderDataList.get(decoderID);
		DecoderInfo decoderInfo = decoderData.getDecoderInfo();

		List<String> outputFrameRateList = OutputFrameRate.getOutputFrameRateList();
		List<String> quadModeList = QuadMode.getQuadModeList();
		List<String> stillImageList = StillImage.getStillImageList();
		List<String> hdrList = HDR.getHDRList();
		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;
		List<String> streamIDList = new LinkedList<>();

		if (this.streamDataDTOList != null) {
			streamIDList.addAll(this.streamDataDTOList.keySet());
		} else {
			streamIDList.add(DecoderConstant.DEFAULT_STREAM_ID);
		}

		switch (decoderControllingMetric) {
			case STREAM_ID:
				Integer streamID = this.localStreamDataList.get(value).getStreamInfo().getId();
				if (streamID == null) {
					streamID = -1;
				}
				decoderInfo.setStreamId(streamID);
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STREAM_ID.getName(), streamIDList, streamID.toString()));
				isEmergencyDelivery = true;
				break;
			case STILL_IMAGE:
				StillImage stillImage = StillImage.getByName(value);
				decoderInfo.setStillImage(stillImage.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE.getName(), stillImageList, stillImage.getName()));
				isEmergencyDelivery = true;
				break;
			case STILL_IMAGE_DELAY:
				decoderInfo.setStillImageDelay(Integer.parseInt(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createText(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE_DELAY.getName(), decoderInfo.getStillImageDelay().toString()));
				isEmergencyDelivery = true;
				break;
			case HDR_DYNAMIC_RANGE:
				HDR hdr = HDR.getByName(value);
				decoderInfo.setHdrDynamicRange(hdr.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName(), hdrList, hdr.getName()));
				isEmergencyDelivery = true;
				break;
			case OUTPUT_1:
				decoderInfo.setOutput1(mapSwitchControlValue(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_1.getName(), decoderInfo.getOutput1(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				isEmergencyDelivery = true;
				break;
			case OUTPUT_2:
				decoderInfo.setOutput2(mapSwitchControlValue(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_2.getName(), decoderInfo.getOutput1(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				isEmergencyDelivery = true;
				break;
			case OUTPUT_3:
				decoderInfo.setOutput3(mapSwitchControlValue(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_3.getName(), decoderInfo.getOutput1(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				isEmergencyDelivery = true;
				break;
			case OUTPUT_4:
				decoderInfo.setOutput4(mapSwitchControlValue(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_4.getName(), decoderInfo.getOutput1(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				isEmergencyDelivery = true;
				break;
			case OUTPUT_FRAME_RATE:
				OutputFrameRate outputFrameRate = OutputFrameRate.getByName(value);
				decoderInfo.setOutputFrameRate(outputFrameRate.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_FRAME_RATE.getName(), outputFrameRateList, outputFrameRate.getName()));
				isEmergencyDelivery = true;
				break;
			case BUFFERING_MODE:
				BufferingMode bufferingMode = BufferingMode.getByName(value);
				decoderInfo.setBufferingMode(bufferingMode.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				isEmergencyDelivery = true;
				break;
			case BUFFERING_DELAY:
				decoderInfo.setBufferingDelay(Integer.parseInt(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				isEmergencyDelivery = true;
				break;
			case MULTI_SYNC_BUFFERING_DELAY:
				decoderInfo.setMultisyncBufferingDelay(Integer.parseInt(value));
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				isEmergencyDelivery = true;
				break;
			case QUAD_MODE:
				QuadMode quadMode = QuadMode.getByName(value);
				decoderInfo.setQuadMode(quadMode.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.QUAD_MODE.getName(), quadModeList, quadMode.getName()));
				isEmergencyDelivery = true;
				break;
			case STATE:
				State state = State.getByCode(Integer.parseInt(value));
				decoderInfo.setState(state.getCode());
				decoderData.setDecoderInfo(decoderInfo);
				this.localDecoderDataList.put(decoderID, decoderData);
				boolean controlResult;
				switch (state) {
					case STOPPED:
						controlResult = performDecoderControl(this.localDecoderDataList, decoderID, DecoderURL.STOP_DECODER, state.getName());
						break;
					case START:
						controlResult = performDecoderControl(this.localDecoderDataList, decoderID, DecoderURL.START_DECODER, state.getName());
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + state);
				}
				if (controlResult) {
					advancedControllableProperties.add(createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.STATE.getName(), decoderInfo.getState().isRunning(),
							DecoderConstant.OFF, DecoderConstant.ON));
				}
			case APPLY_CHANGE:
				performDecoderControl(this.localDecoderDataList, decoderID, DecoderURL.UPDATE_DECODER, decoderControllingMetric.getName());
				break;
			case CANCEL:
				this.localDecoderDataList.putAll(this.decoderDataDTOList);
			default:
				throw new IllegalStateException("Unexpected value: " + controllableProperty);
		}
	}

	/**
	 * This method is used to perform decoder control start/ stop/ update
	 *
	 * @param localDecoderDataList list of decoder data
	 * @param decoderID ID of decoder ID
	 */
	private boolean performDecoderControl(Map<String, DecoderData> localDecoderDataList, String decoderID, String controlURL, String controlMethod) {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = localDecoderDataList.get(decoderID).getDecoderInfo().jsonRequest();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				DecoderData decoderData = doPut(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID + controlURL), request, DecoderData.class);
				if (decoderData == null) {
					throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
				}
			} else {
				throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
		}
		return true;
	}

	/**
	 * This method is used to map switch control value from string to boolean
	 *
	 * @param value value of switch control in String
	 * @return boolean value of switch control true/false
	 */
	public boolean mapSwitchControlValue(String value) {
		return value.equals("1");
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	//region Perform stream control
	//--------------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion
}
