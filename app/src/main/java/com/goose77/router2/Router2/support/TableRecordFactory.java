package com.goose77.router2.Router2.support;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.tableRecord.AdjacencyRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.net.InetAddress;

/**
 * Created by goose on 1/26/2018.
 */

/**
 * Factory for the creation of various record types
 */
public class TableRecordFactory implements Factory<TableRecord,String> {
    static TableRecordFactory ourInstance = new TableRecordFactory();
    /**
     * Public method to get the desired item of the specified type
     *
     * @param type - The type of object to be created determined at run time
     * @param data - The data to be used to construct the desired object
     * @return object - This object will be of the specified U data type
     */
    @Override
    public <U extends TableRecord>U getItem(int type, String data) {
        if(type == Constants.ADJACENCY_RECORD_ID){
            return (U)new AdjacencyRecord(data);
        }

        return null;
    }

    /**
     * Empty constructor
     */
    private TableRecordFactory(){
    }

    /**
     * Returns this instance of the factory (Singleton Pattern)
     * @return
     */
    public static TableRecordFactory getInstance(){
        return ourInstance;
    }
}
