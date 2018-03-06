package com.goose77.router2.Router2.UI;

import android.app.Activity;
import android.util.Log;

import com.goose77.router2.R;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.daemon.ARPDaemon;
import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.networks.daemon.LL2Daemon;
import com.goose77.router2.Router2.support.ParentActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 2/8/2018.
 */

/**
 *  Manages the various SingleTableUI objects
 *  Observers to the bootloader so it will create the SingleTableUI elements when boot up is complete
 */
public class TableUI implements Runnable, Observer{
    private AdjacencyTableUI adjacencyUI;
    private SingleTableUI arpTableUI;
    private SingleTableUI routingTableUI;
    private SingleTableUI forwardingUI;

    /**
     * Empty constructor
     */
    public TableUI( ){
        //Log.i(Constants.logTag, "Stuff");
    }

    /**
     *  Runnable allows us to run this method on another thread
     */
    @Override
    public void run() {
        arpTableUI.updateView();
    }

    /**
     * Part of the ovserver interface
     * When bootloader is notifies this object it will create the various SingleTableUI objects
     * that will be displayed on the UI
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        Activity activity = ParentActivity.getParentActivity();
        adjacencyUI = new AdjacencyTableUI(activity, R.id.AdjacencyTable, LL1Daemon.getInstance().getAdjacencyTable(), LL1Daemon.getInstance());
        arpTableUI = new SingleTableUI(activity, R.id.arpTableListView, ARPDaemon.getInstance().getArpTable());
        //todo instantiate the other SingleTableUI objcets
    }
}
