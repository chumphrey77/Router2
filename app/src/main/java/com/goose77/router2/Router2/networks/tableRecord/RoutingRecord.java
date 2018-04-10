package com.goose77.router2.Router2.networks.tableRecord;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.networks.datagram_fields.NetworkDistancePair;
import com.goose77.router2.Router2.support.HeaderFieldFactory;
import com.goose77.router2.Router2.support.Utilities;

/**
 * Created by goose on 3/8/2018.
 */

/**
 * A record for a path to given a ll3p address
 */
public class RoutingRecord extends TableRecordClass {
    public NetworkDistancePair networkDistancePair;
    public Integer nextHop;
    public Integer key;

    /**
     * Constructor that requires the ll3p Address as an integer, the distance (in hops from the ll3p
     * address as an Integer, and the ll3p address of the next hop as an integer
     * @param networkNumber
     * @param distance
     * @param nextHop
     */
    public RoutingRecord(Integer networkNumber, Integer distance, Integer nextHop) {
        super();
        this.nextHop = nextHop;
        genterateKey(networkNumber);
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        String pairString = Utilities.padHexString(Integer.toHexString(networkNumber),1) +Utilities.padHexString(Integer.toHexString(distance),1);
        networkDistancePair = fieldFactory.getItem(Constants.NETWORK_DISTANCE_PAIR_FIELD_ID, pairString);
    }

    /**
     * Creates a unique key for each record based on both ll3p addresses stored in the record
     * @param networkNumber
     */
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

    public String toString(){
        return networkDistancePair.toString() + " Next Hop: "+Utilities.padHexString(Integer.toHexString(nextHop),Constants.NETWORK_DISTANCE_PAIR_BYTE_PAD) + " Age: " + getAgeInSeconds();
    }
}