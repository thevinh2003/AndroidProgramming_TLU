package com.example.demoasynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void>{
    Activity parentContext;

    public MyAsyncTask(Activity context){
        parentContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(parentContext, "Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i <= 100 ; i++) {
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressBar = parentContext.findViewById(R.id.progressBar);
        progressBar.setProgress(values[0]);
        TextView textView = parentContext.findViewById(R.id.tvPercent);
        textView.setText(values[0] + "%");
        Button btnStart = parentContext.findViewById(R.id.btnStart);
        btnStart.setText("Loading ...");
        btnStart.setEnabled(false);
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Button btnStart = parentContext.findViewById(R.id.btnStart);
        btnStart.setText("Start");
        btnStart.setEnabled(true);
        Toast.makeText(parentContext, "Finish", Toast.LENGTH_SHORT).show();
    }
}
