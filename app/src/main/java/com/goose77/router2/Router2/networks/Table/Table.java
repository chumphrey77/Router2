package com.goose77.router2.Router2.networks.Table;

import com.goose77.router2.Router2.networks.tableRecord.TableRecord;
import com.goose77.router2.Router2.support.LabException;
import com.goose77.router2.Router2.support.TableRecordFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Created by goose on 2/1/2018.
 */

public class Table extends Observable implements TableInterface {
    protected List<TableRecord> table;

    public Table(){
        this.table = Collections.synchronizedList(new ArrayList<TableRecord>());
    }
    /**
     * Returns the complete list of records in this table
     *
     * @return List<TableRecord> list
     */
    @Override
    public List<TableRecord> getTableAsList() {
        return table;
    }

    /**
     * Add a record to the list of records
     *
     * @param tableRecord@return TableRecord record
     */
    @Override
    public TableRecord addItem(TableRecord tableRecord) {
        table.add(tableRecord);
        return tableRecord;
    }

    /**
     * Get a single record out of the table using a TableRecord key
     *
     * @param tableRecord@return TableRecord record
     * @throws LabException
     */
    @Override
    public TableRecord getItem(TableRecord tableRecord) throws LabException {
        try {
            if(table.contains(tableRecord)) {
                int index = table.indexOf(tableRecord);
                return table.get(index);
            }
            else{
                throw new LabException("Could not get item from table");
            }
        }
        catch(LabException e){
             throw e;
        }
    }

    /**
     * Remove an item from the table
     *
     * @param key@return TableRecord record
     */
    @Override
    public void removeItem(Integer key) {
        for(TableRecord record: table){
            if(record.getKey() == key){
                table.remove(record);
            }
        }
    }

    /**
     * Retrieve an item from the table using an Integer key
     *
     * @param key@return TableRecord record
     * @throws LabException
     */
    @Override
    public TableRecord getItem(Integer key) throws LabException {
        for(TableRecord record : table){
            if(record.getKey().equals(key)){
                return record;
            }
        }
        return null;
    }

    /**
     * Remove all records from the table
     */
    @Override
    public void clear() {
        table.clear();
    }

    public void updateDispaly(){
        setChanged();
        notifyObservers();
    }

    public ArrayList<TableRecord> getTableAsArrayList() {
        return (ArrayList<TableRecord>)table;
    }
}
