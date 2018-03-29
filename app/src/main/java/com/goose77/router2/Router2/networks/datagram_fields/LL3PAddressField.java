package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by Colten on 2/22/2018.
 */

/**
 * The address field for the 3rd layer
 * The address is a 2byte hex number
 * The first byte network number
 * The Second byte is the host number
 * Also stores if this address is the source address
 */
public class LL3PAddressField implements HeaderField{
    private Integer address;
    private Integer networkNumber;
    private Integer hostNumber;
    private Boolean isSourceAddress;
    private String explanationStirng="";

    /**
     * Constructor that splits the concatinated address into the separate network and host numbers
     * Also saves whether it is a source address or not
     * @param addr
     * @param isSrcAddr
     */
    public LL3PAddressField(String addr, Boolean isSrcAddr){
        this.isSourceAddress = isSrcAddr;
        address = Integer.parseInt(addr, 16);
       calculateAddrs(addr);
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
        return Utilities.padHexString(Integer.toHexString(address),Constants.LL3P_ADDR_BYTE_LENGTH);
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return explanationStirng;
    }

    private void calculateAddrs(String addr){
        networkNumber = Integer.parseInt(addr.substring(0, 2), 16);
        hostNumber = Integer.parseInt(addr.substring(2), 16);
    }

    /**
     * Builds the explanation string
     */
    private void buildExplanation(){
        explanationStirng = "Network Number: " + Utilities.toHexString(networkNumber.toString());
        explanationStirng+= " | Host Number: " + Utilities.toHexString(hostNumber.toString());
        if(isSourceAddress){
            explanationStirng+= " | Is Source Address";
        }
        else{
            explanationStirng+=" | Is NOT source Address";
        }
    }

    public Integer getKey(){
        return address;
    }

    public Boolean getIsSourceAddress(){
        return isSourceAddress;
    }
}
