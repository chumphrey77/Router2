package com.goose77.router2.Router2.networks;

import android.util.Log;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * Class that holds constants for the rest of the application to use
 */
public class Constants {

    //Own Network Number Stuff
    public static final Integer OWN_NETWORK_NUMBER = 8;
    public static final Integer OWN_HOST_NUMBER = 8;
    public static final Integer OWN_DISTANCE = 0;

    // LRP Packet Disassembly offsets
    public static final Integer LRP_PACKET_LL3P_ADDR_OFFSET = 0;
    public static final Integer LRP_PACKET_LL3P_ADDR_END_OFFSET = 4;
    public static final Integer LRP_PACKET_SEQ_NUM_OFFSET = 4;
    public static final Integer LRP_PACKET_SEQ_NUM_END_OFFSET = 5;
    public static final Integer LRP_PACKET_COUNT_OFFSET = 5;
    public static final Integer LRP_PACKET_COUNT_END_OFFSET = 6;
    public static final Integer LRP_PACKET_FIRST_NETWORK_DISTANCE_PAIR_OFFSET = 6;
    public static final Integer LRP_PACKET_NETWORK_DISTANCE_PAIR_END_OFFSET =4;

    //Network Distance Pair offsets and byte pad sizes
    public static final Integer NETWORK_CHAR_OFFSET = 0;
    public static final Integer DISTANCE_CHAR_OFFSET = 2;
    public static final Integer NETWORK_DISTANCE_PAIR_BYTE_PAD = 2;

    // Scheduler update intervals and estimated Router boot timer
    public static final Integer LRP_DAEMON_UPDATE_INTERVAL = 10;
    public static final Integer ARP_DAEMON_UPDATE_INTERVAL = 5;
    public static final Integer UI_UPDATE_INTERVAL =1;
    public static final Integer ROUTER_BOOT_TIME = 7;
    public static final Integer LL3P_ADDR_BYTE_LENGTH = 4;

    //Number of threads <- used in Scheduler
    public static final Integer THREAD_COUNT = 5;

    // the IP address of this system will be stored here in dotted decimal notation
    public static final String IP_ADDRESS;

    // These are strings used in debugging messages or messages sent to the log file.
    public static final String routerName = "Goose";
    public static final String logTag = "GOOSE: ";

    // Header field ID so, HeaderFieldFactory knows what field to make
    public static final int NETWORK_DISTANCE_PAIR_FIELD_ID = 3775;
    public static final int LRP_ROUTE_COUNT_FIELD_ID = 3776;
    public static final int LRP_SEQUENCE_NUMBER_FIELD_ID = 3779;
    public static final int LL2P_SOURCE_ADDRESS_FIELD_ID = 2778;
    public static final int LL2P_DEST_ADDRESS_FIELD_ID = 2776;
    public static final int LL2P_TYPE_FIELD_ID = 2777;
    public static final int CRC_ID = 7776;
    public static final int DATAGRAM_PAYLOAD_FIELD_ID = 7779;
    public static final int LL3P_SOURCE_ADDRESS_FIELD_ID = 3777;
    public static final int LL3P_DESTINATION_ADDRESS_FIELD_ID = 3778;

    //Record ID so that TableRecord factory know what kind of recrod to make
    public static final int ADJACENCY_RECORD_ID = 5776;

    //Character length of various fields in a frame
    public static final int ADDR_LENGTh = 3;
    public static final int CRC_LENGTH = 2;
    public static final int TYPE_LENGTH = 2;

    //Length of each header field in bytes
    public static final int ADDR_LENGTH_BYTES = ADDR_LENGTh*2;
    public static final int CRC_LENGTH_BYTES = CRC_LENGTH*2;
    public static final int TYPE_LENGTH_BYTES = TYPE_LENGTH*2;

    //Offset (from beginning) of packet for various fields in a frame in characters
    public static final int DEST_ADDR_OFFSET = 0;
    public static final int SRC_ADDR_OFFSET = 3;
    public static final int TYPE_OFFSET = 6;
    public static final int PAYLOAD_OFFSET = 8;
    public static final int CRC_OFFSET_FROM_END = 2;

    //Offset lengths in bytes
    public static final int DEST_ADDR_OFFSET_BYTES = DEST_ADDR_OFFSET*2;
    public static final int SRC_ADDR_OFFSET_BYTES = SRC_ADDR_OFFSET*2;
    public static final int TYPE_OFFSET_BYTES = TYPE_OFFSET*2;
    public static final int PAYLOAD_OFFSET_BYTES = PAYLOAD_OFFSET*2;
    public static final int CRC_OFFSET_FROM_END_BYTES = CRC_OFFSET_FROM_END*2;

    //Source Address for my router
    public static final String SRC_ADDR = "C4111E";
    public static final String LL3P_SRC_ADDR = "0801";

    //IDs for different types of packets
    public static final int LL2P_TYPE_IS_LL3P = 8001;
    public static final int LL2P_TYPE_IS_RESERVED = 8002;
    public static final int LL2P_TYPE_IS_LRP = 8003;
    public static final int LL2P_TYPE_IS_ECHO_REQUEST = 8004;
    public static final int LL2P_TYPE_IS_ECHO_REPLY = 8005;
    public static final int LL2P_TYPE_IS_ARP_REQUEST = 8006;
    public static final int LL2P_TYPE_IS_ARP_REPLY = 8007;
    public static final int LL2P_TYPE_IS_TEXT = 8008;

    //Offsets for Table Factory
    public static final int INET_OFFSET = 0;
    public static final int IP_ADDR_OFFSET_FROM_END = 6;

    //UDP port
    public static final int UDP_PORT = 49999;


    //Time out Constants
    public static final int MAX_TIME = 30;
    static {
        IP_ADDRESS = getLocalIpAddress();
        Log.i(Constants.logTag, "IP Address is "+IP_ADDRESS); // this will show up in the log file
    }

    /*
     * getLocalIPAddress - this function goes through the network interfaces,
     *    looking for one that has a valid IP address.
     * Care must be taken to avoid a loopback address and IPv6 Addresses.
     * @return - a string containing the IP address in dotted decimal notation.
     */
    private static String getLocalIpAddress() {
        //String address= null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
