/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common;

import java.util.UUID;

/**
 * Normalizing Data
 *
 * @author Harry
 * @since 1.0
 */
public class NormalizeData {
	private final String uuidDay = UUID.randomUUID().toString().replace(DecoderConstant.DASH, "");
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
		return time.replace("d", uuidDay).replace("s", DecoderConstant.SECOND).replace(uuidDay, DecoderConstant.DAY)
				.replace("h", DecoderConstant.HOUR).replace("m", DecoderConstant.MINUTE);
	}
}
