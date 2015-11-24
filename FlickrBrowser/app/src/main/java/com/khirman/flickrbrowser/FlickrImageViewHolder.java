package com.khirman.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by stas on 11/14/15.
 */
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {
    protected ImageView trumbnail;
    protected TextView  title;

    public FlickrImageViewHolder(View view) {
        super(view);
        this.trumbnail = (ImageView)view.findViewById(R.id.trumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
    }
}
