package com.goose77.router2.Router2.networks.datagram;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Layer 2 Frame Class
 * Requires a Destination Address,
 *             Source Address
 *             Type
 *             Payload
 *             CRC
 */
public class LL2PFrame implements Datagram {
    private LL2PAddressField destinationAddress;
    private LL2PAddressField sourceAddress;
    private LL2PTypeField type;
    private DatagramPayloadField payload;
    private CRC crc;

    /**
     * Returns the hex equivalent of the datagram fields
     *
     * @return String hexString
     */
    @Override
    public String toHexString() {
        String hexString;
        hexString = destinationAddress.toHexString() + sourceAddress.toHexString() + type.toHexString() +
                    payload.toHexString() + crc.toHexString();
        return hexString;
    }

    /**
     * Returns the explanation of all fields in the datagram
     *
     * @return String explanationString
     */
    @Override
    public String toProtocolExplanationString() {
        String explainString = destinationAddress.explainSelf() + sourceAddress.explainSelf() + type.explainSelf() + payload.explainSelf() + crc.explainSelf();
        return explainString;
    }

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     *
     * @return String summary
     */
    @Override
    public String toSummaryString() {

        return toTransmissionString().substring(0,20) + "...";
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
        return  destinationAddress.toTransmissionString() + sourceAddress.toTransmissionString()
                                    + type.toTransmissionString() + payload.toTransmissionString() + crc.toTransmissionString();

    }

    /**
     * Constructor primaarily for testing,
     * Takes in concrete field objects
     * @param LL2PAddressField destinationAddress
     * @param LL2PAddressField sourceAddress
     * @param LL2PTypeField type
     * @param DatagramPayloadField payload
     * @param CRC crc
     */
    public LL2PFrame(LL2PAddressField destinationAddress, LL2PAddressField sourceAddress, LL2PTypeField type,
                     DatagramPayloadField payload, CRC crc){
        this.destinationAddress = destinationAddress;
        this.sourceAddress = sourceAddress;
        this.type = type;
        this.payload = payload;
        this.crc = crc;
    }

    /**
     * Primary constructor
     * Takes in byte array that is parsed to a string and uses factories to create all of
     * the field objects
     * @param byte[] frame
     */
    public LL2PFrame(byte[] frame){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        String frameString = new String(frame);
        String destAddrString = frameString.substring(Constants.DEST_ADDR_OFFSET, Constants.DEST_ADDR_OFFSET_BYTES + Constants.ADDR_LENGTH_BYTES);
        String srcAddrString = frameString.substring(Constants.SRC_ADDR_OFFSET_BYTES, (Constants.SRC_ADDR_OFFSET_BYTES+Constants.ADDR_LENGTH_BYTES));
        String typeString = frameString.substring(Constants.TYPE_OFFSET_BYTES, Constants.TYPE_OFFSET_BYTES+Constants.TYPE_LENGTH_BYTES);
        String payloadString = frameString.substring(Constants.PAYLOAD_OFFSET_BYTES, frame.length-Constants.CRC_OFFSET_FROM_END_BYTES);
        String crcString = frameString.substring(frame.length-Constants.CRC_OFFSET_FROM_END_BYTES, frame.length);
        this.destinationAddress = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, destAddrString);
        this.sourceAddress = factory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, srcAddrString);
        this.type =factory.getItem(Constants.LL2P_TYPE_FIELD_ID, typeString);
        this.payload = factory.getItem(Integer.parseInt(typeString), payloadString);
        this.crc = factory.getItem(Constants.CRC_ID, crcString);
    }

    /**
     * Cretes a payload field
     */
    private void makePayloadField(){
        //todo find out what this means
    }


    public LL2PAddressField getDestinationAddress() {
        return destinationAddress;
    }

    public LL2PAddressField getSourceAddress() {
        return sourceAddress;
    }

    public LL2PTypeField getType() {
        return type;
    }

    public DatagramPayloadField getPayload() {
        return payload;
    }

    public CRC getCrc() {
        return crc;
    }
}
