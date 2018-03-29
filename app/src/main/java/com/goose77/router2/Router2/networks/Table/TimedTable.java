package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.util.ArrayList;

/**
 * Created by Colten on 2/22/2018.
 */

/**
 * Table class that has records that expire after a given length of time
 */
public class TimedTable extends Table {

    /**
     * Calls the Table constructor
     */
    public TimedTable(){
        super();
    }

    /**
     * Checks each record to see if it expires
     * Add expired records to an ArrayList of expired records
     * Then removes each expired record from the table
     * returns the expired records
     * @param maxAgeAllowed
     * @return expiredRecords
     */
    public ArrayList<TableRecord> expireRecords(Integer maxAgeAllowed){
        ArrayList<TableRecord> expiredRecords = new ArrayList<TableRecord>();
        for(TableRecord tableRecord : table){
            if(tableRecord.getAgeInSeconds() > maxAgeAllowed){
                expiredRecords.add(tableRecord);
            }
        }
        for(TableRecord expiredRecord: expiredRecords){
            table.remove(expiredRecord);
        }
        if(expiredRecords.size() > 0){
            setChanged();
            notifyObservers();
        }
        return expiredRecords;
    }

    /**
     * Update the last time a record was used to now
     * @param key
     */
    public void touchRoutingRecord(Integer key){
        try {
            ((RoutingRecord) this.getItem(key)).updateTime();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Update the last time a record was used to now
     * @param key
     */
    public void touch(Integer key){
        try {
            ((ARPRecord) this.getItem(key)).updateTime();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the list contains a certain record
     * @param record
     * @return containsRecord
     */
    public boolean containsRecord(TableRecord record) {
        if (record instanceof ARPRecord) {
            for (TableRecord thisRecord : table) {
                if ((thisRecord).getKey().equals(record.getKey())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
