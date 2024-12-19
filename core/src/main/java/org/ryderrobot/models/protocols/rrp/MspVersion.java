package org.ryderrobot.models.protocols.rrp;

/**
 * Used by factories to know which drone configuration to use. Provides details regarding drone.
 *
 * Identification is broken up as follows:
 * SKU(MultiType)(Version)
 *
 * For example SKULD001 represents SKU - Land Drone - version 001
 * SKUQC001 represents Quad copter - version 001
 */
public enum MspVersion {
    VIRTUAL,
    SKULD001,
    SKULD002,
}
