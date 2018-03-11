package com.goose77.router2.Router2.networks.datagram;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LRPRouteCount;
import com.goose77.router2.Router2.networks.datagram_fields.LRPSequenceNumber;
import com.goose77.router2.Router2.networks.datagram_fields.NetworkDistancePair;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

import java.util.List;

/**
 * Created by goose on 3/8/2018.
 */

/**
 * Class for creating an LRP packet that will be place inside a LL2PFrame
 * This type of packet is used to distribute my routers knowledge of routes to my adjacent nodes
 */
public class LRPPacket implements Datagram {
    private LL3PAddressField ll3pAddr;
    private LRPSequenceNumber seqNum;
    private LRPRouteCount count;
    private List<NetworkDistancePair> routes;

    /**
     * Constructor that takes in Integer values and the list of routes to create the packet
     * This could be used for sending LRP packets
     * @param ll3p
     * @param seqNum
     * @param count
     * @param routes
     */
    public LRPPacket(Integer ll3p, Integer seqNum, Integer count, List<NetworkDistancePair> routes){
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        String ll3pAddrString = Integer.toHexString(ll3p);
        String seqNumString = Integer.toHexString(seqNum);
        String countStirng = Integer.toHexString(count);
        this.routes = routes;

        ll3pAddr = fieldFactory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, ll3pAddrString);
        this.seqNum = fieldFactory.getItem(Constants.LRP_SEQUENCE_NUMBER_FIELD_ID, seqNumString);
        this.count = fieldFactory.getItem(Constants.LRP_ROUTE_COUNT_FIELD_ID, countStirng);
    }

    /**
     * Constructor that takes in a String to create a usable LRP packet
     * This will parse the string into the necessary fields for creating an LRP packet
     * @param packetString
     */
    public LRPPacket(String packetString){
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        int packetSize = packetString.length();
        String ll3pAddrString = packetString.substring(Constants.LRP_PACKET_LL3P_ADDR_OFFSET,
                Constants.LRP_PACKET_LL3P_ADDR_END_OFFSET);
        String seqNumString = packetString.substring(Constants.LRP_PACKET_SEQ_NUM_OFFSET,
                Constants.LRP_PACKET_SEQ_NUM_END_OFFSET);
        String countString = packetString.substring(Constants.LRP_PACKET_COUNT_OFFSET,
                Constants.LRP_PACKET_COUNT_END_OFFSET);
        for(int i = Constants.LRP_PACKET_FIRST_NETWORK_DISTANCE_PAIR_OFFSET;
            i <= packetSize - Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET;
            i += Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET){
            routes.add((NetworkDistancePair) fieldFactory.getItem(Constants.NETWORK_DISTANCE_PAIR_FIELD_ID,
                    packetString.substring(i, i+Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET)));
        }
        ll3pAddr = fieldFactory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, ll3pAddrString);
        seqNum = fieldFactory.getItem(Constants.LRP_SEQUENCE_NUMBER_FIELD_ID, seqNumString);
        count = fieldFactory.getItem(Constants.LRP_ROUTE_COUNT_FIELD_ID, countString);
    }


    /**
     * Constructor that takes in a byte array to create a usable LRP packet
     * This could be used for receive packets as a byte array and needing to construct a useful object
     * out of it
     * @param packet
     */
    public LRPPacket(byte[] packet){
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        String packetString = new String(packet);
        int packetSize = packetString.length();
        String ll3pAddrString = packetString.substring(Constants.LRP_PACKET_LL3P_ADDR_OFFSET,
                Constants.LRP_PACKET_LL3P_ADDR_END_OFFSET);
        String seqNumString = packetString.substring(Constants.LRP_PACKET_SEQ_NUM_OFFSET,
                Constants.LRP_PACKET_SEQ_NUM_END_OFFSET);
        String countString = packetString.substring(Constants.LRP_PACKET_COUNT_OFFSET,
                Constants.LRP_PACKET_COUNT_END_OFFSET);
        for(int i = Constants.LRP_PACKET_FIRST_NETWORK_DISTANCE_PAIR_OFFSET;
            i <= packetSize - Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET;
            i += Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET){
            routes.add((NetworkDistancePair) fieldFactory.getItem(Constants.NETWORK_DISTANCE_PAIR_FIELD_ID,
                    packetString.substring(i, i+Constants.LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET)));
        }
        ll3pAddr = fieldFactory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, ll3pAddrString);
        seqNum = fieldFactory.getItem(Constants.LRP_SEQUENCE_NUMBER_FIELD_ID, seqNumString);
        count = fieldFactory.getItem(Constants.LRP_ROUTE_COUNT_FIELD_ID, countString);
    }
    /**
     * Returns the hex equivalent of the datagram fields
     *
     * @return String hexString
     */
    @Override
    public String toHexString() {
        String hexString="";
        hexString = ll3pAddr.toHexString() + seqNum.toHexString() + count.toHexString();
        for(NetworkDistancePair pair : routes){
            hexString += pair.toHexString();
        }
        return hexString;
    }

    /**
     * Returns the explanation of all fields in the datagram
     *
     * @return String explanationString
     */
    @Override
    public String toProtocolExplanationString() {
        String exp ="LRP Packet \n";
        exp = ll3pAddr.explainSelf() + seqNum.explainSelf() + count.explainSelf();
        for(NetworkDistancePair pair : routes){
            exp += pair.explainSelf();
        }
        return exp;
    }

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     *
     * @return String summary
     */
    @Override
    public String toSummaryString() {
        return toTransmissionString().substring(0,20) + "...";    }

    /**
     * Returns the properly formmatted packet as a string
     * Addresses as hex
     * Type as hex
     * Payload as Ascii
     * CRC as hex
     *
     * @return String transmissionString
     */
    @Override
    public String toTransmissionString() {
        String transmission = ll3pAddr.toTransmissionString() + seqNum.toTransmissionString() + count.toTransmissionString();
        for(NetworkDistancePair pair : routes){
            transmission += pair.toTransmissionString();
        }
        return transmission;
    }

    public Integer getRouteCount(){
        return count.getRouteCount();
    }

    public byte[] getBytes(){
        return toTransmissionString().getBytes();
    }

    public LL3PAddressField getLl3pAddr() {
        return ll3pAddr;
    }

    public void setLl3pAddr(LL3PAddressField ll3pAddr) {
        this.ll3pAddr = ll3pAddr;
    }

    public LRPSequenceNumber getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(LRPSequenceNumber seqNum) {
        this.seqNum = seqNum;
    }

    public LRPRouteCount getCount() {
        return count;
    }

    public void setCount(LRPRouteCount count) {
        this.count = count;
    }

    public List<NetworkDistancePair> getRoutes() {
        return routes;
    }

    public void setRoutes(List<NetworkDistancePair> routes) {
        this.routes = routes;
    }
}
