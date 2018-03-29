package com.goose77.router2.Router2.networks.daemon;

import com.goose77.router2.Router2.UI.TableUI;
import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by goose on 2/28/2018.
 */

/**
 * Time based schedule class that will periodically run the "run()" methods in classes that implement
 * the runnable interface
 */
public class Scheduler implements Observer {
    private static Scheduler ourInstance = new Scheduler();
    private ScheduledThreadPoolExecutor threadManager;
    private ARPDaemon arpDaemon;
   private LRPDaemon lrpDaemon;
    private TableUI tableUI;

    /**
     * Constructor that gets references to the objects that will be run
     */
    private Scheduler(){
        arpDaemon = ARPDaemon.getInstance();
        lrpDaemon = LRPDaemon.getInstance();
        tableUI = UIManager.getInstance().getTableUI();
    }


    public static Scheduler getInstance(){
        return ourInstance;
    }

    /**
     * When bootloader is done booting the scheduler will start scheduling events to occur on a
     * certain time interval
     * TableUI will update the display elements every second
     * ARPDaemon will update the ARP list every 5 seconds
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        threadManager = new ScheduledThreadPoolExecutor(Constants.THREAD_COUNT);
        threadManager.scheduleAtFixedRate(tableUI,
                                    Constants.ROUTER_BOOT_TIME,
                                    Constants.UI_UPDATE_INTERVAL,
                                    TimeUnit.SECONDS);
        threadManager.scheduleAtFixedRate(arpDaemon,
                                    Constants.ROUTER_BOOT_TIME,
                                    Constants.ARP_DAEMON_UPDATE_INTERVAL,
                                    TimeUnit.SECONDS);
        threadManager.scheduleAtFixedRate(lrpDaemon,
                                    Constants.ROUTER_BOOT_TIME,
                                    Constants.LRP_DAEMON_UPDATE_INTERVAL,
                                    TimeUnit.SECONDS);

    }
}
