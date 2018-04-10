package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.RoutingRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.LabException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goose on 3/8/2018.
 */

/**
 * This class is used for both the routing table and forwarding table objects
 * The routing table stores all known up to date routes to a node
 * The forwarding table only hold the best routes from the routing table
 * Both tables are timed and the records will expire from them if the record isn't "touched" by the
 * end of the expiration time
 */
public class RoutingTable extends TimedTable {

    /**
     * Constructor just calls the TimedTable super constructor
     */
    public RoutingTable(){
        super();
    }

    /**
     * For adding new routes to the routing table
     * Checks to make sure the new route isn't already in the table -> if it is it touches the
     * record
     * If it isn't already in the table it adds it
     * If the record is in the table, but the distance field has changed, it updates the record
     * in the table to reflect the change
     * @param newEntry
     */
    public void addNewRoute(TableRecord newEntry){
        RoutingRecord entry = (RoutingRecord) newEntry;
        RoutingRecord addRecord = null;
        Integer removalKey = null;
        if(containsRoute(entry.getKey())) {
            for (TableRecord record : table) {
                RoutingRecord routingRecord = (RoutingRecord) record;
                if (routingRecord.getKey().equals(entry.getKey())) {
                   if (routingRecord.getDistance().equals(entry.getDistance())) {
                       touchRoutingRecord(routingRecord.getKey());
                    } else {
                        removalKey = routingRecord.getKey();
                        addRecord = entry;
                    }
                }
            }
        }

        else{
            addItem((entry));
        }
        if(addRecord != null){
            removeItem(removalKey);
            addItem(addRecord);
        }
    }

    /**
     * Removes a record from the table based on the key
     * @param key@return TableRecord record
     */
    @Override
    public void removeItem(Integer key) {
        RoutingRecord removalRecord = null;
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(table.contains(record)){
                if(routingRecord.getKey().equals(key)){
                    removalRecord = (RoutingRecord) record;
                }
            }
        }
        if(removalRecord != null) {
            table.remove(removalRecord);
        }
    }

    /**
     * Used for the forwarding table
     * Simply returns the next hop to get to a network stored in the forwarding table
     * @param network
     * @return
     */
    public Integer getNextHop(Integer network){
        for(TableRecord record : table){
            RoutingRecord routingRecord  = (RoutingRecord) record;
            if(routingRecord.getNetworkNumber().equals(network)){
                return routingRecord.getNextHop();
            }
        }
        return null;
    }

    /**
     * Gets a route list of all routes that don't have the next hop of a given ll3paddress
     * @param ll3pAddr
     * @return
     */
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

    /**
     * Removes all routes that were obtained from a given ll3p address
     * @param ll3pAddr
     */
    public void removeRoutesFrom(Integer ll3pAddr){
        for(TableRecord record : table){
            RoutingRecord routingRecord = (RoutingRecord) record;
            if(routingRecord.getNextHop().equals(ll3pAddr)){
                table.remove(routingRecord);
            }
        }
    }

    /**
     * Returns a list of the best routes for every node known in the routing table
     * Best being defined as fewest hops
     * @return
     */
    public List<RoutingRecord> getBestRoutes(){
        Integer routingNumber = 0;
        Integer lowestDistance = 255;
        List<RoutingRecord> bestRoutes = new ArrayList<RoutingRecord>();
        Boolean alreadyContains;
        for(TableRecord record : table){
            lowestDistance = 255;
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
                                if(compareRoutingRecord.getDistance() < lowestDistance ){
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

    /**
     * Finds the best route for a single node.
     * @param network
     * @return
     */
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

    /**
     * Uses the addNewRoutes method to add a list of routes to the routing table
     * @param routes
     */
    public void addRoutes(List<RoutingRecord> routes){
        for(RoutingRecord routingRecord : routes){
            addNewRoute(routingRecord);
        }
    }

    /**
     * Rather than worrying about updating times on records in the forwarding table
     * and having to delete routes that are no longer vaild
     * I figured I might as well just delete all the routes in the forwarding table
     * and all the best routes back in. Simpler this way
     * @param routes
     */
    public void addForwardingRoutes(List<RoutingRecord> routes){
        table.clear();
        table.addAll(routes);
    }

    /**
     * Simply determines whether a route is contained in the table.
     * Replies with a boolean yes or no
     * @param key
     * @return
     */
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
