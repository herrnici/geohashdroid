/**
 * GHDConstants.java
 * Copyright (C)2009 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENSE file at the toplevel.
 */
package net.exclaimindustries.geohashdroid;

import java.text.DecimalFormat;

/**
 * The <code>GHDConstants</code> class doesn't do anything directly.  All it
 * does is serve as a place to store project-wide statics.
 * 
 * @author Nicholas Killewald
 */
public final class GHDConstants {
    /** Preferences base. */
    public static final String PREFS_BASE = "GeohashDroid";
    
    /**
     * Prefs key where the last latitude is stored. 
     * @see PREF_REMEMBER_GRATICULE
     * */
    public static final String PREF_DEFAULT_LAT = "DefaultLatitude";
    /** Prefs key where the last longitude is stored. */
    public static final String PREF_DEFAULT_LON = "DefaultLongitude";
    /** Prefs key where we keep track of whether we were in globalhash mode. */
    public static final String PREF_GLOBALHASH_MODE = "GlobalhashMode";
    /** Prefs key specifying coordinate units. */
    public static final String PREF_COORD_UNITS = "CoordUnits";
    /** Prefs key specifying distance units. */
    public static final String PREF_DIST_UNITS = "Units";
    /** Prefs key specifying whether or not to remember the last graticule. */
    public static final String PREF_REMEMBER_GRATICULE = "RememberGraticule";
    /** Prefs key specifying whether or not to auto-zoom. */
    public static final String PREF_AUTOZOOM = "AutoZoom";
    /** Prefs key specifying info box size. */
    public static final String PREF_INFOBOX_SIZE = "InfoBoxSize";
    /** Prefs key specifying stock cache size. */
    public static final String PREF_STOCK_CACHE_SIZE = "StockCacheSize";
    /** Prefs key specifying to show nearby meetup points. */
    public static final String PREF_NEARBY_POINTS = "NearbyPoints";
    /** Prefs key specifying whether the closest checkbox is ticked. */
    public static final String PREF_CLOSEST = "ClosestOn";
    /** Prefs key specifying wiki user name. */
    public static final String PREF_WIKI_USER = "WikiUserName";
    /** Prefs key specifying wiki user pass. */
    public static final String PREF_WIKI_PASS = "WikiPassword";
    /** Prefs key specifying to use the phone's time, not the wiki's */
    public static final String PREF_WIKI_PHONE_TIME = "WikiUsePhoneTime";
    /** Prefs key specifying if the Today checkbox is ticked. */
    public static final String PREF_TODAY = "AlwaysToday";
    /**
     * Prefs key tracking if we've reported on the closeness of the user to the
     * final destination.
     */
    public static final String PREF_CLOSENESS_REPORTED = "ClosenessReported";
    
    /** Threshold for the "Accuracy Low" warning (currently 64m). **/
    public static final int LOW_ACCURACY_THRESHOLD = 64;
    /** Threshold for the "Accuracy Really Low" warning (currently 200m). **/
    public static final int REALLY_LOW_ACCURACY_THRESHOLD = 200;
    
    /**
     * Action for picking a graticule. In Geohash Droid, this means to go to
     * GraticuleMap. Though, so long as it returns a
     * net.exclaimindustries.geohashdroid.Graticule object, I'd assume anything
     * could take its place if someone else writes a better graticule picker.
     */
    public static final String PICK_GRATICULE = "net.exclaimindustries.geohashdroid.PICK_GRATICULE";
    
    /**
     * Broadcast intent for the alarm that tells StockService that it's time to
     * go fetch a stock.  At that time, it'll retrieve stock data for "today"
     * and "yesterday".  In this case, "today" and "yesterday" are both relative
     * to when stock data is expected to exist for the actual "today"; for
     * instance, if this is called on a Saturday, "today" will be Friday (the
     * NYSE isn't open on Saturday, so Friday's open value is used) and
     * "yesterday" will also be Friday (both 30W and non-30W users get the same
     * hash data on Saturdays and Sundays).
     * 
     * After this data is retrieved, a STOCK_RESULT broadcast will occur.
     */
    public static final String STOCK_ALARM = "net.exclaimindustries.geohashdroid.STOCK_ALARM";

    /**
     * Broadcast intent to tell StockService to retrieve a specific day's stock
     * value.  This will require some extra Intent data to tell it what date it
     * should be retrieving.  Also, assuming this isn't what StockService is
     * already fetching, this will abort anything currently in progress.
     * 
     * TODO: Determine what that extra data should be; a manual fetch request
     * should include the graticule and perform 30W adjustments.
     */
    public static final String STOCK_FETCH = "net.exclaimindustries.geohashdroid.STOCK_FETCH";
    
    /**
     * Broadcast intent to tell StockService to abort whatever it was doing.
     */
    public static final String STOCK_ABORT = "net.exclaimindustries.geohashdroid.STOCK_ABORT";
    
    /**
     * Broadcast intent sent back by StockService when a result has been
     * retrieved.  In general, you'd register something to pick this up on an
     * as-you-need-it basis, not register it with the manifest.
     * 
     * TODO: Determine the data that'll come back with this; it'll either just
     * be the stock value or a parcelized Info object.
     */
    public static final String STOCK_RESULT = "net.exclaimindustries.geohashdroid.STOCK_RESULT";
    
    /** The decimal format for most distances. */
    public static final DecimalFormat DIST_FORMAT = new DecimalFormat("###.######");
    /** The decimal format for most accuracy readouts. */
    public static final DecimalFormat ACCURACY_FORMAT = new DecimalFormat("###.##");
}
