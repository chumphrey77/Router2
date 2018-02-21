package com.goose77.router2.Router2.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goose77.router2.R;
import com.goose77.router2.Router2.MainActivity;
import com.goose77.router2.Router2.networks.datagram.LL2PFrame;
import com.goose77.router2.Router2.support.Bootloader;
import com.goose77.router2.Router2.support.FrameLogger;
import com.goose77.router2.Router2.support.ParentActivity;
import com.goose77.router2.Router2.support.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Colten on 2/15/2018.
 */

public class SnifferUI implements Observer {
    private Activity parentActivity;
    private Context context;
    private FrameLogger frameLogger;
    private SnifferFrameListAdapter frameListAdapter;
    private ListView frameListView;
    private TextView protocolBreakoutTextView;
    private TextView frameBytesTestView;

    /**
     * Class that handles the UI of router
     */
    public SnifferUI(){}

    /**
     * Connect all the UI assets to the java code
     */
    public void connectWidgets(){
        frameListView = (ListView) parentActivity.findViewById(R.id.packetDisplay);
        frameListAdapter = new SnifferFrameListAdapter(context, frameLogger.getFrameList());
        frameListView.setAdapter(frameListAdapter);
        protocolBreakoutTextView = (TextView) parentActivity.findViewById(R.id.ProtocolExp);
        frameBytesTestView = (TextView) parentActivity.findViewById(R.id.packetContents);
        frameListView.setOnItemClickListener(showUpdateFrameList);
    }

    /**
     * When a frame is selected from the Sniffer UI, display the frame information
     */
    private AdapterView.OnItemClickListener showUpdateFrameList = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            LL2PFrame frameSelected = frameLogger.getFrameList().get(i);
            protocolBreakoutTextView.setText(frameSelected.toProtocolExplanationString());
            frameBytesTestView.setText(formatBytes(frameSelected));
        }
    };

    /**
     * Formats the text for the frame information
     * Each row starts with an byte offset
     * Following the offset will be 8 hex representations of the ASCII characters in the frame
     * Following the hex characters are the actual characters in the frame
     *
     * @param frame
     * @return
     */
    private String formatBytes(LL2PFrame frame){
        String hexString = frame.toHexString();
        String formatted ="";
        String offset ="";
        Integer length = hexString.length();
        Integer count =0;
        Integer counter =0;
        byte[] byteArray = new byte[1];
        for(Integer i =0; i < length; i++){
            if(i % 8 == 0 ){
                if( i > 0) {
                    formatted += "  " + frame.toTransmissionString().substring(i - 8, i) + "\n";
                }
                offset = Utilities.padHexString(Integer.toHexString(i), 2);
                formatted += offset + " ";
                count+=8;
            }
                formatted += Utilities.toHexString(hexString.substring(i, i + 1)) + " ";
        }
        if(count % 8 == 0){
            formatted += "  "+frame.toTransmissionString().substring(count-8) + "\n";
        }
        else {
            formatted += "  " + frame.toTransmissionString().substring(count) + "\n";
        }
        return formatted;
    }

    /**
     * When the bootloader finishes booting the router
     * This will save references for required references
     * Add the FrameLogger as an observer
     * Connect UI elements to this class
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        if(observable instanceof Bootloader) {
            this.parentActivity = ParentActivity.getParentActivity();
            context = parentActivity.getBaseContext();
            frameLogger = FrameLogger.getInstance();
            frameLogger.addObserver(this);
            connectWidgets();
        }
        else if(observable instanceof  FrameLogger){
            frameListAdapter.notifyDataSetChanged();
        }
    }












    //*******************************************************************************************
    //                          Given Classes
    //*******************************************************************************************

    /*
     *     This class is a holder. It holds widgets (views) that make
     *     up a single row in the sniffer top window.
     */
    private static class ViewHolder {
        TextView packetNumber;
        TextView packetSummaryString;
    }

    /**
     * SnifferFrameListAdapter is a private adapter to display numbered rows from a ListView
     * object which contains all frames transmitted or received.
     *
     * It is instantiated above and note that the constructor passes the context as well as
     * the frameList.
     */
    private class SnifferFrameListAdapter extends ArrayAdapter<LL2PFrame> {
        // this is the ArrayList that will be displayed in the rows on the ListView.
        private ArrayList<LL2PFrame> frameList;

        /*
        *  The constructor is passed the context and the arrayList.
        *  the arrayList is assigned to the local variable so its contents can be
        *  adapted to the listView.
        */
        public SnifferFrameListAdapter(Context context, ArrayList<LL2PFrame> frames) {
            super(context, 0, frames);
            frameList = frames;
        }

        /**
         * Here is where the work is performed to adapt a specific row in the arrayList to
         * a row on the screen.
         *
         * @param position    - position in the array we're working with
         * @param convertView - a row View that passed in – has a view to use or a null object
         * @param parent      - the main view that contains the rows.  Note that is is the ListView object.
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // First retrieve a frame object from the arrayList at the position we're working on
            LL2PFrame ll2PFrame = getItem(position);
            // declare a viewHolder - this simply is a single object to hold a two widgets
            ViewHolder viewHolder;

            /**
             * If convertView is null then we didn't get a recycled View, we have to create from scratch.
             * We do that here.
             */
            if (convertView == null) {
                // inflate the view defined in the layout xml file using an inflater we create here.
                LayoutInflater inflator = LayoutInflater.from(context);
                convertView = inflator.inflate(R.layout.sniffer_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.packetNumber = (TextView) convertView.findViewById(R.id.snifferFrameNumberTextView);
                viewHolder.packetSummaryString = (TextView) convertView.findViewById(R.id.snifferItemTextView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.packetNumber.setText(Integer.toString(position));
            viewHolder.packetSummaryString.setText(formatSummary(frameList.get(position)));
            return convertView;
        }

        public String formatSummary(LL2PFrame frame){
            String summary ="";
            summary = frame.getDestinationAddress().toTransmissionString() +" | " +
                    frame.getSourceAddress().toTransmissionString() + " | "
                    + frame.getType().toTransmissionString() + " | " +
                    frame.getPayload().toTransmissionString().substring(0, 15) +"...";
            return summary;
        }
    }
}
