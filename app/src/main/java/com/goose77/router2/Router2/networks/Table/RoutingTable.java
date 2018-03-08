package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

/**
 * Created by goose on 3/8/2018.
 */

public class RoutingTable extends TimedTable {

    public RoutingTable(){
        super();
    }

    public void addNewRoute(TableRecord newEntry){
        RoutingRecord entry = (RoutingRecord) newEntry;
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord)record;
            if(routingRecord.getKey().equals(entry.getKey())){
                if(routingRecord.getDistance().equals(entry.getDistance())){
                    touch(routingRecord.getKey());
                }
                else{
                    removeItem(routingRecord.getKey());
                    addItem(record);
                }
            }
            else{
                addItem(record);
            }
        }
    }

    @Override
    public void removeItem(Integer key) {
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(table.contains(record)){
                if(routingRecord.getKey().equals(key)){
                    table.remove(record);
                }
            }
        }
    }

    public Integer getNextHop(Integer network){
        for(TableRecord record : table){
            
        }
    }
}
