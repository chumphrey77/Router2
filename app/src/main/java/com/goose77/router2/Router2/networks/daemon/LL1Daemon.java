package com.goose77.router2.Router2.networks.daemon;

import android.os.AsyncTask;
import android.util.Log;

import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.Table;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.networks.tableRecord.AdjacencyRecord;
import com.goose77.router2.Router2.support.FrameLogger;
import com.goose77.router2.Router2.support.GetIPAddress;
import com.goose77.router2.Router2.support.LabException;
import com.goose77.router2.Router2.support.TableRecordFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 2/1/2018.
 */

/**
 * The level 1 daemon that provides services like sending and
 * receiving packets to the level 2 daemon
 */
public class LL1Daemon extends Observable implements Observer {
    private static LL1Daemon ourInstance =  new LL1Daemon();
    private Table adjacencyTable;
    private GetIPAddress nameServer;
    private UIManager uiManager;
    private TableRecordFactory tableRecordFactory;
    private SendLayer1Frame frameTransmitter;
    private  LL2Daemon ll2Daemon;

    /**
     * Default constructor. creates the adjacency table
     */
    private LL1Daemon(){
        adjacencyTable = new Table();
    }

    /**
     * Singleton getter for the LL1Daemon
     * @return LL1Daemon ourInstance
     */
    public static LL1Daemon getInstance(){
        return ourInstance;
    }

    /**
     * When notified of an change in Bootloader (Router is booted)
     * Get the instance of InetAddress converter object
     * Get the instance of a TableFactory
     * Make the frameLogger an observer
     * Get the instance of the UIManager
     * Create the frame transimitter
     * Startup the Receieve frame thread to Rx frames
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        nameServer = GetIPAddress.getInstance();
        tableRecordFactory = TableRecordFactory.getInstance();
        addObserver(FrameLogger.getInstance());
        uiManager = UIManager.getInstance();
        ll2Daemon = LL2Daemon.getInstance();

        new ReceiveLayer1Frame().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * Remove a router node from the Adjacency table
     * @param  recordToRemove
     */
    public void removeAdjacency(AdjacencyRecord recordToRemove){
        try {
            if (adjacencyTable.getItem(recordToRemove) instanceof  AdjacencyRecord) {
               adjacencyTable.removeItem(adjacencyTable.getItem(recordToRemove).getKey());
            }
        }
        catch(LabException e){
            e.printStackTrace();
        }
    }

    /**
     * Return the Adjacency Table
     * @return AdjacencyTable adjacencyTable
     */
    public Table getAdjacencyTable(){
        return adjacencyTable;
    }

    public void addAdjacencyTable(String ipAddress, String ll2pAddress){
        AdjacencyRecord record = tableRecordFactory.getItem(Constants.ADJACENCY_RECORD_ID, ipAddress+ll2pAddress);
        adjacencyTable.addItem(record);
        ll2Daemon.sendARPRequest(Integer.parseInt(ll2pAddress, 16));
    }

    public void addAdjacencyTable(AdjacencyRecord record){
        adjacencyTable.addItem(record);
    }


    /**
     * Use the adjacency table to get the adjacenecyRecord and retrieve the iNet object from it
     * Create a send packet from the iNet object and the frame that was passed in
     * Send the frame to the adjacent node.
     * Notify observers that the frame was sent
     * @param ll2p
     */
    public void sendFrame(LL2PFrame ll2p){
        try {
            frameTransmitter = new SendLayer1Frame();
            AdjacencyRecord adjacentRouter = (AdjacencyRecord) adjacencyTable.getItem(ll2p.getDestinationAddress().getAddress());
            if(adjacentRouter != null) {
                InetAddress ipAddress = adjacentRouter.getIpAddress();
                DatagramPacket sendPacket = new DatagramPacket(ll2p.toTransmissionString().getBytes(), ll2p.toTransmissionString().length(),
                        ipAddress,
                        Constants.UDP_PORT);
                frameTransmitter.execute(sendPacket);
                setChanged();
                notifyObservers(ll2p);
            }
        }
        catch(LabException e){
            e.printStackTrace();
        }

    }

    /**
     * Recieve and output a frame from elsewhere
     * Notify observers that a frame was received and give them the frame.
     * @param frame
     */
    public void processLayer1FrameBytes(byte[] frame){
        LL2PFrame layer2Frame = new LL2PFrame(frame);
        //Log.i(Constants.logTag, layer2Frame.toProtocolExplanationString());
        setChanged();
        notifyObservers(layer2Frame);

       ll2Daemon.processLL2PFrame(layer2Frame);
    }
}
