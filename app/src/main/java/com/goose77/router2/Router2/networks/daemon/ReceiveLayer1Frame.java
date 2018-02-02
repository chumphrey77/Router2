package com.goose77.router2.Router2.networks.daemon;

import android.os.AsyncTask;
import android.util.Log;

import com.goose77.router2.Router2.networks.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveLayer1Frame extends AsyncTask<Void, Void, byte[]> {

    private static DatagramSocket receiveSocket;

    @Override
    protected byte[] doInBackground(Void... nothingToSeeHere) {

        byte[] receiveData = new byte[1024];
        //   completely hide this function from the layer 1 daemon by placing all socket stuff here.
        if (receiveSocket==null){
            try {
                receiveSocket = new DatagramSocket(Constants.UDP_PORT); // receive port defined above.
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        // create a datagram packet to receive the UPD data.
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        Log.d(Constants.logTag, "Inside rx unicast Thread");
        try {
            receiveSocket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int bytesReceived = receivePacket.getLength ();
        byte[] frameBytes = new String(receivePacket.getData()).substring(0,bytesReceived).getBytes();

        Log.d(Constants.logTag, "Received bytes: "+ new String(frameBytes));
        return frameBytes;
    }

    @Override
    protected void onPostExecute(byte[] frameBytes) {
        LL1Daemon.getInstance().processLayer1FrameBytes(frameBytes);
        new ReceiveLayer1Frame().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}

