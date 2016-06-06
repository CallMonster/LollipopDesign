package tjabc.tj.com.lollipopdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import java.lang.reflect.Field;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import tjabc.tj.com.lollipopdesign.R;

public class NavigationActivity extends Activity {

    @Bind(R.id.navigation) NavigationView navigation;
    @Bind(R.id.drawer_layout) DrawerLayout drawer_layout;

    private HashMap<Integer,Boolean> menuIdMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        menuIdMap=new HashMap<Integer,Boolean>();
        clearSelected();

        setDrawerLeftEdgeSize(this, drawer_layout, 0.3f);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_original:
                        if(menuIdMap.get(R.id.navigation_original)==false){
                            clearSelected();
                            menuIdMap.put(R.id.navigation_original,true);

                            item.setChecked(true); // 改变item选中状态
                            setTitle(item.getTitle()); // 改变页面标题，标明导航状态
                            drawer_layout.closeDrawers(); // 关闭导航菜单
                        }
                        break;
                    case R.id.navigation_library:
                        if(menuIdMap.get(R.id.navigation_library)==false){
                            clearSelected();
                            menuIdMap.put(R.id.navigation_library,true);

                            item.setChecked(true); // 改变item选中状态
                            setTitle(item.getTitle()); // 改变页面标题，标明导航状态
                            drawer_layout.closeDrawers(); // 关闭导航菜单
                        }
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 初始化选项
     */
    private void clearSelected(){
        menuIdMap.put(R.id.navigation_original, false);
        menuIdMap.put(R.id.navigation_library, false);
    }

    /**
     * 设置左侧热区的宽度
     * @param activity 当前的activity
     * @param drawerLayout 所设置宽度的drawerlayout
     * @param displayWidthPercentage 所于比例
     */
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }
}
