package tjabc.tj.com.lollipopdesign.activity;

import android.animation.Animator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.entity.AppInfo;
import tjabc.tj.com.lollipopdesign.util.UploadHelper;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int SCALE_DELAY = 30;

    @Bind(R.id.row_container) LinearLayout row_container;
    @Bind(R.id.container) CoordinatorLayout container;
    @Bind(R.id.fab_normal) FloatingActionButton fab_normal;

    private AppInfo mAppInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.onBackPressed();
            }
        });

        fab_normal.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_file_upload).color(Color.WHITE).actionBar());
        fab_normal.setOnClickListener(this);

        for (int i = 1; i < row_container.getChildCount(); i++) {
            View rowView = row_container.getChildAt(i);
            rowView.animate().setStartDelay(100 + i * SCALE_DELAY).scaleX(1).scaleY(1);
        }

        ComponentName componentName = null;
        if (savedInstanceState != null) {
            componentName = savedInstanceState.getParcelable("appInfo");
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            componentName = (ComponentName) getIntent().getExtras().get("appInfo");
        }

        if (componentName != null) {
            Intent intent = new Intent();
            intent.setComponent(componentName);
            ResolveInfo app = getPackageManager().resolveActivity(intent, 0);
            mAppInfo = new AppInfo(this, app);
        }

        if (mAppInfo != null) {
            toolbar.setTitle(mAppInfo.getName());

            View view = row_container.findViewById(R.id.row_name);
            fillRow(view, "Application Name", mAppInfo.getName());
            ((ImageView) view.findViewById(R.id.appIcon)).setImageDrawable(mAppInfo.getIcon());

            view = row_container.findViewById(R.id.row_package_name);
            fillRow(view, "Package Name", mAppInfo.getPackageName());

            view = row_container.findViewById(R.id.row_activity);
            fillRow(view, "Activity", mAppInfo.getActivityName());

            view = row_container.findViewById(R.id.row_component_info);
            fillRow(view, "ComponentInfo", mAppInfo.getComponentInfo());

            view = row_container.findViewById(R.id.row_version);
            fillRow(view, "Version", mAppInfo.getVersionName() + " (" + mAppInfo.getVersionCode() + ")");

            view = row_container.findViewById(R.id.row_moments);
            fillRow(view, "Moments", "First installed: " + new Date(mAppInfo.getFirstInstallTime()) + "\nLast updated: " + new Date(mAppInfo.getLastUpdateTime()));

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("appInfo", mAppInfo.getComponentName());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_normal:
                UploadHelper.getInstance(DetailActivity.this, null).upload(mAppInfo);
                break;
        }
    }

    /**
     * fill the rows with some information
     * @param view parentView
     * @param title 标题
     * @param description 描述概要
     */
    public void fillRow(View view, final String title, final String description) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(description);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("AppInfo", description);
                clipboard.setPrimaryClip(clip);

                Snackbar.make(container, "Copied " + title, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        for (int i = row_container.getChildCount() - 1; i > 0; i--) {
            View rowView = row_container.getChildAt(i);
            ViewPropertyAnimator propertyAnimator = rowView.animate()
                    .setStartDelay((row_container.getChildCount() - 1 - i) * SCALE_DELAY)
                    .scaleX(0).scaleY(0);

            propertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        finish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
    }
}
