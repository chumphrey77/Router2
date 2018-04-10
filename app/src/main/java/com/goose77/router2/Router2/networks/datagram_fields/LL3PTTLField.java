package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by goose on 3/29/2018.
 */

public class LL3PTTLField implements HeaderField{
   private Integer ttl;

    public LL3PTTLField(String data){
        ttl = Integer.parseInt(data, 16);
    }

    public void decrementTTL(){
        ttl--;
    }

    public Integer getTtl() {
        return ttl;
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
        return Utilities.padHexString(Integer.toHexString(ttl),1);
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return " TTL: " + ttl + "\n";
    }

    public String toString(){
        return explainSelf();
    }
}
