package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.LL3PDatagram;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.support.Bootloader;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 3/29/2018.
 */

/**
 * Processes LL3P Packets for text datagrams
 */
public class LL3PDaemon implements Observer {
    private ARPDaemon arpDaemon;
    private LL2Daemon ll2Daemon;
    private Integer identifier = 0;
    private LRPDaemon lrpDaemon;
    public static final LL3PDaemon ourInstance = new LL3PDaemon();

    private LL3PDaemon(){};

    @Override
    /**
     * When bootloaderr is finished booting instantiate references to the other Daemons
     */
    public void update(Observable observable, Object o) {
        if(observable instanceof Bootloader){
            arpDaemon = ARPDaemon.getInstance();
            ll2Daemon = LL2Daemon.getInstance();
            lrpDaemon = LRPDaemon.getInstance();
        }
    }

    /**
     * Send a message to another ll3p address
     * @param message
     * @param layer3Address
     */
    public void sendPayload(String message, Integer layer3Address){
        LL3PDatagram ll3PDatagram = new LL3PDatagram(message, layer3Address, identifier);
        sendLL3PToNextHop(ll3PDatagram);
        identifier++;
    }

    /**
     * Send a packet to its next hop on the network
     * @param datagram
     */
    public void sendLL3PToNextHop(LL3PDatagram datagram){
        try {
           Integer nextHopIntegerKey = ((RoutingRecord)lrpDaemon.getForwardingTable().getRoute(datagram.getDestAddr().getKey())).getNextHop();
           Integer ll2PAddressInteger = arpDaemon.getMACAddress(nextHopIntegerKey);
           ll2Daemon.sendLL3PDatagram(datagram, ll2PAddressInteger);
        }
        catch(Exception e){
            UIManager.getInstance().displayMessage(datagram.getDestAddr().toString() + " Dropped packet");
        }
    }

    /**
     * Determine if packet needs to be sent to another hop or displayed
     * @param packet
     * @param ll2pAddr
     */
    public void processLL3PPacket(LL3PDatagram packet, Integer ll2pAddr){
        arpDaemon.getArpTable().touch(ll2pAddr);
        if(packet.getDestAddr().getKey().equals(Integer.parseInt(Constants.LL3P_SRC_ADDR,16))){
            UIManager.getInstance().displayMessage(packet.getSourceAddr().getKey() ,packet.getPayload().getPayload().toSummaryString());
        }
        else{
            packet.getTtl().decrementTTL();
            sendLL3PToNextHop(packet);
        }
    }

    public static LL3PDaemon getInstance(){
        return ourInstance;
    }
}
