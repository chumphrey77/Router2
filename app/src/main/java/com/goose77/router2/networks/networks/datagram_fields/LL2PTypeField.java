package com.goose77.router2.networks.networks.datagram_fields;

import com.goose77.router2.networks.Constants;
import com.goose77.router2.networks.headerFields.HeaderField;
import com.goose77.router2.support.Utilities;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Encapsulates the type of frame that is being transmitted
 */
public class LL2PTypeField implements HeaderField {
    private Integer type;
    private String explanation;
    private final int id = Constants.LL2P_TYPE_FIELD_ID;

    /**
     * Generate a debugging string and return it
     * @return String
     */
    public String toString(){
        String toStringString;
        toStringString = "The type field is:" + type + "\n" + "The explanation is:" + explanation + "\n";
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
        String hexString = Integer.toHexString(type);
        hexString = Utilities.padHexString(hexString, Constants.TYPE_LENGTH);
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
     * Constructor that takes in an int and uses it
     * @param Integer typeField
     */
    public LL2PTypeField(Integer typeField){
        this.type = typeField;
    }

    /**
     * Primary constructor that uses a String to create the object
     * Parses the input String to be an Int then saves it
     * @param String typevalueString
     */
    public LL2PTypeField(String typevalueString){
        this.type = Integer.valueOf(typevalueString,16);
        this.setExplanation();
    }

    /**
     * Creates the explanation field
     */
    private void setExplanation(){
        this.explanation = "The type is: " + toHexString();
    }
}
