package com.goose77.router2.Router2.networks.tableRecord;

import com.goose77.router2.Router2.networks.Constants;
import com.goose77.router2.Router2.networks.datagram_fields.LL2PAddressField;
import com.goose77.router2.Router2.networks.datagram_fields.LL3PAddressField;
import com.goose77.router2.Router2.support.HeaderFieldFactory;

/**
 * Created by Colten on 2/22/2018.
 */

public class ARPRecord extends TableRecordClass {
    LL2PAddressField ll2pAddress;
    LL3PAddressField ll3pAddress;

    public ARPRecord(String ll2pAndll3pAddress){
        super();
        String ll2p;
        String ll3p;
        HeaderFieldFactory fieldFactory = HeaderFieldFactory.getInstance();
        ll2p = ll2pAndll3pAddress.substring(0, Constants.SRC_ADDR_OFFSET_BYTES);
        ll3p = ll2pAndll3pAddress.substring(Constants.SRC_ADDR_OFFSET_BYTES+1);

        this.ll2pAddress = fieldFactory.getItem(Constants.LL2P_DEST_ADDRESS_FIELD_ID, ll2p);
        this.ll3pAddress = fieldFactory.getItem(Constants.LL3P_DESTINATION_ADDRESS_FIELD_ID, ll3p);
    }

    public ARPRecord(Integer ll2p, Integer ll3p){
        super();
        ll2pAddress = new LL2PAddressField(ll2p, false);
        ll3pAddress = new LL3PAddressField(ll3p.toString(), false);
    }

    public ARPRecord(){
        super();
        ll2pAddress = null;
        ll3pAddress = null;
    }

    public Integer getKey(){
        return ll2pAddress.getAddress();
    }

    public String toString(){
        String toString;
        toString = "LL2P: " + ll2pAddress.toHexString();
        toString += " LL3P: " + ll3pAddress.toHexString();
        return toString;
    }
    public LL2PAddressField getLl2pAddress() {
        return ll2pAddress;
    }

    public void setLl2pAddress(LL2PAddressField ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }

    public LL3PAddressField getLl3pAddress() {
        return ll3pAddress;
    }

    public void setLl3pAddress(LL3PAddressField ll3pAddress) {
        this.ll3pAddress = ll3pAddress;
    }
}
