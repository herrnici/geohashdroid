/*
 * DetailedInfoFragment.java
 * Copyright (C) 2015 Nicholas Killewald
 *
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENSE file at the toplevel.
 */

package net.exclaimindustries.geohashdroid.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.exclaimindustries.geohashdroid.R;
import net.exclaimindustries.geohashdroid.util.GHDConstants;
import net.exclaimindustries.geohashdroid.util.Info;
import net.exclaimindustries.geohashdroid.util.UnitConverter;

import java.text.DateFormat;

/**
 * The DetailedInfoFragment shows us some detailed info.  It's Javadocs like
 * this that really sell the whole concept, I know.
 */
public class DetailedInfoFragment extends CentralMapExtraFragment {
    private TextView mDate;
    private TextView mYouLat;
    private TextView mYouLon;
    private TextView mDestLat;
    private TextView mDestLon;
    private TextView mDistance;
    private TextView mAccuracy;
    private View mYouBlock;
    private View mDistanceBlock;

    private Location mLastLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.detail, container, false);

        // TextViews!
        mDate = (TextView)layout.findViewById(R.id.detail_date);
        mYouLat = (TextView)layout.findViewById(R.id.you_lat);
        mYouLon = (TextView)layout.findViewById(R.id.you_lon);
        mDestLat = (TextView)layout.findViewById(R.id.dest_lat);
        mDestLon = (TextView)layout.findViewById(R.id.dest_lon);
        mDistance = (TextView)layout.findViewById(R.id.distance);
        mAccuracy = (TextView)layout.findViewById(R.id.accuracy);
        mYouBlock = layout.findViewById(R.id.you_block);
        mDistanceBlock = layout.findViewById(R.id.distance_block);

        // Button!
        Button closeButton = (Button) layout.findViewById(R.id.close);

        // Button does a thing!
        if(closeButton != null) registerCloseButton(closeButton);

        updateDisplay();

        return layout;
    }

    /**
     * Sets the Info.  If null, this will make it go to standby.  Whatever gets
     * set here will override any arguments originally passed in if and when
     * onSaveInstanceState is needed.
     *
     * @param info the new Info
     */
    public void setInfo(@Nullable final Info info) {
        super.setInfo(info);

        updateDisplay();
    }

    private void updateDisplay() {
        // Good!  This is almost the same as the InfoBox.  It just has more
        // detail and such.
        Activity activity = getActivity();

        if(activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    float accuracy = 0.0f;
                    if(mLastLocation != null) accuracy = mLastLocation.getAccuracy();

                    // If we can't get to the user's current location due to
                    // pesky permissions perils, just hide the relevant blocks.
                    // I mean, it'll be a somewhat sparse fragment, but it'll at
                    // least not have ugly Stand By lines all over.
                    if(mPermissionsDenied) {
                        mYouBlock.setVisibility(View.GONE);
                        mDistanceBlock.setVisibility(View.GONE);
                    } else {
                        mYouBlock.setVisibility(View.VISIBLE);
                        mDistanceBlock.setVisibility(View.VISIBLE);
                    }

                    // One by one, just like InfoBox!  I mean, not JUST like it.
                    // We split the coordinate parts into different TextViews
                    // here, and we have the date to display, but other than
                    // THAT...
                    if(mInfo == null) {
                        mDestLat.setText(R.string.standby_title);
                        mDestLon.setText("");
                        mDate.setText("");
                    } else {
                        mDestLat.setText(UnitConverter.makeLatitudeCoordinateString(getActivity(), mInfo.getFinalLocation().getLatitude(), false, UnitConverter.OUTPUT_DETAILED));
                        mDestLon.setText(UnitConverter.makeLongitudeCoordinateString(getActivity(), mInfo.getFinalLocation().getLongitude(), false, UnitConverter.OUTPUT_DETAILED));
                        mDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(
                                mInfo.getCalendar().getTime()));
                    }

                    // Location and accuracy!
                    if(mLastLocation == null) {
                        mYouLat.setText(R.string.standby_title);
                        mYouLon.setText("");
                        mAccuracy.setText("");
                    } else {
                        mYouLat.setText(UnitConverter.makeLatitudeCoordinateString(getActivity(), mLastLocation.getLatitude(), false, UnitConverter.OUTPUT_DETAILED));
                        mYouLon.setText(UnitConverter.makeLongitudeCoordinateString(getActivity(), mLastLocation.getLongitude(), false, UnitConverter.OUTPUT_DETAILED));

                        mAccuracy.setText(getString(R.string.details_accuracy,
                                UnitConverter.makeDistanceString(getActivity(),
                                        GHDConstants.ACCURACY_FORMAT, mLastLocation.getAccuracy())));
                    }

                    // Distance!
                    if(mLastLocation == null || mInfo == null) {
                        mDistance.setText(R.string.standby_title);
                        mDistance.setTextColor(getResources().getColor(R.color.details_text));
                    } else {
                        float distance = mLastLocation.distanceTo(mInfo.getFinalLocation());
                        mDistance.setText(UnitConverter.makeDistanceString(getActivity(), GHDConstants.DIST_FORMAT, distance));

                        // Plus, if we're close enough AND accurate enough, make the
                        // text be green.  We COULD do this with geofencing
                        // callbacks and all, but, I mean, we're already HERE,
                        // aren't we?
                        if(accuracy < GHDConstants.LOW_ACCURACY_THRESHOLD && distance <= accuracy)
                            mDistance.setTextColor(getResources().getColor(R.color.details_in_range));
                        else
                            mDistance.setTextColor(getResources().getColor(R.color.details_text));

                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FragmentType getType() {
        return FragmentType.DETAILS;
    }

    @Override
    public void onLocationChanged(Location location) {
        // Ding!
        mLastLocation = location;
        updateDisplay();
    }

    @Override
    public void permissionsDenied(boolean denied) {
        // Dong!
        mPermissionsDenied = denied;
        updateDisplay();
    }
}
