package com.goose77.router2.Router2.networks.datagram;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Interface for all of the Header Fields for frames
 */
public interface HeaderField {
    /**
     * Generate a debugging string and return it
     * @return String
     */
    String toString();

    /**
     * Generate the string that will be transmitted in frames
     * @return String
     */
    String toTransmissionString();

    /**
     * Create a hex representation of the field
     * @return String
     */
    String toHexString();

    /**
     * Give an explanation of what this field is
     * @return String
     */
    String explainSelf();
}
