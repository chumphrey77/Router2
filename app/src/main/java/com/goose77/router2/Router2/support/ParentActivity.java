package com.goose77.router2.Router2.support;

import android.app.Activity;

/**
 * Created by goose on 1/11/2018.
 */

/**
 * A buffer class that holds a reference to the MainActivity
 */
public class ParentActivity {
    private static Activity localParentActivity;

    /**
     * Returns a reference to the MainActivity to any other object
     * @return
     */
    public static Activity getParentActivity(){
        return localParentActivity;
    }

    /**
     * Sets the local copy of the Activity to the reference to the Main Activity
     * @param parentActivity
     */
    public static void setParentActivity(Activity parentActivity){
        localParentActivity = parentActivity;
    }
}
