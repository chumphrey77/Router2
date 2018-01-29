package com.goose77.router2.networks.networks.datagram_fields;

import com.goose77.router2.networks.Constants;
import com.goose77.router2.networks.headerFields.HeaderField;
import com.goose77.router2.support.Utilities;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Encapsulates the CRC value of a frame
 */
public class CRC implements HeaderField {
    private String crcValue;
    private String explanation;

    /**
     * Generate a debugging string and return it
     * @return String
     */
    public String toString(){
        String toStringString;
        toStringString = "The CRC value is:" + this.crcValue + "\n";
        return toStringString;
    }

    /**
     * Generate the string that will be transmitted in frames
     * @return String
     */
    @Override
    public String toTransmissionString() {
        return toHexString();
    }

    /**
     * Create a hex representation of the field
     * @return String
     */
    @Override
    public String toHexString() {
        int hexint = Integer.parseInt(crcValue, 16);
        String hexString = Integer.toHexString(hexint);
        hexString = Utilities.padHexString(hexString, Constants.CRC_LENGTH);
        return hexString;
    }

    /**
     * Give an explanation of what this field is
     * @return String
     */
    @Override
    public String explainSelf() {
        setExplanation();
        return explanation;
    }

    /**
     * Constructor for the CRCField, gets and sets the initial value
     * Truncates the value to 4 characters if it is more than 4 characters
     * Pads the value with 0s if it has fewer than 4 characters
     * @param typeValueString
     */
    public CRC(String typeValueString){
        this.crcValue = typeValueString.substring(0, Math.min(typeValueString.length(), 4));
        this.crcValue = Utilities.padHexString(crcValue, 2);
        this.setExplanation();
    }

    private void setExplanation(){
        this.explanation = "The CRC is: " + this.crcValue + "\n";
    }
}

