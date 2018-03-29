package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.RoutingTable;
import com.goose77.router2.Router2.networks.Table.Table;
import com.goose77.router2.Router2.networks.datagram.LRPPacket;
import com.goose77.router2.Router2.networks.datagram_fields.NetworkDistancePair;
import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.Bootloader;
import com.goose77.router2.Router2.support.ParentActivity;
import com.goose77.router2.Router2.support.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 3/15/2018.
 */

/**
 * Daemon in charge of routing and forwarding tables.
 * This daemon will keep track of all and best routes to each known node.
 * This daemon will keep above mentioned tables up to date
 */
public class LRPDaemon implements Runnable, Observer {
    private static LRPDaemon lrpDaemon = new LRPDaemon();
    public ARPDaemon arpDaemon;
    private RoutingTable routingTable;
    private RoutingTable forwardingTable;
    private LL2Daemon ll2Daemon;
    private int sequenceNumber;

    /**
     * Constructor that just gets reference to ll2Daemon
     */
    public LRPDaemon(){
        ll2Daemon = LL2Daemon.getInstance();
    }


    public static LRPDaemon getInstance(){
        return lrpDaemon;
    }

    /**
     * Method that is run by scheduler every 10 seconds
     * Keeps routing and forwarding tables up to date, including expiring routes that haven't been
     * heard from in 30 seconds
     */
    @Override
    public void run() {
        ParentActivity.getParentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateRoutes();
            }
        });
    }

    /**
     * When Bootloader finished booting gets instance of the ARPDaemon and creates
     * the routing and forwarding table
     * If notified by the ArpDaemon routes will be removed from the routing table because they
     * have been dropped
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Bootloader){
            arpDaemon = ARPDaemon.getInstance();
            routingTable = new RoutingTable();
            forwardingTable = new RoutingTable();
        }
        if(observable instanceof ARPDaemon){
            for( Object ll3pO : (List) o) {
                Integer ll3p = (Integer) ll3pO;
                routingTable.removeRoutesFrom(ll3p);
            }
        }
    }

    public RoutingTable getRouteTable(){
        return routingTable;
    }

    public List<TableRecord> getRoutingTableAsList(){
        return routingTable.getTableAsList();
    }

    public RoutingTable getFIB(){
        return forwardingTable;
    }

    public List<TableRecord> getForwardingTabkeAsList(){
       return forwardingTable.getTableAsList();
    }

    /**
     * Processes a LRP packet in byte form from the LL2Daemon
     * @param lrpPacketBytes
     * @param ll2pSource
     */
    public void receiveNewLRP(byte[] lrpPacketBytes, Integer ll2pSource){
        LRPPacket lrpPacket = new LRPPacket(lrpPacketBytes);
        Integer ll3p = lrpPacket.getLl3pAddr().getKey();
        arpDaemon.updateARPEntry(ll3p);
        List<NetworkDistancePair> networkDistancePairs = lrpPacket.getRoutes();
        List<RoutingRecord> routingRecords = new ArrayList<>();
        for(NetworkDistancePair networkDistancePair : networkDistancePairs){
            routingRecords.add(new RoutingRecord(networkDistancePair.getNetwork(),
                    networkDistancePair.getDistance(),
                    ll3p));
        }
        routingTable.addRoutes(routingRecords);
        forwardingTable.addForwardingRoutes(routingTable.getBestRoutes());
    }

    /**
     * Expires records in the routing and forwarding tables
     * Updates the routing and forwarding tables with any changes
     * Send updates to adjacent nodes with all known routes excluding the ones learned from that node
     */
    private void updateRoutes(){
        routingTable.expireRecords(Constants.MAX_TIME);
        forwardingTable.expireRecords(Constants.MAX_TIME);

        RoutingRecord ownRoutingRecord = new RoutingRecord(Constants.OWN_NETWORK_NUMBER,
                Constants.OWN_DISTANCE, Integer.parseInt(Constants.LL3P_SRC_ADDR,16));
        routingTable.addNewRoute(ownRoutingRecord);

        for(Integer ll3p : arpDaemon.getAttachedNodes()){
            RoutingRecord adjacentRoutingRecord = new RoutingRecord(Utilities.getNetworkFromLL3P(ll3p),
                    1,ll3p);
            routingTable.addNewRoute(adjacentRoutingRecord);
        }
        forwardingTable.addForwardingRoutes(routingTable.getBestRoutes());

       for(Integer ll3p : arpDaemon.getAttachedNodes()) {
           ArrayList<NetworkDistancePair> pairs = new ArrayList<>();
           for(RoutingRecord routingRecord : forwardingTable.getRouteListExcluding(ll3p)){
               pairs.add(routingRecord.getNetworkDistancePair());
           }
               LRPPacket lrpPacket = new LRPPacket(ll3p, sequenceNumber, forwardingTable.getRouteListExcluding(ll3p).size(), pairs);
               sendUpdate(lrpPacket, ll3p);
       }
    }

    public Table getForwardingTable(){
        return forwardingTable;
    }

    public Table getRoutingTable(){
        return routingTable;
    }

    /**
     *  Process a LRP packet in bytes version
     * @param lrpBytes
     */
    public void processLRP(byte[] lrpBytes){
        LRPPacket lrpPacket = new LRPPacket(lrpBytes);
        Integer ll3p = lrpPacket.getLl3pAddr().getKey();
        arpDaemon.updateARPEntry(ll3p);
        List<NetworkDistancePair> networkDistancePairs = lrpPacket.getRoutes();
        List<RoutingRecord> routingRecords = new ArrayList<>();
        for(NetworkDistancePair networkDistancePair : networkDistancePairs){
            routingRecords.add(new RoutingRecord(networkDistancePair.getNetwork(),
                    networkDistancePair.getDistance(),
                    ll3p));
        }
        routingTable.addRoutes(routingRecords);
        forwardingTable.addForwardingRoutes(routingTable.getBestRoutes());
    }

    /**
     * Send a LRP packet to a given ll3p address by looking up the mac address in the ARP table
     * @param packet
     * @param ll3p
     */
    private void sendUpdate(LRPPacket packet, Integer ll3p){
        Integer ll2p = arpDaemon.getMACAddress(ll3p);
        ll2Daemon.sendLRPUpdate(packet, ll2p);
    }

    /**
     * Process a LRP packet in LRP packet form instead of byte form
     * This is the one used in most situations
     * @param lrpPacket
     */
    public void processLRPPacket(LRPPacket lrpPacket){
        Integer ll3p = lrpPacket.getLl3pAddr().getKey();
        arpDaemon.updateARPEntry(ll3p);
        List<NetworkDistancePair> networkDistancePairs = lrpPacket.getRoutes();
        List<RoutingRecord> routingRecords = new ArrayList<>();
        for(NetworkDistancePair networkDistancePair : networkDistancePairs){
            routingRecords.add(new RoutingRecord(networkDistancePair.getNetwork(),
                    networkDistancePair.getDistance(),
                    ll3p));
        }
        routingTable.addRoutes(routingRecords);
        forwardingTable.addForwardingRoutes(routingTable.getBestRoutes());
    }
}
