package com.khirman.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 11/9/15.
 */
public class GetFlickrJsonData extends GetRawData {
    final private String TAG = GetFlickrJsonData.class.getSimpleName();

    private List<Photo> mPhotos;
    private Uri mDistinationUri;

    public GetFlickrJsonData(String searchCreteria, boolean matchAll) {
        super(null);
        mPhotos = new ArrayList<Photo>();
        createAndUpdateUri(searchCreteria,matchAll);
    }

    public void execute(){
        Log.d(TAG, "Executing URL: "+mDistinationUri.toString());
        super.setmRawUrl(mDistinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        downloadJsonData.execute(mDistinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCreteria, boolean matchAll) {
        final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM="tags";
        final String TAGMODE_PARAM="tagmode";
        final String FORMAT_PARAM="format";
        final String NO_JSON_CALLBACK_PARAM="nojsoncallback";

        mDistinationUri = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(TAGS_PARAM,searchCreteria).
                appendQueryParameter(TAGMODE_PARAM,matchAll? "ALL":"ANY").
                appendQueryParameter(FORMAT_PARAM,"json").
                appendQueryParameter(NO_JSON_CALLBACK_PARAM,"1").
                build();


        return  mDistinationUri != null;
    }

    public List<Photo> getmPhotos() {
        return mPhotos;
    }

    private void processResult() {
        if(getmDownloadStatus() != DownloadStatus.OK){
            Log.e(TAG, "Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS="items";
        final String FLICKR_TITLE="title";
        final String FLICKR_MEDIA="media";
        final String FLICKR_PHOTO_URL="m";
        final String FLICKR_AUTHOR="author";
        final String FLICKR_AUTHOR_ID="author_id";
        final String FLICKR_LINK="link";
        final String FLICKR_TAGS="tags";

        try {
            JSONObject jsonObject= new JSONObject(getmData());
            JSONArray  itemsArray = jsonObject.getJSONArray(FLICKR_ITEMS);

            for(int i=0; i< itemsArray.length();i++){
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title=jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String author_id = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photo_url = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photoObject = new Photo(title,author, author_id, link, tags, photo_url);

                this.mPhotos.add(photoObject);
            }

            for(Photo singlePhoto :mPhotos){
                Log.d(TAG,singlePhoto.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"Error parsing JSON");
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            processResult();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {
            // dirty hack as in original we not suppose to call inner class execute directly
            String[] par = { mDistinationUri.toString()};
            return super.doInBackground(par);
        }
    }

}
