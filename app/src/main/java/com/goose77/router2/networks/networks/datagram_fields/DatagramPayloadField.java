package com.goose77.router2.networks.networks.datagram_fields;

import android.provider.ContactsContract;

import com.goose77.router2.networks.datagram.TextDatagram;
import com.goose77.router2.networks.headerFields.HeaderField;
import com.goose77.router2.networks.datagram.Datagram;
/**
 * Created by goose on 1/25/2018.
 */

/**
 * Encapsulates the payload of a frame. Stores lower level datagram objects
 */
public class DatagramPayloadField implements HeaderField {
    private Datagram datagram;

    /**
     * Generate a debugging string and return it
     * @return String
     */
    public String toString(){
        String toStringString;
        toStringString = "The payload is:" + datagram.toTransmissionString() + "\n";
        return toStringString;
    }

    /**
     * Generate the string that will be transmitted in frames
     * @return String
     */
    @Override
    public String toTransmissionString() {
        return datagram.toTransmissionString();
    }


    /**
     * Create a hex representation of the field
     * @return String
     */
    @Override
    public String toHexString() {
        return datagram.toHexString();
    }

    /**
     * Give an explanation of what this field is
     * @return String
     */
    @Override
    public String explainSelf() {
        return datagram.toProtocolExplanationString();
    }

    /**
     * Constructor that takes in a Datagram object and sets it to this object
     * @param pkt
     */
    public DatagramPayloadField(Datagram pkt){
            this.datagram = pkt;
    }

    /**
     * return just the Datagram paylaod of this object
     * @return Datagram
     */
    public Datagram getPayload(){
        return datagram;
    }

    /**
     * Primary constructor that takes in a string and creates a TextDatagram object
     * that is what will actually be seen as the payload
     * @param String text
     */
    public DatagramPayloadField(String text){
        this.datagram = new TextDatagram(text);
    }
}
