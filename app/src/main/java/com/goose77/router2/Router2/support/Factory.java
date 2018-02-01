package com.goose77.router2.Router2.support;

/**
 * Created by goose on 1/26/2018.
 */

/**
 * Interface for Factories
 * @param <T>
 * @param <S>
 */
public interface Factory <T, S> {
    /**
     * Public method to get the desired item of the specified type
     *
     * @param type - The type of object to be created determined at run time
     * @param data - The data to be used to construct the desired object
     * @return object - This object will be of the specified U data type
     */
    <U extends T> U getItem(int type, S data);
}
