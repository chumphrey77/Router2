package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.networks.Table.TimedTable;
import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Colten on 2/22/2018.
 */

public class ARPDaemon implements Runnable, Observer{
    private TimedTable arpTable;
    private static ARPDaemon ourInstance = new ARPDaemon();
    private LL2Daemon ll2Daemon;

    public ARPDaemon(){
        arpTable = new TimedTable();
    }

    @Override
    public void run() {
        //todo Blank for now
    }

    @Override
    public void update(Observable observable, Object o) {
        ll2Daemon = LL2Daemon.getInstance();
        //todo add this class as an observer to Bootloader
    }

    public void addARPEntry(Integer ll2pAddress, Integer ll3pAddress){
        ARPRecord arpRecord = new ARPRecord(ll2pAddress, ll3pAddress);
        if(arpTable.containsRecord(arpRecord)){
            arpTable.touch(ll2pAddress);
        }
        else{
            arpTable.addItem(arpRecord);
        }
    }

    public void testARP(Integer ll2pAddress, Integer ll3pAddress){
        addARPEntry(ll2pAddress, ll3pAddress);
        addARPEntry(ll2pAddress, ll3pAddress);
        addARPEntry(ll2pAddress+1, ll3pAddress+1);
        ArrayList<TableRecord> expired = arpTable.expireRecords(5);
        //todo call testARP in bootloader
    }
}
