package com.goose77.router2.Router2.networks.tableRecord;

/**
 * Created by goose on 1/25/2018.
 */

/**
 * Interface for all Table records
 */
public interface TableRecord {
    /**
     * Returns the key for this record
     * @return Integer key
     */
    Integer getKey();

    /**
     * Returns how many seconds have passed since the record was referenced
     * @return Integer age
     */
    int getAgeInSeconds();
}
