package com.goose77.router2.Router2.networks.datagram;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PChecksum;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PIdentifierField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PTTLField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PTypeField;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

/**
 * Created by goose on 3/29/2018.
 */

/**
 * LL3P datagram for transmitting text datagrams through the network
 */
public class LL3PDatagram implements Datagram {
    private LL3PAddressField sourceAddr;
    private LL3PAddressField destAddr;
    private LL3PTTLField ttl;
    private LL3PIdentifierField identifier;
    private LL3PTypeField type;
    private LL3PChecksum checksum;
    private DatagramPayloadField payload;

    /**
     * Byte constructor that breaks up a packet into its components
     * @param datagram
     */
    public LL3PDatagram(byte[] datagram){
        String datagramString = new String(datagram);
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        String srcAddrString = datagramString.substring(Constants.LL3P_SRC_ADDR_OFFSET, Constants.LL3P_DEST_ADDR_OFFSET);
        String destAddrString = datagramString.substring(Constants.LL3P_DEST_ADDR_OFFSET, Constants.LL3P_TYPE_OFFSET);
        String typeString = datagramString.substring(Constants.LL3P_TYPE_OFFSET, Constants.LL3P_IDENTIFIER_OFFSET);
        String identifierString = datagramString.substring(Constants.LL3P_IDENTIFIER_OFFSET, Constants.LL3P_TTL_OFFSET);
        String ttlString = datagramString.substring(Constants.LL3P_TTL_OFFSET, Constants.LL3P_PAYLOAD_OFFSET);
        String payloadString = datagramString.substring(Constants.LL3P_PAYLOAD_OFFSET, datagramString.length() - Constants.LL3P_CHECKSUM_OFFSET_FROM_END);
        String checksumString = datagramString.substring(datagramString.length() - Constants.LL3P_CHECKSUM_OFFSET_FROM_END);
        sourceAddr = factory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, srcAddrString);
        destAddr = factory.getItem(Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID, destAddrString);
        type = factory.getItem(Constants.LL3P_TYPE_ID, typeString);
        identifier = factory.getItem(Constants.LL3P_IDENTIFIER_ID, identifierString);
        ttl = factory.getItem(Constants.LL3P_TTL_ID, ttlString);
        payload = factory.getItem(Constants.LL2P_TYPE_IS_TEXT, payloadString);
        checksum = factory.getItem(Constants.LL3P_CHECKSUM_ID, checksumString);
    }

    /**
     * String constructor that breaks the packets into its components
     * @param datagramString
     */
    public LL3PDatagram(String datagramString){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        String srcAddrString = datagramString.substring(Constants.LL3P_SRC_ADDR_OFFSET, Constants.LL3P_DEST_ADDR_OFFSET);
        String destAddrString = datagramString.substring(Constants.LL3P_DEST_ADDR_OFFSET, Constants.LL3P_TYPE_OFFSET);
        String typeString = datagramString.substring(Constants.LL3P_TYPE_OFFSET, Constants.LL3P_IDENTIFIER_OFFSET);
        String identifierString = datagramString.substring(Constants.LL3P_IDENTIFIER_OFFSET, Constants.LL3P_TTL_OFFSET);
        String ttlString = datagramString.substring(Constants.LL3P_TTL_OFFSET, Constants.LL3P_PAYLOAD_OFFSET);
        String payloadString = datagramString.substring(Constants.LL3P_PAYLOAD_OFFSET, datagramString.length() - Constants.LL3P_CHECKSUM_OFFSET_FROM_END);
        String checksumString = datagramString.substring(datagramString.length() - Constants.LL3P_CHECKSUM_OFFSET_FROM_END);
        sourceAddr = factory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, srcAddrString);
        destAddr = factory.getItem(Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID,destAddrString);
        type = factory.getItem(Constants.LL3P_TYPE_ID, typeString);
        identifier = factory.getItem(Constants.LL3P_IDENTIFIER_ID, identifierString);
        ttl = factory.getItem(Constants.LL3P_TTL_ID, ttlString);
        payload = factory.getItem(Constants.LL2P_TYPE_IS_TEXT, payloadString);
        checksum = factory.getItem(Constants.LL3P_CHECKSUM_ID, checksumString);
    }

    /**
     * Direct object constructor that build the components of the datagram
     * @param message
     * @param ll3PAddress
     * @param indentifier
     */
    public LL3PDatagram(String message, Integer ll3PAddress, Integer indentifier){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        sourceAddr = factory.getItem(Constants.LL3P_SOURCE_ADDRESS_FIELD_ID, Constants.LL3P_SRC_ADDR);
        destAddr = factory.getItem(Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID, Integer.toHexString(ll3PAddress));
        type = factory.getItem(Constants.LL3P_TYPE_ID, Integer.toString(Constants.LL2P_TYPE_IS_LL3P));
        identifier = factory.getItem(Constants.LL3P_IDENTIFIER_ID, Integer.toHexString(indentifier));
        ttl = factory.getItem(Constants.LL3P_TTL_ID, "f");
        payload = factory.getItem(Constants.LL2P_TYPE_IS_TEXT, message);
        checksum = factory.getItem(Constants.LL3P_CHECKSUM_ID, "0000");
    }

    /**
     * Returns the hex equivalent of the datagram fields
     *
     * @return String hexString
     */
    @Override
    public String toHexString() {
        return sourceAddr.toHexString()+destAddr.toHexString()+type.toHexString()+identifier.toHexString()
                +ttl.toHexString()+payload.toTransmissionString()+checksum.toHexString();
    }

    /**
     * Returns the explanation of all fields in the datagram
     *
     * @return String explanationString
     */
    @Override
    public String toProtocolExplanationString() {
        return sourceAddr.explainSelf() + destAddr.explainSelf()+type.explainSelf()+
                identifier.explainSelf()+ttl.explainSelf()+payload.explainSelf()+checksum.explainSelf();
    }

    /**
     * One line explanation that displays the top lvl protocol and some of its info
     *
     * @return String summary
     */
    @Override
    public String toSummaryString() {
        return toProtocolExplanationString();
    }

    public LL3PAddressField getSourceAddr() {
        return sourceAddr;
    }

    public LL3PAddressField getDestAddr() {
        return destAddr;
    }

    public LL3PTTLField getTtl() {
        return ttl;
    }

    public LL3PIdentifierField getIdentifier() {
        return identifier;
    }

    public LL3PTypeField getType() {
        return type;
    }

    public LL3PChecksum getChecksum() {
        return checksum;
    }

    public DatagramPayloadField getPayload() {
        return payload;
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

    @Override
    public String toString(){
        return payload.explainSelf();
    }
}
