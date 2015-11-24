package com.khirman.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by stas on 11/7/15.
 */

enum DownloadStatus {IDLE, PROCESSING, NO_INITIALISED, FAILED_OR_EMPTY, OK}

public class GetRawData {
    private final String TAG = GetRawData.class.getSimpleName();
    private String mData;
    private String mRawUrl;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String rawUrl) {
        this.mRawUrl = rawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset(){
        this.mRawUrl = null;
        this.mData = null;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void execute(){
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String> {

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

            mData = s;
            Log.d(TAG,"Data returned is: " + mData);
            if(mData == null){
                if(mRawUrl==null){
                    mDownloadStatus = DownloadStatus.NO_INITIALISED;
                }
                else {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }

            }
            else{
                mDownloadStatus = DownloadStatus.OK;
            }


        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
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
            StringBuilder tempBuffer = new StringBuilder();
            BufferedReader reader = null;
            HttpURLConnection connection = null;

            if (params == null)
                return null;

            try {
                mDownloadStatus = DownloadStatus.PROCESSING;

                URL url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int response = connection.getResponseCode();
                Log.d("DownloadData", "Response code: " + response);

                InputStream is = connection.getInputStream();
                if (is == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    tempBuffer.append(line);
                }

                return tempBuffer.toString();

            } catch (MalformedURLException e) {
                Log.d(TAG, "Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                Log.d(TAG, "Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closeing stream");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
