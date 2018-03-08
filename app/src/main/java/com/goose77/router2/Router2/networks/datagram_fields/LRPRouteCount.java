package com.goose77.router2.Router2.networks.datagram_fields;

import com.goose77.router2.Router2.networks.datagram.HeaderField;

/**
 * Created by goose on 3/8/2018.
 */

public class LRPRouteCount implements HeaderField {
    private Integer routeCount;

    public LRPRouteCount(String rtCount){
        routeCount = Integer.parseInt(rtCount, 16);
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
        return Integer.toHexString(routeCount);
    }

    /**
     * Give an explanation of what this field is
     *
     * @return String
     */
    @Override
    public String explainSelf() {
        return "Route Count: " + routeCount;
    }

    public Integer getRouteCount() {
        return routeCount;
    }
}
