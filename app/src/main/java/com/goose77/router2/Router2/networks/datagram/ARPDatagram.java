package com.goose77.router2.Router2.networks.datagram;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

/**
 * Created by Colten on 2/22/2018.
 */

/**
 * Wraps the LL3PAddress in a Datagram
 */
public class ARPDatagram implements Datagram {
    LL3PAddressField ll3pAddress;

    /**
     * Constructor that gets an ll3pAddress string and converts it to an ll3pAddress
     * @param ll3P
     */
    public ARPDatagram(String ll3P){
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        ll3pAddress = fieldFactory.getItem(Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID, ll3P);
    }

    /**
     * Returns the hex equivalent of the datagram fields
     *
     * @return String hexString
     */
    @Override
    public String toHexString() {
        return ll3pAddress.toHexString();
    }

    /**
     * Returns the explanation of all fields in the datagram
     *
     * @return String explanationString
     */
    @Override
    public String toProtocolExplanationString() {
        return "LL3P Address:" + ll3pAddress.toHexString() +" \n" ;
    }

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     *
     * @return String summary
     */
    @Override
    public String toSummaryString() {
        return ll3pAddress.explainSelf();
    }

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
        return toHexString();
    }
}
