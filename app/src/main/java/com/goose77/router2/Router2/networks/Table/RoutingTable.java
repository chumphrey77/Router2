package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.LabException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goose on 3/8/2018.
 */

public class RoutingTable extends TimedTable {

    public RoutingTable(){
        super();
    }

    public void addNewRoute(TableRecord newEntry){
        RoutingRecord entry = (RoutingRecord) newEntry;
        if(containsRoute(entry.getKey())) {
            for (TableRecord record : table) {
                RoutingRecord routingRecord = (RoutingRecord) record;
                if (routingRecord.getKey().equals(entry.getKey())) {
                    if (routingRecord.getDistance().equals(entry.getDistance())) {
                        touch(routingRecord.getKey());
                    } else {
                        removeItem(routingRecord.getKey());
                        addItem(entry);
                    }
                }
            }
        }
        else{
            addItem((entry));
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
            RoutingRecord routingRecord  = (RoutingRecord) record;
            if(routingRecord.getNetworkNumber().equals(network)){
                return routingRecord.getNextHop();
            }
        }
        return null;
    }

    public List<RoutingRecord> getRouteListExcluding(Integer ll3pAddr){
        List<RoutingRecord> exclusionList = new ArrayList<RoutingRecord>();
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(!(routingRecord.getNextHop().equals(ll3pAddr))){
                exclusionList.add(routingRecord);
            }
        }
        return exclusionList;
    }

    public void removeRoutesFRom(Integer ll3pAddr){
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(routingRecord.getNextHop().equals(ll3pAddr)){
                table.remove(routingRecord);
            }
        }
    }

    public List<RoutingRecord> getBestRoutes(){
        Integer routingNumber = 0;
        Integer lowestDistance = 0;
        List<RoutingRecord> bestRoutes = new ArrayList<RoutingRecord>();
        Boolean alreadyContains;
        for(TableRecord record : table){
            lowestDistance = 0;
            RoutingRecord routingRecord = (RoutingRecord) record;
            routingNumber = routingRecord.getNetworkNumber();
            alreadyContains = false;
            for(RoutingRecord bestRoute : bestRoutes){
                if(bestRoute.getNetworkNumber().equals(routingNumber)){
                    alreadyContains = true;
                }
            }
            if(!alreadyContains){
                for(TableRecord compareRecord : table){
                    RoutingRecord compareRoutingRecord = (RoutingRecord) compareRecord;
                    if(compareRoutingRecord.getNetworkNumber().equals(routingRecord.getNetworkNumber())){
                        if(compareRoutingRecord.getDistance() < lowestDistance){
                            lowestDistance = compareRoutingRecord.getDistance();
                        }
                    }
                }
                for(TableRecord compareRecord : table){
                    RoutingRecord compareRoutingRecord = (RoutingRecord) compareRecord;
                    if(compareRoutingRecord.getNetworkNumber().equals(routingRecord.getNetworkNumber())){
                        if(compareRoutingRecord.getDistance().equals(lowestDistance)){
                            bestRoutes.add(compareRoutingRecord);
                        }
                    }
                }
            }
        }
        return bestRoutes;
    }

    public RoutingRecord getBestRoute (Integer network){
        try {
            for (TableRecord record : table) {
                RoutingRecord routingRecord = (RoutingRecord) record;
                if (routingRecord.getNetworkNumber().equals(network)) {
                    return routingRecord;
                }
            }
            throw new LabException("Best route for network: " + network + " not found");
        }
        catch(LabException e){
            e.printStackTrace();
        }
        return null;
    }

    public void addRoutes(List<RoutingRecord> routes){
        for(RoutingRecord routingRecord : routes){
            addNewRoute(routingRecord);
        }
    }

    private boolean containsRoute(Integer key){
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(routingRecord.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }
}
