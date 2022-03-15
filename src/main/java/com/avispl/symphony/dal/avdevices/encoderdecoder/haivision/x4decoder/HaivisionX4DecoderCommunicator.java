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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DropdownList;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.Encapsulation;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.FecRTP;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.NetworkType;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.SRTMode;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.StreamControllingMetric;
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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.deviceinfo.SystemInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.SRT;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Source;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Stream;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamDataWrapper;
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
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public class HaivisionX4DecoderCommunicator extends RestCommunicator implements Monitorable, Controller {
	private AuthenticationCookie authenticationCookie;
	private AuthenticationInfo authenticationInfo;
	private SystemInfo systemInfo;
	private Map<String, String> failedMonitor;
	private Set<Integer> filteredStreamIDSet;
	private Set<String> streamNameSet;
	private Set<String> streamStatusSet;
	private Set<String> portNumberSet;
	private boolean isUpdateLocalDecoderControl = false;
	private boolean isUpdateLocalStreamControl = false;
	private boolean isCreatedStreamControl = false;

	private boolean isEmergencyDelivery = false;
	private ExtendedStatistics localExtendedStatistics;

	// Decoder and stream DTO
	private List<DecoderInfo> decoderInfoDTOList;
	private List<StreamInfo> streamInfoDTOList;
	private List<DecoderInfo> localDecoderInfoList;
	private List<StreamInfo> localStreamInfoList;
	private StreamInfo createStream;

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
		if (splitProperty.length != 2) {
			throw new IllegalArgumentException("Unexpected length of control property");
		}
		ControllingMetricGroup controllingGroup = ControllingMetricGroup.getByName(splitProperty[0]);

		switch (controllingGroup) {
			case DECODER:
				String name = splitProperty[0].substring(7);
				Integer decoderID = Integer.parseInt(name);
				decoderControl(stats, advancedControllableProperties, decoderID, splitProperty[1], value);
				break;
			case CREATE_STREAM:
				createStreamControl(stats, advancedControllableProperties, ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH, splitProperty[1], value);
				break;
			case STREAM:
				String streamName = splitProperty[0].substring(6);
				streamControl(stats, advancedControllableProperties, ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH, streamName, splitProperty[1], value);
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
		filteredStreamIDSet = new HashSet<>();

		if (localExtendedStatistics == null) {
			localExtendedStatistics = new ExtendedStatistics();
		}
		if (authenticationCookie == null) {
			authenticationCookie = initAuthenticationCookie();
		}
		if (systemInfo == null) {
			systemInfo = new SystemInfo();
		}
		if (decoderInfoDTOList == null) {
			decoderInfoDTOList = new ArrayList<>();
		}
		if (streamInfoDTOList == null) {
			streamInfoDTOList = new ArrayList<>();
		}
		if (localDecoderInfoList == null) {
			localDecoderInfoList = new ArrayList<>();
		}
		if (localStreamInfoList == null) {
			localStreamInfoList = new ArrayList<>();
		}

		if (createStream == null || isCreatedStreamControl) {
			createStream = defaultStream();
			isCreatedStreamControl = false;
		}
		if (!isEmergencyDelivery) {
			populateDecoderMonitoringMetrics(stats);
			if (isUpdateLocalDecoderControl || localDecoderInfoList.size() == 0) {
				localDecoderInfoList = decoderInfoDTOList.stream().map(decoderInfo -> new DecoderInfo(decoderInfo)).collect(Collectors.toList());
				isUpdateLocalDecoderControl = false;
			}
			if (isUpdateLocalStreamControl || localStreamInfoList.size() != filteredStreamIDSet.size()) {
				localStreamInfoList.clear();
				localStreamInfoList = streamInfoDTOList.stream().map(streamInfo -> new StreamInfo(streamInfo))
						.filter(streamInfo -> filteredStreamIDSet.contains(streamInfo.getId())).collect(Collectors.toList());
				isUpdateLocalStreamControl = false;
			}
			// check Role is Admin or Operator
			String role = authenticationInfo.getAuthenticationRole().getRole();
			if (role.equals(DecoderConstant.OPERATOR_ROLE) || role.equals(DecoderConstant.ADMIN_ROLE)) {
				populateControllingMetrics(stats, advancedControllableProperties);
				extendedStatistics.setControllableProperties(advancedControllableProperties);
			}

			extendedStatistics.setStatistics(stats);
			localExtendedStatistics = extendedStatistics;
		}
		isEmergencyDelivery = false;
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
			throw new ResourceNotReachableException("Retrieve role based error: " + e.getMessage());
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
	 * Update failedMonitor with getting system info error message
	 *
	 * @param failedMonitor list statistics property
	 */
	private void updateSystemInfoFailedMonitor(Map<String, String> failedMonitor) {
		failedMonitor.put(MonitoringMetricGroup.SYSTEM_INFO.getName(), DecoderConstant.GETTING_SYSTEM_INFO_ERR);
	}

	/**
	 * This method is used to retrieve System info by send GET request to http://{IP_Address}/apis/system_info
	 *
	 * When sessionID is null, the failedMonitor is going to update
	 * When there is no response data, the failedMonitor is going to update
	 * When there is an exception, the failedMonitor is going to update and exception is not populated
	 */
	private void retrieveSystemInfo() {
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				systemInfo = doGet(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.SYSTEM_INFO), SystemInfo.class);
				if (systemInfo != null) {
					updateSystemInfoFailedMonitor(failedMonitor);
				}
			} else {
				updateSystemInfoFailedMonitor(failedMonitor);
			}
		} catch (Exception e) {
			updateSystemInfoFailedMonitor(failedMonitor);
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
		DecoderInfo decoderInfo = decoderData.getDecoderInfo();
		DecoderStats decoderStats = decoderData.getDecoderStats();
		List<AudioPair> audioPairs = decoderStats.getAudioPairs();

		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + decoderID + DecoderConstant.HASH;

		for (DecoderMonitoringMetric item : DecoderMonitoringMetric.values()) {
			stats.put(decoderStatisticGroup + item.getName(), checkForNullData(decoderData.getValueByDecoderMonitoringMetric(item)));
		}
		if (audioPairs != null) {
			for (AudioPair audioPair : audioPairs) {
				int index = 1;
				String audioPairGroup = decoderStatisticGroup + DecoderConstant.AUDIO_PAIR + index++;
				for (AudioPairMetric item : AudioPairMetric.values()) {
					stats.put(audioPairGroup + item.getName(), checkForNullData(audioPair.getValueByAudioPairMetric(item)));
				}
			}
		}
		if (localDecoderInfoList.size() > decoderID) {
			DecoderInfo localDecoderInfo = this.localDecoderInfoList.get(decoderID);
			DecoderInfo decoderInfoDTO = this.decoderInfoDTOList.get(decoderID);
			if (decoderInfoDTO.equals(localDecoderInfo) && !decoderInfo.equals(decoderInfoDTO)) {
				this.decoderInfoDTOList.set(decoderID, decoderInfo);
				this.isUpdateLocalDecoderControl = true;
			}
		}
		if (!isUpdateLocalDecoderControl) {
			if (this.decoderInfoDTOList.size() > decoderID) {
				this.decoderInfoDTOList.set(decoderID, decoderInfo);
			} else {
				this.decoderInfoDTOList.add(decoderID, decoderInfo);
			}
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
	 * Update failedMonitor with getting stream statistics error message
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
		StreamInfo streamInfo = stream.getStreamInfo();
		Integer streamID = streamInfo.getId();
		String streamName =  streamInfo.getName();

		if(StringUtils.isNullOrEmpty(streamName)){
			streamName = streamInfo.getDefaultStreamName();
		}
		String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + streamName + DecoderConstant.HASH;

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

		Optional<StreamInfo> streamInfoDTO = this.streamInfoDTOList.stream().filter(st -> streamID.equals(st.getId())).findFirst();
		Optional<StreamInfo> localStreamInfo = this.localStreamInfoList.stream().filter(st -> streamID.equals(st.getId())).findFirst();
		if (localStreamInfo.isPresent() && localStreamInfo.get().equals(streamInfoDTO.get()) && !streamInfoDTO.get().equals(streamInfo)) {
			this.streamInfoDTOList.remove(streamInfoDTO.get());
			this.streamInfoDTOList.add(streamInfo);
			this.isUpdateLocalStreamControl = true;
		}

		if (!isUpdateLocalStreamControl) {
			if (streamInfoDTO.isPresent()) {
				this.streamInfoDTOList.remove(streamInfoDTO.get());
				this.streamInfoDTOList.add(streamInfo);
			} else {
				this.streamInfoDTOList.add(streamInfo);
			}
		}
		filteredStreamIDSet.add(streamID);
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
						if (this.streamName != null && streamNameSet != null &&  streamNameSet.contains(streamInfo.getName())) {
							populateStreamStats(stats, stream);
							continue;
						}

						// Stream status filtering
						if (this.streamStatus != null && streamStatusSet != null && !streamStatusSet.contains(streamStats.getState())) {
							continue;
						}

						// Port number filtering
						if (this.portNumber != null && portNumberSet != null) {
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

		// Retrieving all systemInfo
		retrieveSystemInfo();

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
		for (Integer decoderID = DecoderConstant.MIN_DECODER_ID; decoderID < DecoderConstant.MAX_DECODER_ID; decoderID++) {
			populateDecoderControl(stats, advancedControllableProperties, decoderID);
		}

		// Create stream control
		populateCreateStreamControl(stats, advancedControllableProperties, createStream, ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH);

		// Stream control
		for (StreamInfo streamInfo : this.localStreamInfoList) {
			String streamName = streamInfo.getName();
			if (StringUtils.isNullOrEmpty(streamName)) {
				streamName = streamInfo.getDefaultStreamName();
			}
			populateStreamControl(stats, advancedControllableProperties, streamInfo, ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH);
		}
	}

	/**
	 * This method is used to handle  input from adapter properties and convert it to Set of String  for control
	 *
	 * @return Set<String> is the Set of String of filter element
	 */
	public Set<String> handleAdapterPropertiesInputFromUser(String input) {
		if (!StringUtils.isNullOrEmpty(input)) {
			String[] listAdapterPropertyElement = input.split(DecoderConstant.COMMA);

			// Remove start and end spaces of each adapterProperty
			Set<String> setAdapterPropertiesElement = new HashSet<>();
			for (String adapterPropertyElement : listAdapterPropertyElement) {
				setAdapterPropertiesElement.add(adapterPropertyElement.trim());
			}
			return setAdapterPropertiesElement;
		}
		return null;
	}

	/**
	 * This method is used to handle  input from adapter properties (port, port range)
	 * When the input is an Integer value this method will check whether it is match with port from stream stats or not
	 * When the input is a range value this method will convert the range to min/ max port value and check whether it covers port from stream stats or not
	 *
	 * @return boolean the port and port range filtering result
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
		DecoderInfo decoderInfo = this.localDecoderInfoList.get(decoderID);
		HDR hdr = decoderInfo.getHdrDynamicRange();
		OutputFrameRate outputFrameRate = decoderInfo.getOutputFrameRate();
		StillImage stillImage = decoderInfo.getStillImage();

		Integer streamID = decoderInfo.getStreamId();
		String streamName = DecoderConstant.NONE;
		if (this.localStreamInfoList != null) {
			for (StreamInfo streamInfo : localStreamInfoList) {
				if (streamID.equals(streamInfo.getId())) {
					streamName = streamInfo.getName();
					if (StringUtils.isNullOrEmpty(streamName)) {
						streamName = streamInfo.getDefaultStreamName();
					}
					break;
				}
			}
		}

			// Get list values of controllable property (dropdown)
			List<String> outputFrameRateList = DropdownList.Names(OutputFrameRate.class);
			List<String> stillImageList = DropdownList.Names(StillImage.class);
			List<String> hdrList = DropdownList.Names(HDR.class);
			List<String> streamNameList = new ArrayList<>();

			streamNameList.add(DecoderConstant.NONE);
			if (this.localStreamInfoList != null) {
				for (StreamInfo streamInfo : localStreamInfoList) {
					if (!StringUtils.isNullOrEmpty(streamInfo.getName())) {
						streamNameList.add(streamInfo.getName());
					} else {
						streamNameList.add(streamInfo.getDefaultStreamName());
					}
				}
			}

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;

		// Populate stream dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STREAM.getName(), streamNameList, streamName));

		if (systemInfo.isHasHDR()) {
			// Populate HDR dropdown list control
			advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName(), hdrList, hdr.getName()));
		}
		// Populate output frame rate dropdown list control
		advancedControllableProperties.add(
				createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_FRAME_RATE.getName(), outputFrameRateList, outputFrameRate.getName()));

		// Populate quad mode dropdown list control
		populateDecoderControlQuadMode(stats, advancedControllableProperties, decoderInfo);

		// Populate still image dropdown list control
		advancedControllableProperties.add(createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE.getName(), stillImageList, stillImage.getName()));

		// Populate still image delay numeric control
		advancedControllableProperties.add(createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE_DELAY.getName(), decoderInfo.getStillImageDelay().toString()));

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
		List<String> bufferingModeList = DropdownList.Names(BufferingMode.class);
		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;

		// remove unused keys
		stats.remove(decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName());
		stats.remove(decoderControllingGroup + DecoderControllingMetric.BUFFERING_DELAY.getName());
		stats.remove(decoderControllingGroup + DecoderControllingMetric.MULTI_SYNC_BUFFERING_DELAY.getName());
		switch (bufferingMode) {
			case AUTO:
				// Populate buffering mode dropdown list control
				addAdvanceControlProperties(advancedControllableProperties,
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));
				break;
			case FIXED:
				// Populate buffering mode dropdown list control
				addAdvanceControlProperties(advancedControllableProperties,
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));

				// Populate fixed delay numeric control
				advancedControllableProperties.add(createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_DELAY.getName(), decoderInfo.getBufferingDelay().toString()));
				break;
			case MULTI_SYNC:
				// Populate buffering mode dropdown list control
				addAdvanceControlProperties(advancedControllableProperties,
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_MODE.getName(), bufferingModeList, bufferingMode.getName()));

				// Populate multi sync delay numeric control
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.MULTI_SYNC_BUFFERING_DELAY.getName(), decoderInfo.getMultisyncBufferingDelay().toString()));
				break;
		}
	}

	/**
	 * This method is used for populate all quad mode of decoder control:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param decoderInfo set of decoder configuration
	 */
	private void populateDecoderControlQuadMode(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, DecoderInfo decoderInfo) {
		// Get controllable property current value
		String decoderID = decoderInfo.getId();
		QuadMode quadMode = decoderInfo.getQuadMode();

		// Get list values of controllable property (dropdown)
		List<String> quadModeList = DropdownList.Names(QuadMode.class);

		String quadModeKey = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH + DecoderControllingMetric.QUAD_MODE.getName();

		// Remove unused keys
		stats.remove(quadModeKey);
		if (decoderInfo.getOutput1() && decoderInfo.getOutput2() && decoderInfo.getOutput3() && decoderInfo.getOutput4()) {
			addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, quadModeKey, quadModeList, quadMode.getName()));
		} else {
			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(quadModeKey)) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
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
		DecoderInfo decoderInfo = this.localDecoderInfoList.get(decoderID);
		DecoderInfo decoderInfoDTO = this.decoderInfoDTOList.get(decoderID);

		String applyChange = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH + DecoderControllingMetric.APPLY_CHANGE.getName();
		String cancel = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH + DecoderControllingMetric.CANCEL.getName();

		if (!decoderInfo.equals(decoderInfoDTO)) {
			stats.put(ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH + DecoderControllingMetric.EDITED.getName(), "True");
			stats.put(applyChange, DecoderConstant.EMPTY);
			stats.put(cancel, DecoderConstant.EMPTY);
			addAdvanceControlProperties(advancedControllableProperties, createButton(applyChange, DecoderConstant.APPLY, DecoderConstant.APPLYING));
			addAdvanceControlProperties(advancedControllableProperties, createButton(cancel, DecoderConstant.CANCEL, DecoderConstant.CANCELLING));
		} else {
			stats.remove(applyChange);
			stats.remove(cancel);
			stats.put(ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH + DecoderControllingMetric.EDITED.getName(), "False");

			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(applyChange)) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(cancel)) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
		}
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
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param controllableProperty name of controllable property
	 * @param decoderID ID of decoder
	 * @param value value of controllable property
	 */
	private void decoderControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, Integer decoderID, String controllableProperty, String value) {
		DecoderControllingMetric decoderControllingMetric = DecoderControllingMetric.getByName(controllableProperty);
		DecoderInfo decoderInfo = this.localDecoderInfoList.get(decoderID);

		List<String> outputFrameRateList =  DropdownList.Names(OutputFrameRate.class);
		List<String> quadModeList = DropdownList.Names(QuadMode.class);
		List<String> stillImageList = DropdownList.Names(StillImage.class);
		List<String> hdrList = DropdownList.Names(HDR.class);
		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;
		List<String> streamNameList = new ArrayList<>();

		streamNameList.add(DecoderConstant.NONE);
		if (this.localStreamInfoList != null) {
			for (StreamInfo streamInfo : localStreamInfoList) {
				if (!StringUtils.isNullOrEmpty(streamInfo.getName())) {
					streamNameList.add(streamInfo.getName());
				} else {
					streamNameList.add(streamInfo.getDefaultStreamName());
				}
			}
		}

		switch (decoderControllingMetric) {
			case STREAM:
				Integer streamID = DecoderConstant.DEFAULT_STREAM_ID;
				String streamName = DecoderConstant.NONE;
				for (StreamInfo streamInfo : localStreamInfoList) {
					if (value.equals(streamInfo.getName()) || value.equals(streamInfo.getDefaultStreamName())) {
						streamID = streamInfo.getId();
						streamName = value;
						break;
					}
				}

				decoderInfo.setStreamId(streamID);
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STREAM.getName(), streamNameList, streamName));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case STILL_IMAGE:
				StillImage stillImage = StillImage.getByName(value);
				decoderInfo.setStillImage(stillImage.getCode());
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE.getName(), stillImageList, stillImage.getName()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case STILL_IMAGE_DELAY:
				decoderInfo.setStillImageDelay(Integer.parseInt(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE_DELAY.getName(), decoderInfo.getStillImageDelay().toString()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case HDR_DYNAMIC_RANGE:
				HDR hdr = HDR.getByName(value);
				if (systemInfo.isHasHDR()) {
					decoderInfo.setHdrDynamicRange(hdr.getCode());
					this.localDecoderInfoList.set(decoderID, decoderInfo);
					addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName(), hdrList, hdr.getName()));
					populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
					populateLocalExtendedStats(stats, advancedControllableProperties);
				}
				break;
			case OUTPUT_1:
				decoderInfo.setOutput1(mapSwitchControlValue(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_1.getName(), decoderInfo.getOutput1(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateDecoderControlQuadMode(stats, advancedControllableProperties, decoderInfo);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case OUTPUT_2:
				decoderInfo.setOutput2(mapSwitchControlValue(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_2.getName(), decoderInfo.getOutput2(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateDecoderControlQuadMode(stats, advancedControllableProperties, decoderInfo);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case OUTPUT_3:
				decoderInfo.setOutput3(mapSwitchControlValue(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_3.getName(), decoderInfo.getOutput3(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateDecoderControlQuadMode(stats, advancedControllableProperties, decoderInfo);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case OUTPUT_4:
				decoderInfo.setOutput4(mapSwitchControlValue(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_4.getName(), decoderInfo.getOutput4(),
						DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateDecoderControlQuadMode(stats, advancedControllableProperties, decoderInfo);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case OUTPUT_FRAME_RATE:
				OutputFrameRate outputFrameRate = OutputFrameRate.getByName(value);
				decoderInfo.setOutputFrameRate(outputFrameRate.getCode());
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.OUTPUT_FRAME_RATE.getName(), outputFrameRateList, outputFrameRate.getName()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case BUFFERING_MODE:
				BufferingMode bufferingMode = BufferingMode.getByName(value);
				decoderInfo.setBufferingMode(bufferingMode.getCode());
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				populateDecoderControlBufferingMode(stats, advancedControllableProperties, decoderInfo);
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case BUFFERING_DELAY:
				decoderInfo.setBufferingDelay(Integer.parseInt(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				advancedControllableProperties.add(createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.BUFFERING_DELAY.getName(), decoderInfo.getBufferingDelay().toString()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case MULTI_SYNC_BUFFERING_DELAY:
				decoderInfo.setMultisyncBufferingDelay(Integer.parseInt(value));
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				advancedControllableProperties.add(
						createNumeric(stats, decoderControllingGroup + DecoderControllingMetric.MULTI_SYNC_BUFFERING_DELAY.getName(), decoderInfo.getMultisyncBufferingDelay().toString()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case QUAD_MODE:
				QuadMode quadMode = QuadMode.getByName(value);
				decoderInfo.setQuadMode(quadMode.getCode());
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties,createDropdown(stats, decoderControllingGroup + DecoderControllingMetric.QUAD_MODE.getName(), quadModeList, quadMode.getName()));
				populateApplyChangeAndCancelButtonForDecoder(stats, advancedControllableProperties, decoderID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case STATE:
				State state = State.getByCode(Integer.parseInt(value));
				decoderInfo.setState(state.getCode());
				this.localDecoderInfoList.set(decoderID, decoderInfo);
				DecoderInfo controlResult;
				switch (state) {
					case STOPPED:
						performActiveDecoderControl(stats, advancedControllableProperties, decoderInfo, DecoderURL.STOP_DECODER, state.getName());
						break;
					case START:
						performActiveDecoderControl(stats, advancedControllableProperties, decoderInfo, DecoderURL.START_DECODER, state.getName());
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + state);
				}
				break;
			case APPLY_CHANGE:
				controlResult = performDecoderControl(decoderInfo, DecoderURL.UPDATE_DECODER, decoderControllingMetric.getName());

				if (controlResult != null) {
					this.localDecoderInfoList.set(decoderID, controlResult);
					populateDecoderControl(stats, advancedControllableProperties, decoderID);
				}
				break;
			case CANCEL:
				this.localDecoderInfoList.clear();
				this.localDecoderInfoList.addAll(this.decoderInfoDTOList);
				populateDecoderControl(stats, advancedControllableProperties, decoderID);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + controllableProperty);
		}
	}

	/**
	 * This method is used for populate local extended stats when emergency delivery:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 */
	private void populateLocalExtendedStats(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		this.localExtendedStatistics.setStatistics(stats);
		this.localExtendedStatistics.setControllableProperties(advancedControllableProperties);
		isEmergencyDelivery = true;
	}

	/**
	 * This method is used to perform decoder control start/ stop/ update
	 *
	 * @param decoderInfo set of decoder config info
	 * @param controlMethod start/stop/update
	 * @param controlURL API entry point
	 * @return decoder info set of decoder config info
	 */
	private DecoderInfo performDecoderControl(DecoderInfo decoderInfo, String controlURL, String controlMethod) {
		DecoderInfo decoderInfoResult;
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = decoderInfo.jsonRequest();
				String decoderID = decoderInfo.getId();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				DecoderData decoderData = doPut(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID + controlURL), request, DecoderData.class);
				decoderInfoResult = decoderData.getDecoderInfo();
				if (decoderData == null) {
					throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
				}
			} else {
				throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod + DecoderConstant.NEXT_LINE + e.getMessage());
		}
		return decoderInfoResult;
	}

	/**
	 * This method is used to perform decoder control start/ stop/ update
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param decoderInfo set of decoder config info
	 * @param controlMethod start/stop/update
	 * @param controlURL API entry point

	 */
	private void performActiveDecoderControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, DecoderInfo decoderInfo, String controlURL, String controlMethod) {
		String decoderID = decoderInfo.getId();
		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + decoderID + DecoderConstant.HASH;
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = decoderInfo.jsonRequest();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				DecoderData decoderData = doPut(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.DECODERS + DecoderConstant.SLASH + decoderID + controlURL), request, DecoderData.class);
				if (decoderData == null) {
					populateActiveControlAfterControlFailed(decoderInfo);
					addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.STATE.getName(), decoderInfo.getState().isRunning(),
							DecoderConstant.OFF, DecoderConstant.ON));
					throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
				}else{
					this.decoderInfoDTOList.set(Integer.parseInt(decoderID), decoderData.getDecoderInfo());
					this.localDecoderInfoList.set(Integer.parseInt(decoderID), decoderData.getDecoderInfo());
					populateDecoderControl(stats, advancedControllableProperties, Integer.parseInt(decoderID));
				}
			} else {
				populateActiveControlAfterControlFailed(decoderInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.STATE.getName(), decoderInfo.getState().isRunning(),
						DecoderConstant.OFF, DecoderConstant.ON));
				throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod);
			}
		} catch (Exception e) {
			populateActiveControlAfterControlFailed(decoderInfo);
			addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, decoderControllingGroup + DecoderControllingMetric.STATE.getName(), decoderInfo.getState().isRunning(),
					DecoderConstant.OFF, DecoderConstant.ON));
			throw new ResourceNotReachableException(DecoderConstant.DECODER_CONTROL_ERR + controlMethod + DecoderConstant.NEXT_LINE + e.getMessage());
		}
	}


	/**
	 * This method is used to perform decoder control start/ stop/ update
	 *
	 * @param decoderInfo list of decoder data
	 */
	private void populateActiveControlAfterControlFailed(DecoderInfo decoderInfo) {
		State state = decoderInfo.getState();
		Integer decoderID = Integer.parseInt(decoderInfo.getId());
			switch (state) {
				case STOPPED:
					state = State.START;
					decoderInfo.setState(state.getCode());
					this.localDecoderInfoList.set(decoderID, decoderInfo);
					break;
				case START:
					state = State.STOPPED;
					decoderInfo.setState(state.getCode());
					this.localDecoderInfoList.set(decoderID, decoderInfo);
					break;
				default:
					break;
			}
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

	//region Populate stream control
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method is used for populate all stream control properties:
	 * <li>Protocol: TS over UDP</li>
	 * <li>Protocol: TS over RTP</li>
	 * <li>Protocol: TS over SRT</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Get controllable property current value
		String streamName = streamInfo.getName();
		Encapsulation encapsulation = streamInfo.getEncapsulation();
		List<String> encapsulationList = DropdownList.Names(Encapsulation.class);

		// Populate stream name text control
		addAdvanceControlProperties(advancedControllableProperties,
				createText(stats, streamGroup + StreamControllingMetric.STREAM_NAME.getName(), streamName));
		// Populate Encapsulation dropdown control
		addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamGroup + StreamControllingMetric.ENCAPSULATION.getName(), encapsulationList, encapsulation.getName()));

		// Populate delete button
		addAdvanceControlProperties(advancedControllableProperties, createButton(streamGroup + DecoderConstant.DELETE, DecoderConstant.DELETE, DecoderConstant.DELETING));
		stats.put(streamGroup + DecoderConstant.DELETE, DecoderConstant.EMPTY);

		switch (encapsulation) {
			case TS_OVER_UDP:
				populateStreamControlCaseTSOverUDP(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case TS_OVER_RTP:
				populateStreamControlCaseTSOverRTP(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case TS_OVER_SRT:
				populateStreamControlCaseTSOverSRT(stats, advancedControllableProperties, streamInfo, streamGroup);
		}

		// Populate apply change and cancel button
		populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamInfo.getId());
	}

	/**
	 * This method is used for populate all create stream control properties:
	 * <li>Protocol: TS over UDP</li>
	 * <li>Protocol: TS over RTP</li>
	 * <li>Protocol: TS over SRT</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateCreateStreamControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Get controllable property current value
		Encapsulation encapsulation = streamInfo.getEncapsulation();
		List<String> encapsulationList = DropdownList.Names(Encapsulation.class);
		String streamName = streamInfo.getName();

		// Populate stream name text control
		advancedControllableProperties.add(
				createText(stats, streamGroup + StreamControllingMetric.STREAM_NAME.getName(), streamName));

		// Populate Encapsulation dropdown control
		advancedControllableProperties.add(createDropdown(stats, streamGroup + StreamControllingMetric.ENCAPSULATION.getName(), encapsulationList, encapsulation.getName()));

		// Populate create button
		advancedControllableProperties.add(createButton(streamGroup + DecoderConstant.CREATE, DecoderConstant.CREATE, DecoderConstant.CREATING));
		stats.put(streamGroup + DecoderConstant.CREATE, DecoderConstant.EMPTY);

		switch (encapsulation) {
			case TS_OVER_UDP:
				populateStreamControlCaseTSOverUDP(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case TS_OVER_RTP:
				populateStreamControlCaseTSOverRTP(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case TS_OVER_SRT:
				populateStreamControlCaseTSOverSRT(stats, advancedControllableProperties, streamInfo, streamGroup);
		}
		// Populate cancel button
		populateCancelButtonForCreateStream(stats, advancedControllableProperties);
	}

	/**
	 * This method is used for populate all stream control properties:
	 * <li>Protocol: TS over UDP</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverUDP(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Populate port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.PORT.getName(), checkForNullData(streamInfo.getPort().toString())));
		// Populate network type control
		populateNetWorkTypeStreamControl(stats, advancedControllableProperties, streamInfo, streamGroup);
	}

	/**
	 * This method is used for populate all stream control properties:
	 * <li>Protocol: TS over RTP</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverRTP(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Get controllable property current value
		Integer port = streamInfo.getPort();
		FecRTP fecRTP = streamInfo.getFecRtp();
		List<String> fecRTPs = DropdownList.Names(FecRTP.class);

		// Populate port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.PORT.getName(), checkForNullData(port.toString())));

		// Populate fecRTP dropdown control
		addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamGroup + StreamControllingMetric.FEC_RTP.getName(), fecRTPs, fecRTP.getName()));

		// Populate network type control
		populateNetWorkTypeStreamControl(stats, advancedControllableProperties, streamInfo, streamGroup);
	}

	/**
	 * This method is used for populate stream control properties case NetworkType:
	 * Network type
	 * <li>Multicast</li>
	 * <li>Uni-cast</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateNetWorkTypeStreamControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Get controllable property current value
		String multicastAddress = streamInfo.getAddress();
		String sourceIp = streamInfo.getSourceIp();
		List<String> netWorkTypes = DropdownList.Names(NetworkType.class);

		// Populate network type control
		if (multicastAddress.equals(DecoderConstant.ADDRESS_ANY) && sourceIp.equals(DecoderConstant.ADDRESS_ANY)) {
			addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamGroup + StreamControllingMetric.NETWORK_TYPE.getName(), netWorkTypes, NetworkType.UNI_CAST.getName()));
		} else {
			addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamGroup + StreamControllingMetric.NETWORK_TYPE.getName(), netWorkTypes, NetworkType.MULTI_CAST.getName()));
			addAdvanceControlProperties(advancedControllableProperties,
					createText(stats, streamGroup + StreamControllingMetric.MULTICAST_ADDRESS.getName(), multicastAddress));
			addAdvanceControlProperties(advancedControllableProperties,
					createText(stats, streamGroup + StreamControllingMetric.SOURCE_ADDRESS.getName(), sourceIp));
		}
	}

	/**
	 * This method is used for populate all stream control properties:
	 * Protocol: TS over SRT
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRT(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		SRTMode srtMode = streamInfo.getSrtMode();
		List<String> srtModeList = DropdownList.Names(SRTMode.class);

		addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamGroup + StreamControllingMetric.SRT_MODE.getName(), srtModeList, srtMode.getName()));

		switch (srtMode) {
			case LISTENER:
				populateStreamControlCaseTSOverSRTListener(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case CALLER:
				populateStreamControlCaseTSOverSRTCaller(stats, advancedControllableProperties, streamInfo, streamGroup);
				break;
			case RENDEZVOUS:
				populateStreamControlCaseTSOverSRTRendezvous(stats, advancedControllableProperties, streamInfo, streamGroup);
		}
		populateStreamControlCaseTSOverSRTStreamConversion(stats, advancedControllableProperties, streamInfo, streamGroup);
		populateStreamControlCaseTSOverSRTEncrypted(stats, advancedControllableProperties, streamInfo, streamGroup);
	}

	/**
	 * This method is used for populate all stream control properties :
	 * Protocol: TS over SRT
	 * SRT mode: Listener
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRTListener(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Populate port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.PORT.getName(), checkForNullData(streamInfo.getPort().toString())));

		// Populate latency numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.LATENCY.getName(), checkForNullData(streamInfo.getLatency().toString())));

		// Populate reject unencrypted caller switch control
		if (streamInfo.getPassphraseSet()) {
			addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, streamGroup + StreamControllingMetric.REJECT_UNENCRYPTED_CALLERS.getName(), streamInfo.getStrictMode(),
					DecoderConstant.DISABLE, DecoderConstant.ENABLE));
		}
	}

	/**
	 * This method is used for populate all stream control properties :
	 * Protocol: TS over SRT
	 * Stream conversion
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRTStreamConversion(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo,
			String streamGroup) {
		boolean srtToUDP = streamInfo.getSrtToUdp();

		// Populate srt to udp switch control
		addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, streamGroup + StreamControllingMetric.SRT_TO_UDP_STREAM_CONVERSION.getName(), srtToUDP,
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));

		// Populate relevant control when srt to udp is enabled
		if (srtToUDP) {
			addAdvanceControlProperties(advancedControllableProperties,
					createText(stats, streamGroup + StreamControllingMetric.SRT_TO_UDP_ADDRESS.getName(), streamInfo.getSrtToUdpAddress()));
			addAdvanceControlProperties(advancedControllableProperties,
					createNumeric(stats, streamGroup + StreamControllingMetric.SRT_TO_UDP_PORT.getName(), checkForNullData(streamInfo.getSrtToUdpPort().toString())));
			addAdvanceControlProperties(advancedControllableProperties,
					createText(stats, streamGroup + StreamControllingMetric.SRT_TO_UDP_TOS.getName(), streamInfo.getSrtToUdpTos()));
			addAdvanceControlProperties(advancedControllableProperties,
					createNumeric(stats, streamGroup + StreamControllingMetric.SRT_TO_UDP_TTL.getName(), checkForNullData(streamInfo.getSrtToUdpTtl().toString())));
		}
	}

	/**
	 * This method is used for populate all stream control properties :
	 * Protocol: TS over SRT
	 * Encrypted
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRTEncrypted(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		boolean encrypted = streamInfo.getPassphraseSet();

		// Populate encrypted switch control
		addAdvanceControlProperties(advancedControllableProperties, createSwitch(stats, streamGroup + StreamControllingMetric.ENCRYPTED.getName(), encrypted,
				DecoderConstant.DISABLE, DecoderConstant.ENABLE));

		// Populate relevant control when encrypted is enabled
		if (encrypted) {
			addAdvanceControlProperties(advancedControllableProperties,
					createText(stats, streamGroup + StreamControllingMetric.PASSPHRASE.getName(), streamInfo.getPassphrase()));
		}
	}

	/**
	 * This method is used for populate all stream control properties :
	 * Protocol: TS over SRT
	 * SRT mode: caller
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRTCaller(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Populate Address text control
		if (streamInfo.getAddress().equals(DecoderConstant.ADDRESS_ANY)) {
			streamInfo.setAddress(DecoderConstant.EMPTY);
		}
		addAdvanceControlProperties(advancedControllableProperties,
				createText(stats, streamGroup + StreamControllingMetric.ADDRESS.getName(), streamInfo.getAddress()));

		// Populate source port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.SOURCE_PORT.getName(), checkForNullData(streamInfo.getSourcePort().toString())));

		// Populate port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.DESTINATION_PORT.getName(), checkForNullData(streamInfo.getPort().toString())));

		// Populate latency numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.LATENCY.getName(), checkForNullData(streamInfo.getLatency().toString())));
	}

	/**
	 * This method is used for populate all stream control properties :
	 * Protocol: TS over SRT
	 * SRT mode: Rendezvous
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamInfo stream config info
	 */
	private void populateStreamControlCaseTSOverSRTRendezvous(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, StreamInfo streamInfo, String streamGroup) {
		// Populate Address text control
		if (streamInfo.getAddress().equals(DecoderConstant.ADDRESS_ANY)) {
			streamInfo.setAddress(DecoderConstant.EMPTY);
		}
		addAdvanceControlProperties(advancedControllableProperties,
				createText(stats, streamGroup + StreamControllingMetric.ADDRESS.getName(), streamInfo.getAddress()));

		// Populate source port (auto assign)
		stats.put(streamGroup + StreamControllingMetric.SOURCE_PORT.getName(), streamInfo.getPort().toString());

		// Populate port numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.DESTINATION_PORT.getName(), checkForNullData(streamInfo.getPort().toString())));

		// Populate latency numeric control
		addAdvanceControlProperties(advancedControllableProperties,
				createNumeric(stats, streamGroup + StreamControllingMetric.LATENCY.getName(), checkForNullData(streamInfo.getLatency().toString())));
	}

	/**
	 * This method is used for populate apply change button and cancel button of stream control:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamID ID of stream
	 */
	private void populateApplyChangeAndCancelButtonForStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, Integer streamID) {
		Optional<StreamInfo> streamInfoDTOOptional = this.streamInfoDTOList.stream().filter(st -> streamID.equals(st.getId())).findFirst();
		Optional<StreamInfo> streamInfoOptional = this.localStreamInfoList.stream().filter(st -> streamID.equals(st.getId())).findFirst();

		StreamInfo streamInfoDTO = defaultStream();
		StreamInfo streamInfo = defaultStream();

		if (streamInfoDTOOptional.isPresent()){
			streamInfoDTO = streamInfoDTOOptional.get();
		}
		if (streamInfoOptional.isPresent()){
			streamInfo = streamInfoOptional.get();
		}
		Encapsulation encapsulation = streamInfoDTO.getEncapsulation();
		SRTMode srtMode = streamInfoDTO.getSrtMode();
		Boolean encrypted = streamInfoDTO.getPassphraseSet();
		Boolean srtToUDP = streamInfoDTO.getSrtToUdp();


		if (streamInfoOptional.isPresent()) {
			String streamName =  streamInfo.getName();
			if(StringUtils.isNullOrEmpty(streamName)){
				streamName = streamInfo.getDefaultStreamName();
			}
			String applyChange = ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH + StreamControllingMetric.APPLY_CHANGE.getName();
			String cancel = ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH + StreamControllingMetric.CANCEL.getName();

			if (!streamInfo.equalsByProtocol(streamInfoDTO, encapsulation, srtMode, encrypted, srtToUDP)) {
				stats.put(ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH + StreamControllingMetric.EDITED.getName(), "True");
				stats.put(applyChange, DecoderConstant.EMPTY);
				stats.put(cancel, DecoderConstant.EMPTY);
				addAdvanceControlProperties(advancedControllableProperties, createButton(applyChange, DecoderConstant.APPLY, DecoderConstant.APPLYING));
				addAdvanceControlProperties(advancedControllableProperties, createButton(cancel, DecoderConstant.CANCEL, DecoderConstant.CANCELLING));
			} else {
				stats.remove(applyChange);
				stats.remove(cancel);
				stats.put(ControllingMetricGroup.STREAM.getName() + streamName + DecoderConstant.HASH + StreamControllingMetric.EDITED.getName(), "False");

				for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
					if (controllableProperty.getName().equals(applyChange)) {
						advancedControllableProperties.remove(controllableProperty);
						break;
					}
				}
				for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
					if (controllableProperty.getName().equals(cancel)) {
						advancedControllableProperties.remove(controllableProperty);
						break;
					}
				}
			}
		}
	}

	/**
	 * This method is used for populate cancel button of create stream control:
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 */
	private void populateCancelButtonForCreateStream(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		StreamInfo streamInfoDTO = defaultStream();
		Encapsulation encapsulation = createStream.getEncapsulation();
		SRTMode srtMode = createStream.getSrtMode();
		Boolean encrypted = createStream.getPassphraseSet();
		Boolean srtToUDP = createStream.getSrtToUdp();

		String cancel = ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH + StreamControllingMetric.CANCEL.getName();

		if (!streamInfoDTO.equalsByProtocol(createStream, encapsulation, srtMode, encrypted, srtToUDP)) {
			stats.put(ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH + StreamControllingMetric.EDITED.getName(), "True");
			stats.put(cancel, DecoderConstant.EMPTY);
			addAdvanceControlProperties(advancedControllableProperties, createButton(cancel, DecoderConstant.CANCEL, DecoderConstant.CANCELLING));
		} else {
			stats.remove(cancel);
			stats.put(ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH + StreamControllingMetric.EDITED.getName(), "False");

			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(cancel)) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
		}
	}

	/**
	 * This method is used to add default value of stream config to localStreamInfoList
	 */
	private StreamInfo defaultStream() {
		StreamInfo streamInfo = new StreamInfo();
		streamInfo.setName(DecoderConstant.EMPTY);
		streamInfo.setPort(DecoderConstant.MIN_PORT);
		streamInfo.setEncapsulation(Encapsulation.TS_OVER_UDP.getCode());
		streamInfo.setAddress(DecoderConstant.ADDRESS_ANY);
		streamInfo.setSourceIp(DecoderConstant.ADDRESS_ANY);
		return streamInfo;
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	// region Perform create stream control
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method is used for calling control all create stream control properties in case:
	 * <li>Stream Name</li>
	 * <li>Encapsulation</li>
	 * <li>Network type</li>
	 * <li>Port</li>
	 * <li>Multicast address</li>
	 * <li>Source address</li>
	 * <li>Source port</li>
	 * <li>Destination port</li>
	 * <li>FecRTP</li>
	 * <li>SRT mode</li>
	 * <li>Latency</li>
	 * <li>Stream conversion</li>
	 * <li>SrtToUdpAddress</li>
	 * <li>SrtToUdpPort</li>
	 * <li>SrtToUdpTos</li>
	 * <li>SrtToUdpTtl</li>
	 * <li>Encrypted</li>
	 * <li>Passphrase</li>
	 * <li>Reject unencrypted caller</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param controllableProperty name of controllable property
	 * @param value value of controllable property
	 */
	private void createStreamControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String streamControllingGroup,
			String controllableProperty, String value) {
		StreamControllingMetric streamControllingMetric = StreamControllingMetric.getByName(controllableProperty);

		switch (streamControllingMetric) {
			case STREAM_NAME:
				createStream.setName(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.STREAM_NAME.getName(), createStream.getName()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ENCAPSULATION:
				Encapsulation encapsulation = Encapsulation.getByName(value);
				removeUnusedStatsAndControlByProtocol(stats, advancedControllableProperties, createStream, streamControllingGroup);
				createStream.setEncapsulation(encapsulation.getCode());
				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case NETWORK_TYPE:
				NetworkType networkType = NetworkType.getByName(value);
				removeUnusedStatsAndControlByNetworkType(stats, advancedControllableProperties, createStream, streamControllingGroup);
				switch (networkType) {
					case UNI_CAST:
						createStream.setAddress(DecoderConstant.ADDRESS_ANY);
						createStream.setSourceIp(DecoderConstant.ADDRESS_ANY);
						break;
					case MULTI_CAST:
						createStream.setAddress(DecoderConstant.EMPTY);
						createStream.setSourceIp(DecoderConstant.EMPTY);
						break;
					default:
						break;
				}

				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case PORT:
				createStream.setPort(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.PORT.getName(), checkForNullData(createStream.getPort().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ADDRESS:
				createStream.setAddress(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.ADDRESS.getName(), createStream.getAddress()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case MULTICAST_ADDRESS:
				createStream.setAddress(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.MULTICAST_ADDRESS.getName(), createStream.getAddress()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SOURCE_ADDRESS:
				createStream.setSourceIp(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SOURCE_ADDRESS.getName(), createStream.getSourceIp()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SOURCE_PORT:
				createStream.setSourcePort(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SOURCE_PORT.getName(), checkForNullData(createStream.getSourcePort().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case DESTINATION_PORT:
				createStream.setPort(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.DESTINATION_PORT.getName(), checkForNullData(createStream.getPort().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case FEC_RTP:
				FecRTP fecRTP = FecRTP.getByName(value);
				List<String> fecRTPList = DropdownList.Names(FecRTP.class);
				createStream.setFecRtp(fecRTP.getCode());
				addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamControllingGroup + StreamControllingMetric.FEC_RTP.getName(), fecRTPList, fecRTP.getName()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_MODE:
				SRTMode srtMode = SRTMode.getByName(value);
				removeUnusedStatsAndControlBySRTMode(stats, advancedControllableProperties, createStream, streamControllingGroup);
				createStream.setSrtMode(srtMode.getCode());
				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case LATENCY:
				createStream.setLatency(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.LATENCY.getName(), checkForNullData(createStream.getLatency().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_STREAM_CONVERSION:
				Boolean srtToUDP = mapSwitchControlValue(value);
				removeUnusedStatsAndControlByStreamConversion(stats, advancedControllableProperties, createStream, streamControllingGroup);
				createStream.setSrtToUdp(srtToUDP);
				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_ADDRESS:
				createStream.setSrtToUdpAddress(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_ADDRESS.getName(), createStream.getSrtToUdpAddress()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_PORT:
				createStream.setSrtToUdpPort(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_PORT.getName(), checkForNullData(createStream.getSrtToUdpPort().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_TOS:
				createStream.setSrtToUdpTos(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_TOS.getName(), createStream.getSrtToUdpTos()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_TTL:
				createStream.setSrtToUdpTtl(Integer.parseInt(value));
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_TTL.getName(), checkForNullData(createStream.getSrtToUdpTtl().toString())));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ENCRYPTED:
				Boolean encrypted = mapSwitchControlValue(value);
				removeUnusedStatsAndControlByEncrypted(stats, advancedControllableProperties, createStream, streamControllingGroup);
				createStream.setPassphraseSet(encrypted);
				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case PASSPHRASE:
				createStream.setPassphrase(value);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.PASSPHRASE.getName(), createStream.getPassphrase()));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case REJECT_UNENCRYPTED_CALLERS:
				Boolean rejectUnencryptedCallers = mapSwitchControlValue(value);
				createStream.setStrictMode(rejectUnencryptedCallers);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(
						stats, streamControllingGroup + StreamControllingMetric.REJECT_UNENCRYPTED_CALLERS.getName(), rejectUnencryptedCallers, DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateCancelButtonForCreateStream(stats, advancedControllableProperties);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case CREATE:
				performCreateStreamControl(createStream);
				break;
			case CANCEL:
				createStream = defaultStream();
				populateCreateStreamControl(stats, advancedControllableProperties, createStream, streamControllingGroup);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + controllableProperty);
		}
	}

	/**
	 * This method is used to perform create stream control
	 *
	 * @param streamInfo set of stream config info
	 */
	private StreamInfo performCreateStreamControl(StreamInfo streamInfo) {
		StreamInfo streamInfoResult = new StreamInfo();
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = streamInfo.jsonRequest();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				StreamDataWrapper streamDataWrapper = doPost(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.STREAMS), request, StreamDataWrapper.class);
				if (streamDataWrapper == null) {
					throw new ResourceNotReachableException(DecoderConstant.CREATE_STREAM_CONTROL_ERR);
				} else {
					isCreatedStreamControl = true;
				}
			} else {
				throw new ResourceNotReachableException(DecoderConstant.CREATE_STREAM_CONTROL_ERR);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.CREATE_STREAM_CONTROL_ERR + e.getMessage());
		}
		return streamInfoResult;
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty from {@link HaivisionX4DecoderCommunicator#localExtendedStatistics}
	 *
	 * @param stats Map of statistics that contains statistics to be removed
	 * @param controls List of controls that contains AdvancedControllableProperty to be removed
	 * @param listKeys list key of statistics to be removed
	 */
	private void removeUnusedStatsAndControls(Map<String, String> stats, List<AdvancedControllableProperty> controls, List<String> listKeys) {
		for (String key : listKeys) {
			stats.remove(key);
			controls.removeIf(advancedControllableProperty -> advancedControllableProperty.getName().equals(key));
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty based on protocol:
	 * <li>TS over UDP</li>
	 * <li>TS over RTP</li>
	 * <li>TS over SRT</li>
	 *
	 * @param stats Map of statistics
	 * @param controls List of AdvancedControllableProperty
	 * @param preStreamInfo previous stream info
	 * @param groupName group name
	 */
	private void removeUnusedStatsAndControlByProtocol(Map<String, String> stats, List<AdvancedControllableProperty> controls, StreamInfo preStreamInfo, String groupName) {
		Encapsulation preEncapsulation = preStreamInfo.getEncapsulation();
		List<String> listKeyToBeRemove = new ArrayList<>();
		switch (preEncapsulation) {
			case TS_OVER_UDP:
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.NETWORK_TYPE.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.MULTICAST_ADDRESS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SOURCE_ADDRESS.getName()));
				removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
				break;
			case TS_OVER_RTP:
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.NETWORK_TYPE.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.FEC_RTP.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.MULTICAST_ADDRESS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SOURCE_ADDRESS.getName()));
				removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
				break;
			case TS_OVER_SRT:
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_MODE.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.LATENCY.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_STREAM_CONVERSION.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_ADDRESS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_TOS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_TTL.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.ENCRYPTED.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PASSPHRASE.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.REJECT_UNENCRYPTED_CALLERS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.ADDRESS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SOURCE_PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.DESTINATION_PORT.getName()));
				removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
				break;
			default:
				break;
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty based on Network type:
	 * <li>Uni-cast</li>
	 * <li>Multicast</li>
	 *
	 * @param stats Map of statistics
	 * @param controls List of AdvancedControllableProperty
	 * @param preStreamInfo previous stream info
	 * @param groupName group name
	 */
	private void removeUnusedStatsAndControlByNetworkType(Map<String, String> stats, List<AdvancedControllableProperty> controls, StreamInfo preStreamInfo, String groupName) {
		List<String> listKeyToBeRemove = new ArrayList<>();
		if (!preStreamInfo.getAddress().equals(DecoderConstant.ADDRESS_ANY) || !preStreamInfo.getSourceIp().equals(DecoderConstant.ADDRESS_ANY)) {
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.MULTICAST_ADDRESS.getName()));
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SOURCE_ADDRESS.getName()));
			removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty based on SRT mode:
	 * <li>Listener</li>
	 * <li>Caller</li>
	 * <li>Rendezvous</li>
	 *
	 * @param stats Map of statistics
	 * @param controls List of AdvancedControllableProperty
	 * @param preStreamInfo previous stream info
	 * @param groupName group name
	 */
	private void removeUnusedStatsAndControlBySRTMode(Map<String, String> stats, List<AdvancedControllableProperty> controls, StreamInfo preStreamInfo, String groupName) {
		List<String> listKeyToBeRemove = new ArrayList<>();
		SRTMode preSRTMode = preStreamInfo.getSrtMode();
		switch (preSRTMode) {
			case LISTENER:
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PORT.getName()));
				removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
				break;
			case CALLER:
			case RENDEZVOUS:
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.ADDRESS.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SOURCE_PORT.getName()));
				listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.DESTINATION_PORT.getName()));
				removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
				break;
			default:
				break;
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty based on SRT mode:
	 *
	 * @param stats Map of statistics
	 * @param controls List of AdvancedControllableProperty
	 * @param preStreamInfo previous stream info
	 * @param groupName group name
	 */
	private void removeUnusedStatsAndControlByStreamConversion(Map<String, String> stats, List<AdvancedControllableProperty> controls, StreamInfo preStreamInfo, String groupName) {
		List<String> listKeyToBeRemove = new ArrayList<>();
		if (preStreamInfo.getSrtToUdp()) {
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_ADDRESS.getName()));
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_PORT.getName()));
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_TOS.getName()));
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.SRT_TO_UDP_TTL.getName()));
			removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
		}
	}

	/**
	 * This method is used to remove unused statistics/AdvancedControllableProperty based on Encrypted:
	 *
	 * @param stats Map of statistics
	 * @param controls List of AdvancedControllableProperty
	 * @param preStreamInfo previous stream info
	 * @param groupName group name
	 */
	private void removeUnusedStatsAndControlByEncrypted(Map<String, String> stats, List<AdvancedControllableProperty> controls, StreamInfo preStreamInfo, String groupName) {
		List<String> listKeyToBeRemove = new ArrayList<>();
		if (preStreamInfo.getPassphraseSet()) {
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.PASSPHRASE.getName()));
			listKeyToBeRemove.add(String.format("%s%s", groupName, StreamControllingMetric.REJECT_UNENCRYPTED_CALLERS.getName()));
			removeUnusedStatsAndControls(stats, controls, listKeyToBeRemove);
		}
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	// region Perform stream control
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * This method is used for calling control all stream control properties in case:
	 * <li>Stream Name</li>
	 * <li>Encapsulation</li>
	 * <li>Network type</li>
	 * <li>Port</li>
	 * <li>Multicast address</li>
	 * <li>Source address</li>
	 * <li>Source port</li>
	 * <li>Destination port</li>
	 * <li>FecRTP</li>
	 * <li>SRT mode</li>
	 * <li>Latency</li>
	 * <li>Stream conversion</li>
	 * <li>SrtToUdpAddress</li>
	 * <li>SrtToUdpPort</li>
	 * <li>SrtToUdpTos</li>
	 * <li>SrtToUdpTtl</li>
	 * <li>Encrypted</li>
	 * <li>Passphrase</li>
	 * <li>Reject unencrypted caller</li>
	 *
	 * @param stats is the map that store all statistics
	 * @param advancedControllableProperties is the list that store all controllable properties
	 * @param streamControllingGroup stream controlling group
	 * @param streamName name of stream
	 * @param controllableProperty name of controllable property
	 * @param value value of controllable property
	 */
	private void streamControl(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties, String streamControllingGroup, String streamName,
			String controllableProperty, String value) {
		StreamControllingMetric streamControllingMetric = StreamControllingMetric.getByName(controllableProperty);

		Integer streamIndex = 0;
		StreamInfo streamInfo = new StreamInfo();
		for (int i = 0; i < localStreamInfoList.size(); i++) {
			streamInfo = localStreamInfoList.get(i);
			if (streamName.equals(streamInfo.getName()) || streamName.equals(streamInfo.getDefaultStreamName())) {
				streamIndex = i;
				break;
			}
		}
		Integer streamID = streamInfo.getId();

		switch (streamControllingMetric) {
			case STREAM_NAME:
				streamInfo.setName(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.STREAM_NAME.getName(), streamInfo.getName()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ENCAPSULATION:
				Encapsulation encapsulation = Encapsulation.getByName(value);
				removeUnusedStatsAndControlByProtocol(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				streamInfo.setEncapsulation(encapsulation.getCode());
				this.localStreamInfoList.set(streamIndex, streamInfo);
				populateStreamControl(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case NETWORK_TYPE:
				NetworkType networkType = NetworkType.getByName(value);
				removeUnusedStatsAndControlByNetworkType(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				switch (networkType) {
					case UNI_CAST:
						streamInfo.setAddress(DecoderConstant.ADDRESS_ANY);
						streamInfo.setSourceIp(DecoderConstant.ADDRESS_ANY);
						break;
					case MULTI_CAST:
						streamInfo.setAddress(DecoderConstant.EMPTY);
						streamInfo.setSourceIp(DecoderConstant.EMPTY);
						break;
					default:
						break;
				}
				this.localStreamInfoList.set(streamIndex, streamInfo);

				populateStreamControl(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case PORT:
				streamInfo.setPort(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.PORT.getName(), checkForNullData(streamInfo.getPort().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ADDRESS:
				streamInfo.setAddress(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.ADDRESS.getName(), streamInfo.getAddress()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case MULTICAST_ADDRESS:
				streamInfo.setAddress(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.MULTICAST_ADDRESS.getName(), streamInfo.getAddress()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SOURCE_ADDRESS:
				streamInfo.setSourceIp(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SOURCE_ADDRESS.getName(), streamInfo.getSourceIp()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SOURCE_PORT:
				streamInfo.setSourcePort(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SOURCE_PORT.getName(), checkForNullData(streamInfo.getSourcePort().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);

				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case DESTINATION_PORT:
				streamInfo.setPort(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.DESTINATION_PORT.getName(), checkForNullData(streamInfo.getPort().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case FEC_RTP:
				FecRTP fecRTP = FecRTP.getByName(value);
				List<String> fecRTPList = DropdownList.Names(FecRTP.class);
				streamInfo.setFecRtp(fecRTP.getCode());
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties, createDropdown(stats, streamControllingGroup + StreamControllingMetric.FEC_RTP.getName(), fecRTPList, fecRTP.getName()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_MODE:
				SRTMode srtMode = SRTMode.getByName(value);
				removeUnusedStatsAndControlBySRTMode(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				streamInfo.setSrtMode(srtMode.getCode());
				this.localStreamInfoList.set(streamIndex, streamInfo);
				populateStreamControl(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case LATENCY:
				streamInfo.setLatency(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.LATENCY.getName(), checkForNullData(streamInfo.getLatency().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_STREAM_CONVERSION:
				Boolean srtToUDP = mapSwitchControlValue(value);
				removeUnusedStatsAndControlByStreamConversion(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				streamInfo.setSrtToUdp(srtToUDP);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				populateStreamControl(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_ADDRESS:
				createStream.setSrtToUdpAddress(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_ADDRESS.getName(), streamInfo.getSrtToUdpAddress()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_PORT:
				streamInfo.setSrtToUdpPort(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_PORT.getName(), checkForNullData(streamInfo.getSrtToUdpPort().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_TOS:
				streamInfo.setSrtToUdpTos(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_TOS.getName(), streamInfo.getSrtToUdpTos()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case SRT_TO_UDP_TTL:
				streamInfo.setSrtToUdpTtl(Integer.parseInt(value));
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createNumeric(stats, streamControllingGroup + StreamControllingMetric.SRT_TO_UDP_TTL.getName(), checkForNullData(streamInfo.getSrtToUdpTtl().toString())));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case ENCRYPTED:
				Boolean encrypted = mapSwitchControlValue(value);
				removeUnusedStatsAndControlByEncrypted(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				streamInfo.setPassphraseSet(encrypted);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				populateStreamControl(stats, advancedControllableProperties, streamInfo, streamControllingGroup);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case PASSPHRASE:
				streamInfo.setPassphrase(value);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties,
						createText(stats, streamControllingGroup + StreamControllingMetric.PASSPHRASE.getName(), streamInfo.getPassphrase()));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case REJECT_UNENCRYPTED_CALLERS:
				Boolean rejectUnencryptedCallers = mapSwitchControlValue(value);
				streamInfo.setStrictMode(rejectUnencryptedCallers);
				this.localStreamInfoList.set(streamIndex, streamInfo);
				addAdvanceControlProperties(advancedControllableProperties, createSwitch(
						stats, streamControllingGroup + StreamControllingMetric.REJECT_UNENCRYPTED_CALLERS.getName(), rejectUnencryptedCallers, DecoderConstant.DISABLE, DecoderConstant.ENABLE));
				populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				populateLocalExtendedStats(stats, advancedControllableProperties);
				break;
			case APPLY_CHANGE:
				StreamInfo controlResult = performStreamControl(streamInfo);
				if (controlResult != null) {
					populateApplyChangeAndCancelButtonForStream(stats, advancedControllableProperties, streamID);
				}
				break;
			case CANCEL:
				Optional<StreamInfo> streamInfoDTO = this.streamInfoDTOList.stream().filter(st -> streamName.equals(st.getName())).findFirst();
				if (streamInfoDTO.isPresent()) {
					controlResult = streamInfoDTO.get();
				} else {
					controlResult = defaultStream();
				}
				this.localStreamInfoList.set(streamIndex, controlResult);
				populateStreamControl(stats, advancedControllableProperties, controlResult, streamControllingGroup);
				break;
			case DELETE:
				Boolean deleteResult = performDeleteStream(streamInfo);

				if (deleteResult){
					this.localStreamInfoList.remove(streamInfo);
					for (StreamInfo localStreamInfo : this.localStreamInfoList) {
						populateStreamControl(stats, advancedControllableProperties, localStreamInfo, ControllingMetricGroup.STREAM.getName() + localStreamInfo.getName() + DecoderConstant.HASH);
					}
				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + controllableProperty);
		}
	}

	/**
	 * This method is used to perform stream control
	 *
	 * @param streamInfo set of stream config info
	 */
	private StreamInfo performStreamControl(StreamInfo streamInfo) {
		StreamInfo streamInfoResult = new StreamInfo();
		Integer streamID = streamInfo.getId();
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = streamInfo.jsonRequest();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				StreamDataWrapper streamDataWrapper = doPut(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.STREAMS + DecoderConstant.SLASH + streamID), request, StreamDataWrapper.class);
				if (streamDataWrapper == null) {
					throw new ResourceNotReachableException(DecoderConstant.APPLY_CHANGE_STREAM_CONTROL_ERR);
				}
			} else {
				throw new ResourceNotReachableException(DecoderConstant.APPLY_CHANGE_STREAM_CONTROL_ERR);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.APPLY_CHANGE_STREAM_CONTROL_ERR + e.getMessage());
		}
		return streamInfoResult;
	}

	/**
	 * This method is used to perform delete stream control
	 *
	 * @param streamInfo set of stream config info
	 */
	private boolean performDeleteStream(StreamInfo streamInfo) {
		Integer streamID = streamInfo.getId();
		try {
			if (this.authenticationCookie.getSessionID() != null) {
				String request = streamInfo.jsonRequest();
				if (logger.isDebugEnabled()) {
					logger.debug(request);
				}
				doDelete(buildDeviceFullPath(DecoderURL.BASE_URI + DecoderURL.STREAMS + DecoderConstant.SLASH + streamID));

			} else {
				throw new ResourceNotReachableException(DecoderConstant.DELETE_STREAM_CONTROL_ERR);
			}
		} catch (Exception e) {
			throw new ResourceNotReachableException(DecoderConstant.DELETE_STREAM_CONTROL_ERR + e.getMessage());
		}

		return true;
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion

	//region Populate advanced controllable properties
	//--------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Add advancedControllableProperties if  advancedControllableProperties different empty
	 *
	 * @param advancedControllableProperties advancedControllableProperties is the list that store all controllable properties
	 * @param property the property is item advancedControllableProperties
	 */
	private void addAdvanceControlProperties(List<AdvancedControllableProperty> advancedControllableProperties, AdvancedControllableProperty property) {
		if (property != null) {
			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(property.getName())) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
			advancedControllableProperties.add(property);
		}
	}

	/**
	 * Instantiate Text controllable property
	 *
	 * @param name name of the property
	 * @param label default button label
	 * @return instance of AdvancedControllableProperty with AdvancedControllableProperty.Button as type
	 */
	private AdvancedControllableProperty createButton(String name, String label, String labelPressed) {
		AdvancedControllableProperty.Button button = new AdvancedControllableProperty.Button();
		button.setLabel(label);
		button.setLabelPressed(labelPressed);
		button.setGracePeriod(0L);
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
		if (stringValue == null) {
			stringValue = DecoderConstant.EMPTY;
		}
		stats.put(name, stringValue);
		AdvancedControllableProperty.Text text = new AdvancedControllableProperty.Text();
		return new AdvancedControllableProperty(name, new Date(), text, stringValue);
	}

	/**
	 * Create a controllable property Numeric
	 *
	 * @param name the name of property
	 * @param initialValue character String
	 * @return AdvancedControllableProperty Text instance
	 */
	private AdvancedControllableProperty createNumeric(Map<String, String> stats, String name, String initialValue) {
		stats.put(name, checkForNullData(initialValue));
		AdvancedControllableProperty.Numeric numeric = new AdvancedControllableProperty.Numeric();
		return new AdvancedControllableProperty(name, new Date(), numeric, initialValue);
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	//endregion
}