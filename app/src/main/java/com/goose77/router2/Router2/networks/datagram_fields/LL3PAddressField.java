package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by Colten on 2/22/2018.
 */

public class LL3PAddressField implements HeaderField{
    private Integer address;
    private Integer networkNumber;
    private Integer hostNumber;
    private Boolean isSourceAddress;
    private String explanationStirng="";


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
        return Utilities.toHexString(address.toString());
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
        networkNumber = Integer.parseInt(addr.substring(0, 4), 16);
        hostNumber = Integer.parseInt(addr.substring(5), 16);
    }

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
}
