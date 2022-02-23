/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

import java.util.UUID;

/**
 * Normalized Data
 *
 * @author Harry
 * @since 1.0
 */
public class NomalizeData {
	private final String uuidDay = UUID.randomUUID().toString().replace(DecoderConstant.DASH, "");
	private final String uuidSeconds = UUID.randomUUID().toString().replace(DecoderConstant.DASH, "");
	/**
	 * Format time data
	 *
	 * @param time the time is String
	 * @return String
	 */
	public String formatTimeData(String time) {
		if (DecoderConstant.NONE.equals(time)) {
			return DecoderConstant.NONE;
		}
		int index = time.indexOf(DecoderConstant.SPACE);
		if (index > -1) {
			time = time.substring(0, index + 1);
		}
		time.replace("d", uuidDay).replace("s", uuidSeconds);
		return time.replace(uuidDay, DecoderConstant.DAY).replace("h", DecoderConstant.HOUR).replace("m", DecoderConstant.MINUTE).replace(uuidSeconds, DecoderConstant.SECOND);
	}
}
