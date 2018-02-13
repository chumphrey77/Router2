package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;
/**
 * Created by goose on 1/25/2018.
 */

public class LL2PAddressField implements HeaderField {
    private Integer address;
    private boolean isSourceAddress;
    private String explanation;

    /**
     * Generate a debugging string and return it
     * @return String
     */
    public String toString(){
        String toStringString;
        toStringString = "The address field is:" + address + "\n" + "The Source Address is:" + isSourceAddress + "\n" + "The explanation is:" + explanation + "\n";
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
        String hexString = Integer.toHexString(address);
        hexString = Utilities.padHexString(hexString, Constants.ADDR_LENGTh);
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
     * Integer Constrcutor that takes the address as an int
     * @param Integer address
     * @param boolean isSource
     */
    public LL2PAddressField(Integer address, boolean isSource){
        this.address = address;
        this.isSourceAddress = isSource;
        this.setExplanation();
    }

    /**
     * Primaty Constructor that takes the address as a string and turns it into an int
     * @param String address
     * @param boolean isSource
     */
    public LL2PAddressField(String address, boolean isSource){
        this.address = Integer.valueOf(address,16);
        this.isSourceAddress = isSource;
        this.setExplanation();
    }

    /**
     * Create the explanation field
     */
    private void setExplanation(){
        this.explanation = "LL2P Address Field. Address = " + toHexString()+"\n";
    }

    /**
     * Returns wether or not thet object is a source address
     * True = src address
     * False = destination address
     * @return
     */
    public boolean isSourceAddressField(){
        return this.isSourceAddress;
    }

    public Integer getAddress() {
        return address;
    }
}
