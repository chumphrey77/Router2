package com.goose77.router2.Router2.UI;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.goose77.router2.Router2.support.Bootloader;
import com.goose77.router2.Router2.support.ParentActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * The high level User interface class
 * Observers boot loader for changes
 * Gives access to changing UI elements
 * Implements the Singleton Pattern
 */
public class UIManager implements Observer {
    private static final UIManager ourInstance = new UIManager();
    private Activity parentActivity = ParentActivity.getParentActivity();
    private Context context;
    private TableUI tableUI;
    private SnifferUI snifferUI;
    private Messenger messenger;

    /**
     * Empty Constructor
     */
    private UIManager(){
         tableUI = new TableUI();
         snifferUI = new SnifferUI();
         messenger = new Messenger();
    }

    /**
     * Gives a reference to the Singleton object
     * @return ourInstance Singleton object
     */
    public static UIManager getInstance(){
        return ourInstance;
    }

    /**
     * Display a message via the Toast Android class of a length
     * @param message The message that will be displayed
     * @param displayTime The length of the message
     */
    public void displayMessage(String message, int displayTime){
        Toast.makeText(context, message, displayTime).show();

    }

    /**
     * A simplified override of the other displayMessage method
     * Gives a default length for the message
     * @param message the message that is to be displayed
     */
    public void displayMessage(String message){
        displayMessage(message, Toast.LENGTH_LONG);
    }

    /**
     * A simplified override of the other displayMessage method
     * Gives a default length for the message
     * @param message the message that is to be displayed
     */
    public void displayMessage( Integer ll3p, String message){
        messenger.receiveMessage(ll3p, message);
    }

    /**
     * Givess access to on screen widgets
     */
    public void setUpWidgets(){ }

    /**
     * Updates the context variable when the Bootloader objects sends out a notify command
     * to its observers.
     * @Override update
     * @param observable The object that is being observed
     * @param object A blank object that will be cast to the observed type
     */
    public void update(Observable observable, Object object){
        if(observable.getClass() == Bootloader.class){
            Bootloader bootLoader = (Bootloader)object;
            context = parentActivity.getBaseContext();
            messenger.finishCreatingMessenger();
        }
        setUpWidgets();
    }

    /**
     * open the messaging thing
     */
    public void openMessenger(){
        messenger.openMessengerWindow();
    }



    public TableUI getTableUI() {
        return tableUI;
    }

    private void setTableUI(TableUI tableUI) {
        this.tableUI = tableUI;
    }

    public SnifferUI getSnifferUI(){
        return snifferUI;
    }

    public Messenger getMessenger(){
        return messenger;
    }
}
