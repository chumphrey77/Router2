package com.goose77.router2.Router2.networks.tableRecord;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.networks.datagram_fields.NetworkDistancePair;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

/**
 * Created by goose on 3/8/2018.
 */

public class RoutingRecord extends TableRecordClass {
    public NetworkDistancePair networkDistancePair;
    public Integer nextHop;
    public Integer key;

    public RoutingRecord(Integer networkNumber, Integer distance, Integer nextHop) {
        super();
        this.nextHop = nextHop;
        genterateKey(networkNumber);
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        String pairString = (Integer.toHexString(networkNumber)) + Integer.toHexString(distance);
        fieldFactory.getItem(Constants.NETWORK_DISTANCE_PAIR_FIELD_ID, pairString);
    }


    private void genterateKey(Integer networkNumber) {
        key = networkNumber * 256 * 256 + nextHop ;
    }

    public NetworkDistancePair getNetworkDistancePair() {
        return networkDistancePair;
    }

    public Integer getNextHop() {
        return nextHop;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    public Integer getDistance() {
        return networkDistancePair.getDistance();
    }

    public Integer getNetworkNumber(){
        return networkDistancePair.getNetwork();
    }
}