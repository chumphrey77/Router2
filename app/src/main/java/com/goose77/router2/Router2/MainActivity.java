package com.goose77.router2.Router2;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.goose77.router2.R;
import com.goose77.router2.Router2.UI.AddAdjacencyDialog;
import com.goose77.router2.Router2.UI.UIManager;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.support.Bootloader;

/**
 * Creates the bootloader objects and begins the bootup process of the router
 */
public class MainActivity extends AppCompatActivity implements AddAdjacencyDialog.AdjacencyPairListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Bootloader(this);
    }

    //Add the Show IP Address button to the menu
    //When the button is clicked show the message with the devices IP Address
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UIManager uiManager = UIManager.getInstance();
        if (item.getItemId() == R.id.showIPAddress){
            uiManager.displayMessage("Your IP address is "+ Constants.IP_ADDRESS);
        }
        if(item.getItemId() == R.id.AddAdjacency){
            AddAdjacencyDialog dialog = new AddAdjacencyDialog();
            dialog.show(getFragmentManager(), "add_adjacency_dialog");
        }
        if(item.getItemId() == R.id.Messenger){
            uiManager.getMessenger().openMessengerWindow();
        }
        return super.onOptionsItemSelected(item);
    }


    //Create the Menu when the app is loaded
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFinishedEditDialog(String ipAddress, String ll2pAddress) {
        LL1Daemon.getInstance().addAdjacencyTable(ipAddress, ll2pAddress);
    }
}
