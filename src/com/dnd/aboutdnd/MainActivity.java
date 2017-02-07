package com.dnd.aboutdnd;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Region;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final ThreadLocal<SlidingTabLayout> mSlidingTabLayout = new ThreadLocal<>();
    ViewPager mViewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    CoordinatorLayout coordinatorLayout;

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

}