/**
 * HashBuilder.java
 * Copyright (C)2009 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENCE file at the toplevel.
 */
package net.exclaimindustries.geohashdroid;

import java.util.Calendar;

import android.os.Handler;

/**
 * <p>
 * The <code>HashBuilder</code> class encompasses a whole bunch of static
 * methods to grab and store the day's DJIA and calculate the hash, given a
 * <code>Graticule</code> object.
 * </p>
 * 
 * <p>
 * This implementation uses the peeron.com site to get the DJIA
 * (http://irc.peeron.com/xkcd/map/data/2008/12/03).
 * </p>
 * 
 * @author Nicholas Killewald
 */
public class HashBuilder {
    // You don't construct a HashBuilder!  You gotta EARN it!
    private HashBuilder() { }
   
    /**
     * Initializes HashBuilder.  This should be called only once.  Well, it can
     * be called more often, but it won't do anything past the first time.
     * 
     * TODO: And come to think of it, it doesn't do anything now, either.
     */
    public static void initialize() {
        // TODO: PUT INIT STUFF HERE ONCE NEEDED
    }
    
    /**
     * Starts a request to get an Info object based on the date and graticule.
     * The response will come to the Handler specified.  This will return right
     * away and kick off a new thread to do the job.
     * 
     * TODO: Needs some way to abort the connection, as well as some way to tell
     * if the process is busy right now.
     * 
     * @param c Calendar object with the adventure date requested (this will
     *          account for the 30W Rule, so don't put it in) 
     * @param g Graticule to use
     * @param h Handler to handle the response once it comes in
     */
    public static void requestInfo(Calendar c, Graticule g, Handler h) {
        
    }
    
    /**
     * Checks if the stock price for the given date and graticule (accounting
     * for the 30W rule) is stored and can be retrieved without going to the
     * internet.  This is not intended to be threaded.
     * 
     * @param c Calendar object with the adventure date requested (this will
     *          account for the 30W Rule, so don't put it in) 
     * @param g Graticule to use
     * @return true if the stock value is stored, false if we need to go to the
     *         internet for it
     */
    public static boolean hasStockStored(Calendar c, Graticule g) {
        // This is always false until stock caching is working.
        return false;
    }
}