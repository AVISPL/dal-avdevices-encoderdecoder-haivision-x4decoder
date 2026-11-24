/*
 * Copyright (c) 2026 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * AdapterMetadata group: version and build date from version.properties, adapter uptime from the adapter's own runtime.
 *
 * @since 1.1.2
 */
@Tag("unitTest")
public class AdapterMetadataTest {

	@Test
	void testMetadataProperties() {
		AdapterMetadata metadata = new AdapterMetadata();
		Map<String, String> statistics = new HashMap<>();
		metadata.populate(statistics);

		String version = statistics.get("AdapterMetadata#AdapterVersion");
		Assertions.assertNotNull(version);
		Assertions.assertFalse(version.contains("${"), "version.properties must be filtered by Maven: " + version);
		Assertions.assertTrue(statistics.get("AdapterMetadata#AdapterBuildDate").matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"),
				statistics.get("AdapterMetadata#AdapterBuildDate"));
		Assertions.assertEquals("0", statistics.get("AdapterMetadata#AdapterUptime(min)"));
		Assertions.assertNotNull(statistics.get("AdapterMetadata#AdapterUptime"));
	}

	@Test
	void testUptimeFormat() {
		Assertions.assertEquals("48 d 11 hr 5 min 38 sec", AdapterMetadata.formatUptime(48L * 86400 + 11 * 3600 + 5 * 60 + 38));
		Assertions.assertEquals("5 min 2 sec", AdapterMetadata.formatUptime(5 * 60 + 2));
		Assertions.assertEquals("1 hr 0 min 0 sec", AdapterMetadata.formatUptime(3600));
		Assertions.assertEquals("0 sec", AdapterMetadata.formatUptime(0));
	}

	@Test
	void testDeviceReportsMetadata() throws Exception {
		HaivisionX4DecoderCommunicator communicator = new HaivisionX4DecoderCommunicator();
		Map<String, String> statistics = new HashMap<>();
		java.lang.reflect.Method method = HaivisionX4DecoderCommunicator.class.getDeclaredMethod("populateAdapterMetadata", Map.class);
		method.setAccessible(true);
		method.invoke(communicator, statistics);
		Assertions.assertTrue(statistics.containsKey("AdapterMetadata#AdapterVersion"));
		Assertions.assertTrue(statistics.containsKey("AdapterMetadata#AdapterUptime(min)"));
	}
}
