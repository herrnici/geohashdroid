/*
 * VersionHistoryDialog.java
 * Copyright (C) 2015 Nicholas Killewald
 *
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENSE file at the toplevel.
 */

package net.exclaimindustries.geohashdroid.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.exclaimindustries.geohashdroid.R;
import net.exclaimindustries.geohashdroid.util.VersionHistoryParser;
import net.exclaimindustries.geohashdroid.util.VersionHistoryParser.VersionEntry;

import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * This pops up the version history on demand.  Said demand also includes the
 * first time a new version has been launched.
 *
 * @author Nicholas Killewald
 */
public class VersionHistoryDialogFragment extends DialogFragment {
    // This is just a simple dialog with a simple list.  But, said list needs a
    // less-simple adapter, which we bring up here.
    private class EntryAdapter extends ArrayAdapter<VersionEntry> {
        public EntryAdapter(Context c, List<VersionEntry> entries) {
            super(c, 0, entries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final VersionEntry entry = getItem(position);

            if(convertView == null) {
                // Let's inflate us a view.  Yeah.  Let's do just that.
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.version_history_block, parent, false);
            }

            // Since we know what sort of view this is, we know we should have
            // all the child views handy.  First, all the TextViews.
            ((TextView)convertView.findViewById(R.id.version)).setText(entry.versionName);
            ((TextView)convertView.findViewById(R.id.release_date)).setText(entry.date);
            ((TextView)convertView.findViewById(R.id.title)).setText("\"" + entry.title + "\"");
            ((TextView)convertView.findViewById(R.id.header)).setText(entry.header);
            ((TextView)convertView.findViewById(R.id.footer)).setText(entry.footer);

            // Meanwhile, each bullet has to be added in a for loop.
            LinearLayout bullets = (LinearLayout)convertView.findViewById(R.id.bullets);

            // Clear out anything that was there before.
            bullets.removeAllViews();

            for(String s : entry.bullets) {
                View bullet = LayoutInflater.from(getActivity()).inflate(R.layout.version_history_bullet, bullets, false);

                TextView bulletText = (TextView)bullet.findViewById(R.id.bulletText);
                bulletText.setText(s);
                bullets.addView(bullet);
            }

            return convertView;
        }
    }

    /**
     * Generates a new VersionHistoryDialogFragment, parsing out the XML as it
     * does so.
     *
     * @param c a Context to get the XML data
     * @return a new Fragment
     */
    public static VersionHistoryDialogFragment newInstance(Context c) {
        // GENERATE!
        ArrayList<VersionHistoryParser.VersionEntry> entries = new ArrayList<>();

        try {
            entries = VersionHistoryParser.parseVersionHistory(c);
        } catch(XmlPullParserException xppe) {
            // TODO: So... what do we do in this case?  Right now, it'll just
            // return an empty list, which I guess is okay, given this should
            // never happen...
        }

        return newInstance(entries);
    }

    /**
     * Generates a new VersionHistoryDialogFragment, using an ArrayList of
     * version data already parsed elsewhere.
     *
     * @param entries an ArrayList of VersionEntries to display
     * @return a new Fragment
     */
    public static VersionHistoryDialogFragment newInstance(ArrayList<VersionEntry> entries) {
        VersionHistoryDialogFragment frag = new VersionHistoryDialogFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("entries", entries);

        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<VersionEntry> entries = getArguments().getParcelableArrayList("entries");

        // Rack 'em!
        return new AlertDialog.Builder(getActivity())
                .setAdapter(new EntryAdapter(getActivity(), entries), null)
                .setTitle(R.string.title_versionhistory)
                .setPositiveButton(getString(R.string.cool_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }
}
