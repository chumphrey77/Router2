package com.goose77.router2.Router2.support;

import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 2/1/2018.
 */

/**
 * Keeps a list of LL2PFrames and notifies the SnifferUI when the list changes
 * Observes BootLoader and LL1Daemon for changes
 */
public class FrameLogger extends Observable implements Observer {
    private ArrayList<LL2PFrame> frameList;
    private static FrameLogger ourInstance = new FrameLogger();

    /**
     * Default constructor that creates the LL2PFrame list
     */
    private FrameLogger(){
        frameList = new ArrayList<LL2PFrame>();
    }

    /**
     * When Bootloader notifies the FrameLogger of an update, the FrameLogger notifies the SnifferUI
     * When the LL1Daemon notifies the FrameLogger of an Update, the FrameLogger adds the new frame
     * to the list and notifies the SnifferUI
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Bootloader){
            //todo addObserver SnifferUI
            //  ourInstance.addObserver((SnifferUI) o);
        }
        else if(observable instanceof LL1Daemon){
            if(o instanceof LL2PFrame){
                frameList.add((LL2PFrame) o);
            }
            setChanged();
            notifyObservers();
        }
    }

    public static FrameLogger getInstance(){
        return ourInstance;
    }
}
