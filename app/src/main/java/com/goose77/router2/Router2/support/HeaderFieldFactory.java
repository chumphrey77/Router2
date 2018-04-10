package com.goose77.router2.Router2.support;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PChecksum;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PIdentifierField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PTTLField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PTypeField;
import com.goose77.router2.Router2.networks.datagram_fields.LRPRouteCount;
import com.goose77.router2.Router2.networks.datagram_fields.LRPSequenceNumber;
import com.goose77.router2.Router2.networks.datagram_fields.NetworkDistancePair;

/**
 * Factory for the create of various header field for packets
 */

public class HeaderFieldFactory implements Factory<HeaderField,String> {
    private static HeaderFieldFactory ourInstance = new HeaderFieldFactory();

    /**
     * Public method to get the desired item of the specified type
     *
     * @param type - The type of object to be created determined at run time
     * @param data - The data to be used to construct the desired object
     * @return object - This object will be of the specified U data type
     */
    @Override
    public <U extends HeaderField>U getItem(int type, String data) {

        if(type == Constants.LL2P_DEST_ADDRESS_FIELD_ID){
           return (U)new LL2PAddressField(data, false);
        }
        else if (type == Constants.LL2P_SOURCE_ADDRESS_FIELD_ID){
            return (U)new LL2PAddressField(data, true);
        }
        else if (type == Constants.LL2P_TYPE_FIELD_ID){
            return (U)new LL2PTypeField(data);
        }
        else if(type == Constants.CRC_ID){
            return (U)new CRC(data);
        }
        else if(type == Constants.LL2P_TYPE_IS_ARP_REPLY){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_ARP_REQUEST){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_ECHO_REQUEST){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_ECHO_REPLY){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_TEXT){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_LL3P){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_RESERVED){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL2P_TYPE_IS_LRP){
            return (U)new DatagramPayloadField(type, data);
        }
        else if(type == Constants.LL3P_SOURCE_ADDRESS_FIELD_ID){
            return (U)new LL3PAddressField (data, true);
        }
        else if(type == Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID){
            return (U)new LL3PAddressField (data, false);
        }
        else if(type == Constants.LRP_SEQUENCE_NUMBER_FIELD_ID){
            return (U)new LRPSequenceNumber(data);
        }
        else if(type == Constants.LRP_ROUTE_COUNT_FIELD_ID){
            return (U)new LRPRouteCount(data);
        }
        else if(type == Constants.NETWORK_DISTANCE_PAIR_FIELD_ID){
            U distancePair =  (U)new NetworkDistancePair(data);
            return distancePair;
        }
        else if(type == Constants.LL3P_TYPE_ID){
            return (U)new LL3PTypeField(data);
        }
        else if(type == Constants.LL3P_IDENTIFIER_ID){
            return (U)new LL3PIdentifierField(data);
        }
        else if(type == Constants.LL3P_TTL_ID){
            return (U)new LL3PTTLField(data);
        }
        else if(type == Constants.LL3P_CHECKSUM_ID){
            return (U)new LL3PChecksum(data);
        }
        else {
            return null;
        }
    }

    /**
     * Empty constructor
     */
    private HeaderFieldFactory(){
    }

    /**
     * Returns an this instance of factory (Singleton pattern)
     * @return
     */
    public static HeaderFieldFactory getInstance(){
        return ourInstance;
    }
}
