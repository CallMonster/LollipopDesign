package tjabc.tj.com.lollipopdesign.adapter;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tjabc.tj.com.lollipopdesign.R;
import tjabc.tj.com.lollipopdesign.synth.SynthFragment;

/**
 * Created by Lee on 2016/5/11.
 */
public class SynthAdapter extends FragmentPagerAdapter {

    private static final Section[] SECTIONS = {
            new Section("Tiffany", R.drawable.gril_a),
            new Section("Taeyeon", R.drawable.gril_b),
            new Section("Yoona", R.drawable.gril_c)
    };

    public SynthAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SynthFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return SECTIONS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < SECTIONS.length) {
            return SECTIONS[position].getTitle();
        }
        return null;
    }

    @DrawableRes
    public int getDrawable(int position) {
        if (position >= 0 && position < SECTIONS.length) {
            return SECTIONS[position].getDrawable();
        }
        return -1;
    }

    private static final class Section {
        private final String mTitle; // 标题
        @DrawableRes
        private final int mDrawable; // 图片

        public Section(String title, int drawable) {
            mTitle = title;
            mDrawable = drawable;
        }

        public String getTitle() {
            return mTitle;
        }

        public int getDrawable() {
            return mDrawable;
        }
    }

}
