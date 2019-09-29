package com.cti.statscsgo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cti.statscsgo.ListAdapter.CustomListAdapter;
import com.cti.statscsgo.R;

public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    private String[] list_items = { "Total stats", "Weapon stats", "Maps stats", "Last match"
    };
    private int[] img_items = { R.drawable.total_stats, R.drawable.weapon, R.drawable.map,
            R.drawable.last_match
    };

    private Fragment frTotalStats, frGunStats, frMapsStats, frLastMatch, savedFragment;

    //ListView - menu
    private ListView leftMenu;

    //For switching fragments in StatsActivity
    private FragmentManager fm;
    private FragmentTransaction ft;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frTotalStats = new TotalStats();
        frGunStats = new GunStats();
        frMapsStats = new MapStats();
        frLastMatch = new LastMatch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        leftMenu = (ListView) view.findViewById(R.id.listView_menu);
        leftMenu.setAdapter(new CustomListAdapter(getActivity(), list_items, img_items));

        savedFragment = frTotalStats;

        leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        /*ft.setTransition(
                                FragmentTransaction.TRANSIT_FRAGMENT_OPEN
                        ).replace(R.id.content_frame, frTotalStats).commit();*/
                        savedFragment = frTotalStats;
                        break;
                    case 1:
                        /*ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.content_frame, frGunStats).commit();*/
                        savedFragment = frGunStats;
                        break;
                    case 2:
                        //ft.replace(R.id.content_frame, frMapsStats).commit();
                        savedFragment = frMapsStats;
                        break;
                    case 3:
                        savedFragment = frLastMatch;
                }
                drawerLayout.closeDrawers();
            }
        });
        return view;
    }

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolBar) {
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolBar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                fm = getActivity().getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.content_frame, savedFragment).commit();
            }

            /*@Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6)
                    toolBar.setAlpha(1-slideOffset);
            }*/
        };

        this.drawerLayout.setDrawerListener(drawerToggle);
        this.drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

}
