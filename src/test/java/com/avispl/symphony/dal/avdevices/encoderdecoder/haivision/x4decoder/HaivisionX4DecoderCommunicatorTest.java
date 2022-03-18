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
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DeviceInfoMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric.DecoderControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric.StreamControllingMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.ExceptionMessage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.MonitoringData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.ControllingMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;

/**
 * Unit test for HaivisionX4DecoderCommunicator
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @version 1.0
 * @since 1.0
 */
public class HaivisionX4DecoderCommunicatorTest {
	private  final  HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicator = new HaivisionX4DecoderCommunicator();

	@BeforeEach()
	public void setUp() throws Exception {
		haivisionX4DecoderCommunicator.setHost("mx4d.demo.haivision.com");
		haivisionX4DecoderCommunicator.setPort(443);
		haivisionX4DecoderCommunicator.setLogin("operator");
		haivisionX4DecoderCommunicator.setPassword("12345678");
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
		haivisionX4DecoderCommunicatorSpy.setHost("mx4d.demo.haivision.com");
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
	 * Failed to get system info
	 * Failed to get decoder statistic3
	 * Failed to get decoder statistic1
	 * Failed to get decoder statistic2
	 * Failed to get decoder statistic0
	 * Failed to get stream statistic
	 * Failed to get device info
	 */
	@Tag("Mock")
	@Test
	void testHaivisionX4DecoderCommunicatorGetMonitoringDataFailed() {
		AuthenticationCookie authenticationCookie = new AuthenticationCookie();
		authenticationCookie.setSessionID(DecoderConstant.AUTHORIZED);
		HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicatorSpy = Mockito.spy(new HaivisionX4DecoderCommunicator());
		doReturn(authenticationCookie).when(haivisionX4DecoderCommunicatorSpy).initAuthenticationCookie();
		haivisionX4DecoderCommunicatorSpy.setHost("mx4d.demo.haivision.com");

		ResourceNotReachableException exception = Assertions.assertThrows(ResourceNotReachableException.class, () -> {
			haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_SYSTEM_INFO.getMessage()
						+ ExceptionMessage.GETTING_DECODER_STATS_ERR.getMessage()
						+ ExceptionMessage.GETTING_STREAM_STATS_ERR.getMessage()
						+ ExceptionMessage.GETTING_DEVICE_INFO.getMessage()
				,
				exception.getMessage());
	}

	/**
	 * Test HaivisionX4DecoderCommunicator adapter filtering exist stream name
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorFiltering() {
		String streamName = "SRT - WAN Listen (6515), tests";
		String portNumber = "1257-90000";

		haivisionX4DecoderCommunicator.setStreamName(streamName);
		haivisionX4DecoderCommunicator.setPortNumber(portNumber);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String streamStatisticGroup = MonitoringMetricGroup.STREAM_STATISTICS.getName() + "SRT - WAN Listen (6515)" + DecoderConstant.HASH;
		Assertions.assertEquals("SRT - WAN Listen (6515)", stats.get(streamStatisticGroup + StreamMonitoringMetric.NAME.getName()));

		String streamStatisticGroup3 = MonitoringMetricGroup.STREAM_STATISTICS.getName() + "tests" + DecoderConstant.HASH;
		Assertions.assertNull(stats.get(streamStatisticGroup3 + StreamMonitoringMetric.NAME.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty decoder control: output control (switch control)
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
	 * Test HaivisionX4Decoder.controlProperty decoder control: HDR control (dropdown control)
	 */
	@Tag("RealDevice")
	@Test
	void testSetHDRControl() {
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

	/**
	 * Test HaivisionX4Decoder.controlProperty decoder control: still image delay (text control)
	 */
	@Tag("RealDevice")
	@Test
	void testStillImageDelayControl() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("Decoder0" + "#StillImageDelay");
		controllableProperty.setValue("10000");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.DECODER.getName() + 0 + DecoderConstant.HASH;
		Assertions.assertEquals("1000", stats.get(decoderControllingGroup + DecoderControllingMetric.STILL_IMAGE_DELAY.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty create stream: protocol
	 */
	@Tag("RealDevice")
	@Test
	void testCreateStreamControlEncapsulation() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("CreateStream" + "#Protocol");
		controllableProperty.setValue("TS over SRT");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH;
		Assertions.assertEquals("TS over SRT", stats.get(decoderControllingGroup + StreamControllingMetric.ENCAPSULATION.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty create stream: stream name
	 */
	@Tag("RealDevice")
	@Test
	void testCreateStreamControlStreamName() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("CreateStream" + "#SrtToUdpTos");
		controllableProperty.setValue("254");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH;
		Assertions.assertEquals("Harry test", stats.get(decoderControllingGroup + StreamControllingMetric.STREAM_NAME.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty create stream: port
	 */
	@Tag("RealDevice")
	@Test
	void testCreateStreamControlPort() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("CreateStream" + "#Port");
		controllableProperty.setValue("1725");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		controllableProperty.setProperty("CreateStream" + "#Create");
		controllableProperty.setValue("1725");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.CREATE_STREAM.getName() + DecoderConstant.HASH;
		Assertions.assertEquals("1725", stats.get(decoderControllingGroup + StreamControllingMetric.PORT.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty create stream: protocol
	 */
	@Tag("RealDevice")
	@Test
	void testStreamControlEncapsulation() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("Streamharry-test" + "#Protocol");
		controllableProperty.setValue("TS over SRT");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		controllableProperty.setProperty("Streamharry-test" + "#Cancel");
		controllableProperty.setValue("0");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.STREAM.getName() + "harry-test" +DecoderConstant.HASH;
		Assertions.assertEquals("TS over RTP", stats.get(decoderControllingGroup + StreamControllingMetric.ENCAPSULATION.getName()));
	}

	/**
	 * Test HaivisionX4Decoder.controlProperty create stream: stream name
	 */
	@Tag("RealDevice")
	@Test
	void testStreamControlStreamName() {
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty("StreamSRT - WAN Listen (6516)" + "#SrtToUdpStreamConversion");
		controllableProperty.setValue("0");

		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);
		haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);

		controllableProperty.setProperty("StreamSRT - WAN Listen (6516)" + "#ApplyChange");
		controllableProperty.setValue("0");
		haivisionX4DecoderCommunicator.controlProperty(controllableProperty);

		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderControllingGroup = ControllingMetricGroup.STREAM.getName() + "harry-test" + DecoderConstant.HASH;
		Assertions.assertEquals("0", stats.get(decoderControllingGroup + StreamControllingMetric.SRT_TO_UDP_STREAM_CONVERSION.getName()));
	}
}
