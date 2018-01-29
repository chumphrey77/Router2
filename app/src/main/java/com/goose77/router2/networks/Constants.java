package com.goose77.router2.networks;

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
    // the IP address of this system will be stored here in dotted decimal notation
    public static final String IP_ADDRESS;

    // These are strings used in debugging messages or messages sent to the log file.
    public static final String routerName = "Magrathea";
    public static final String logTag = "MAGRATHEA: ";

    // Header field ID so, HeaderFieldFactory knows what field to make
    public static final int LL2P_SOURCE_ADDRESS_FIELD_ID = 2778;
    public static final int LL2P_DEST_ADDRESS_FIELD_ID = 2776;
    public static final int LL2P_TYPE_FIELD_ID = 2777;
    public static final int CRC_ID = 7776;
    public static final int DATAGRAM_PAYLOAD_FIELD_ID = 7779;

    //Record ID so that TableRecord factory know what kind of recrod to make
    public static final int ADJACENCY_RECORD_ID = 5776;

    //Lengths (int bytes) of various fields in a frame
    public static final int ADDR_LENGTh = 3;
    public static final int CRC_LENGTH = 2;
    public static final int TYPE_LENGTH = 2;

    //Offset (from beginning) of packet for various fields in a frame
    public static final int DEST_ADDR_OFFSET = 0;
    public static final int SRC_ADDR_OFFSET = 3;
    public static final int TYPE_OFFSET = 6;
    public static final int PAYLOAD_OFFSET = 8;
    public static final int CRC_OFFSET_FROM_END = 2;

    //Source Address for my router
    public static final String SRC_ADDR = "C4111E";

    //IDs for different types of packets
    public static final int LL2P_TYPE_IS_LL3P = 8001;
    public static final int LL2P_TYPE_IS_RESERVED = 8002;
    public static final int LL2P_IS_LRP = 8003;
    public static final int LL2P_TYPE_IS_ECHO_REQUEST = 8004;
    public static final int LL2P_TYPE_IS_ECHO_REPLY = 8005;
    public static final int LL2P_TYPE_IS_ARP_REQUEST = 8006;
    public static final int LL2P_TYPE_IS_ARP_REPLY = 8007;
    public static final int LL2P_TYPE_IS_TEXT = 8008;
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
