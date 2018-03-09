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
        List<RoutingRecord> bestRoutes = new ArrayList<RoutingRecord>();
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            for(RoutingRecord bestRouteSoFar : bestRoutes){
                if(routingRecord.getNetworkNumber().equals(bestRouteSoFar.getNetworkNumber())){
                    if(routingRecord.getDistance() < bestRouteSoFar.getDistance()){
                        bestRoutes.remove(bestRouteSoFar);
                        bestRoutes.add(routingRecord);
                    }
                }
                else{
                    bestRoutes.add(routingRecord);
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
}
