/*
 *  * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderURL;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.AudioPairMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.SRTMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.AudioPair;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderStats;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.deviceinfo.DeviceInfo;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.SRT;
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
		//TODO
	}

	/**
	 * This method is called by Symphony to control the properties in the device
	 *
	 * @param list the list ControllableProperty instance
	 */
	@Override
	public void controlProperties(List<ControllableProperty> list) {
		// TODO
	}

	/**
	 * This method is called by Symphony to get the list of statistics to be displayed
	 *
	 * @return List<Statistics> This return the list of statistics
	 */
	@Override
	public List<Statistics> getMultipleStatistics() {
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		Map<String, String> stats = new HashMap<>();
		failedMonitor = new HashMap<>();
		if (authenticationCookie == null) {
			authenticationCookie = initAuthenticationCookie();
		}

		populateDecoderMonitoringMetrics(stats);
		extendedStatistics.setStatistics(stats);

		return Collections.singletonList(extendedStatistics);
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
		return value == null || value.equals("") ? DecoderConstant.NONE : value;
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
					String deviceInfoGroup = MonitoringMetricGroup.DEVICE_INFO.getName() + DecoderConstant.SPACE + DecoderConstant.HASH;

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
					DecoderStats decoderStats = decoderData.getDecoderStats();
					List<AudioPair> audioPairs = decoderStats.getAudioPairs();

					String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + DecoderConstant.SPACE + decoderID + DecoderConstant.HASH;

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
	private void updateStreamStats(Map<String, String> stats, Stream stream) {
		SRT srt = stream.getStreamStats().getSrt();
		String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + DecoderConstant.SPACE + stream.getStreamInfo().getName() + DecoderConstant.HASH;

		for (StreamMonitoringMetric item : StreamMonitoringMetric.values()) {
			stats.put(streamStatisticGroup + item.getName(), checkForNullData(stream.getValueByStreamMonitoringMetric(item)));
		}

		if (stream.getStreamStats().getSrt() != null) {
			for (SRTMetric item : SRTMetric.values()) {
				stats.put(streamStatisticGroup + item.getName(), checkForNullData(srt.getValueBySRTMonitoringMetric(item)));
			}
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
							updateStreamStats(stats, stream);
							continue;
						}

						// Stream status filtering
						if (this.streamStatus != null && !streamStatusSet.contains(streamStats.getState())) {
							continue;
						}

						// Port number filtering
						if (this.portNumber != null) {
							Integer port = Integer.parseInt(streamInfo.getPort());
							boolean isValidPort = handleAdapterPortRangeFromUser(port);
							if (!isValidPort) {
								continue;
							}
						}
						updateStreamStats(stats, stream);
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
}
