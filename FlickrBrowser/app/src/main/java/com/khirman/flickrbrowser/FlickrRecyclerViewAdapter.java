package com.khirman.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by stas on 11/14/15.
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter <FlickrImageViewHolder> {

    private final static String LOG_TAG= FlickrRecyclerViewAdapter.class.getSimpleName();

    private List<Photo> mPhotosList=null;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> mPhotosList) {
        this.mPhotosList = mPhotosList;
        this.mContext = context;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        return flickrImageViewHolder;
    }


    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        Photo photoItem = mPhotosList.get(position);

        Log.d(LOG_TAG,"Processing item -->" + position);

        Picasso.with(mContext).load(photoItem.getmImage()).
                error(R.drawable.placeholder).
                placeholder(R.drawable.placeholder).
                into(holder.trumbnail);
        holder.title.setText(photoItem.getmTitle());
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return (null != mPhotosList ? mPhotosList.size():0);
    }

    public void loadNewData(List<Photo> newPhotos){
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }
}
