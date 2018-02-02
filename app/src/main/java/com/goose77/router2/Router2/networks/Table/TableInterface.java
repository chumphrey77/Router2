package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.LabException;

import java.util.List;

/**
 * Created by goose on 2/1/2018.
 */

public interface TableInterface {
    /**
     * Returns the complete list of records in this table
     * @return List<TableRecord> list
     */
    List<TableRecord> getTableAsList();

    /**
     * Add a record to the list of records
     * @param TableRecord tableRecord
     * @return TableRecord record
     */
    TableRecord addItem(TableRecord tableRecord);

    /**
     * Get a single record out of the table using a TableRecord key
     * @param TableRecord tableRecord
     * @return TableRecord record
     * @throws LabException
     */
    TableRecord getItem(TableRecord tableRecord) throws LabException;

    /**
     * Remove an item from the table
     * @param Integer key
     * @return TableRecord record
     */
    void removeItem(Integer key);

    /**
     * Retrieve an item from the table using an Integer key
     * @param Integer key
     * @return TableRecord record
     * @throws LabException
     */
    TableRecord getItem(Integer key) throws LabException;

    /**
     * Remove all records from the table
     */
    void clear();

}
