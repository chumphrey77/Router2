package com.goose77.router2.support;

import android.app.Activity;

import com.goose77.router2.UI.UIManager;

import com.goose77.router2.networks.Constants;


import java.util.Observable;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * Extends Observable
 * Boots the router and notifies all observing objects of boot completion.
 * Also runs any tests
 */
public class Bootloader extends Observable {
    /**
     * Gets a reference to the MainAcivity
     * Constructs the BootLoader
     * @param parentActivity
     */
    public Bootloader(Activity parentActivity){
        bootRouter(parentActivity);
    }

    /**
     * Adds oberservers, makes changes and notifies the observers of changes
     * Testing will be done in this method
     * @param parentActivity
     */
    private void bootRouter(Activity parentActivity){
        ParentActivity.setParentActivity(parentActivity);
        addObserver(UIManager.getInstance());
        setChanged();
        notifyObservers();
        UIManager.getInstance().displayMessage(Constants.routerName + " is up and running!");
        UIManager.getInstance().displayMessage("The IP Address is " + Constants.IP_ADDRESS);

        test();
    }

    /**
     * Method for testing any functionaility
     */
    private void test(){

    }
}
