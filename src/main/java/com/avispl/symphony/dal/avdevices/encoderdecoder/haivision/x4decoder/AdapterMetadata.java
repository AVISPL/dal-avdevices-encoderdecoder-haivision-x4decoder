/*
 * Copyright (c) 2026 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;

/**
 * Adapter metadata reported in the AdapterMetadata group: adapter version and build date (from version.properties,
 * filtered by Maven) and adapter uptime. Filled from the adapter's own runtime state, never from the device.
 *
 * @author Symphony Dev Team<br>
 * @since 1.1.2
 */
public class AdapterMetadata {
	static final String GROUP = "AdapterMetadata#";
	static final String ADAPTER_VERSION = GROUP + "AdapterVersion";
	static final String ADAPTER_BUILD_DATE = GROUP + "AdapterBuildDate";
	static final String ADAPTER_UPTIME = GROUP + "AdapterUptime";
	static final String ADAPTER_UPTIME_MIN = GROUP + "AdapterUptime(min)";

	private static final String VERSION_PROPERTIES = "version.properties";
	private static final String VERSION_KEY = "adapter.version";
	private static final String BUILD_DATE_KEY = "adapter.build.date";

	private static final Log LOG = LogFactory.getLog(AdapterMetadata.class);
	private final Properties properties = new Properties();
	private final long initializationTimestamp = System.currentTimeMillis();

	/**
	 * Creates adapter metadata; the adapter uptime counts from this moment.
	 */
	public AdapterMetadata() {
		try (InputStream in = AdapterMetadata.class.getClassLoader().getResourceAsStream(VERSION_PROPERTIES)) {
			if (in != null) {
				properties.load(in);
			} else {
				LOG.warn("Unable to find " + VERSION_PROPERTIES + ", adapter version and build date are not available");
			}
		} catch (IOException e) {
			LOG.warn("Unable to read " + VERSION_PROPERTIES, e);
		}
	}

	/**
	 * Add the AdapterMetadata properties to the statistics.
	 *
	 * @param statistics statistics to add the properties to
	 */
	public void populate(Map<String, String> statistics) {
		statistics.put(ADAPTER_VERSION, valueOrNA(properties.getProperty(VERSION_KEY)));
		statistics.put(ADAPTER_BUILD_DATE, valueOrNA(properties.getProperty(BUILD_DATE_KEY)));
		long uptimeSeconds = (System.currentTimeMillis() - initializationTimestamp) / 1000;
		statistics.put(ADAPTER_UPTIME, formatUptime(uptimeSeconds));
		statistics.put(ADAPTER_UPTIME_MIN, String.valueOf(uptimeSeconds / 60));
	}

	/**
	 * Format uptime as "48 d 11 hr 5 min 38 sec", leaving out zero leading units.
	 *
	 * @param totalSeconds uptime in seconds
	 * @return formatted uptime
	 */
	static String formatUptime(long totalSeconds) {
		long days = totalSeconds / 86400;
		long hours = totalSeconds % 86400 / 3600;
		long minutes = totalSeconds % 3600 / 60;
		long seconds = totalSeconds % 60;
		StringBuilder result = new StringBuilder();
		if (days > 0) {
			result.append(days).append(" d ");
		}
		if (days > 0 || hours > 0) {
			result.append(hours).append(" hr ");
		}
		if (days > 0 || hours > 0 || minutes > 0) {
			result.append(minutes).append(" min ");
		}
		return result.append(seconds).append(" sec").toString();
	}

	private static String valueOrNA(String value) {
		return value == null || value.trim().isEmpty() || value.contains("${") ? DecoderConstant.NA : value;
	}
}
