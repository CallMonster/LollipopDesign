package tjabc.tj.com.lollipopdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.adapter.ApplicationAdapter;
import tjabc.tj.com.lollipopdesign.entity.AppInfo;
import tjabc.tj.com.lollipopdesign.itemanimator.CustomItemAnimator;
import tjabc.tj.com.lollipopdesign.util.UploadHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static MainActivity instance;
    private static final int DRAWER_ITEM_OPEN_SOURCE = 10;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.swipe_container) SwipeRefreshLayout swipe_container;
    @Bind(R.id.list) RecyclerView mRecyclerView;
    @Bind(R.id.progressBar) ProgressBar progressBar;
    @Bind(R.id.fab_normal) FloatingActionButton fab_normal;
    private Drawer drawer;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static UploadHelper.UploadComponentInfoTask uploadComponentInfoTask = null;
    private ArrayList<AppInfo> applicationList= new ArrayList<AppInfo>();;
    private ApplicationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        instance=this;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        drawer = new DrawerBuilder(this)
                .withToolbar(toolbar)
                .addDrawerItems(new SwitchDrawerItem().withOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {

                    }
                }).withName(R.string.drawer_switch).withChecked(pref.getBoolean("autouploadenabled", false)))
                .addStickyDrawerItems(new SecondaryDrawerItem().withName(R.string.drawer_opensource)
                                .withIdentifier(DRAWER_ITEM_OPEN_SOURCE)
                                .withIcon(FontAwesome.Icon.faw_github)
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == DRAWER_ITEM_OPEN_SOURCE) {
                            new LibsBuilder()
                                    .withFields(R.string.class.getFields())
                                    .withVersionShown(true)
                                    .withLicenseShown(true)
                                    .withActivityTitle(getString(R.string.drawer_opensource))
                                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                    .start(MainActivity.this);
                        }
                        return false;
                    }
                })
                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState)
                .build();

        fab_normal.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_file_upload).color(Color.WHITE).actionBar());
        fab_normal.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new CustomItemAnimator());
        //mRecyclerView.setItemAnimator(new ReboundItemAnimator());

        mAdapter = new ApplicationAdapter(this,new ArrayList<AppInfo>());
        mRecyclerView.setAdapter(mAdapter);
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipe_container.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
        swipe_container.setRefreshing(true);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new InitializeApplicationsTask().execute();
            }
        });

        new InitializeApplicationsTask().execute();

        if (savedInstanceState != null) {
            if (uploadComponentInfoTask != null) {
                if (uploadComponentInfoTask.isRunning) {
                    uploadComponentInfoTask.showProgress(this);
                }
            }
        }

        //show progress
        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initView() {
        pref = getSharedPreferences("com.mikepenz.applicationreader", 0);
        editor = pref.edit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (drawer != null) {
            outState = drawer.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_normal:
                uploadComponentInfoTask = UploadHelper.getInstance(MainActivity.this, applicationList).uploadAll();

                Intent intent=new Intent(this,TabLayoutActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * A simple AsyncTask to load the list of applications and display them
     */
    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mAdapter.clearApplications();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            applicationList.clear();

            //Query the applications
            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
            for (ResolveInfo ri : ril) {
                applicationList.add(new AppInfo(MainActivity.this, ri));
            }
            Collections.sort(applicationList);//对list进行排序

            for (AppInfo appInfo : applicationList) {
                //load icons before shown. so the list is smoother
                appInfo.getIcon();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //handle visibility
            mRecyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            //set data for list
            mAdapter.addApplications(applicationList);
            swipe_container.setRefreshing(false);
            super.onPostExecute(result);
        }
    }

    /**
     * helper class to start the new detailActivity animated
     *
     * @param appInfo 
     * @param appIcon
     */
    public void animateActivity(AppInfo appInfo, View appIcon) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("appInfo", appInfo.getComponentName());
        ActivityOptionsCompat transitionActivityOptions =ActivityOptionsCompat
                .makeSceneTransitionAnimation(this,Pair.create((View) fab_normal,"fab"),
                        Pair.create(appIcon, "appIcon"));
        startActivity(i, transitionActivityOptions.toBundle());
    }

}
