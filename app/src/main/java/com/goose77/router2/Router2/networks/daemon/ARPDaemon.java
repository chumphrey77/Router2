package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.TimedTable;
import com.goose77.router2.Router2.networks.datagram.ARPDatagram;
import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Colten on 2/22/2018.
 */

/**
 * Controls Layer 3 ARP packet usage
 */
public class ARPDaemon implements Runnable, Observer{
    private TimedTable arpTable;
    private static ARPDaemon ourInstance = new ARPDaemon();
    private LL2Daemon ll2Daemon;

    /**
     * Instantiates a TimedTable when the object is created
     */
    private ARPDaemon(){
        arpTable = new TimedTable();
    }

    /**
     * Currently just a placeholder
     * Will be implemented next lab
     */
    @Override
    public void run() {
        ArrayList<TableRecord> expired = arpTable.expireRecords(Constants.MAX_TIME);
    }

    /**
     * Singleton getter
     * @return ARPDaemon
     */
    public static ARPDaemon getInstance(){
        return ourInstance;
    }

    /**
     * When the bootloader finishes loading the router, gets a reference of LL2Daemon
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        ll2Daemon = LL2Daemon.getInstance();
    }

    /**
     * Creates an arpRecord, checks if it already exists in the TimedTable
     * If the record already exists, touch the record
     * If the record doesn't already exist, add it to the TimedTable
     * @param ll2pAddress
     * @param ll3pAddress
     */
    public void addARPEntry(Integer ll2pAddress, Integer ll3pAddress){
        ARPRecord arpRecord = new ARPRecord(ll2pAddress, ll3pAddress);
        if(arpTable.containsRecord(arpRecord)){
            arpTable.touch(ll3pAddress);
        }
        else{
            arpTable.addItem(arpRecord);
        }
    }

    /**
     * Tests ARP functions
     * Adds 2 of the same record to see if ARPDaemon properly touches already created record
     * Creates a different record and adds it to the table
     * Checks for expiration
     * Pauses for 5 Seconds
     * Waits for expiration
     * @param ll2pAddress
     * @param ll3pAddress
     */
    public void testARP(Integer ll2pAddress, Integer ll3pAddress){
        addARPEntry(ll2pAddress, ll3pAddress);
        addARPEntry(ll2pAddress, ll3pAddress);
        addARPEntry(ll2pAddress+1, ll3pAddress+1);
        ArrayList<TableRecord> expired = arpTable.expireRecords(Constants.MAX_TIME);
        try {
            Thread.sleep(5000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<TableRecord> expired2 = arpTable.expireRecords(Constants.MAX_TIME);
    }

    /**
     * Touches an ARP Entry to resets its age timer
     * @param ll3pAddress
     */
    public void updateARPEntry(Integer ll3pAddress){
        try {
            if (arpTable.getItem(ll3pAddress) instanceof ARPRecord) {
                arpTable.touch(ll3pAddress);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public TimedTable getArpTable(){
        return arpTable;
    }

    /**
     * Returns each of the individual LL3P addresses represented in the ARP Table
     * in a list format
     * @return
     */
    public List<Integer> getAttachedNodes(){
        List<Integer> ll3PAddresses = new ArrayList<>();
        for(TableRecord record : arpTable.getTableAsList()){
            if(record instanceof ARPRecord){
                ll3PAddresses.add(record.getKey());
            }
        }
        return ll3PAddresses;
    }

    /**
     * Adds an ARPEntry (or touches an existing ARPEntry when a Reply is Rx'd
     * @param ll2pAddress
     * @param arpDatagram
     */
    public void processARPReply(String ll2pAddress, ARPDatagram arpDatagram){
        Integer ll2p = Integer.parseInt(ll2pAddress, 16);
        Integer ll3p = Integer.parseInt(arpDatagram.toTransmissionString(), 16);
        addARPEntry(ll2p, ll3p);
    }

    /**
     * Adds an ARPEntry (or touches an existing ARPEntry when a Request is Rx'd
     * Also sends an ARPReply
     * @param ll2pAddress
     * @param arpDatagram
     */
    public void processARPRequest(String ll2pAddress, ARPDatagram arpDatagram){
        Integer ll2p = Integer.parseInt(ll2pAddress, 16);
        Integer ll3p = Integer.parseInt(arpDatagram.toTransmissionString(), 16);
        addARPEntry(ll2p, ll3p);
        ll2Daemon.sendARPReply(ll2p);
    }

    /**
     * Get the LL2PAddress that corresponds to an LL3Address in the ARP Table
     * @param ll3pAddress
     * @return
     */
    public Integer getMACAddress(Integer ll3pAddress){
        Integer macAddr = null;
        try {
            ARPRecord record = (ARPRecord) arpTable.getItem(ll3pAddress);
            macAddr = record.getLl2pAddress().getAddress();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return macAddr;
    }

    public ArrayList<TableRecord> getARPTable(){
        return arpTable.getTableAsArrayList();
    }

    public void snedARPRequest(Integer ll2pAddress){
       ll2Daemon.sendARPRequest(ll2pAddress);
    }
}
