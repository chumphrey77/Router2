package com.goose77.router2.Router2.support;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram.ARPDatagram;
import com.goose77.router2.Router2.networks.datagram.Datagram;
import com.goose77.router2.Router2.networks.datagram.HeaderField;
import com.goose77.router2.Router2.networks.datagram.LL3PDatagram;
import com.goose77.router2.Router2.networks.datagram.LRPPacket;
import com.goose77.router2.Router2.networks.datagram.TextDatagram;

/**
 * Created by goose on 3/1/2018.
 */

/**
 * Factory for the creation of the various datagram types
 */
public class DatagramFactory implements Factory<Datagram,String> {
    private static DatagramFactory ourInstance = new DatagramFactory();

    private DatagramFactory(){}
    /**
     * Public method to get the desired item of the specified type
     *
     * @param type - The type of object to be created determined at run time
     * @param data - The data to be used to construct the desired object
     * @return object - This object will be of the specified U data type
     */
    @Override
    public <U extends Datagram> U getItem(int type, String data) {
        if(type == (Constants.LL2P_TYPE_IS_TEXT)) {
            return (U)new TextDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_ARP_REPLY)){
            return (U)new ARPDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_ARP_REQUEST)){
            return (U)new ARPDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_ECHO_REPLY)){
            return (U)new TextDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_ECHO_REQUEST)){
            return (U)new TextDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_LRP)){
            return (U)new LRPPacket(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_LL3P)){
            return (U) new LL3PDatagram(data);
        }
        else if(type == (Constants.LL2P_TYPE_IS_RESERVED)){
            //todo implement this type
        }
        return null;
    }

    public static DatagramFactory getOurInstance(){
        return ourInstance;
    }
}
