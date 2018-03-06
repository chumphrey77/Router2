package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.ARPDatagram;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.networks.datagram.TextDatagram;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Colten on 2/15/2018.
 */

public class LL2Daemon implements Observer {
    private UIManager uiManager;
    private LL1Daemon ll1Daemon;
    private ARPDaemon arpDaemon;
    private static LL2Daemon ourInstance = new LL2Daemon();

    /**
     * Control the layer 2 functionality for the router
     */
    private LL2Daemon(){ }

    /**
     * Process what to do with a layer 2 packet
     * @param frame
     */
    public void processLL2PFrame(LL2PFrame frame){
        if(frame.getDestinationAddress().getAddress().equals(Integer.parseInt(Constants.SRC_ADDR,16))){
            //todo check crc
            checkTypeField(frame);
        }
        else{
            uiManager.displayMessage("Frame with wrong Destination ADDR RX'd");
        }
    }

    /**
     * When notified by the bootloader get instances of ll1daemon and UIManager
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        uiManager = UIManager.getInstance();
        ll1Daemon = LL1Daemon.getInstance();
        arpDaemon = ARPDaemon.getInstance();
    }


    /**
     * Check type field and process the packets accordingly
     * LL3P frame -> unsupported
     * Reserved -> unsupported
     * LRP -> unsupported
     * Echo Request -> send echo reply
     * Echo Reply ->  display message
     * ARP Request -> unsupported
     * ARP Reply -> unsupported
     * Text -> Display toast saying message was rx'd
     * @param frame
     */
    public void checkTypeField(LL2PFrame frame){
        if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_LL3P))){
            uiManager.displayMessage("Unsupported Frame Type Rx'd");
        }
        else if(frame.getType().toTransmissionString().equals(Constants.LL2P_TYPE_IS_RESERVED)){
            uiManager.displayMessage("Unsupported Frame Type Rx'd");
        }
        else if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_LRP))){
            uiManager.displayMessage("Unsupported Frame Type Rx'd");
        }
        else if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_ECHO_REQUEST))){
            answerEchoRequest(frame);
        }
        else if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_ECHO_REPLY))){
            uiManager.displayMessage(frame.toSummaryString());
            //sendEchoRequest(frame.getDestinationAddress().getAddress());
        }
        else if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_ARP_REQUEST))){
            arpDaemon.processARPRequest(frame.getSourceAddress().toTransmissionString(), (ARPDatagram) frame.getPayload().getPayload());
        }
        else if(frame.getType().toTransmissionString().equals(Integer.toString(Constants.LL2P_TYPE_IS_ARP_REPLY))){
            arpDaemon.processARPReply(frame.getDestinationAddress().toTransmissionString(), (ARPDatagram) frame.getPayload().getPayload());
        }
        else if(frame.getType().toTransmissionString().equals((Integer.toString(Constants.LL2P_TYPE_IS_TEXT)))){
            uiManager.displayMessage("Rx'd Frame of Text Type");
        }
    }

    /**
     * Create and send LL2Frame to LL1Daemon to send to requester
     * @param frame
     */
    public void answerEchoRequest(LL2PFrame frame){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        LL2PAddressField destAddr = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, frame.getSourceAddress().toTransmissionString());
        LL2PAddressField srcAddr = factory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PTypeField type = factory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ECHO_REPLY));
        DatagramPayloadField payload =  frame.getPayload();
        CRC crc = factory.getItem(Constants.CRC_ID, "0000"); //todo Implement CRC
        LL2PFrame  newFrame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
        ll1Daemon.sendFrame(newFrame);
    }

    /**
     * Create LL2Frame and send it to the address requested
     * @param destAddrInt
     */
    public void sendEchoRequest(Integer destAddrInt){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        LL2PAddressField destAddr = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, Integer.toString(destAddrInt));
        LL2PAddressField srcAddr = factory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PTypeField type = factory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_TEXT));
        DatagramPayloadField payload =  factory.getItem(Constants.LL2P_TYPE_IS_ECHO_REQUEST,"Colten's Echo Request");
        CRC crc = factory.getItem(Constants.CRC_ID, "0000"); //todo Implement CRC
        LL2PFrame  newFrame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
        ll1Daemon.sendFrame(newFrame);
    }

    /**
     * Creat an ARPRequest and send it to the destiantion
     * @param destAddrInt
     */
    public void sendARPRequest(Integer destAddrInt){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        LL2PAddressField destAddr = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, Integer.toHexString(destAddrInt));
        LL2PAddressField srcAddr = factory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PTypeField type = factory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ARP_REQUEST));
        DatagramPayloadField payload =  factory.getItem(Constants.LL2P_TYPE_IS_ARP_REQUEST,  Constants.LL3P_SRC_ADDR );
        CRC crc = factory.getItem(Constants.CRC_ID, "0000"); //todo Implement CRC
        LL2PFrame  newFrame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
        ll1Daemon.sendFrame(newFrame);
    }

    /**
     * Create an ARPReply and send it to the destination
     * @param destAddrInt
     */
    public void sendARPReply(Integer destAddrInt){
        HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
        LL2PAddressField destAddr = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, Integer.toHexString(destAddrInt));
        LL2PAddressField srcAddr = factory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PTypeField type = factory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ARP_REPLY));
        DatagramPayloadField payload =  factory.getItem(Constants.LL2P_TYPE_IS_ARP_REPLY, Constants.LL3P_SRC_ADDR);
        CRC crc = factory.getItem(Constants.CRC_ID, "0000"); //todo Implement CRC
        LL2PFrame  newFrame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
        ll1Daemon.sendFrame(newFrame);
    }

    public static LL2Daemon getInstance(){
        return ourInstance;
    }
}
