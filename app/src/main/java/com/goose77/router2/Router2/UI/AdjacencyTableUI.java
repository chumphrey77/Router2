package com.goose77.router2.Router2.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.goose77.router2.R;
import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.Table.TableInterface;
import com.goose77.router2.Router2.networks.daemon.LL1Daemon;
import com.goose77.router2.Router2.networks.datagram.Datagram;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.networks.datagram_fields.CRC;
import com.goose77.router2.Router2.networks.datagram_fields.DatagramPayloadField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PTypeField;
import com.goose77.router2.Router2.networks.tableRecord.AdjacencyRecord;
import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.HeaderFieldFactory;
import com.goose77.router2.Router2.support.LabException;

import java.util.ArrayList;

/**
 * Created by goose on 2/8/2018.
 */

/**
 * SingleTableUI class that is specifically for the adjacency table
 */
public class AdjacencyTableUI extends SingleTableUI{
    private LL1Daemon ll1Daemon;

    /**
     * Set the class field from passed in information
     * Set the onClickListeners to wait on UI clicks
     * @param parentActivity
     * @param i
     * @param tableInterface
     * @param manager
     */
    public AdjacencyTableUI(Activity parentActivity, int i, TableInterface tableInterface, LL1Daemon manager){
        super(parentActivity, i, tableInterface);
        ll1Daemon = manager;
        tableListViewWidget.setOnItemClickListener(sendEchoRequest);
        tableListViewWidget.setOnItemLongClickListener(removeAdjacency);
    }

    /**
     * When the adjacency record is clicked on the screen,
     * Create an echo request and send it to the adjacent device
     */
    private AdapterView.OnItemClickListener sendEchoRequest = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            HeaderFieldFactory factory = HeaderFieldFactory.getInstance();
            LL2PAddressField destAddr =  factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, ((AdjacencyRecord)tableList.get(i)).getLl2pAddress());
            LL2PAddressField srcAddr = factory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, Constants.SRC_ADDR);
            LL2PTypeField type = factory.getItem(Constants.LL2P_TYPE_FIELD_ID, Integer.toString(Constants.LL2P_TYPE_IS_ECHO_REQUEST));
            DatagramPayloadField payload = factory.getItem(Constants.LL2P_TYPE_IS_ECHO_REQUEST, "Echo Request");
            CRC crc = factory.getItem(Constants.CRC_ID, "0000");  //todo generate crc
            LL2PFrame frame = new LL2PFrame(destAddr, srcAddr, type, payload, crc);
            LL1Daemon.getInstance().sendFrame(frame);
            UIManager.getInstance().displayMessage("Echo Request sent");
        }
    };

    /**
     * When an adjacency record is long clicked on the UI,
     * delete the record from the table and thus from the UI
     */
    private AdapterView.OnItemLongClickListener removeAdjacency= new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ll1Daemon.removeAdjacency((AdjacencyRecord) tableList.get(i));
                updateView();
            return false;
        }
    };


}



