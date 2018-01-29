package com.goose77.router2.support;

import com.goose77.router2.networks.Constants;
import com.goose77.router2.networks.headerFields.HeaderField;
import com.goose77.router2.networks.networks.datagram_fields.CRC;
import com.goose77.router2.networks.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.networks.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.networks.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.networks.tableRecord.AdjacencyRecord;
import com.goose77.router2.networks.tableRecord.TableRecord;

import java.net.InetAddress;

/**
 * Created by goose on 1/26/2018.
 */

/**
 * Factory for the creation of various record types
 */
public class TableRecordFactory implements Factory<TableRecord,Object[]> {
    static TableRecordFactory ourInstance = new TableRecordFactory();
    /**
     * Public method to get the desired item of the specified type
     *
     * @param type - The type of object to be created determined at run time
     * @param data - The data to be used to construct the desired object
     * @return object - This object will be of the specified U data type
     */
    @Override
    public <U extends TableRecord>U getItem(int type, Object... data) {
        if(type == Constants.ADJACENCY_RECORD_ID){
            return (U)new AdjacencyRecord((InetAddress) data[0], (String)data[1]);
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
