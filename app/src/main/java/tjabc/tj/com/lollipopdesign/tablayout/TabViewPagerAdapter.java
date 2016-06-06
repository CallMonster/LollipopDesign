package tjabc.tj.com.lollipopdesign.tablayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> fragArr;
    private Context context;
    public TabViewPagerAdapter(Context context,FragmentManager fm,ArrayList<String> fragArr){
        super(fm);
        this.fragArr=fragArr;
        this.context=context;
    }

    @Override
    public int getCount() {
        return fragArr.size();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context,fragArr.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "null";
        if (position == 0) {
            title = "Tab1";
        } else if (position == 1) {
            title = "Tab2";
        } else if (position == 2) {
            title = "Tab3";
        }
        return title;
    }
}
