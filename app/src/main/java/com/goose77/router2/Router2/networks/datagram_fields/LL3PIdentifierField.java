package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by goose on 3/29/2018.
 */

public class LL3PIdentifierField implements HeaderField{
    private Integer identifier;

    public LL3PIdentifierField(String data){
        identifier = Integer.parseInt(data, 16);
    }

    public void incrementIdentifier(){
        identifier++;
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
        return Utilities.padHexString(Integer.toHexString(identifier), 2);
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return " Identifier: " + identifier +"\n";
    }

    public String toString(){
        return explainSelf();
    }
}
