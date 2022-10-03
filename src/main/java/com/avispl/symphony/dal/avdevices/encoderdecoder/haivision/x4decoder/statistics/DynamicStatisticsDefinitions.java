/*
 * Copyright (c) 2022 AVI-SPL Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.statistics;

import java.util.Arrays;
import java.util.Optional;

/**
 * DynamicStatisticsDefinitions contains definitions for Dynamic property candidates.
 * Values, defined here, grant availability of certain properties as historical/dynamic.
 *
 * @author Maksym.Rossiitsev / Symphony Dev Team<br>
 * @since 1.1.0
 * */
public enum DynamicStatisticsDefinitions {
    Temperature("Temperature"),
    VideoLatency("DecoderBufferingVideoLatency(ms)"),
    BitrateKbps("Bitrate(kbps)"),
    BitrateMbps("Bitrate(Mbps)"),
    GeneralBitRateMbps("GeneralBitRate(Mbps)"),
    AvSyncMs("AvSyncMs"),
    MultisyncDelayActual("MultisyncDelayActual(ms)"),
    MultisyncTransmissionTime("MultisyncTransmissionTime(ms)"),
    VideoLoadPercentage("VideoLoadPercentage");

    private final String name;
    DynamicStatisticsDefinitions(final String name) {
        this.name = name;
    }

    /**
     * Retrieves {@link #name}
     *
     * @return value of {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * Check if dynamic property definition exists, by name.
     * The reason the validation is made by "endsWith" is that the group names may be dynamic,
     * so not to expand properties definitions more and making them too vast and vague - we have a specific
     * definitions, ignoring the group names.
     *
     * @param name of the property to check
     * @return true if definition exists, false otherwise
     * */
    public static boolean checkIfExists(String name) {
        Optional<DynamicStatisticsDefinitions> dynamicStatisticsProperty = Arrays.stream(DynamicStatisticsDefinitions.values()).filter(c -> name.endsWith(c.getName())).findFirst();
        if (dynamicStatisticsProperty.isPresent()) {
            return true;
        }
        return false;
    }
}
