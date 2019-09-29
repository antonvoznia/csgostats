package com.cti.statscsgo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cti.statscsgo.R;
import com.cti.statscsgo.SlidingTabs.SlidingTabLayout;

public class GunStats extends Fragment {

    public GunStats() {
        fr1 = GunStats1.getFragment();
        fr2 = GunStats1.getFragment();
        fr3 = GunStats1.getFragment();
    }

    private ViewPager viewPager;
    private SlidingTabLayout mTab;

    private Fragment fr1, fr2, fr3;


    ViewPager.OnPageChangeListener listener;

    //Check if is sorted
    boolean flagSort = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sliding_tabs, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        if (flagSort) {
            Bundle bnd = new Bundle();
            bnd.putInt("POS_FR", 1);
            fr1.setArguments(bnd);
            bnd = new Bundle();
            bnd.putInt("POS_FR", 2);
            fr2.setArguments(bnd);
            bnd = new Bundle();
            bnd.putInt("POS_FR", 3);
            fr3.setArguments(bnd);

            listener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            };
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager()));
            viewPager.setOnPageChangeListener(listener);
            mTab = (SlidingTabLayout) view.findViewById(R.id.tabs);
            mTab.setViewPager(viewPager);

            viewPager.setCurrentItem(0);
        }

        return view;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private String[] tabText = getResources().getStringArray(R.array.tabs_text);

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return fr1;
                case 1: return fr2;
                case 2: return fr3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabText[position];
        }
    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
