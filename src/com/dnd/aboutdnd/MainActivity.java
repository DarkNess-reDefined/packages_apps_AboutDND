package com.dnd.aboutdnd;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Region;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final ThreadLocal<SlidingTabLayout> mSlidingTabLayout = new ThreadLocal<>();
    ViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    CoordinatorLayout coordinatorLayout;
    ProgressDialog mProgressDialog;
    PowerManager.WakeLock mWakeLock;
    private final String LOG_DIR = "/data/data/com.dnd.aboutdnd/cache/changeLog.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarTextAppearance();
        this.initLayout();
        this.addContent();
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Opening DND Github", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.dnd_github)));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Snackbar.make(view,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initLayout() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(new SlidingTabAdapter());

        mSlidingTabLayout.set((SlidingTabLayout) findViewById(R.id.sliding_tabs));
        mSlidingTabLayout.get().setCustomTabView(R.layout.tab_head, R.id.toolbar_tab_txtCaption);
        mSlidingTabLayout.get().setSelectedIndicatorColors(getResources().getColor(R.color.colorPrimaryDark));
        mSlidingTabLayout.get().setViewPager(mViewPager);
    }

    public void addTab(int layout, String tabTitle) {
        this.addTab(layout, tabTitle, -1);
    }

    public void addTab(int layout, String tabTitle, int position) {
        SlidingTabAdapter mTabs = (SlidingTabAdapter) mViewPager.getAdapter();
        mTabs.addView(getLayoutInflater().inflate(layout, null), tabTitle, position);
        mTabs.notifyDataSetChanged();
        mSlidingTabLayout.get().populateTabStrip();
    }

    public void addContent() {
        addTab(R.layout.content_about, "Darkness");
        addTab(R.layout.content_team, "Team");
        addTab(R.layout.content_maintainers, "Maintainers");
    }

    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showChanges(View view) {
        if(isNetworkAvailable()) {
            final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
            downloadTask.execute(getResources().getString(R.string.dnd_changelog));

            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    downloadTask.cancel(true);
                }
            });
        } else {
            Snackbar.make(coordinatorLayout,R.string.no_Internet, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void underDevelopment(View view) {
        Snackbar.make(view, "Feature is Under Development", Snackbar.LENGTH_SHORT).show();
    }
	
	public void darknessGPlus(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://plus.google.com/u/3/communities/102951559675870137271"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }
	
	public void darknessTelegram(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://t.me/DarknessReDefined"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }
	
    public void akAbhishekGit(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/AKabhishek"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void akAbhishekGPlus(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://plus.google.com/101368184428720999404"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }

    }

    public void dark_knightGit(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/DarkKnight6499"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void kishPatelGit(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/kishpatel1998"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void rohanKhuranaGit(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/rk2810"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void rohanKhuranaGPlus(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://plus.google.com/100048469267627096696"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void monojPGit(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/iammanojrocker"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }

    public void monojPGPlus(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://plus.google.com/109824935415535687098"));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(coordinatorLayout,"Please install a browser and try again", Snackbar.LENGTH_LONG).show();
        }
    }
	
	class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        StringBuilder log;

        public DownloadTask(Context context) {
            this.context = context;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context,"Network error", Toast.LENGTH_LONG).show();
            else {
                try {
                    String strLine="";
                    log = new StringBuilder();
                    FileReader fReader = new FileReader(LOG_DIR);
                    BufferedReader bReader = new BufferedReader(fReader);

                    /** Reading the contents of the file , line by line */
                    while( (strLine=bReader.readLine()) != null  ){
                        log.append(strLine+"\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                changelogActivity cl = new changelogActivity();
                Intent intent = new Intent(MainActivity.this, cl.getClass());
                intent.putExtra("log", log.toString());
                MainActivity.this.startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                File outputDir = context.getCacheDir();
                output = new FileOutputStream(LOG_DIR);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
    }

}