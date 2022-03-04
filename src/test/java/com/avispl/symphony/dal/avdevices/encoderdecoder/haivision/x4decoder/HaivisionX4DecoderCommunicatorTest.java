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

import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.ControllingMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.DecoderControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.ExceptionMessage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.MonitoringData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;
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
	void testHaivisionX4DecoderCommunicatorGetMonitoringDataSuccessful() {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String deviceInfoGroup = MonitoringMetricGroup.DEVICE_INFO.getName() + DecoderConstant.HASH;
		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + 1 + DecoderConstant.HASH;

		Assertions.assertEquals(MonitoringData.SERIAL_NUMBER.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.SERIAL_NUMBER.getName()));
		Assertions.assertEquals(MonitoringData.HARDWARE_REVISION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.HARDWARE_REVISION.getName()));
		Assertions.assertEquals(MonitoringData.PART_NUMBER.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.PART_NUMBER.getName()));
		Assertions.assertEquals(MonitoringData.FIRMWARE_VERSION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_VERSION.getName()));
		Assertions.assertEquals(MonitoringData.HARDWARE_COMPATIBILITY.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.HARDWARE_COMPATIBILITY.getName()));
		Assertions.assertEquals(MonitoringData.CPLD_REVISION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.CPLD_REVISION.getName()));
		Assertions.assertEquals(MonitoringData.BOOT_VERSION.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.BOOT_VERSION.getName()));
		Assertions.assertEquals(MonitoringData.CARD_TYPE.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.CARD_TYPE.getName()));
		Assertions.assertEquals(MonitoringData.FIRMWARE_DATE.getData(), stats.get(deviceInfoGroup + DeviceInfoMetric.FIRMWARE_DATE.getName()));
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
			 haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
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
			haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_DECODER_STATS_ERR.getMessage() + ExceptionMessage.GETTING_STREAM_STATS_ERR.getMessage() + ExceptionMessage.GETTING_DEVICE_INFO.getMessage(),
				exception.getMessage());
	}

	/**
	 * Test HaivisionX4DecoderCommunicator adapter filtering in case having 3 filtering
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorFiltering() {
		String streamName = "SRT - WAN Listen (6515), test";
		String portNumber = "1257-90000";

		haivisionX4DecoderCommunicator.setStreamName(streamName);
		haivisionX4DecoderCommunicator.setPortNumber(portNumber);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + "SRT - WAN Listen (6515)" + DecoderConstant.HASH;
		Assertions.assertEquals("SRT - WAN Listen (6515)", stats.get(streamStatisticGroup + StreamMonitoringMetric.NAME.getName()));

		String streamStatisticGroup3 = MonitoringMetricGroup.STREAM_STATISTICS.getName() + "test" + DecoderConstant.HASH;
		Assertions.assertNull(stats.get(streamStatisticGroup3 + StreamMonitoringMetric.NAME.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty case output control
	 */
	@Tag("RealDevice")
	@Test
	void testSetOutputControl() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("Decoder0" + "#Output2");
		controllableProperty.setValue("1");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + 0 + DecoderConstant.HASH;
		Assertions.assertEquals("1", stats.get(decoderControllingGroup + DecoderControllingMetric.OUTPUT_2.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty HDR control
	 */
	@Tag("RealDevice")
	@Test
	void testSetCancelControl() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("Decoder0" + DecoderConstant.HASH + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName() );
		controllableProperty.setValue("ForcePQ");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + 0 + DecoderConstant.HASH;
		Assertions.assertEquals("ForcePQ", stats.get(decoderControllingGroup + DecoderControllingMetric.HDR_DYNAMIC_RANGE.getName()));
	}
}
