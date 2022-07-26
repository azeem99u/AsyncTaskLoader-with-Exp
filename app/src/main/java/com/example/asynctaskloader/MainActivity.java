package com.example.asynctaskloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String AZEEM = "AZEEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(AZEEM,"My name is Azeem");
            getSupportLoaderManager().initLoader(100,bundle,this).forceLoad();
           // getSupportLoaderManager().restartLoader(100,bundle,this).forceLoad();
            // use condition to next time call restartLoader

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(100);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        List<String> songs = Arrays.asList(DownloadList.Songs);

        return new MyLoader(this,args,songs);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Button button = findViewById(R.id.button);
        button.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    private static class MyLoader extends AsyncTaskLoader<String>{

        private static final String TAG = "mytask";
        private Bundle mArgs;
        private List<String> listOfSongs;
        public MyLoader(@NonNull Context context, Bundle args, List<String> songs) {
            super(context);
            mArgs = args;
            listOfSongs = songs;
        }

        @Nullable
        @Override
        public String loadInBackground() {
            Log.d(TAG, "loadInBackground:"+ mArgs.getString(AZEEM));
            for (String song: listOfSongs
                 ) {
                SystemClock.sleep(5000);
                Log.d(TAG, "song downloaded: "+ song);
            }

            Log.d(TAG, "Task Completed");
            return "task Completed :";
        }

        @Override
        public void deliverResult(@Nullable String data) {
            data = " and my real name is Ahmed";
            super.deliverResult(data);
        }

    }
}