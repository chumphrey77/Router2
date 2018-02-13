package com.goose77.router2.Router2.networks.tableRecord;

import java.util.Calendar;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Super class for Table records
 */
public class TableRecordClass implements TableRecord {
   private int lastTimeTouched;

    /**
     * Constructs object and updates the last time the record was touched to now
     */
   public TableRecordClass(){
       updateTime();
   }


    /**
     * Returns the key for this record
     *
     * @return Integer key
     */
    @Override
    public Integer getKey() {
        return 0;
    }

    /**
     * Returns how many seconds have passed since the record was referenced
     *
     * @return Integer age
     */
    @Override
    public int getAgeInSeconds() {
        return getTimeInSeconds() - lastTimeTouched;
    }

    /**
     * Update the last time touched to now
     */
    public void updateTime(){
        this.lastTimeTouched = getTimeInSeconds();
    }

    /**
     * Get the current time in seconds
     * @return int
     */
    private int getTimeInSeconds(){
       return  this.lastTimeTouched =Integer.parseInt(Long.toString(Calendar.getInstance().getTimeInMillis()/1000));
    }
}
