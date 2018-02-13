package com.goose77.router2.Router2.UI;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.goose77.router2.R;
import com.goose77.router2.Router2.networks.Table.Table;
import com.goose77.router2.Router2.networks.Table.TableInterface;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by goose on 2/8/2018.
 */

/**
 * Class for holding the tables to be displayed on the app UI
 * Observes an underlying table so that it can update when the table updates
 */
public class SingleTableUI implements Observer {
    protected Activity parentActivity;
    protected Table tableToDisplay;
    protected List<TableRecord> tableList;
    protected ListView tableListViewWidget;
    private ArrayAdapter arrayAdapter;

    /**
     * Constructor that sets all of the fields based on passed in parameters
     * Also creates an array adapter for the table
     * Sets this object as an observer of the passed in table
     * @param activity
     * @param tableID
     * @param table
     */
    public SingleTableUI(Activity activity, int tableID, TableInterface table){
        this.parentActivity = activity;
        this.tableToDisplay = (Table)table;
        tableList=tableToDisplay.getTableAsList();
        arrayAdapter = new ArrayAdapter(parentActivity.getBaseContext(), android.R.layout.simple_list_item_1, tableToDisplay.getTableAsList());
        tableListViewWidget = parentActivity.findViewById(tableID);
        tableListViewWidget.setAdapter(arrayAdapter);
        tableToDisplay.addObserver(this);
    }

    /**
     * Implemented as required by observer interface
     * Calls updateView method
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        updateView();
    }


    /**
     * Updates the the table UI elements that corresponds to this object
     */
    public void updateView(){
        // Force all our work here to be on the UI thread!
        parentActivity.runOnUiThread(new Runnable() {
            @Override // this is a mini-Runnable class’s run method!
            public void run() {
                // notify the OS that the dataset has changed. It will update screen!
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

}
