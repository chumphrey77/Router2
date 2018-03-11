package com.goose77.router2.Router2.support;

import android.app.Activity;
import android.util.Log;

import com.goose77.router2.Router2.UI.UIManager;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.RoutingTable;
import com.goose77.router2.Router2.networks.Table.Table;
import com.goose77.router2.Router2.networks.daemon.ARPDaemon;
import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.networks.daemon.LL2Daemon;
import com.goose77.router2.Router2.networks.daemon.Scheduler;
import com.goose77.router2.Router2.networks.datagram.ARPDatagram;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.tableRecord.AdjacencyRecord;
import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;


import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * Extends Observable
 * Boots the router and notifies all observing objects of boot completion.
 * Also runs any tests
 */
public class Bootloader extends Observable {
    /**
     * Gets a reference to the MainAcivity
     * Constructs the BootLoader
     * @param parentActivity
     */
    public Bootloader(Activity parentActivity){
        bootRouter(parentActivity);
    }

    /**
     * Adds oberservers, makes changes and notifies the observers of changes
     * Testing will be done in this method!
     * @param parentActivity
     */
    private void bootRouter(Activity parentActivity){
        ParentActivity.setParentActivity(parentActivity);
        addObserver(UIManager.getInstance());
        addObserver(LL1Daemon.getInstance());
        addObserver(UIManager.getInstance().getSnifferUI());
        addObserver(ARPDaemon.getInstance());
        FrameLogger.getInstance().addObserver(UIManager.getInstance().getSnifferUI());
        addObserver(FrameLogger.getInstance());
        addObserver(UIManager.getInstance().getTableUI());
        addObserver(Scheduler.getInstance());
        addObserver(LL2Daemon.getInstance());
        setChanged();
        notifyObservers();

        test();
    }

    /**
     * Method for testing any functionaility
        */
    private void test() {
    /*   Lab 1 Tests
        UIManager.getInstance().displayMessage(Constants.routerName + " is up and running!");
        UIManager.getInstance().displayMessage("The IP Address is " + Constants.IP_ADDRESS);

        Lab 2 Tests
        HeaderFieldFactory headerFactory = HeaderFieldFactory.getInstance();
        LL2PAddressField srcAddr= headerFactory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PAddressField destAddr= headerFactory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, "314159");
        LL2PTypeField type = headerFactory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ARP_REPLY));
        DatagramPayloadField payload = headerFactory.getItem(Constants.DATAGRAM_PAYLOAD_FIELD_ID, "ITS ALIVE!");
        CRC crc = headerFactory.getItem(Constants.CRC_ID, "0001");

        LL2PFrame frame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);

        String byteString = destAddr.toTransmissionString() + srcAddr.toTransmissionString()  + type.toTransmissionString() + payload.toTransmissionString() + crc.toTransmissionString();


            byte[] byteArray = byteString.getBytes();
            LL2PFrame frame2 = new LL2PFrame(byteArray);
            Log.i(Constants.logTag, crc.explainSelf());



        TableRecordFactory tableRecordFactory = TableRecordFactory.getInstance();
        try {
            InetAddress inet = InetAddress.getByName("10.1.1.1");
            AdjacencyRecord adjacencyRecord = tableRecordFactory.getItem(Constants.ADJACENCY_RECORD_ID, inet, Constants.SRC_ADDR);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        // Lab 3 Tests

      /*  TableRecordFactory tableRecordFactory = TableRecordFactory.getInstance();
        GetIPAddress nameServer = GetIPAddress.getInstance();
        InetAddress inet = nameServer.getInetAddress("10.1.1.1");
        AdjacencyRecord adjacencyRecord = tableRecordFactory.getItem(Constants.ADJACENCY_RECORD_ID, "10.1.1.1"+"c4111e");

        Table adjacencyTable = new Table();
        adjacencyTable.addItem(adjacencyRecord);
        adjacencyTable.removeItem(adjacencyRecord.getKey());

        LL1Daemon daemon = LL1Daemon.getInstance();
        Table adjacencyTable2 = daemon.getAdjacencyTable();
        daemon.addAdjacencyTable("10.1.1.1", Constants.SRC_ADDR);
        try {
            AdjacencyRecord adjacencyRecord1 = (AdjacencyRecord) adjacencyTable2.getItem(0);
            daemon.removeAdjacency(adjacencyRecord1);
        }
        catch(LabException e){
            e.printStackTrace();
        }
        System.out.print("st");

        HeaderFieldFactory headerFactory = HeaderFieldFactory.getInstance();
        LL2PAddressField srcAddr= headerFactory.getItem(Constants.LL2P_SOURCE_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
        LL2PAddressField destAddr= headerFactory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, "112233");
        LL2PTypeField type = headerFactory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ARP_REPLY));
        DatagramPayloadField payload = headerFactory.getItem(Constants.DATAGRAM_PAYLOAD_FIELD_ID, "ITS ALIVE!");
        CRC crc = headerFactory.getItem(Constants.CRC_ID, "0001");

        LL2PFrame frame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
        daemon.addAdjacencyTable("192.168.86.27", "112233");
        daemon.sendFrame(frame);*/

      //Lab 7 Test
      /*  Integer ll2pAddr = Integer.parseInt(Constants.SRC_ADDR, 16);
        Integer ll3pAddr = Integer.parseInt(Constants.LL3P_SRC_ADDR, 16);
        ARPDaemon.getInstance().testARP(ll2pAddr, ll3pAddr);*/

      //lab 8 test
       // LL2Daemon ll2Daemon = LL2Daemon.getInstance();
        //LL1Daemon.getInstance().addAdjacencyTable("10.30.35.84", "112233");
        //ll2Daemon.sendARPRequest(Integer.parseInt("112233", 16));

      //lab 9 test
        //Add two of the same Route, See if a duplicate is added to the routing table
        RoutingTable routingTable = new RoutingTable();
        RoutingTable forwardingTable = new RoutingTable();
        RoutingRecord routingRecord = new RoutingRecord(1,2,8);
        routingTable.addNewRoute(routingRecord);
        routingTable.addNewRoute(routingRecord);
        //Check to make sure record expired from routing table
        try {
            Thread.sleep(6000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        routingTable.expireRecords(Constants.MAX_TIME);

        //Change the distance on an existing route, make sure routing table is updated
        RoutingRecord routingRecord2 = new RoutingRecord(2,3,8);
        routingTable.addNewRoute(routingRecord2);
        RoutingRecord routingRecord3 = new RoutingRecord(2,4,8);
        routingTable.addNewRoute(routingRecord3);

        //Get the best routes from the routing table, make sure they are actually the best routes
        RoutingRecord routingRecord4 = new RoutingRecord(4,4,8);
        RoutingRecord routingRecord5 = new RoutingRecord(4,2,7);
        RoutingRecord routingRecord6 = new RoutingRecord(4,3,5);
        RoutingRecord routingRecord7 = new RoutingRecord(5,1,5);
        RoutingRecord routingRecord8 = new RoutingRecord(5,3,7);
        RoutingRecord routingRecord9 = new RoutingRecord(5,4,6);
        RoutingRecord routingRecord10 = new RoutingRecord(6,2,7);
        RoutingRecord routingRecord11 = new RoutingRecord(6,4,5);
        RoutingRecord routingRecord12 = new RoutingRecord(6,1,6);
        routingTable.addNewRoute(routingRecord4);
        routingTable.addNewRoute(routingRecord5);
        routingTable.addNewRoute(routingRecord6);
        routingTable.addNewRoute(routingRecord7);
        routingTable.addNewRoute(routingRecord8);
        routingTable.addNewRoute(routingRecord9);
        routingTable.addNewRoute(routingRecord10);
        routingTable.addNewRoute(routingRecord11);
        routingTable.addNewRoute(routingRecord12);
        List<RoutingRecord>bestRoutes = routingTable.getBestRoutes();

        //Add routes to the routing table via a list of records, check to make sure they are added
        //Get the best routes for all routes currently in the routing table.
        //Add the best routes to the forwarding table, make sure only the best routes are in the
        //forwarding table
        RoutingRecord routingRecord13 = new RoutingRecord(10,4,8);
        RoutingRecord routingRecord14 = new RoutingRecord(10,2,7);
        RoutingRecord routingRecord15 = new RoutingRecord(10,3,5);
        RoutingRecord routingRecord16 = new RoutingRecord(11,2,5);
        RoutingRecord routingRecord17 = new RoutingRecord(11,3,7);
        RoutingRecord routingRecord18 = new RoutingRecord(11,4,6);
        RoutingRecord routingRecord19 = new RoutingRecord(12,3,7);
        RoutingRecord routingRecord20 = new RoutingRecord(12,4,5);
        RoutingRecord routingRecord21 = new RoutingRecord(12,2,6);
        List<RoutingRecord>routes = new ArrayList<RoutingRecord>();
        routes.add( routingRecord13);
        routes.add(routingRecord14);
        routes.add(routingRecord15);
        routes.add(routingRecord16);
        routes.add(routingRecord17);
        routes.add(routingRecord18);
        routes.add(routingRecord19);
        routes.add(routingRecord20);
        routes.add(routingRecord21);
        routingTable.addRoutes(routes);
        bestRoutes = routingTable.getBestRoutes();
        forwardingTable.addForwardingRoutes(bestRoutes);

        //Make better routes to existing nodes and add them to the routing table.
        //Get the best routes from the routing table
        //Add the best routes to the forwarding table
        RoutingRecord routingRecord22 = new RoutingRecord(10,1,10);
        RoutingRecord routingRecord23 = new RoutingRecord(11,1,11);
        RoutingRecord routingRecord24 = new RoutingRecord(12,1,12);
        List<RoutingRecord>newRoutes = new ArrayList<RoutingRecord>();
        newRoutes.add(routingRecord22);
        newRoutes.add(routingRecord23);
        newRoutes.add(routingRecord24);
        routingTable.addRoutes(newRoutes);
        bestRoutes = routingTable.getBestRoutes();
        forwardingTable.addForwardingRoutes(bestRoutes);


    }
}
