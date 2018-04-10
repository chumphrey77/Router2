package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;

/**
 * Created by goose on 3/29/2018.
 */

public class LL3PChecksum implements HeaderField{
    private String checksum;

    public LL3PChecksum(String data){
        checksum = data;
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
        return checksum;
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return " Checksum: " + checksum + "\n";
    }

    public String toString(){
        return explainSelf();
    }
}
