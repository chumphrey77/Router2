package com.goose77.router2.Router2.networks.tableRecord;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.support.GetIPAddress;
import com.goose77.router2.Router2.support.Utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Encapsulates an Adjacency table record
 */
public class AdjacencyRecord extends TableRecordClass {
    private Integer ll2pAddress;
    private InetAddress ipAddress;

    /**
     * Secondary Constructor that takes the address as an int
     * @param  ipAddress
     * @param  ll2pAddress
     */
    public AdjacencyRecord(InetAddress ipAddress, Integer ll2pAddress){
        super();
        this. ipAddress = ipAddress;
        this.ll2pAddress = ll2pAddress;
    }

    /**
     * Constructor that takes the address as a string
     * @param  ipAddress
     * @param ll2pAddressString
     */
    public AdjacencyRecord(InetAddress ipAddress, String ll2pAddressString){
        super();
        this. ipAddress = ipAddress;
        this.ll2pAddress = Integer.parseInt(ll2pAddressString, 16);    }

    /**
     * Primaray constructor that just takes in a string and parses it
     * @param data
     */
    public AdjacencyRecord(String data){
        super();
        String ll2pAddressString = data.substring(data.length() - Constants.ADDR_LENGTH_BYTES);
        String ipAddressString = data.substring(0, data.length()-Constants.ADDR_LENGTH_BYTES);
        GetIPAddress nameServer = GetIPAddress.getInstance();
        this.ipAddress = nameServer.getInetAddress(ipAddressString);
        this.ll2pAddress = Integer.parseInt(ll2pAddressString,16);
    }

    /**
     * Returns the key for this record
     * @return Integer
     */
    public Integer getKey(){
        return ll2pAddress;
    }

    /**
     * Return how long it has been since this record was updated (in seconds)
     * @return Integer
     */
    public int getAgeInSeconds(){
        return 0;
    }

    /**
     * Returns a debugging string
     * @return String
     */
    public String toString(){
        String string;
        string = "LL2P Address: " + Integer.toHexString(ll2pAddress) + "; IP Address: " + ipAddress.toString().substring(1) + "\n";
        return string;
    }

    public String getLl2pAddress() {
        return Integer.toHexString(ll2pAddress);
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setLl2pAddress(Integer ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
