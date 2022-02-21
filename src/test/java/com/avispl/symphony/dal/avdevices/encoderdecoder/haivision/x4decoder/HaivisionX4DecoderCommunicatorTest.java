/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import static org.mockito.Mockito.doReturn;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.FilteringErrorGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.StreamConfigMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.ExceptionMessage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.FilteringData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.MonitoringData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;

/**
 * Unit test for HaivisionX4DecoderCommunicator
 *
 * @author Harry
 * @version 1.0
 * @since 1.0
 */
public class HaivisionX4DecoderCommunicatorTest {
	private  final  HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicator = new HaivisionX4DecoderCommunicator();

	@BeforeEach()
	public void setUp() throws Exception {
		haivisionX4DecoderCommunicator.setHost("***REMOVED***");
		haivisionX4DecoderCommunicator.setPort(443);
		haivisionX4DecoderCommunicator.setLogin("operator");
		haivisionX4DecoderCommunicator.setPassword("supervisor");
		haivisionX4DecoderCommunicator.init();
		haivisionX4DecoderCommunicator.connect();
	}

	@AfterEach()
	public void destroy() throws Exception {
		haivisionX4DecoderCommunicator.disconnect();
	}

	/**
	 * Test HaivisionX4DecoderCommunicator.getMultipleStatistics successful with valid username password
	 * Expected retrieve valid device monitoring data
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorGetMonitoringDataSuccessful() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String deviceInfoGroup = MonitoringMetricGroup.DEVICE_INFO.getName() + DecoderConstant.SPACE + DecoderConstant.HASH;
		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + DecoderConstant.SPACE + 1 + DecoderConstant.HASH;

		Assertions.assertEquals(MonitoringData.SERIAL_NUMBER.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.SERIAL_NUMBER.getName()));
		Assertions.assertEquals(MonitoringData.HARDWARE_REVISION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.HARDWARE_REVISION.getName()));
		Assertions.assertEquals(MonitoringData.PART_NUMBER.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.PART_NUMBER.getName()));
		Assertions.assertEquals(MonitoringData.FIRMWARE_VERSION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_VERSION.getName()));
		Assertions.assertEquals(MonitoringData.DECODER_ID.getData(), stats.get(decoderStatisticGroup + DecoderMonitoringMetric.DECODER_ID.getName()));
	}

	/**
	 * Test HaivisionX4DecoderCommunicator.getMultipleStatistics will throw ResourceNotReachableException when Username or Password are incorrect
	 * Expected throw ResourceNotReachableException "Username and Password are incorrect";
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorLogInFailed() {
		HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicatorSpy = Mockito.spy(HaivisionX4DecoderCommunicator.class);
		haivisionX4DecoderCommunicatorSpy.setHost("***REMOVED***");
		Mockito.when(haivisionX4DecoderCommunicatorSpy.getLogin()).thenReturn("123");
		Mockito.when(haivisionX4DecoderCommunicatorSpy.getPassword()).thenReturn("456");
		ResourceNotReachableException exception = Assertions.assertThrows(ResourceNotReachableException.class, () -> {
			ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_SESSION_ID_ERRO.getMessage(), exception.getMessage());
	}

	/**
	 * Test HaivisionX4DecoderCommunicator.getMultipleStatistics get errors when retrieving decoders statistic and streams statistics
	 * Expected throw ResourceNotReachableException
	 * failed to get decoder statistic3
	 * failed to get decoder statistic1
	 * failed to get decoder statistic2
	 * failed to get decoder statistic0
	 * failed to get stream statistic
	 * failed to get device info
	 */
	@Tag("Mock")
	@Test
	void testHaivisionX4DecoderCommunicatorGetMonitoringDataFailed() {
		AuthenticationCookie authenticationCookie = new AuthenticationCookie();
		authenticationCookie.setSessionID(DecoderConstant.AUTHORIZED);
		HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicatorSpy = Mockito.spy(new HaivisionX4DecoderCommunicator());
		doReturn(authenticationCookie).when(haivisionX4DecoderCommunicatorSpy).initAuthenticationCookie();
		haivisionX4DecoderCommunicatorSpy.setHost("***REMOVED***");

		ResourceNotReachableException exception = Assertions.assertThrows(ResourceNotReachableException.class, () -> {
			ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_DECODER_STATS_ERR.getMessage() + ExceptionMessage.GETTING_STREAM_STATS_ERR.getMessage() + ExceptionMessage.GETTING_DEVICE_INFO.getMessage(),
				exception.getMessage());
	}

	/**
	 * Test HaivisionX4DecoderCommunicator adapter filtering in case having 3 filtering
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorFiltering() throws Exception {
		String streamName = "Harry-test, test, test  test , test    ";
		String streamsStatus = "ACTIVE, TEST";
		String portNumber = "1234, 1257-9000, 1534, 9000000-80000000, 90000-50000, 6009-ad";

		haivisionX4DecoderCommunicator.setStreamName(streamName);
		haivisionX4DecoderCommunicator.setStreamStatus(streamsStatus);
		haivisionX4DecoderCommunicator.setPortNumber(portNumber);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(),
				stats.get(FilteringErrorGroup.STREAM_NAME.getName() + DecoderConstant.HASH + StreamMonitoringMetric.NAME.getName() + DecoderConstant.SPACE + "test"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(),
				stats.get(FilteringErrorGroup.STREAM_NAME.getName() + DecoderConstant.HASH + StreamMonitoringMetric.NAME.getName() + DecoderConstant.SPACE + "test  test"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamMonitoringMetric.STATE.getName() + DecoderConstant.SPACE + "TEST"));
		Assertions.assertNull(stats.get(FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamMonitoringMetric.STATE.getName() + DecoderConstant.SPACE + "ACTIVE"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamConfigMetric.PORT.getName() + DecoderConstant.SPACE + "1234"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamConfigMetric.PORT.getName() + DecoderConstant.SPACE + "1534"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamConfigMetric.PORT.getName() + DecoderConstant.SPACE + "9000000-80000000"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamConfigMetric.PORT.getName() + DecoderConstant.SPACE + "90000-50000"));
		Assertions.assertEquals(FilteringData.INVALID_INPUT.getData(), stats.get(
				FilteringErrorGroup.STREAM_STATUS_AND_PORT_NUMBER.getName() + DecoderConstant.HASH + StreamConfigMetric.PORT.getName() + DecoderConstant.SPACE + "6009-ad"));
	}

}
