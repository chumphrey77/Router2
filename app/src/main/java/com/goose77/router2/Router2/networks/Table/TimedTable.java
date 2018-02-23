package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.ARPRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.util.ArrayList;

/**
 * Created by Colten on 2/22/2018.
 */

public class TimedTable extends Table {

    public TimedTable(){
        super();
    }

    public ArrayList<TableRecord> expireRecords(Integer maxAgeAllowed){
        ArrayList<TableRecord> expiredRecords = new ArrayList<TableRecord>();
        for(TableRecord tableRecord : table){
            if(tableRecord.getAgeInSeconds() > maxAgeAllowed){
                expiredRecords.add(tableRecord);
                table.remove(tableRecord);
            }
        }
        return expiredRecords;
    }

    public void touch(Integer key){
        try {
            this.touch(key);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

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
