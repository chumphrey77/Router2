package com.goose77.router2.Router2.support;

/**
 * Created by goose on 1/11/2018.
 */

import java.util.Calendar;

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
    public static long baseDateInSeconds = Calendar.getInstance().getTimeInMillis()/1000;

    static public String padHexString(String inputString, int byteCount){
        int inputStringLength = inputString.length();
        int numberOfCharacters = byteCount*2;
        int differenceInNumOfCharacters = numberOfCharacters - inputStringLength;
        String paddingCharacters = "";
        String outputString =inputString;
        if(differenceInNumOfCharacters > 0){
            for(int i =0; i < differenceInNumOfCharacters; i++){
                paddingCharacters = paddingCharacters + "0";
            }
            outputString = paddingCharacters+inputString;
        }
        return outputString;
    }

    public String toAsciiString(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String toHexString(String s){
        String hexString ="";
        char[]  ch = s.toCharArray();

        StringBuilder builder = new StringBuilder();

        for(char c : ch){
            Integer i = (int)c;
            builder.append(Integer.toHexString(i));
        }
        return builder.toString();
    }

    public int  getTimeInSeconds(){
        return (int) (Calendar.getInstance().getTimeInMillis()/1000 - baseDateInSeconds);
    }
}
