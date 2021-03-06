package com.goose77.router2.Router2.networks.datagram;

import com.goose77.router2.Router2.support.Utilities;

import java.math.BigInteger;

/**
 * Created by goose on 1/26/2018.
 */

/**
 * The text object for payloads.
 * Actually stores the text and does not accept more datagrams
 * Bottom layer of Datagram interface
 */
public class TextDatagram implements Datagram {
    private String payload;
    /**
     * Returns the hex equivalent of the datagram fields
     *
     * @return String hexString
     */
    @Override
    public String toHexString() {
        return Utilities.toHexString(payload);
    /*    try{
            return String.format("%040x", new BigInteger(1, payload.getBytes("UTF8")));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;*/
    }

    /**
     * Returns the explanation of all fields in the datagram
     *
     * @return String explanationString
     */
    @Override
    public String toProtocolExplanationString() {
        return "The payload is: \""+payload +"\" \n";
    }

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     *
     * @return String summary
     */
    @Override
    public String toSummaryString() {
        String sum ="";
        if(payload.length() > 20) {
            sum = payload.substring(0, 20) + "...";
        }
        else{
           sum = payload;
        }
        return sum;
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
        return payload;
    }

    /**
     * Constructor that sets the text for the payload
     * @param  payload
     */
    public TextDatagram(String payload){
        this.payload = payload;
    }
}
