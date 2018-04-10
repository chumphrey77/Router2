package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by goose on 3/8/2018.
 */

/**
 * Header object that tells the destination network number and the number of hops needed to get to it
 */
public class NetworkDistancePair implements HeaderField {
    private Integer network;
    private Integer pairInt;
    private Integer distance;

    /**
     * Takes a string and parses it into the a network number for the network and the number
     * of hops it will take to get to that network
     * @param pair
     */
    public NetworkDistancePair(String pair){
        network = Integer.parseInt(pair.substring(Constants.NETWORK_CHAR_OFFSET,Constants.DISTANCE_CHAR_OFFSET),16);
        distance = Integer.parseInt(pair.substring(Constants.DISTANCE_CHAR_OFFSET),16);
        pairInt = Integer.parseInt(pair, 16);
    }
    /**
     * Generate the string that will be transmitted in frames
     *
     * @return String
     */
    @Override
    public String toTransmissionString() {
        return toHexString();
    }

    public Integer getPairInt(){
        return pairInt;
    }
    /**
     * Create a hex representation of the field
     *
     * @return String
     */
    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(network),Constants.NETWORK_DISTANCE_PAIR_BYTE_PAD/2) +
                Utilities.padHexString(Integer.toHexString(distance),Constants.NETWORK_DISTANCE_PAIR_BYTE_PAD/2);
    }

    public String toString(){
        return "Network number: " + Utilities.padHexString(Integer.toHexString(network),Constants.NETWORK_DISTANCE_PAIR_BYTE_PAD/2)
                + " Distance: " + distance;
    }
    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return "Network Num: " + Utilities.padHexString(Integer.toHexString(network),1) + "\n" + "Route Distance: " +Utilities.padHexString(Integer.toHexString(distance), 1) + "\n";
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getNetwork() {
        return network;
    }
}
