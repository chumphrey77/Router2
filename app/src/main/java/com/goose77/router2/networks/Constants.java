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
