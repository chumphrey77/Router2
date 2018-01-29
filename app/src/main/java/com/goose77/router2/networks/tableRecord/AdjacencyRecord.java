package com.goose77.router2.networks.tableRecord;

import com.goose77.router2.networks.Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Encapsulates an Adjacency table record
 */
public class AdjacencyRecord extends TableRecordClass {
    private int ll2pAddress;
    private InetAddress ipAddress;

    /**
     * Secondary Constructor that takes the address as an int
     * @param IntAddress ipAddress
     * @param Integer ll2pAddress
     */
    public AdjacencyRecord(InetAddress ipAddress, Integer ll2pAddress){
        super();
        this. ipAddress = ipAddress;
        this.ll2pAddress = ll2pAddress;
    }

    /**
     * Constructor that takes the address as a string
     * @param InetAddress ipAddress
     * @param String ll2pAddressString
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
        String ll2pAddressString = data.substring(data.length() - Constants.ADDR_LENGTh*2);
        String ipAddressString = data.substring(0, data.length()-Constants.ADDR_LENGTh*2);
        try {
            this.ipAddress = InetAddress.getByName(ipAddressString);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.ll2pAddress = Integer.parseInt(ll2pAddressString);
    }

    /**
     * Returns the key for this record
     * @return Integer
     */
    public int getKey(){
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
        string = "LL2P Address: 0x" + ll2pAddress + "; IP Address: " + ipAddress.toString() + "\n";
        return string;
    }

    public int getLl2pAddress() {
        return ll2pAddress;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setLl2pAddress(int ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}
