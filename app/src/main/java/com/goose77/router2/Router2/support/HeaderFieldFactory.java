package com.goose77.router2.Router2.support;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.networks.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.networks.datagram_fields.LL2PTypeField;

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
        else if(type == Constants.DATAGRAM_PAYLOAD_FIELD_ID){
            return (U)new DatagramPayloadField(data);
        }
        return null;
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
