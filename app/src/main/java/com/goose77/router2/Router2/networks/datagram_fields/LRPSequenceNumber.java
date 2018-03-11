package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by goose on 3/8/2018.
 */

/**
 * Header field for LRP packets that just states what packet number this is in a sequence of LRP
 * updates
 */
public class LRPSequenceNumber implements HeaderField {
    private Integer sequenceNumber;

    /**
     * Simply parses a string into a decimal number to tell the sequence number
     * @param seqNum
     */
    public LRPSequenceNumber(String seqNum){
        sequenceNumber = Integer.parseInt(seqNum, 16);
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

    /**
     * Create a hex representation of the field
     *
     * @return String
     */
    @Override
    public String toHexString() {
        String seqNum = Integer.toHexString(sequenceNumber);
        return seqNum;
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return "LRP Sequence Number: " + toHexString();
    }
}
