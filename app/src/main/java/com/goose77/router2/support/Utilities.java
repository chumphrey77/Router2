package com.goose77.router2.support;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * Class for holding useful methods that will be used across the application
 */
public class Utilities {
    /**
     * Takes in a string and the number of bytes that string needs to be and
     * pads 0's onto the front of the string to make the original string the
     * correct length
     * @param inputString The string that needs to be padded
     * @param byteCount The number of bytes that inputString needs to be
     * @return outputString The padded string
     */
    static public String padHexString(String inputString, int byteCount){
        int inputStringLength = inputString.length();
        int numberOfCharacters = byteCount*2;
        int differenceInNumOfCharacters = numberOfCharacters - inputStringLength;
        String paddingCharacters = "";
        String outputString ="";
        if(differenceInNumOfCharacters > 0){
            for(int i =0; i < differenceInNumOfCharacters; i++){
                paddingCharacters = paddingCharacters + "0";
            }
            outputString = paddingCharacters+inputString;
        }
        return outputString;
    }
}
