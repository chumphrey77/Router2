package com.goose77.router2.Router2.support;

import android.app.Activity;
import android.util.Log;

import com.goose77.router2.Router2.UI.UIManager;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.Table;
import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.tableRecord.AdjacencyRecord;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        addObserver(FrameLogger.getInstance());
        addObserver(UIManager.getInstance().getTableUI());
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

    }
}
