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
    DecoderBufferingVideoLatency("DecoderBufferingVideoLatency(ms)"),
    DecoderAudioPair1Bitrate("DecoderAudioPair1Bitrate(kbps)"),
    DecoderAudioPair2Bitrate("DecoderAudioPair2Bitrate(kbps)"),
    DecoderAudioPair3Bitrate("DecoderAudioPair3Bitrate(kbps)"),
    DecoderAudioPair4Bitrate("DecoderAudioPair4Bitrate(kbps)"),
    DecoderAudioPair5Bitrate("DecoderAudioPair5Bitrate(kbps)"),
    DecoderAudioPair6Bitrate("DecoderAudioPair6Bitrate(kbps)"),
    DecoderAudioPair7Bitrate("DecoderAudioPair7Bitrate(kbps)"),
    DecoderAudioPair8Bitrate("DecoderAudioPair8Bitrate(kbps)"),
    DecoderAudioPair9Bitrate("DecoderAudioPair9Bitrate(kbps)"),
    DecoderAudioPair10Bitrate("DecoderAudioPair10Bitrate(kbps)"),
    DecoderAudioPair11Bitrate("DecoderAudioPair11Bitrate(kbps)"),
    DecoderAudioPair12Bitrate("DecoderAudioPair12Bitrate(kbps)"),
    DecoderAudioPair13Bitrate("DecoderAudioPair13Bitrate(kbps)"),
    DecoderAudioPair14Bitrate("DecoderAudioPair14Bitrate(kbps)"),
    DecoderAudioPair15Bitrate("DecoderAudioPair15Bitrate(kbps)"),
    DecoderAudioPair16Bitrate("DecoderAudioPair16Bitrate(kbps)"),
    Audio1Bitrate("Audio1Bitrate(kbps)"),
    Audio2Bitrate("Audio2Bitrate(kbps)"),
    Audio3Bitrate("Audio3Bitrate(kbps)"),
    Audio4Bitrate("Audio4Bitrate(kbps)"),
    Audio5Bitrate("Audio5Bitrate(kbps)"),
    Audio6Bitrate("Audio6Bitrate(kbps)"),
    Audio7Bitrate("Audio7Bitrate(kbps)"),
    Audio8Bitrate("Audio8Bitrate(kbps)"),
    Audio9Bitrate("Audio9Bitrate(kbps)"),
    Audio10Bitrate("Audio10Bitrate(kbps)"),
    Audio11Bitrate("Audio11Bitrate(kbps)"),
    Audio12Bitrate("Audio12Bitrate(kbps)"),
    Audio13Bitrate("Audio13Bitrate(kbps)"),
    Audio14Bitrate("Audio14Bitrate(kbps)"),
    Audio15Bitrate("Audio15Bitrate(kbps)"),
    Audio16Bitrate("Audio16Bitrate(kbps)"),
    Video1BitrateMbps("Video1Bitrate(Mbps)"),
    Video2BitrateMbps("Video2Bitrate(Mbps)"),
    Video3BitrateMbps("Video3Bitrate(Mbps)"),
    Video4BitrateMbps("Video4Bitrate(Mbps)"),
    Video5BitrateMbps("Video5Bitrate(Mbps)"),
    Video6BitrateMbps("Video6Bitrate(Mbps)"),
    Video7BitrateMbps("Video7Bitrate(Mbps)"),
    Video8BitrateMbps("Video8Bitrate(Mbps)"),
    Video9BitrateMbps("Video9Bitrate(Mbps)"),
    Video10BitrateMbps("Video10Bitrate(Mbps)"),
    Video11BitrateMbps("Video11Bitrate(Mbps)"),
    Video12BitrateMbps("Video12Bitrate(Mbps)"),
    Video13BitrateMbps("Video13Bitrate(Mbps)"),
    Video14BitrateMbps("Video14Bitrate(Mbps)"),
    Video15BitrateMbps("Video15Bitrate(Mbps)"),
    Video16BitrateMbps("Video16Bitrate(Mbps)"),
    DecoderAudioPair1AvSyncMs("DecoderAudioPair1AvSyncMs(ms)"),
    DecoderAudioPair2AvSyncMs("DecoderAudioPair2AvSyncMs(ms)"),
    DecoderAudioPair3AvSyncMs("DecoderAudioPair3AvSyncMs(ms)"),
    DecoderAudioPair4AvSyncMs("DecoderAudioPair4AvSyncMs(ms)"),
    DecoderAudioPair5AvSyncMs("DecoderAudioPair5AvSyncMs(ms)"),
    DecoderAudioPair6AvSyncMs("DecoderAudioPair6AvSyncMs(ms)"),
    DecoderAudioPair7AvSyncMs("DecoderAudioPair7AvSyncMs(ms)"),
    DecoderAudioPair8AvSyncMs("DecoderAudioPair8AvSyncMs(ms)"),
    DecoderAudioPair9AvSyncMs("DecoderAudioPair9AvSyncMs(ms)"),
    DecoderAudioPair10AvSyncMs("DecoderAudioPair10AvSyncMs(ms)"),
    DecoderAudioPair11AvSyncMs("DecoderAudioPair11AvSyncMs(ms)"),
    DecoderAudioPair12AvSyncMs("DecoderAudioPair12AvSyncMs(ms)"),
    DecoderAudioPair13AvSyncMs("DecoderAudioPair13AvSyncMs(ms)"),
    DecoderAudioPair14AvSyncMs("DecoderAudioPair14AvSyncMs(ms)"),
    DecoderAudioPair15AvSyncMs("DecoderAudioPair15AvSyncMs(ms)"),
    DecoderAudioPair16AvSyncMs("DecoderAudioPair16AvSyncMs(ms)"),
    MultisyncDelayActual("MultisyncDelayActual(ms)"),
    MultisyncTransmissionTime("MultisyncTransmissionTime(ms)"),
    VideoLoadPercentage("VideoLoadPercentage(%)"),
    GeneralBitrateMbps("GeneralBitRate(Mbps)"),
    SRTBuffer("SRTBuffer(ms)"),
    SRTLatency("SRTLatency(ms)"),
    SRTPathMaxBandwidth("SRTPathMaxBandwidth(kbps)");

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
