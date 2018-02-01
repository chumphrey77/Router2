package com.goose77.router2.Router2.networks.datagram;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Interface for all datagrams
 */
public interface Datagram {
    /**
     * Returns the hex equivalent of the datagram fields
     * @return String hexString
     */
    String toHexString();

    /**
     * Returns the explanation of all fields in the datagram
     * @return String explanationString
     */
    String toProtocolExplanationString();

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     * @return String summary
     */
    String toSummaryString();

    /**
     * Returns the properly formmatted packet as a string
     * Addresses as hex
     * Type as hex
     * Payload as Ascii
     * CRC as hex
     * @return String transmissionString
     */
    String toTransmissionString();
}
